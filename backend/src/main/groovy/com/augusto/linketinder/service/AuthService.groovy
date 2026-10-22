package com.augusto.linketinder.service

import com.augusto.linketinder.dao.CandidatoDAO
import com.augusto.linketinder.dao.DaoFactory
import com.augusto.linketinder.dao.EmpresaDAO
import com.sun.net.httpserver.HttpExchange

import javax.crypto.SecretKeyFactory
import javax.crypto.spec.PBEKeySpec
import java.security.MessageDigest
import java.security.SecureRandom
import java.util.concurrent.ConcurrentHashMap

class AuthService {
    private static final int ITERACOES = 120_000
    private static final SecureRandom random = new SecureRandom()
    static long validadeTokenMs = 8 * 60 * 60 * 1000L

    private static final Map<String, Map> tokens = new ConcurrentHashMap<>()

    public static final int MAX_FALHAS = 5
    static final long BLOQUEIO_MS = 5 * 60 * 1000L
    private static final Map<String, Map> falhas = new ConcurrentHashMap<>()

    private final CandidatoDAO candidatoDao
    private final EmpresaDAO empresaDao

    AuthService(CandidatoDAO candidatoDao, EmpresaDAO empresaDao) {
        this.candidatoDao = candidatoDao
        this.empresaDao = empresaDao
    }

    AuthService() {
        this(DaoFactory.getCandidatoDAO(), DaoFactory.getEmpresaDAO())
    }

    static String hash(String senha) {
        byte[] salt = new byte[16]
        random.nextBytes(salt)
        byte[] hash = pbkdf2(senha, salt, ITERACOES)
        return "${ITERACOES}:${salt.encodeBase64()}:${hash.encodeBase64()}"
    }

    static boolean verify(String senha, String armazenado) {
        def partes = armazenado?.split(':')
        if (senha == null || partes?.length != 3) {
            return false
        }
        byte[] esperado = partes[2].decodeBase64()
        byte[] calculado = pbkdf2(senha, partes[1].decodeBase64(), partes[0].toInteger())
        return MessageDigest.isEqual(esperado, calculado)
    }

    private static byte[] pbkdf2(String senha, byte[] salt, int iteracoes) {
        def spec = new PBEKeySpec(senha.toCharArray(), salt, iteracoes, 256)
        return SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256").generateSecret(spec).encoded
    }

    Map login(String cpf, String cnpj, String senha) {
        def tipo = cpf ? 'candidato' : 'empresa'
        def chave = "${tipo}:${cpf ?: cnpj}".toString()
        def agora = System.currentTimeMillis()

        falhas.values().removeIf { it.ultima < agora - BLOQUEIO_MS }
        if (falhas[chave]?.quantidade >= MAX_FALHAS) {
            return [bloqueado: true]
        }

        Map credenciais = cpf ? candidatoDao.findCredenciaisByCpf(cpf) : (cnpj ? empresaDao.findCredenciaisByCnpj(cnpj) : null)
        if (!credenciais || !verify(senha, credenciais.senha as String)) {
            def registro = falhas.computeIfAbsent(chave) { [quantidade: 0] }
            registro.quantidade = (registro.quantidade as int) + 1
            registro.ultima = agora
            return null
        }
        falhas.remove(chave)
        tokens.values().removeIf { it.expira < System.currentTimeMillis() }

        def token = UUID.randomUUID().toString()
        tokens[token] = [tipo: tipo, id: credenciais.id, expira: System.currentTimeMillis() + validadeTokenMs]
        return [token: token, tipo: tipo, id: credenciais.id]
    }

    static Map getSession(HttpExchange exchange) {
        def header = exchange.requestHeaders.getFirst("Authorization")
        def sessao = header?.startsWith("Bearer ") ? tokens[header.substring(7)] : null
        if (sessao && sessao.expira < System.currentTimeMillis()) {
            tokens.remove(header.substring(7))
            return null
        }
        return sessao
    }

    static int checkAccess(HttpExchange exchange, String tipo, Integer id) {
        def sessao = getSession(exchange)
        if (!sessao) {
            return 401
        }
        if (sessao.tipo != tipo || (id != null && sessao.id != id)) {
            return 403
        }
        return 0
    }
}

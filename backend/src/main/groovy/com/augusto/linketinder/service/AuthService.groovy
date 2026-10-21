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

    // shortcut: tokens vivem só em memória e somem quando o servidor reinicia; trocar por JWT ou tabela se precisar persistir
    private static final Map<String, Map> tokens = new ConcurrentHashMap<>()

    private final CandidatoDAO candidatoDao
    private final EmpresaDAO empresaDao

    AuthService(CandidatoDAO candidatoDao, EmpresaDAO empresaDao) {
        this.candidatoDao = candidatoDao
        this.empresaDao = empresaDao
    }

    AuthService() {
        this(DaoFactory.getCandidatoDAO(), DaoFactory.getEmpresaDAO())
    }

    // Formato salvo no banco: iteracoes:saltBase64:hashBase64
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

    // Retorna [token, tipo, id] ou null se as credenciais forem inválidas
    Map login(String cpf, String cnpj, String senha) {
        def tipo = cpf ? 'candidato' : 'empresa'
        Map credenciais = cpf ? candidatoDao.findCredenciaisByCpf(cpf) : (cnpj ? empresaDao.findCredenciaisByCnpj(cnpj) : null)
        if (!credenciais || !verify(senha, credenciais.senha as String)) {
            return null
        }
        def token = UUID.randomUUID().toString()
        tokens[token] = [tipo: tipo, id: credenciais.id]
        return [token: token, tipo: tipo, id: credenciais.id]
    }

    // Retorna [tipo, id] do usuário logado ou null se o token for ausente/inválido
    static Map getSession(HttpExchange exchange) {
        def header = exchange.requestHeaders.getFirst("Authorization")
        return header?.startsWith("Bearer ") ? tokens[header.substring(7)] : null
    }

    // 0 = liberado, 401 = sem login, 403 = logado mas não é o dono (tipo/id diferentes)
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

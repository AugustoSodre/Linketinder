package com.augusto.linketinder.control

import com.augusto.linketinder.dao.CandidatoDAO
import com.augusto.linketinder.dao.CompetenciaDAO
import com.augusto.linketinder.dao.EmpresaDAO
import com.augusto.linketinder.dao.VagaDAO
import com.augusto.linketinder.dao.connectionProvider.ConnectionProvider
import com.augusto.linketinder.dao.connectionProvider.ConnectionProviderFactory
import com.augusto.linketinder.model.Competencia
import com.augusto.linketinder.model.Vaga
import com.augusto.linketinder.model.pessoa.Candidato
import com.augusto.linketinder.model.pessoa.Empresa

class UpdateController {

    private final CandidatoDAO candidatoDao
    private final EmpresaDAO empresaDao
    private final VagaDAO vagaDao
    private final CompetenciaDAO competenciaDao

    UpdateController() {
        this(*createDefaultDaos())
    }

    UpdateController(CandidatoDAO candidatoDao,
                     EmpresaDAO empresaDao,
                     VagaDAO vagaDao,
                     CompetenciaDAO competenciaDao) {
        this.candidatoDao = Objects.requireNonNull(candidatoDao, "candidatoDao")
        this.empresaDao = Objects.requireNonNull(empresaDao, "empresaDao")
        this.vagaDao = Objects.requireNonNull(vagaDao, "vagaDao")
        this.competenciaDao = Objects.requireNonNull(competenciaDao, "competenciaDao")
    }

    List<Candidato> listarCandidatos() {
        return candidatoDao.listAll()
    }

    Map<String, String> camposCandidato() {
        return [
                'nome'     : 'nome',
                'email'    : 'email',
                'estado'   : 'estado',
                'cep'      : 'cep',
                'idade'    : 'idade',
                'cpf'      : 'cpf',
                'descrição': 'descricao',
                'senha'    : 'senha'
        ]
    }

    void atualizarCandidatoCampo(int id, String coluna, Object novoValor) {
        candidatoDao.updateField(id, coluna, novoValor)
    }

    List<Empresa> listarEmpresas() {
        return empresaDao.listAll()
    }

    Map<String, String> camposEmpresa() {
        return [
                'nome'     : 'nome',
                'email'    : 'email',
                'estado'   : 'estado',
                'cep'      : 'cep',
                'país'     : 'pais',
                'CNPJ'     : 'cnpj',
                'descrição': 'descricao',
                'senha'    : 'senha'
        ]
    }

    void atualizarEmpresaCampo(int id, String coluna, Object novoValor) {
        empresaDao.updateField(id, coluna, novoValor)
    }

    List<Vaga> listarVagas() {
        return vagaDao.listAll()
    }

    Map<String, String> camposVaga() {
        return [
                'ID da empresa': 'id_empresa',
                'nome'        : 'nome',
                'descrição'   : 'descricao',
                'cidade'      : 'cidade',
                'estado'      : 'estado'
        ]
    }

    void atualizarVagaCampo(int id, String coluna, Object novoValor) {
        vagaDao.updateField(id, coluna, novoValor)
    }

    List<Competencia> listarCompetencias() {
        return competenciaDao.listAll()
    }

    void atualizarCompetenciaNome(int idCompetencia, String novoNome) {
        competenciaDao.updateName(idCompetencia, novoNome)
    }

    String listarRelacionamentos(String objeto) {
        return competenciaDao.listRelations(objeto)
    }

    void adicionarRelacionamento(String objeto, int idCompetencia, int idObjeto) {
        competenciaDao.addRelation(objeto, idCompetencia, idObjeto)
    }

    void removerRelacionamento(String objeto, int idCompetencia, int idObjeto) {
        competenciaDao.removeRelation(objeto, idCompetencia, idObjeto)
    }

    private static List createDefaultDaos() {
        ConnectionProvider sharedProvider = ConnectionProviderFactory.getProvider()
        CompetenciaDAO competenciaDao = new CompetenciaDAO(sharedProvider)
        VagaDAO vagaDao = new VagaDAO(sharedProvider, competenciaDao)
        CandidatoDAO candidatoDao = new CandidatoDAO(sharedProvider, competenciaDao)
        EmpresaDAO empresaDao = new EmpresaDAO(sharedProvider, competenciaDao, vagaDao)
        return [candidatoDao, empresaDao, vagaDao, competenciaDao]
    }
}

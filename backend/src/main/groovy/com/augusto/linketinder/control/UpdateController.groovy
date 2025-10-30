package com.augusto.linketinder.control

import com.augusto.linketinder.dao.DAO_Candidato
import com.augusto.linketinder.dao.DAO_Competencia
import com.augusto.linketinder.dao.DAO_Empresa
import com.augusto.linketinder.dao.DAO_Vaga
import com.augusto.linketinder.dao.DataSource
import com.augusto.linketinder.model.Competencia
import com.augusto.linketinder.model.Vaga
import com.augusto.linketinder.model.pessoa.Candidato
import com.augusto.linketinder.model.pessoa.Empresa

import java.util.Objects

class UpdateController {

    private final DAO_Candidato candidatoDao
    private final DAO_Empresa empresaDao
    private final DAO_Vaga vagaDao
    private final DAO_Competencia competenciaDao

    UpdateController() {
        this(*createDefaultDaos())
    }

    UpdateController(DAO_Candidato candidatoDao,
                     DAO_Empresa empresaDao,
                     DAO_Vaga vagaDao,
                     DAO_Competencia competenciaDao) {
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
        DataSource sharedDataSource = new DataSource()
        DAO_Competencia competenciaDao = new DAO_Competencia(sharedDataSource)
        DAO_Vaga vagaDao = new DAO_Vaga(sharedDataSource, competenciaDao)
        DAO_Candidato candidatoDao = new DAO_Candidato(sharedDataSource, competenciaDao)
        DAO_Empresa empresaDao = new DAO_Empresa(sharedDataSource, competenciaDao, vagaDao)
        return [candidatoDao, empresaDao, vagaDao, competenciaDao]
    }
}

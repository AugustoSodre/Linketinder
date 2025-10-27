package com.augusto.linketinder.control

import com.augusto.linketinder.DAO.DAO_Candidato
import com.augusto.linketinder.DAO.DAO_Competencia
import com.augusto.linketinder.DAO.DAO_Empresa
import com.augusto.linketinder.DAO.DAO_Vaga
import com.augusto.linketinder.model.Competencia
import com.augusto.linketinder.model.Vaga
import com.augusto.linketinder.model.pessoa.Candidato
import com.augusto.linketinder.model.pessoa.Empresa

class UpdateController {

    private final DAO_Candidato candidatoDao = new DAO_Candidato()
    private final DAO_Empresa empresaDao = new DAO_Empresa()
    private final DAO_Vaga vagaDao = new DAO_Vaga()
    private final DAO_Competencia competenciaDao = new DAO_Competencia()

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
}

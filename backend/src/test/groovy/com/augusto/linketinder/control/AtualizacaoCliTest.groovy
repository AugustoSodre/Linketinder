package com.augusto.linketinder.control

import com.augusto.linketinder.dao.DaoFactory
import com.augusto.linketinder.model.pessoa.Empresa
import com.augusto.linketinder.service.EmpresaService
import com.augusto.linketinder.service.InputService
import org.junit.jupiter.api.Test

import static org.junit.jupiter.api.Assertions.assertEquals

class AtualizacaoCliTest {

    static InputService entrada(String texto) {
        def inputService = new InputService()
        inputService.setBr(new BufferedReader(new StringReader(texto)))
        return inputService
    }

    static List executa(String texto) {
        def chamadas = []
        new AtualizacaoCli(entrada(texto)).executar([[id: 7, nome: 'Jim']], ['nome': 'nome', 'idade': 'idade', 'país': 'pais']) { id, coluna, valor ->
            chamadas << [id, coluna, valor]
        }
        return chamadas
    }

    @Test
    void atualizaOCampoEscolhidoComOValorLidoPeloInputCerto() {
        assertEquals([[7, 'idade', 30]], executa("7\n2\n30\n"))
        assertEquals([[7, 'pais', 'BR']], executa("7\n3\nbr\n"))
    }

    @Test
    void cancelaComZeroOuOpcaoInvalida() {
        assertEquals([], executa("0\n"))
        assertEquals([], executa("7\n0\n"))
        assertEquals([], executa("7\n9\n"))
    }

    @Test
    void todosOsInputsPadraoLeemDoMesmoStdin() {
        assert new InputService().inputServiceCore.br.is(new InputService().inputServiceCore.br)
    }

    @Test
    void empresaControllerAtualizaEmpresaPelaCli() {
        def chamadas = []
        def service = new EmpresaService(DaoFactory.getEmpresaDAO()) {
            @Override
            List<Empresa> getEmpresaList() { [new Empresa(id: 3, nome: 'Tech')] }

            @Override
            void updateEmpresa(int id, String coluna, Object valor) { chamadas << [id, coluna, valor] }
        }
        def controller = new EmpresaController(null, null, service, null, entrada("3\n1\nTech Corp\n"))

        controller.updateEmpresa()

        assertEquals([[3, 'nome', 'Tech Corp']], chamadas)
    }
}


import { createFormListeners, generateCompetenciasText } from "../helpers/formHelper"

export function renderFormCandidato(app: HTMLDivElement){

    let text

    text = `
      <h2>Cadastro Candidato</h2>

      <form action="" id="form-cadastro-candidato" class="form-cadastro">
        <div class="form-item">
          <label for="nome">Nome do Usuário:</label>
          <input type="text" id="nome" name="nome" required>
        </div>
        
        <div class="form-item">
          <label for="email">Email:</label>
          <input type="email" id="email" name="email" required>
        </div>
        
        <div class="form-item">
          <label for="estado">Estado:</label>
          <input type="text" id="estado" name="estado" required>
        </div>
        
        <div class="form-item">
          <label for="cep">CEP:</label>
          <input type="text" id="cep" name="cep" required>
        </div>
        
        <div class="form-item">
          <label for="descricao">Descrição:</label>
          <textarea id="descricao" name="descricao" rows="4" required></textarea>
        </div>
        
        <div class="form-item">
          <label for="idade">Idade:</label>
          <input type="number" id="idade" name="idade" min="0" max="120" required>
        </div>
        
        <div class="form-item">
          <label for="cpf">CPF:</label>
          <input type="text" id="cpf" name="cpf" required>
        </div>
        
        <div class="form-item">
            <label for="competencias-candidato">Competências:</label>
    `

    text += generateCompetenciasText()

    text += `
            </div>

            <div class="form-item"></div>
                <button id="submit-candidato" type="submit">Cadastrar Candidato</button>
            </div>

        </div>
      </form>
    `

    app.innerHTML = text

    createFormListeners()

}

export function renderFormEmpresa(app: HTMLDivElement){

    let text: string = `
        <h2>Cadastro Empresa</h2>

        <form action="" id="form-cadastro-empresa" class="form-cadastro">

        <div id="container-form-empresa">
            <div class="form-item">
            <h3>Sobre a Empresa</h3>
            <label for="nome">Nome da Empresa:</label>
            <input type="text" id="nome" name="nome" required>
            </div>
            
            <div class="form-item">
            <label for="email">Email:</label>
            <input type="email" id="email" name="email" required>
            </div>
            
            <div class="form-item">
            <label for="estado">Estado:</label>
            <input type="text" id="estado" name="estado" required>
            </div>
            
            <div class="form-item">
            <label for="cep">CEP:</label>
            <input type="text" id="cep" name="cep" required>
            </div>
            
            <div class="form-item">
            <label for="descricao">Descrição:</label>
            <textarea id="descricao" name="descricao" rows="4" required></textarea>
            </div>
            
            <div class="form-item">
            <label for="pais">País:</label>
            <input type="text" id="pais" name="pais" required>
            </div>
            
            <div class="form-item">
            <label for="cnpj">CNPJ:</label>
            <input type="text" id="cnpj" name="cnpj" required>
            </div>
        </div>

        <div id="container-form-empresa">
            <div class="form-item">
            <h3>Sobre a Vaga</h3>
            <label for="nome-vaga">Título da Vaga:</label>
            <input type="text" id="nome-vaga" name="nome-vaga" required>
            </div>

            <div class="form-item">
            <label for="descricao-vaga">Descrição da Vaga:</label>
            <input type="text" id="descricao-vaga" name="descricao-vaga" required>
            </div>

            <div class="form-item">
            <label for="competencias-vaga">Competências da Vaga:</label>
    `

    text += generateCompetenciasText()

    text += `
            </div>

            <div class="form-item"></div>
                <button id="submit-empresa" type="submit">Cadastrar Empresa</button>
            </div>

        </div>
      </form>
    `

    app.innerHTML = text

    createFormListeners()
    
}


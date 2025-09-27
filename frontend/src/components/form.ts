import { handleFormCandidato } from "../handlers/formHandler"

export function renderFormCandidato(app: HTMLDivElement){
    app.innerHTML = `
      <h2>Cadastro Candidato</h2>

      <form action="POST" id="form-cadastro">
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
          <input type="text" id="estado" name="estado">
        </div>
        
        <div class="form-item">
          <label for="cep">CEP:</label>
          <input type="text" id="cep" name="cep">
        </div>
        
        <div class="form-item">
          <label for="descricao">Descrição:</label>
          <textarea id="descricao" name="descricao" rows="4"></textarea>
        </div>
        
        <div class="form-item">
          <label for="idade">Idade:</label>
          <input type="number" id="idade" name="idade" min="0" max="120">
        </div>
        
        <div class="form-item">
          <label for="cpf">CPF:</label>
          <input type="text" id="cpf" name="cpf">
        </div>
        
        <div class="form-item"></div>
          <button id="submit-candidato" type="submit">Cadastrar Candidato</button>
        </div>

      </form>
    `

    document.getElementById("form-cadastro")?.addEventListener("submit", (e) => {
        e.preventDefault()

        handleFormCandidato()
    })

}

export function renderFormEmpresa(app: HTMLDivElement){

}
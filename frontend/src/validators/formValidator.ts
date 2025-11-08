import { listCandidatos, listEmpresas } from "../storage/lists"

// Centralized regex constants (input & normalized forms)
export const NAME_REGEX = /^[a-zA-ZÀ-ÿ\s]+$/
export const EMAIL_REGEX = /^[\w.]+@\w+\.\w+(\.\w{2,3})?$/
export const STATE_REGEX = /^[A-Z]{2}$/
export const CEP_INPUT_REGEX = /^\d{5}-?\d{3}$/
export const CEP_NORMALIZED_REGEX = /^\d{8}$/
export const CPF_INPUT_REGEX = /^\d{3}\.?\d{3}\.?\d{3}-?\d{2}$/
export const CPF_NORMALIZED_REGEX = /^\d{11}$/
export const CNPJ_INPUT_REGEX = /^\d{2}\.?\d{3}\.?\d{3}\/??\d{4}-?\d{2}$/
export const CNPJ_NORMALIZED_REGEX = /^\d{14}$/
export const COUNTRY_REGEX = /^[A-Z]{2}$/

// Normalization helpers
export function normalizeCPF(raw: string): string {
  return raw.replaceAll('.', '').replaceAll('-', '').trim()
}

export function normalizeCNPJ(raw: string): string {
  return raw.replaceAll('.', '').replaceAll('-', '').replaceAll('/', '').trim()
}

export function normalizeCEP(raw: string): string {
  return raw.replaceAll('-', '').trim()
}

// Storage helpers
export function isCpfUnique(cpf: string): boolean {
  return !listCandidatos.some(c => ((c as any).cpf ?? '') === cpf)
}

export function isCnpjUnique(cnpj: string): boolean {
  return !listEmpresas.some(e => ((e as any).cnpj ?? '') === cnpj)
}

export function findCandidatoByCpf(cpf: string) {
  return listCandidatos.find(c => ((c as any).cpf ?? '') === cpf)
}

export function findEmpresaByCnpj(cnpj: string) {
  return listEmpresas.find(e => ((e as any).cnpj ?? '') === cnpj)
}

// DOM helpers
export function getFieldValue(id: string): string {
  return (document.getElementById(id) as HTMLInputElement)?.value ?? ''
}

// Backwards-compatible validators (thin wrappers around centralized utilities)
export function getValidNome(): string | null {
  const nome = getFieldValue('nome')
  if (!nome) { alert('Nome Inválido! Tente novamente!'); return null }
  if (!NAME_REGEX.test(nome)) { alert('Nome com caractere Inválido! Tente novamente!'); return null }
  return nome
}

export function getValidEmail(): string | null {
  const email = getFieldValue('email')
  if (!EMAIL_REGEX.test(email)) { alert('Email Inválido! Tente novamente!'); return null }
  return email
}

export function getValidEstado(): string | null {
  const estado = getFieldValue('estado')
  if (!STATE_REGEX.test(estado)) { alert('Estado Inválido! Tente novamente!'); return null }
  return estado
}

export function getValidCEP(): string | null {
  const cepRaw = getFieldValue('cep')
  if (!CEP_INPUT_REGEX.test(cepRaw)) { alert('CEP Inválido! Tente novamente!'); return null }
  const cep = normalizeCEP(cepRaw)
  if (!CEP_NORMALIZED_REGEX.test(cep)) { alert('CEP Inválido! Tente novamente!'); return null }
  return cep
}

export function getValidDescricao(): string | null {
  const descricao = getFieldValue('descricao')
  if (!descricao || descricao.length === 0) { alert('Descrição Inválida! Tente novamente!'); return null }
  return descricao
}

export function getValidIdade(): number | null {
  const raw = getFieldValue('idade')
  const idade = Number(raw)
  if (Number.isNaN(idade) || idade < 0 || idade > 120) { alert('Idade inválida! Tente novamente!'); return null }
  return idade
}

export function getValidCPF(): string | null {
  const cpfRaw = getFieldValue('cpf')
  if (!CPF_INPUT_REGEX.test(cpfRaw)) { alert('CPF Inválido! Tente novamente!'); return null }
  const cpf = normalizeCPF(cpfRaw)
  if (!CPF_NORMALIZED_REGEX.test(cpf)) { alert('CPF Inválido! Tente novamente!'); return null }
  if (!isCpfUnique(cpf)) { alert('CPF Já Cadastrado! Tente novamente!'); return null }
  return cpf
}

export function getValidPais(): string | null {
  const pais = getFieldValue('pais')
  if (!COUNTRY_REGEX.test(pais)) { alert('País Inválido! Tente novamente!'); return null }
  return pais
}

export function getValidCNPJ(): string | null {
  const cnpjRaw = getFieldValue('cnpj')
  if (!CNPJ_INPUT_REGEX.test(cnpjRaw)) { alert('CNPJ Inválido! Tente novamente!'); return null }
  const cnpj = normalizeCNPJ(cnpjRaw)
  if (!CNPJ_NORMALIZED_REGEX.test(cnpj)) { alert('CNPJ Inválido! Tente novamente!'); return null }
  if (!isCnpjUnique(cnpj)) { alert('CNPJ Já Cadastrado! Tente novamente!'); return null }
  return cnpj
}

export function getValidNomeVaga(): string | null {
  const nome = getFieldValue('nome-vaga')
  if (!NAME_REGEX.test(nome)) { alert('Nome com caractere Inválido! Tente novamente!'); return null }
  return nome
}

export function getValidDescricaoVaga(): string | null {
  const descricao = getFieldValue('descricao-vaga')
  if (!descricao || descricao.length === 0) { alert('Descrição Inválida! Tente novamente!'); return null }
  return descricao
}

export function getValidLoginIdentification(idHTML: string): string | null {
  const identification = getFieldValue(idHTML)
  if (idHTML === 'cnpj-login-empresa') {
	if (!CNPJ_INPUT_REGEX.test(identification)) { alert('CNPJ Inválido! Tente novamente!'); return null }
	return normalizeCNPJ(identification)
  }
  if (idHTML === 'cpf-login-candidato') {
	if (!CPF_INPUT_REGEX.test(identification)) { alert('CPF Inválido! Tente novamente!'); return null }
	return normalizeCPF(identification)
  }
  return identification
}

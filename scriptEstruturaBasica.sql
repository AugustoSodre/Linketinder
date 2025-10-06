CREATE DATABASE linketinder;

CREATE TABLE competencia (
  id SERIAL PRIMARY KEY,
  nome VARCHAR(255) NOT NULL
);

CREATE TABLE candidato (
  id SERIAL PRIMARY KEY,
  nome VARCHAR(255) NOT NULL,
  email VARCHAR(255) NOT NULL,
  estado VARCHAR(2) NOT NULL,
  cep VARCHAR(8) NOT NULL,
  idade INTEGER NOT NULL,
  cpf VARCHAR(11) NOT NULL UNIQUE,
  descricao TEXT NOT NULL,
  senha VARCHAR(6) NOT NULL
);

CREATE TABLE empresa (
  id SERIAL PRIMARY KEY,
  nome VARCHAR(255) NOT NULL,
  email VARCHAR(255) NOT NULL,
  estado VARCHAR(2) NOT NULL,
  cep VARCHAR(8) NOT NULL,
  pais VARCHAR(2) NOT NULL,
  cnpj VARCHAR(14) NOT NULL UNIQUE,
  descricao TEXT NOT NULL,
  senha VARCHAR(6) NOT NULL,
);

CREATE TABLE vaga (
  id SERIAL PRIMARY KEY,
  id_empresa INTEGER NOT NULL,
  titulo VARCHAR(255) NOT NULL,
  descricao TEXT NOT NULL,
  local VARCHAR(255) NOT NULL,
  CONSTRAINT fk_vaga_empresa 
    FOREIGN KEY (id_empresa) 
    REFERENCES empresa (id) 
    ON DELETE CASCADE 
    ON UPDATE CASCADE
);

CREATE TABLE competencia_vaga (
  id SERIAL PRIMARY KEY,
  id_competencia INTEGER NOT NULL,
  id_vaga INTEGER NOT NULL,
  CONSTRAINT fk_competencia_vaga_competencia 
    FOREIGN KEY (id_competencia) 
    REFERENCES competencia (id) 
    ON DELETE CASCADE 
    ON UPDATE CASCADE,
  CONSTRAINT fk_competencia_vaga_vaga 
    FOREIGN KEY (id_vaga) 
    REFERENCES vaga (id) 
    ON DELETE CASCADE 
    ON UPDATE CASCADE
);

CREATE TABLE competencia_candidato (
  id SERIAL PRIMARY KEY,
  id_competencia INTEGER NOT NULL,
  id_candidato INTEGER NOT NULL,
  CONSTRAINT fk_competencia_candidato_competencia 
    FOREIGN KEY (id_competencia) 
    REFERENCES competencia (id) 
    ON DELETE CASCADE 
    ON UPDATE CASCADE,
  CONSTRAINT fk_competencia_candidato_candidato 
    FOREIGN KEY (id_candidato) 
    REFERENCES candidato (id) 
    ON DELETE CASCADE 
    ON UPDATE CASCADE
);

CREATE TABLE competencia_empresa (
  id SERIAL PRIMARY KEY,
  id_competencia INTEGER NOT NULL,
  id_empresa INTEGER NOT NULL,
  CONSTRAINT fk_competencia_empresa_competencia 
    FOREIGN KEY (id_competencia) 
    REFERENCES competencia (id) 
    ON DELETE CASCADE 
    ON UPDATE CASCADE,
  CONSTRAINT fk_competencia_empresa_empresa 
    FOREIGN KEY (id_empresa) 
    REFERENCES empresa (id) 
    ON DELETE CASCADE 
    ON UPDATE CASCADE
);

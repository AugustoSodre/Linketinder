INSERT INTO competencia(nome) VALUES ('Angular');
INSERT INTO competencia(nome) VALUES ('React');
INSERT INTO competencia(nome) VALUES ('Django');
INSERT INTO competencia(nome) VALUES ('Springboot');
INSERT INTO competencia(nome) VALUES ('Linux');
INSERT INTO competencia(nome) VALUES ('PowerBI');

INSERT INTO candidato (nome, email, estado, cep, idade, cpf, descricao, senha) 
VALUES ('Jim Halpert', 'jim@office.com', 'SP', '18503001', 32, '12345678901', 'Vendedor experiente com habilidades em pegadinhas.', '120000:wkTZYLbouaRU2lVn3jtk1g==:FbfksaGG2sH/yIsCYKfVh8MzKRhyw7lXlk/xcw8e0Tk=');

INSERT INTO candidato (nome, email, estado, cep, idade, cpf, descricao, senha) 
VALUES ('Pam Beesly', 'pam@office.com', 'SP', '18503002', 30, '23456789012', 'Designer gráfica e artista.', '120000:GfivkyCwN7b0ADYZ4hL2sA==:ginOH/JrIgUpbzF80FB6i7t4YWM0hihKwx5rMqR3cak=');

INSERT INTO candidato (nome, email, estado, cep, idade, cpf, descricao, senha) 
VALUES ('Dwight Schrute', 'dwight@schrutefarms.com', 'SP', '18503003', 38, '34567890123', 'Assistente do gerente regional.', '120000:GS5bc78c4m1B7QtVJILcSA==:nXx2RSfuhyvse2zgk/iiJd2XcnvRLoFXQViSEdPGGio=');

INSERT INTO candidato (nome, email, estado, cep, idade, cpf, descricao, senha) 
VALUES ('Ryan Howard', 'ryan@office.com', 'DF', '10001001', 28, '45678901234', 'Empreendedor, gênio.', '120000:ZASTtbX04cMigc4TjFbbeg==:qWNZ+f1rW4eLMZSj2JWBX8W0QYP+304Nv5l2OFfLIcc=');

INSERT INTO candidato (nome, email, estado, cep, idade, cpf, descricao, senha) 
VALUES ('Angela Martin', 'angela@office.com', 'SP', '18503004', 35, '56789012345', 'Amo gatos e organização.', '120000:Lx26B9VpnmPvxI38arsGtQ==:YN6S0gwTzWQ5KNI1hCheUYi2plmXKCVnXaOzPaNEb90=');

INSERT INTO empresa (nome, email, estado, cep, pais, cnpj, descricao, senha) 
VALUES ('Dunder Mifflin', 'contato@dundermifflin.com', 'SP', '18503100', 'US', '12345678000101', 'Empresa de papel líder da Pensilvânia.', '120000:wSo7BkoxoIS7VQewJxVcZA==:0nNY1AdfZJQhWpWNZ0ipmBxpJfdQQUrc29Rl1sPO0cA=');

INSERT INTO empresa (nome, email, estado, cep, pais, cnpj, descricao, senha) 
VALUES ('Michael Scott Paper Company', 'michael@mspc.com', 'SP', '18503200', 'US', '23456789000102', 'Pequena equipe, grandes oportunidades de crescimento.', '120000:1k7hjdTjVk7z6dMe9o+PfQ==:N9gw61LjWpUR1kZiO+HPKmx24MQhW8FI8C2z2MlycF8=');

INSERT INTO empresa (nome, email, estado, cep, pais, cnpj, descricao, senha) 
VALUES ('Sabre International', 'rh@sabre.com', 'DF', '32801001', 'US', '34567890000103', 'Empresa de software empresarial.', '120000:pOTq3p4zdcV9f5tGo0JkYw==:tAHHbQ78wRbPDno4Zt1M4RolI0OGc9fvEaH1npqWHA4=');

INSERT INTO empresa (nome, email, estado, cep, pais, cnpj, descricao, senha) 
VALUES ('Vance Refrigeration', 'bob@vancerefrigeration.com', 'SP', '18503300', 'US', '45678901000104', 'Empresa de refrigeração.', '120000:37LX9dUGjJyspaHzewmbNA==:vjrteAJZiwHhfxrKZDAMmQbqVT32K/vJP64PeR/UeOQ=');

INSERT INTO empresa (nome, email, estado, cep, pais, cnpj, descricao, senha) 
VALUES ('Base Investimentos', 'base@investimentos.com', 'DF', '70670707', 'BR', '56789012000105', 'Startup de construção civil.', '120000:mxaO5ME66IVoZGJboyR/3g==:MUJNJkl8n1JvbHavscpPci7ygLxR3+Qk8CZisyNguHQ=');

INSERT INTO vaga (id_empresa, nome, descricao, cidade, estado)
VALUES (1, 'Desenvolvedor Frontend', 'Buscamos desenvolvedor para criar interfaces incríveis com React.', 'Scranton', 'PA');

INSERT INTO vaga (id_empresa, nome, descricao, cidade, estado)
VALUES (2, 'Analista de BI', 'Profissional para análise de dados e criação de dashboards no PowerBI.', 'Stamford', 'CT');

INSERT INTO vaga (id_empresa, nome, descricao, cidade, estado)
VALUES (3, 'Desenvolvedor Full-Stack', 'Vaga para desenvolvedor com experiência em Angular e Django.', 'New York', 'NY');

INSERT INTO vaga (id_empresa, nome, descricao, cidade, estado)
VALUES (4, 'Engenheiro DevOps', 'Profissional para gerenciar infraestrutura Linux e deployments.', 'Scranton', 'PA');

INSERT INTO vaga (id_empresa, nome, descricao, cidade, estado)
VALUES (5, 'Desenvolvedor Backend', 'Desenvolvedor experiente em Springboot e Django.', 'Philadelphia', 'PA');

INSERT INTO competencia_candidato (id_competencia, id_candidato) VALUES (2, 1);
INSERT INTO competencia_candidato (id_competencia, id_candidato) VALUES (1, 1);

INSERT INTO competencia_candidato (id_competencia, id_candidato) VALUES (2, 2);
INSERT INTO competencia_candidato (id_competencia, id_candidato) VALUES (6, 2);

INSERT INTO competencia_candidato (id_competencia, id_candidato) VALUES (5, 3);
INSERT INTO competencia_candidato (id_competencia, id_candidato) VALUES (6, 3);
INSERT INTO competencia_candidato (id_competencia, id_candidato) VALUES (3, 3);

INSERT INTO competencia_candidato (id_competencia, id_candidato) VALUES (1, 4);
INSERT INTO competencia_candidato (id_competencia, id_candidato) VALUES (3, 4);
INSERT INTO competencia_candidato (id_competencia, id_candidato) VALUES (2, 4);

INSERT INTO competencia_candidato (id_competencia, id_candidato) VALUES (6, 5);
INSERT INTO competencia_candidato (id_competencia, id_candidato) VALUES (4, 5);

INSERT INTO competencia_vaga (id_competencia, id_vaga) VALUES (2, 1);

INSERT INTO competencia_vaga (id_competencia, id_vaga) VALUES (6, 2);

INSERT INTO competencia_vaga (id_competencia, id_vaga) VALUES (1, 3);
INSERT INTO competencia_vaga (id_competencia, id_vaga) VALUES (3, 3);

INSERT INTO competencia_vaga (id_competencia, id_vaga) VALUES (5, 4);
INSERT INTO competencia_vaga (id_competencia, id_vaga) VALUES (4, 4);

INSERT INTO competencia_vaga (id_competencia, id_vaga) VALUES (4, 5);
INSERT INTO competencia_vaga (id_competencia, id_vaga) VALUES (3, 5);

INSERT INTO competencia_empresa (id_competencia, id_empresa) VALUES (2, 1);
INSERT INTO competencia_empresa (id_competencia, id_empresa) VALUES (1, 1);

INSERT INTO competencia_empresa (id_competencia, id_empresa) VALUES (6, 2);

INSERT INTO competencia_empresa (id_competencia, id_empresa) VALUES (1, 3);
INSERT INTO competencia_empresa (id_competencia, id_empresa) VALUES (3, 3);
INSERT INTO competencia_empresa (id_competencia, id_empresa) VALUES (4, 3);

INSERT INTO competencia_empresa (id_competencia, id_empresa) VALUES (5, 4);
INSERT INTO competencia_empresa (id_competencia, id_empresa) VALUES (4, 4);

INSERT INTO competencia_empresa (id_competencia, id_empresa) VALUES (2, 5);
INSERT INTO competencia_empresa (id_competencia, id_empresa) VALUES (3, 5);

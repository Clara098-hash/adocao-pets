-- Criar o banco de dados
CREATE DATABASE sistema_adocao;


-- Criar a tabela de Pets
CREATE TABLE Pets (
    id SERIAL PRIMARY KEY,               -- Identificador único do pet
    nome VARCHAR(100) NOT NULL,          -- Nome do pet
    especie VARCHAR(50) NOT NULL,           -- Espécie (Cachorro, Gato, etc.)
    raca VARCHAR(100),                   -- Raça do pet (se aplicável)
    idade INTEGER NOT NULL,              -- Idade do pet em anos
    descricao TEXT,                      -- Descrição detalhada do pet
    foto BYTEA,                        -- Foto do pet em formato binário
    status VARCHAR(20) DEFAULT 'Disponível' -- Status do pet: 'Disponível' ou 'Adotado'
);


-- Criar a tabela de Adotantes
CREATE TABLE Adotantes (
    id SERIAL PRIMARY KEY,         -- Identificador único do adotante, auto-incrementado
    nome VARCHAR(100) NOT NULL,    -- Nome do adotante, campo obrigatório
    telefone VARCHAR(20),          -- Número de telefone (opcional)
    email VARCHAR(100),            -- Endereço de e-mail (opcional)
    endereco TEXT                  -- Endereço completo do adotante (opcional)
);


-- Criar a tabela de Adoções
CREATE TABLE Adocao (
    id SERIAL PRIMARY KEY,  -- Identificador único da adoção
    pet_id INTEGER NOT NULL REFERENCES Pets(id) ON DELETE CASCADE,  -- Relacionamento com o pet
    adotante_id INTEGER NOT NULL REFERENCES Adotantes(id) ON DELETE CASCADE,  -- Relacionamento com o adotante
    data_adocao TIMESTAMP DEFAULT CURRENT_TIMESTAMP  -- Data e hora da adoção (automaticamente preenchida com o timestamp atual)
);

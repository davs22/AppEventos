-- victor: esse arquivo cria o DB se necessario modificação faça

--deletar as tabelas 
DROP TABLE IF EXISTS Inscricao;
DROP TABLE IF EXISTS Eventos;
DROP TABLE IF EXISTS Palestrante;
DROP TABLE IF EXISTS Participante;

-- Tabela de Participantes
CREATE TABLE Participante (
    id INTEGER PRIMARY KEY,
    nome TEXT NOT NULL,
    sexo TEXT,
    email TEXT,
    celular TEXT
    senha TEXT NOT NULL,
    tipo TEXT NOT NULL
);

-- Tabela de Palestrantes
CREATE TABLE Palestrante (
    id INTEGER PRIMARY KEY,
    nome TEXT NOT NULL,
    curriculo TEXT,
    areaAtuacao TEXT
);

-- Tabela de Eventos
CREATE TABLE Eventos (
    id INTEGER PRIMARY KEY,
    nome TEXT NOT NULL,
    descricao TEXT,
    data DATE,
    local TEXT,
    palestranteId INTEGER,
    capacidade INTEGER,
    FOREIGN KEY (palestranteId) REFERENCES Palestrante(id),
    
);

-- Tabela associativa entre Evento e Participante (Inscrição)
CREATE TABLE Inscricao (
    id INTEGER PRIMARY KEY,
    id_eventos INTEGER NOT NULL,
    id_participante INTEGER NOT NULL,
    FOREIGN KEY (id_eventos) REFERENCES Eventos(id) ON DELETE CASCADE,
    FOREIGN KEY (id_participante) REFERENCES Participante(id) ON DELETE CASCADE
);



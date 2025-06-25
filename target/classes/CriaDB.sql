DROP TABLE IF EXISTS Inscricao;
DROP TABLE IF EXISTS Eventos;
DROP TABLE IF EXISTS Palestrante;
DROP TABLE IF EXISTS Participante;

CREATE TABLE Participante (
    id INTEGER PRIMARY KEY,
    nome TEXT NOT NULL,
    sexo TEXT,
    email TEXT,
    celular TEXT
    senha TEXT NOT NULL,
    tipo TEXT NOT NULL DEFAULT 'participante'
);

CREATE TABLE Palestrante (
    id INTEGER PRIMARY KEY,
    nome TEXT NOT NULL,
    curriculo TEXT,
    areaAtuacao TEXT
);

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

CREATE TABLE Inscricao (
    id INTEGER PRIMARY KEY,
    id_eventos INTEGER NOT NULL,
    id_participante INTEGER NOT NULL,
    FOREIGN KEY (id_eventos) REFERENCES Eventos(id) ON DELETE CASCADE,
    FOREIGN KEY (id_participante) REFERENCES Participante(id) ON DELETE CASCADE
);



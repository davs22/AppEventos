-- victor: esse arquivo cria o DB se necessario modificação faça

-- Tabela de Participantes
CREATE TABLE Participante (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    nome TEXT NOT NULL,
    sexo TEXT,
    email TEXT,
    celular TEXT
);

-- Tabela de Palestrantes
CREATE TABLE Palestrante (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    nome TEXT NOT NULL,
    curriculo TEXT,
    areaAtuacao TEXT
);

-- Tabela de Eventos (sem campo palestranteId, pois haverá tabela associativa depois)
CREATE TABLE Eventos (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    nome TEXT NOT NULL,
    descricao TEXT,
    data DATE,
    local TEXT
);

-- Tabela associativa entre Evento e Participante (Inscrição)
CREATE TABLE Inscricao (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    id_eventos INTEGER NOT NULL,
    id_participante INTEGER NOT NULL,
    FOREIGN KEY (id_eventos) REFERENCES Eventos(id) ON DELETE CASCADE,
    FOREIGN KEY (id_participante) REFERENCES Participante(id) ON DELETE CASCADE
);

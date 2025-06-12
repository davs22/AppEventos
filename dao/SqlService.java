package dao;

public class SqlService {

    public String EventoSQL(String tipo) {
        if (tipo == null) {
            throw new IllegalArgumentException("Tipo de parâmetro não informado.");
        }

        switch (tipo.toLowerCase()) {
            case "id":
                return "SELECT * FROM Eventos WHERE id = ?";
            case "nome":
                return "SELECT * FROM Eventos WHERE nome = ?";
            case "data":
                return "SELECT * FROM Eventos WHERE data = ?";
            case "local":
                return "SELECT * FROM Eventos WHERE local = ?";
            case "palestranteid":
                return "SELECT * FROM Eventos WHERE palestranteId = ?";
            case "capacidade":
                return "SELECT * FROM Eventos WHERE capacidade = ?";
            default:
                throw new IllegalArgumentException("Tipo de parâmetro não suportado: " + tipo);
        }
    }

    public String ParticipanteSQL(String tipo){
        if (tipo == null) {
            throw new IllegalArgumentException("Tipo de parâmetro não informado.");
        }
    switch (tipo.toLowerCase()) {
        case "id":
            return "SELECT * FROM Participante WHERE id = ?";
        case "nome":
            return "SELECT * FROM Participante WHERE nome = ?";
        case "sexo":
            return "SELECT * FROM Participante WHERE sexo = ?";
        case "email":
            return "SELECT * FROM Participante WHERE email = ?";
        case "celular":
            return "SELECT * FROM Participante WHERE celular = ?";
        case "tipo":
            return "SELECT * FROM Participante WHERE tipo = ? AND tipo <> 'organizador';";
        default:
            throw new IllegalArgumentException("Tipo de parâmetro não suportado: " + tipo);
        }

    }
}

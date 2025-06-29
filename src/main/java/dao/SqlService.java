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
                return "SELECT * FROM Eventos WHERE nome LIKE ?";
            case "descricao":
                return "SELECT * FROM Eventos WHERE descricao LIKE ?";
            case "data":
                return "SELECT * FROM Eventos WHERE data LIKE ?";
            case "local":
                return "SELECT * FROM Eventos WHERE local LIKE ?";
            case "palestranteid":
                return "SELECT * FROM Eventos WHERE palestranteId = ?";
            case "capacidade":
                return "SELECT * FROM Eventos WHERE capacidade = ?";
            default:
                throw new IllegalArgumentException("Tipo de parâmetro não suportado: " + tipo);
        }
    }

    public String ParticipanteSQL(String tipo) {
        switch (tipo.toLowerCase()) {
            case "id":
                return "SELECT * FROM Participante WHERE id = ?";
            case "nome":
                return "SELECT * FROM Participante WHERE nome LIKE ?";
            case "sexo":
                return "SELECT * FROM Participante WHERE sexo LIKE ?";
            case "email":
                return "SELECT * FROM Participante WHERE email LIKE ?";
            case "celular":
                return "SELECT * FROM Participante WHERE celular LIKE ?";
            case "tipo":
                return "SELECT * FROM Participante WHERE tipo LIKE ?";
            default:
                return "SELECT * FROM Participante";
        }
    }

    public String PalestranteSQL(String tipo) {
        switch (tipo.toLowerCase()) {
            case "id":
                return "SELECT * FROM Palestrante WHERE id = ?";
            case "nome":
                return "SELECT * FROM Palestrante WHERE nome LIKE ?";
            case "curriculo":
                return "SELECT * FROM Palestrante WHERE curriculo LIKE ?";
            case "areaatuacao":
                return "SELECT * FROM Palestrante WHERE areaAtuacao LIKE ?";
            default:
                return "SELECT * FROM Palestrante";
        }
    }
}




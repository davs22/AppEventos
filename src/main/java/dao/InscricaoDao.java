package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import table.Eventos;
import table.Inscricao;
import util.SQLiteConnection;

public class InscricaoDao {

    private SQLiteConnection sqlConn;

    public InscricaoDao(SQLiteConnection sqlConn) {
        this.sqlConn = sqlConn;
    }

    public String inscreverParticipante(int idParticipante, int idEventos) {
        try {
            Connection conn = this.sqlConn.connect();

            String verificaSql = "SELECT COUNT(*) FROM Inscricao WHERE id_participante = ? AND id_eventos = ?";
            PreparedStatement verificaStmt = conn.prepareStatement(verificaSql);
            verificaStmt.setInt(1, idParticipante);
            verificaStmt.setInt(2, idEventos);
            ResultSet rs = verificaStmt.executeQuery();

            if (rs.next() && rs.getInt(1) > 0) {
                rs.close();
                verificaStmt.close();
                this.sqlConn.close(conn);
                return "Participante já inscrito neste evento!";
            }

            String sql = "INSERT INTO Inscricao (id_participante, id_eventos) VALUES (?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, idParticipante);
            stmt.setInt(2, idEventos);
            int resultado = stmt.executeUpdate();

            stmt.close();
            this.sqlConn.close(conn);

            return resultado > 0 ? "Inscrição realizada com sucesso!" : "Erro ao realizar inscrição.";
        } catch (Exception e) {
            e.printStackTrace();
            return "Erro ao realizar inscrição.";
        }
    }

    public List<Inscricao> listarInscricoesComDetalhes() {
    List<Inscricao> lista = new ArrayList<>();
    String sql = """
        SELECT i.id, i.id_participante, i.id_eventos,
               p.nome AS nome_participante,
               e.nome AS nome_evento
        FROM Inscricao i
        JOIN Participante p ON i.id_participante = p.id
        JOIN Eventos e ON i.id_eventos = e.id
    """;

    try (Connection conn = this.sqlConn.connect();
         PreparedStatement stmt = conn.prepareStatement(sql);
         ResultSet rs = stmt.executeQuery()) {

        while (rs.next()) {
            Inscricao insc = new Inscricao();
            insc.setId(rs.getInt("id"));
            insc.setIdParticipante(rs.getInt("id_participante"));
            insc.setIdEvento(rs.getInt("id_eventos"));
            insc.setNomeParticipante(rs.getString("nome_participante"));
            insc.setNomeEvento(rs.getString("nome_evento"));
            lista.add(insc);
        }

    } catch (Exception e) {
        e.printStackTrace();
    }
    return lista;
}

    public boolean verificarInscricao(int idParticipante, int idEvento) {
        try {
            Connection conn = this.sqlConn.connect();
            String sql = "SELECT COUNT(*) FROM Inscricao WHERE id_participante = ? AND id_eventos = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, idParticipante);
            stmt.setInt(2, idEvento);
            ResultSet rs = stmt.executeQuery();

            boolean isInscrito = rs.next() && rs.getInt(1) > 0;
            stmt.close();
            this.sqlConn.close(conn);
            return isInscrito;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public String excluirInscricao(int idParticipante, int idEvento) {
        try {
            Connection conn = this.sqlConn.connect();
            String sql = "DELETE FROM Inscricao WHERE id_participante = ? AND id_eventos = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, idParticipante);
            stmt.setInt(2, idEvento);
            int resultado = stmt.executeUpdate();

            stmt.close();
            this.sqlConn.close(conn);

            return resultado > 0 ? "Inscrição excluída com sucesso!" : "Inscrição não encontrada.";
        } catch (Exception e) {
            e.printStackTrace();
            return "Erro ao excluir inscrição.";
        }
    }

    public String solicitarCertificado(int idParticipante, int idEvento) {
        try {
            Connection conn = this.sqlConn.connect();

            String sql = "SELECT p.nome AS nome_participante, e.nome AS nome_eventos " +
                    "FROM Inscricao i " +
                    "JOIN Participante p ON i.id_participante = p.id " +
                    "JOIN Eventos e ON i.id_eventos = e.id " +
                    "WHERE i.id_participante = ? AND i.id_eventos = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, idParticipante);
            stmt.setInt(2, idEvento);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String nomeParticipante = rs.getString("nome_participante");
                String nomeEvento = rs.getString("nome_eventos");

                stmt.close();
                this.sqlConn.close(conn);

                return gerarCertificadoRepresentativo(nomeParticipante, nomeEvento);
            } else {
                stmt.close();
                this.sqlConn.close(conn);
                return "Participante não inscrito neste evento. Não é possível gerar certificado.";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "Erro ao gerar certificado.";
        }
    }

    private String gerarCertificadoRepresentativo(String nomeParticipante, String nomeEvento) {
        return "\n---------------------------\n" +
                "        CERTIFICADO        \n" +
                "---------------------------\n" +
                "Certificamos que " + nomeParticipante +
                " participou com êxito do evento:\n\"" + nomeEvento + "\"\n\n" +
                "Data: " + java.time.LocalDate.now() + "\n" +
                "Assinatura: ____________________\n";
    }

    public List<Eventos> listarEventosPorParticipante(int idParticipante) {
    List<Eventos> eventos = new ArrayList<>();

    String sql = """
        SELECT e.id, e.nome, e.descricao, e.data, e.local, e.capacidade, e.palestrante_id
        FROM Inscricao i
        JOIN Eventos e ON i.id_eventos = e.id
        WHERE i.id_participante = ?
    """;

    try (Connection conn = this.sqlConn.connect();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setInt(1, idParticipante);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            Eventos evento = new Eventos();
            evento.setId(rs.getInt("id"));
            evento.setNome(rs.getString("nome"));
            evento.setDescricao(rs.getString("descricao"));
            evento.setData(rs.getDate("data"));
            evento.setLocal(rs.getString("local"));
            evento.setCapacidade(rs.getInt("capacidade"));
            evento.setPalestranteId(rs.getInt("palestrante_id"));

            eventos.add(evento);
        }

    } catch (Exception e) {
        e.printStackTrace();
    }

    return eventos;
}
    public List<Inscricao> listarInscricoesComDetalhesPorParticipante(int idParticipante) {
    List<Inscricao> inscricoes = new ArrayList<>();

    String sql = """
        SELECT i.id, i.id_participante, p.nome AS nome_participante,
               i.id_eventos AS id_evento, e.nome AS nome_evento
        FROM inscricao i
        JOIN participante p ON i.id_participante = p.id
        JOIN eventos e ON i.id_eventos = e.id
        WHERE i.id_participante = ?
    """;

    try (Connection conn = this.sqlConn.connect();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setInt(1, idParticipante);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            Inscricao ins = new Inscricao();
            ins.setId(rs.getInt("id"));
            ins.setIdParticipante(rs.getInt("id_participante"));
            ins.setNomeParticipante(rs.getString("nome_participante"));
            ins.setIdEvento(rs.getInt("id_evento")); // ← usa o alias corretamente
            ins.setNomeEvento(rs.getString("nome_evento"));
            inscricoes.add(ins);
        }

    } catch (SQLException e) {
        System.out.println("Erro ao buscar inscrições por participante: " + e.getMessage());
    }

    return inscricoes;
}

public int contarInscritosNoEvento(int idEvento) {
    String sql = "SELECT COUNT(*) FROM Inscricao WHERE id_eventos = ?";
    try (Connection conn = this.sqlConn.connect();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setInt(1, idEvento);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            return rs.getInt(1);
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }
    return 0;
}


}

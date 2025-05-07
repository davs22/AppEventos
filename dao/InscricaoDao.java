package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import table.Inscricao;
import util.SQLiteConnection;

public class InscricaoDao {

    private SQLiteConnection sqlConn;

    // Construtor da classe, passando a conexão com o banco de dados
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


    public List<Inscricao> listarInscricoesPorParticipante(int idParticipante) {
        List<Inscricao> inscricoes = new ArrayList<>();
        try {
            Connection conn = this.sqlConn.connect();
            String sql = "SELECT id, id_participante, id_eventos FROM Inscricao WHERE id_participante = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, idParticipante);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                int idEvento = rs.getInt("id_eventos");
                inscricoes.add(new Inscricao(id, idParticipante, idEvento));
            }

            stmt.close();
            this.sqlConn.close(conn);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return inscricoes;
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

            return resultado > 0 ? "Inscrição excluída com sucesso!" : "Erro ao excluir inscrição.";
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

    // Exemplo de método para gerar ID único (se não usar auto_increment no banco)
    private int getNewId() {
        try {
            Connection conn = this.sqlConn.connect();
            String sql = "SELECT MAX(id) AS max_id FROM Inscricao";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            int id = 1;
            if (rs.next()) {
                id = rs.getInt("max_id") + 1;
            }
            stmt.close();
            this.sqlConn.close(conn);
            return id;
        } catch (Exception e) {
            e.printStackTrace();
            return 1;
        }
    }
}

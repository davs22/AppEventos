package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.ArrayList;
import java.util.List;

import java.text.SimpleDateFormat;

import table.Eventos;
import util.SQLiteConnection;

public class EventosDao {
    private SQLiteConnection sqlConn;

    public EventosDao() {
        this.sqlConn = new SQLiteConnection();
    }

    public List<Eventos> listarEventos() throws SQLException {
        List<Eventos> eventosList = new ArrayList<>();
        String sql = "SELECT * FROM Eventos";

        try (Connection conn = this.sqlConn.connect();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Eventos evento = new Eventos();
                evento.setId(rs.getInt("id"));
                evento.setNome(rs.getString("nome"));
                evento.setDescricao(rs.getString("descricao"));
                evento.setData(rs.getDate("data"));
                evento.setLocal(rs.getString("local"));
                evento.setPalestranteId(rs.getInt("palestranteId"));
                evento.setCapacidade(rs.getInt("capacidade"));
                eventosList.add(evento);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar eventos: " + e.getMessage());
            throw e;
        }

        return eventosList;
    }

    public List<Eventos> listarPorParametro(String tipo, String valor) {
        List<Eventos> lista = new ArrayList<>();

        try {
            SqlService sqlsService = new SqlService();
            String sql = sqlsService.EventoSQL(tipo);

            Connection conn = this.sqlConn.connect();
            PreparedStatement pstm = conn.prepareStatement(sql);

            if (tipo.equalsIgnoreCase("id") ||
                tipo.equalsIgnoreCase("palestranteId") ||
                tipo.equalsIgnoreCase("capacidade")) {

                pstm.setInt(1, Integer.parseInt(valor));

            } else if (tipo.equalsIgnoreCase("data")) {
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                java.util.Date utilDate = sdf.parse(valor);
                java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
                pstm.setDate(1, sqlDate);

            } else {
                pstm.setString(1, "%" + valor + "%");
            }

            ResultSet rs = pstm.executeQuery();

            while (rs.next()) {
                Eventos evento = new Eventos(
                    rs.getInt("id"),
                    rs.getString("nome"),
                    rs.getString("descricao"),
                    rs.getDate("data"),
                    rs.getString("local"),
                    rs.getInt("palestranteId"),
                    rs.getInt("capacidade"));
                lista.add(evento);
            }

            rs.close();
            pstm.close();
            this.sqlConn.close(conn);

        } catch (SQLException e) {
            System.err.println("Erro ao listar eventos por parâmetro: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.err.println("Erro de conversão para inteiro: " + e.getMessage());
        } catch (java.text.ParseException e) {
            System.err.println("Erro ao converter data. Use o formato dd-MM-yyyy: " + valor);
        }

        return lista;
    }

    public void criarEvento(Eventos evento) throws SQLException {
        String sql = "INSERT INTO Eventos (nome, descricao, data, local, capacidade, palestranteId) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = this.sqlConn.connect();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, evento.getNome());
            stmt.setString(2, evento.getDescricao());
            stmt.setDate(3, new java.sql.Date(evento.getData().getTime()));
            stmt.setString(4, evento.getLocal());
            stmt.setInt(5, evento.getCapacidade());
            stmt.setInt(6, evento.getPalestranteId());

            int affectedRows = stmt.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        evento.setId(generatedKeys.getInt(1));
                    }
                }
            }
        }
    }

    public void editarEvento(Eventos evento) throws SQLException {
        String sql = "UPDATE Eventos SET nome = ?, descricao = ?, data = ?, local = ?, palestranteId = ?, capacidade = ? WHERE id = ?";

        try (Connection conn = this.sqlConn.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, evento.getNome());
            stmt.setString(2, evento.getDescricao());
            stmt.setDate(3, new java.sql.Date(evento.getData().getTime()));
            stmt.setString(4, evento.getLocal());
            stmt.setInt(5, evento.getPalestranteId());
            stmt.setInt(6, evento.getCapacidade());
            stmt.setInt(7, evento.getId());

            stmt.executeUpdate();
        }
    }

    public void excluirEvento(int eventoId) throws SQLException {
        String sql = "DELETE FROM Eventos WHERE id = ?";

        try (Connection conn = this.sqlConn.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, eventoId);
            stmt.executeUpdate();
        }
    }

    public void associarPalestrante(Eventos evento) throws SQLException {
        String sql = "UPDATE Eventos SET palestranteId = ? WHERE id = ?";

        try (Connection conn = this.sqlConn.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, evento.getPalestranteId());
            stmt.setInt(2, evento.getId());

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new SQLException("A associação falhou. Nenhum evento foi atualizado.");
            }
        }
    }

    public boolean eventoExiste(int eventoId) throws SQLException {
        String sql = "SELECT COUNT(*) FROM Eventos WHERE id = ?";

        try (Connection conn = this.sqlConn.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, eventoId);
            ResultSet rs = stmt.executeQuery();
            return rs.next() && rs.getInt(1) > 0;
        }
    }

    public List<Eventos> buscarEventosPorNome(String nome) throws Exception {
        List<Eventos> lista = new ArrayList<>();
        String sql = "SELECT * FROM eventos WHERE nome LIKE ?";
        
        try (Connection conn = this.sqlConn.connect();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "%" + nome + "%");
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
                lista.add(evento);
            }

        } catch (SQLException e) {
            throw new Exception("Erro ao buscar eventos: " + e.getMessage(), e);
        }

        return lista;
    }

    public Eventos buscarPorId(int id) {
    Eventos evento = null;
    String sql = "SELECT * FROM eventos WHERE id = ?";

    try (Connection conn = sqlConn.connect();
         PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setInt(1, id);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            evento = new Eventos();
            evento.setId(rs.getInt("id"));
            evento.setNome(rs.getString("nome"));
            evento.setDescricao(rs.getString("descricao"));
            evento.setData(rs.getDate("data"));
            evento.setLocal(rs.getString("local"));
            evento.setCapacidade(rs.getInt("capacidade"));
            evento.setPalestranteId(rs.getInt("palestrante_id"));
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }

    return evento;
}

}

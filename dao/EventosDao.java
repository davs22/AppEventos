package dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import table.Eventos;
import util.SQLiteConnection;

public class EventosDao {
    private SQLiteConnection sqlConn;

    public EventosDao() {
        this.sqlConn = new SQLiteConnection();
    }

    public List<Eventos> listarEventos() throws SQLException {
        List<Eventos> eventosList = new ArrayList<>();
        String sql = "SELECT * FROM eventos";

        try (Connection conn = this.sqlConn.connect();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Eventos evento = new Eventos();
                evento.setNome(rs.getString("nome"));
                evento.setDescricao(rs.getString("descricao"));
                evento.setData(rs.getDate("data"));
                evento.setLocal(rs.getString("local"));
                evento.setPalestranteId(rs.getInt("palestrante_id"));
                evento.setCapacidade(rs.getString("capacidade"));

                eventosList.add(evento);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar eventos: " + e.getMessage());
            throw e;
        }

        return eventosList;
    }

    public List<Eventos> listarPorParametro(String nome, String data) {
        try {
            List<Eventos> lista = new ArrayList<Eventos>();
            String sql = "SELECT * FROM eventos";
            String sqlWhere = "";
            boolean temNome = (nome != null && !nome.isEmpty());
            boolean temData = (data != null && !data.isEmpty());
    
            if (temNome || temData) {
                sqlWhere = " WHERE";
                if (temNome) sqlWhere += " nome LIKE ?";
                if (temData) {
                    if (temNome) sqlWhere += " AND data = ?";
                    else sqlWhere += " data = ?";
                }
            }
    
            sql += sqlWhere;
            Connection conn = this.sqlConn.connect();
            PreparedStatement pstm = conn.prepareStatement(sql);
    
            int paramIndex = 1;
            if (temNome) pstm.setString(paramIndex++, "%" + nome + "%");
            if (temData) pstm.setString(paramIndex++, data);
    
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                Eventos eventos = new Eventos(
                    rs.getString("nome"), 
                    rs.getString("descricao"),
                    rs.getDate("data"), 
                    rs.getString("local"), 
                    rs.getInt("palestrante"),
                    rs.getString("capacidade")

                );
                lista.add(eventos);
            }
    
            rs.close();
            pstm.close();
            this.sqlConn.close(conn);
            return lista;
        } catch (SQLException e) {
            System.err.println(
                "Erro no método listarPorParametro(String nome, String data) da classe EventosDao ao executar SELECT: "
                    + e.getMessage());
            return new ArrayList<Eventos>();
        }
    }
    
    // Método para criar evento
    public void criarEvento(Eventos evento) throws SQLException {
        String sql = "INSERT INTO eventos (nome, descricao, data, local, palestrante_id) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = this.sqlConn.connect();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, evento.getNome());
            stmt.setString(2, evento.getDescricao());
            stmt.setDate(3, new java.sql.Date(evento.getData().getTime()));
            stmt.setString(4, evento.getLocal());
            stmt.setInt(5, evento.getPalestranteId());
            stmt.setString(6, evento.getCapacidade());


            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        evento.setPalestranteId(generatedKeys.getInt(1)); // Obtém o ID gerado para o evento
                    }
                }
            }
        }
    }

    // Método para editar evento
    public void editarEvento(Eventos evento) throws SQLException {
        String sql = "UPDATE eventos SET nome = ?, descricao = ?, data = ?, local = ?, palestrante_id = ? WHERE id = ?";

        try (Connection conn = this.sqlConn.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, evento.getNome());
            stmt.setString(2, evento.getDescricao());
            stmt.setDate(3, new java.sql.Date(evento.getData().getTime()));
            stmt.setString(4, evento.getLocal());
            stmt.setInt(5, evento.getPalestranteId());
            stmt.setInt(6, evento.getPalestranteId());
            stmt.setString(6, evento.getCapacidade());

            stmt.executeUpdate();
        }
    }

    // Método para excluir evento
    public void excluirEvento(int eventoId) throws SQLException {
        String sql = "DELETE FROM eventos WHERE id = ?";

        try (Connection conn = this.sqlConn.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, eventoId);
            stmt.executeUpdate();
        }
    }

    // Método para associar palestrante a um evento
    public void associarPalestrante(Eventos evento) throws SQLException {
        String sql = "UPDATE eventos SET palestrante_id = ? WHERE id = ?";

        try (Connection conn = this.sqlConn.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, evento.getPalestranteId());
            stmt.setInt(2, evento.getPalestranteId());

            stmt.executeUpdate();
        }
    }

    // Método para verificar se o palestrante existe
    public boolean palestranteExiste(int palestranteId) throws SQLException {
        String sql = "SELECT COUNT(*) FROM palestrantes WHERE id = ?";

        try (Connection conn = this.sqlConn.connect(); // Usando o método connect() corretamente
     PreparedStatement stmt = conn.prepareStatement(sql)) {

    stmt.setInt(1, palestranteId); // Definindo o ID do palestrante
    ResultSet rs = stmt.executeQuery(); // Executando a consulta

    if (rs.next()) { // Verifica se há resultados
        return rs.getInt(1) > 0; // Retorna true se houver registros
    } else {
        return false; // Caso não haja registros
    }
}

    }
}




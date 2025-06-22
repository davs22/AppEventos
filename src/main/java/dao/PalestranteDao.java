package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import table.Palestrante;
import table.Participante;
import util.SQLiteConnection;

public class PalestranteDao {

    private SQLiteConnection sqlConn;

    public PalestranteDao() {
        this.sqlConn = new SQLiteConnection();
    }

    public List<Palestrante> listarTodos() {
        try {
            List<Palestrante> lista = new ArrayList<>();
            String sql = "SELECT * FROM Palestrante";
            Connection conn = this.sqlConn.connect();
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery(sql);
            while (rs.next()) {
                Palestrante palestrante = new Palestrante(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("curriculo"),
                        rs.getString("areaAtuacao"));
                lista.add(palestrante);
            }
            rs.close();
            stm.close();
            this.sqlConn.close(conn);
            return lista;
        } catch (SQLException e) {
            System.err.println("Erro ao listar palestrantes: " + e.getMessage());
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public List<Palestrante> listarPorParametro(String tipo, String valor) {
    List<Palestrante> lista = new ArrayList<>();

    try {
        SqlService sqlsService = new SqlService();
        String sql = sqlsService.ParticipanteSQL(tipo); // retorna SQL com LIKE ou =

        Connection conn = this.sqlConn.connect();
        PreparedStatement pstm = conn.prepareStatement(sql);

        if (tipo.equalsIgnoreCase("id")) {
            pstm.setInt(1, Integer.parseInt(valor));
        } else {
            pstm.setString(1, "%" + valor + "%");  // busca parcial com LIKE
        }

        ResultSet rs = pstm.executeQuery();
        while (rs.next()) {
            Palestrante palestrante = new Palestrante(
                rs.getInt("id"),
                rs.getString("nome"),
                rs.getString("curriculo"),
                rs.getString("areaAtuacao")
            );
            lista.add(palestrante);
        }

        rs.close();
        pstm.close();
        this.sqlConn.close(conn);

    } catch (SQLException e) {
        System.err.println("Erro ao listar palestrante: " + e.getMessage());
    } catch (NumberFormatException e) {
        System.err.println("ID inválido (esperado um número): " + valor);
    }

    return lista;
}


    public Palestrante buscarPorId(Integer id) {
        try {
            String sql = "SELECT * FROM Palestrante WHERE id = ?";
            Connection conn = this.sqlConn.connect();
            PreparedStatement pstm = conn.prepareStatement(sql);
            pstm.setInt(1, id);
            ResultSet rs = pstm.executeQuery();
            Palestrante palestrante = null;
            if (rs.next()) {
                palestrante = new Palestrante(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("curriculo"),
                        rs.getString("areaAtuacao"));
            }
            rs.close();
            pstm.close();
            this.sqlConn.close(conn);
            return palestrante;
        } catch (SQLException e) {
            System.err.println("Erro ao buscar por ID: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public Palestrante buscarPorCurriculo(String curriculo) {
        try {
            String sql = "SELECT * FROM Palestrante WHERE curriculo = ?";
            Connection conn = this.sqlConn.connect();
            PreparedStatement pstm = conn.prepareStatement(sql);
            pstm.setString(1, curriculo);
            ResultSet rs = pstm.executeQuery();
            Palestrante palestrante = null;
            if (rs.next()) {
                palestrante = new Palestrante(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("curriculo"),
                        rs.getString("areaAtuacao"));
            }
            rs.close();
            pstm.close();
            this.sqlConn.close(conn);
            return palestrante;
        } catch (SQLException e) {
            System.err.println("Erro ao buscar por curriculo: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public Palestrante buscarPorAreaAtuacao(String areaAtuacao) {
        try {
            String sql = "SELECT * FROM Palestrante WHERE areaAtuacao = ?";
            Connection conn = this.sqlConn.connect();
            PreparedStatement pstm = conn.prepareStatement(sql);
            pstm.setString(1, areaAtuacao);
            ResultSet rs = pstm.executeQuery();
            Palestrante palestrante = null;
            if (rs.next()) {
                palestrante = new Palestrante(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("curriculo"),
                        rs.getString("areaAtuacao"));
            }
            rs.close();
            pstm.close();
            this.sqlConn.close(conn);
            return palestrante;
        } catch (SQLException e) {
            System.err.println("Erro ao buscar por área de atuação: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public String inserir(String nome, String curriculo, String areaAtuacao) {
        try {
            Integer id = this.getNewId();
            String sql = "INSERT INTO Palestrante(id, nome, curriculo, areaAtuacao) VALUES(?, ?, ?, ?)";
            Connection conn = this.sqlConn.connect();
            PreparedStatement pstm = conn.prepareStatement(sql);
            pstm.setInt(1, id);
            pstm.setString(2, nome);
            pstm.setString(3, curriculo);
            pstm.setString(4, areaAtuacao);
            pstm.executeUpdate();
            pstm.close();
            this.sqlConn.close(conn);
            return "sucesso";
        } catch (Exception e) {
            System.err.println("Erro ao inserir palestrante: " + e.getMessage());
            e.printStackTrace();
            return "erro";
        }
    }

    public boolean excluirPalestrantePorId(int id) {
    String sql = "DELETE FROM Palestrante WHERE id = ?";
    try (Connection conn = this.sqlConn.connect();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setInt(1, id);
        int linhasAfetadas = stmt.executeUpdate();
        return linhasAfetadas > 0; // retorna true se algo foi deletado

    } catch (SQLException e) {
        System.err.println("Erro ao excluir palestrante com ID " + id + ": " + e.getMessage());
        e.printStackTrace();
        return false;
    }
}

    public String atualizarPalestrante(int idPalestrante, String novoNome, String novoCurriculo, String novaAreaAtuacao) {
        try {
            String sqlUpdate = "UPDATE Palestrante SET nome = ?, curriculo = ?, areaAtuacao = ? WHERE id = ?";
            Connection conn = this.sqlConn.connect();
            PreparedStatement stmt = conn.prepareStatement(sqlUpdate);
            stmt.setString(1, novoNome);
            stmt.setString(2, novoCurriculo);
            stmt.setString(3, novaAreaAtuacao);
            stmt.setInt(4, idPalestrante);
            stmt.executeUpdate();
            stmt.close();
            this.sqlConn.close(conn);
            return "sucesso";
        } catch (Exception e) {
            e.printStackTrace();
            return "erro";
        }
    }

    public boolean palestranteExiste(int palestranteId) throws SQLException {
        String sql = "SELECT COUNT(*) FROM Palestrante WHERE id = ?";
        try (Connection conn = this.sqlConn.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, palestranteId);
            ResultSet rs = stmt.executeQuery();
            return rs.next() && rs.getInt(1) > 0;
        }
    }

    private Integer getNewId() {
        try {
            Integer id = 1;
            String sql = "SELECT MAX(id) AS max_id FROM Palestrante";
            Connection conn = this.sqlConn.connect();
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery(sql);
            if (rs.next())
                id = rs.getInt("max_id") + 1;
            rs.close();
            stm.close();
            this.sqlConn.close(conn);
            return id;
        } catch (Exception e) {
            System.err.println("Erro ao obter novo ID: " + e.getMessage());
            e.printStackTrace();
            return -1;
        }
    }
}

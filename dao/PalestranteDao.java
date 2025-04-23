package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import table.Palestrante;
import util.SQLiteConnection;

public class PalestranteDao {
    
    private SQLiteConnection sqlConn;

    public PalestranteDao() {
        this.sqlConn = new SQLiteConnection();
    }

    public List<Palestrante> listarTodos() {
        try {
            List<Palestrante> lista = new ArrayList<Palestrante>();
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
            System.err.println(
                    "Erro ao listarTodos() de Palestrantes " + e.getMessage());
            e.printStackTrace();
            return new ArrayList<Palestrante>();
        }
    }

    public List<Palestrante> listarPorParametro(String nome, String areaAtuacao) {
        try {
            List<Palestrante> lista = new ArrayList<Palestrante>();
            String sql = "SELECT * FROM Palestrante";
            String sqlWhere = "";
            if (((nome != null) && (!nome.isEmpty())) || ((areaAtuacao != null) && (!areaAtuacao.isEmpty()))) {
                sqlWhere = " WHERE";
                if ((nome != null) && (!nome.isEmpty()))
                    sqlWhere += " nome LIKE ?";
                    if ((areaAtuacao != null) && (!areaAtuacao.isEmpty())) {
                        if (sqlWhere.equals(""))
                            sqlWhere += " areaAtuacao = ?";
                        else
                            sqlWhere += " areaAtuacao = ?";
                }
            }
            sql += sqlWhere;
            Connection conn = this.sqlConn.connect();
            PreparedStatement pstm = conn.prepareStatement(sql);
            if ((nome != null) && (!nome.isEmpty()) && (areaAtuacao != null) && (!areaAtuacao.isEmpty())) {
                pstm.setString(1, nome);
                pstm.setString(2, areaAtuacao);
            } else if ((nome != null) && (!nome.isEmpty()))
                pstm.setString(1, nome);
            else if ((nome != null) && (!nome.isEmpty()))
                pstm.setString(1, areaAtuacao);
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                Palestrante palestrante = new Palestrante(
                    rs.getInt("id"), 
                    rs.getString("nome"),
                    rs.getString("curriculo"), 
                    rs.getString("areaAtuacao"));
                lista.add(palestrante);
            }
            rs.close();
            pstm.close();
            this.sqlConn.close(conn);
            return lista;
        } catch (SQLException e) {
            System.err.println(
                    "Erro no método listarPorParametro: "
                            + e.getMessage());
            return new ArrayList<Palestrante>();
        }
    }

    public Palestrante buscarPorId(Integer id) {
        try {
            Palestrante palestrante = new Palestrante();
            String sql = "SELECT * FROM palestrante where id = ?";
            Connection conn = this.sqlConn.connect();
            PreparedStatement pstm = conn.prepareStatement(sql);
            pstm.setInt(1, id);
            ResultSet rs = pstm.executeQuery();
            if (rs.next())
                palestrante = new Palestrante(
                    rs.getInt("id"), 
                    rs.getString("nome"),
                    rs.getString("curriculo"), 
                    rs.getString("areaAtuacao"));
            rs.close();
            pstm.close();
            this.sqlConn.close(conn);
            return palestrante;
        } catch (SQLException e) {
            System.err.println("Erro no método buscarPorId(Integer id) da classe PalestranteDao ao executar SELECT: "
                    + e.getMessage());
            e.printStackTrace();
            return new Palestrante();
        }
    }

    public Palestrante buscarPorCurriculo(String curriculo) {
        try {
            Palestrante palestrante = new Palestrante();
            String sql = "SELECT * FROM palestrante where curriculo = ?";
            Connection conn = this.sqlConn.connect();
            PreparedStatement pstm = conn.prepareStatement(sql);
            pstm.setString(1, curriculo);
            ResultSet rs = pstm.executeQuery();
            if (rs.next())
                palestrante = new Palestrante(
                    rs.getInt("id"), 
                    rs.getString("nome"),
                    rs.getString("curriculo"), 
                    rs.getString("areaAtuacao"));
            rs.close();
            pstm.close();
            this.sqlConn.close(conn);
            return palestrante;
        } catch (SQLException e) {
            System.err.println(
                    "Erro no método buscarPorCurriculo(String curriculo) da classe PalestranteDao ao executar SELECT: "
                            + e.getMessage());
            e.printStackTrace();
            return new Palestrante();
        }
    }

    public Palestrante buscarPorAreaAtuacao(String areaAtuacao) {
        try {
            Palestrante palestrante = new Palestrante();
            String sql = "SELECT * FROM palestrante where areaAtuacao = ?";
            Connection conn = this.sqlConn.connect();
            PreparedStatement pstm = conn.prepareStatement(sql);
            pstm.setString(1, areaAtuacao);
            ResultSet rs = pstm.executeQuery();
            if (rs.next())
             palestrante = new Palestrante(
                    rs.getInt("id"), 
                    rs.getString("nome"),
                    rs.getString("curriculo"), 
                    rs.getString("areaAtuacao"));
            rs.close();
            pstm.close();
            this.sqlConn.close(conn);
            return palestrante;
        } catch (SQLException e) {
            System.err.println(
                    "Erro no método buscarPorAreaAtuacao(String areaAtuacao) da classe PalestranteDao ao executar SELECT: "
                            + e.getMessage());
            e.printStackTrace();
            return new Palestrante();
        }
    }

    public String inserir(String nome, String curriculo, String areaAtuacao) {
        try {
            Integer id = this.getNewId();
            String sql = "INSERT INTO palestrante(id, nome, curriculo, areaAtuacao) VALUES(?, ?, ?, ?)";
            Connection conn = this.sqlConn.connect();
            PreparedStatement pstm = conn.prepareStatement(sql);
            pstm.setInt(1, id);
            pstm.setString(2, nome);
            pstm.setString(3, curriculo);
            pstm.setString(4, areaAtuacao);
            System.out.println("Resposta: " + pstm.executeUpdate());
            pstm.close();
            this.sqlConn.close(conn);
            return "sucesso";
        } catch (Exception e) {
            System.err.println(
                    "Erro no método inserir(String nome, String curriculo, String areadeatuacao) da classe PalestranteDao ao executar SELECT: "
                            + e.getMessage());
            e.printStackTrace();
            return "erro";
        }
    }

    private Integer getNewId() {
        try {
            Integer id = 1;
            String sql = "SELECT MAX(id) AS max_id FROM palestrante";
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
            System.err.println(
                    "Erro no método getNewId() da classe PalestranteDao ao executar SELECT: "
                            + e.getMessage());
            e.printStackTrace();
            return -1;
        }
    }

    public String excluir(int id) {
        try {
            String sql = "DELETE FROM palestrante WHERE id = ?";
            Connection conn = this.sqlConn.connect();
            PreparedStatement pstm = conn.prepareStatement(sql);
            pstm.setInt(1, id);
            int linhasAfetadas = pstm.executeUpdate();
            System.out.println("Linhas afetadas: " + linhasAfetadas);
            pstm.close();
            this.sqlConn.close(conn);
            return linhasAfetadas > 0 ? "sucesso" : "nenhum registro encontrado";
        } catch (Exception e) {
            System.err.println("Erro no método excluir(int id) da classe PalestranteDao: " + e.getMessage());
            e.printStackTrace();
            return "erro";
        }
    }
    
}



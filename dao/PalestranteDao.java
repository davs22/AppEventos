package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import table.palestrante;
//import table.Participante; ?pode vir a ser util porem
import util.SQLiteConnection;

public class PalestranteDao {
    /*
    seleção por id ------------- falta executar
    seleção por curriculo ------ falta executar
    seleção por areaAtuacao ---- falta executar
    seleção listarPorParametro - necesario corrigir e discutir para compreenção
    */
    private SQLiteConnection sqlConn;

    public PalestranteDao() {
        this.sqlConn = new SQLiteConnection();
    }

    public List<palestrante> listarTodos() {
        try {
            List<palestrante> lista = new ArrayList<palestrante>();
            String sql = "SELECT * FROM Palestrante";
            Connection conn = this.sqlConn.connect();
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery(sql);
            while (rs.next()) {
                //nome, currículo, área de atuação
                palestrante palestrante = new palestrante(
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
                    "Erro no método listarTodos() da classe PalestanteDao ao executar SELECT: " + e.getMessage());
            e.printStackTrace();
            return new ArrayList<palestrante>();
        }
    }
//buscar discutir e corrigir
    public List<palestrante> listarPorParametro(String nome, String sexo) {
        try {
            List<palestrante> lista = new ArrayList<palestrante>();
            String sql = "SELECT * FROM Palestrante";
            String sqlWhere = "";
            if (((nome != null) && (!nome.isEmpty())) || ((sexo != null) && (!sexo.isEmpty()))) {
                sqlWhere = " WHERE";
                if ((nome != null) && (!nome.isEmpty()))
                    sqlWhere += " nome LIKE ?";
                    if ((sexo != null) && (!sexo.isEmpty())) {
                        if (sqlWhere.equals(""))
                            sqlWhere += " email = ?";
                        else
                            sqlWhere += " AND email = ?";
                }
            }
            sql += sqlWhere;
            Connection conn = this.sqlConn.connect();
            PreparedStatement pstm = conn.prepareStatement(sql);
            if ((nome != null) && (!nome.isEmpty()) && (sexo != null) && (!sexo.isEmpty())) {
                pstm.setString(1, nome);
                pstm.setString(2, sexo);
            } else if ((nome != null) && (!nome.isEmpty()))
                pstm.setString(1, nome);
            else if ((nome != null) && (!nome.isEmpty()))
                pstm.setString(1, sexo);
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                palestrante palestrante = new palestrante(
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
                    "Erro no método listarPorParametro(String nome, String sexo) da classe PalestranteteDao ao executar SELECT: "
                            + e.getMessage());
            return new ArrayList<palestrante>();
        }
    }

    public palestrante buscarPorId(Integer id) {
        try {
            palestrante palestrante = new palestrante();
            String sql = "SELECT * FROM palestrante where id = ?";
            Connection conn = this.sqlConn.connect();
            PreparedStatement pstm = conn.prepareStatement(sql);
            pstm.setInt(1, id);
            ResultSet rs = pstm.executeQuery();
            if (rs.next())
                palestrante = new palestrante(
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
            return new palestrante();
        }
    }

    public palestrante buscarPorCurriculo(String curriculo) {
        try {
            palestrante palestrante = new palestrante();
            String sql = "SELECT * FROM palestrante where curriculo = ?";
            Connection conn = this.sqlConn.connect();
            PreparedStatement pstm = conn.prepareStatement(sql);
            pstm.setString(1, curriculo);
            ResultSet rs = pstm.executeQuery();
            if (rs.next())
                palestrante = new palestrante(
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
            return new palestrante();
        }
    }

    public palestrante buscarPorAreaAtuacao(String areaAtuacao) {
        try {
            palestrante palestrante = new palestrante();
            String sql = "SELECT * FROM palestrante where areaAtuacao = ?";
            Connection conn = this.sqlConn.connect();
            PreparedStatement pstm = conn.prepareStatement(sql);
            pstm.setString(1, areaAtuacao);
            ResultSet rs = pstm.executeQuery();
            if (rs.next())
             palestrante = new palestrante(
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
            return new palestrante();
        }
    }
// campos nome, currículo, área de atuação
    public String inserir(String nome, String curriculo, String areadeatuacao) {
        try {
            Integer id = this.getNewId();
            String sql = "INSERT INTO palestrante(id, nome, curriculo, areadeatuacao) VALUES(?, ?, ?, ?)";
            Connection conn = this.sqlConn.connect();
            PreparedStatement pstm = conn.prepareStatement(sql);
            pstm.setInt(1, id);
            pstm.setString(2, nome);
            pstm.setString(3, curriculo);
            pstm.setString(4, areadeatuacao);
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
}



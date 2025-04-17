package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import table.Palestrante;
//import table.Participante; ?pode vir a ser util
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
                //nome, currículo, área de atuação
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
                    "Erro no método listarTodos() da classe PalestanteDao ao executar SELECT: " + e.getMessage());
            e.printStackTrace();
            return new ArrayList<Palestrante>();
        }
    }

    public List<Palestrante> listarPorParametro(String nome, String sexo) {
        try {
            List<Palestrante> lista = new ArrayList<Palestrante>();
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
                Palestrante palestrante = new Palestrante(
                    rs.getInt("id"), 
                    rs.getString("nome"),
                    rs.getString("sexo"), 
                    rs.getString("email"));
                lista.add(palestrante);
            }
            rs.close();
            pstm.close();
            this.sqlConn.close(conn);
            return lista;
        } catch (SQLException e) {
            System.err.println(
                    "Erro no método listarPorParametro(String nome, String sexo) da classe ParticipanteDao ao executar SELECT: "
                            + e.getMessage());
            return new ArrayList<Palestrante>();
        }
    }

    public Palestrante buscarPorId(Integer id) {
        try {
            Palestrante palestrante = new Palestrante();
            String sql = "SELECT * FROM participante where id = ?";
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
            System.err.println("Erro no método buscarPorId(Integer id) da classe ParticipanteDao ao executar SELECT: "
                    + e.getMessage());
            e.printStackTrace();
            return new Palestrante();
        }
    }

    public Palestrante buscarPorEmail(String email) {
        try {
            Palestrante palestrante = new Palestrante();
            String sql = "SELECT * FROM palestrante where email = ?";
            Connection conn = this.sqlConn.connect();
            PreparedStatement pstm = conn.prepareStatement(sql);
            pstm.setString(1, email);
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
                    "Erro no método buscarPorEmail(String email) da classe ParticipanteDao ao executar SELECT: "
                            + e.getMessage());
            e.printStackTrace();
            return new Palestrante();
        }
    }

    public Palestrante buscarPorCelular(String celular) {
        try {
            Palestrante palestrante = new Palestrante();
            String sql = "SELECT * FROM palestrante where celular = ?";
            Connection conn = this.sqlConn.connect();
            PreparedStatement pstm = conn.prepareStatement(sql);
            pstm.setString(1, celular);
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
                    "Erro no método buscarPorEmail(String email) da classe ParticipanteDao ao executar SELECT: "
                            + e.getMessage());
            e.printStackTrace();
            return new Palestrante();
        }
    }
// campos nome, currículo, área de atuação
    public String inserir(String nome, String sexo, String curriculo, String areadeatuacao) {
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
                    "Erro no método inserir(String nome, String sexo, String email, String celular) da classe ParticipanteDao ao executar SELECT: "
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
                    "Erro no método getNewId() da classe ParticipanteDao ao executar SELECT: "
                            + e.getMessage());
            e.printStackTrace();
            return -1;
        }
    }
}



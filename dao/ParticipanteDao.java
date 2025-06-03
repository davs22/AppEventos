package dao;

import org.mindrot.jbcrypt.BCrypt;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import table.Participante;
import util.SQLiteConnection;

public class ParticipanteDao {
    private SQLiteConnection sqlConn;

    public ParticipanteDao() {
        this.sqlConn = new SQLiteConnection();
    }

    public List<Participante> listarTodos() {
        try {
            List<Participante> lista = new ArrayList<Participante>();
            String sql = "SELECT * FROM Participante";
            Connection conn = this.sqlConn.connect();
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery(sql);
            while (rs.next()) {
                Participante participante = new Participante(
                        rs.getInt("id"), 
                        rs.getString("nome"),
                        rs.getString("sexo"), 
                        rs.getString("email"), 
                        rs.getString("celular"),
                        rs.getString("senha"),
                        rs.getString("tipo"));
                lista.add(participante);
            }
            rs.close();
            stm.close();
            this.sqlConn.close(conn);
            return lista;
        } catch (SQLException e) {
            System.err.println(
                    "Erro no método listarTodos() da classe ParticipanteDao ao executar SELECT: " + e.getMessage());
            e.printStackTrace();
            return new ArrayList<Participante>();
        }
    }

    public List<Participante> listarPorParametro(String nome, String sexo) {
        try {
            List<Participante> lista = new ArrayList<Participante>();
            String sql = "SELECT * FROM participante";
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
                Participante participante = new Participante(
                    rs.getInt("id"), 
                    rs.getString("nome"),
                    rs.getString("sexo"), 
                    rs.getString("email"), 
                    rs.getString("celular"),
                    rs.getString("senha"),
                    rs.getString("tipo"));
                lista.add(participante);
            }
            rs.close();
            pstm.close();
            this.sqlConn.close(conn);
            return lista;
        } catch (SQLException e) {
            System.err.println(
                    "Erro no método listarPorParametro(String nome, String sexo) da classe ParticipanteDao ao executar SELECT: "
                            + e.getMessage());
            return new ArrayList<Participante>();
        }
    }

    public Participante buscarPorId(Integer id) {
        try {
            Participante participante = new Participante();
            String sql = "SELECT * FROM Participante where id = ?";
            Connection conn = this.sqlConn.connect();
            PreparedStatement pstm = conn.prepareStatement(sql);
            pstm.setInt(1, id);
            ResultSet rs = pstm.executeQuery();
            if (rs.next())
                participante = new Participante(rs.getInt("id"), 
                rs.getString("nome"),
                rs.getString("sexo"), 
                rs.getString("email"), 
                rs.getString("celular"),
                rs.getString("senha"),
                rs.getString("tipo"));
            rs.close();
            pstm.close();
            this.sqlConn.close(conn);
            return participante;
        } catch (SQLException e) {
            System.err.println("Erro no método buscarPorId(Integer id) da classe ParticipanteDao ao executar SELECT: "
                    + e.getMessage());
            e.printStackTrace();
            return new Participante();
        }
    }

    public Participante buscarPorEmail(String email) {
        try {
            Participante participante = new Participante();
            String sql = "SELECT * FROM Participante where email = ?";
            Connection conn = this.sqlConn.connect();
            PreparedStatement pstm = conn.prepareStatement(sql);
            pstm.setString(1, email);
            ResultSet rs = pstm.executeQuery();
            if (rs.next())
                participante = new Participante(rs.getInt("id"), rs.getString("nome"),
                        rs.getString("sexo"), rs.getString("email"), rs.getString("celular"),
                        rs.getString("senha"), rs.getString("tipo"));
            rs.close();
            pstm.close();
            this.sqlConn.close(conn);
            return participante;
        } catch (SQLException e) {
            System.err.println(
                    "Erro no método buscarPorEmail(String email) da classe ParticipanteDao ao executar SELECT: "
                            + e.getMessage());
            e.printStackTrace();
            return new Participante();
        }
    }

    public Participante buscarPorCelular(String celular) {
        try {
            Participante participante = new Participante();
            String sql = "SELECT * FROM Participante where celular = ?";
            Connection conn = this.sqlConn.connect();
            PreparedStatement pstm = conn.prepareStatement(sql);
            pstm.setString(1, celular);
            ResultSet rs = pstm.executeQuery();
            if (rs.next())
                participante = new Participante(rs.getInt("id"), rs.getString("nome"),
                        rs.getString("sexo"), rs.getString("email"), rs.getString("celular"),
                        rs.getString("senha"), rs.getString("tipo"));
            rs.close();
            pstm.close();
            this.sqlConn.close(conn);
            return participante;
        } catch (SQLException e) {
            System.err.println(
                    "Erro no método buscarPorEmail(String email) da classe ParticipanteDao ao executar SELECT: "
                            + e.getMessage());
            e.printStackTrace();
            return new Participante();
        }
    }

   public String inserir(String nome, String sexo, String email, String celular, String senhaCriptografada, String tipo) {
    try {
        Integer id = this.getNewId();

        String sql = "INSERT INTO Participante(id, nome, sexo, email, celular, senha, tipo) VALUES(?, ?, ?, ?, ?, ?, ?)";
        Connection conn = this.sqlConn.connect();
        PreparedStatement pstm = conn.prepareStatement(sql);
        pstm.setInt(1, id);
        pstm.setString(2, nome);
        pstm.setString(3, sexo);
        pstm.setString(4, email);
        pstm.setString(5, celular);
        pstm.setString(6, senhaCriptografada); // Senha criptografada vai para a coluna 'senha'
        pstm.setString(7, tipo); // Tipo vai corretamente para a coluna 'tipo'

        System.out.println("Resposta: " + pstm.executeUpdate());
        pstm.close();
        this.sqlConn.close(conn);
        return "sucesso";
    } catch (Exception e) {
        System.err.println(
            "Erro no método inserir(...) da classe ParticipanteDao ao executar INSERT: " + e.getMessage());
        e.printStackTrace();
        return "erro";
    }
}


    public String atualizarParticipante(int idParticipante, String novoNome, String novoSexo, String novoEmail, String novoTelefone, String novaSenha) {
        try {
            Connection conn = this.sqlConn.connect();

            String verificaSql = "SELECT COUNT(*) FROM Participante WHERE id = ?";
            PreparedStatement verificaStmt = conn.prepareStatement(verificaSql);
            verificaStmt.setInt(1, idParticipante);
            ResultSet rs = verificaStmt.executeQuery();

            if (!rs.next() || rs.getInt(1) == 0) {
                verificaStmt.close();
                this.sqlConn.close(conn);
                return "Participante não encontrado!";
            }

            String sqlUpdate = "UPDATE Participante SET nome = ?, sexo = ?, email = ?, celular = ?, senha = ? WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(sqlUpdate);
            stmt.setString(1, novoNome);
            stmt.setString(2, novoSexo);
            stmt.setString(3, novoEmail);
            stmt.setString(4, novoTelefone);
            stmt.setString(5, novaSenha);
            stmt.setInt(6, idParticipante);

            int resultado = stmt.executeUpdate();
            stmt.close();
            this.sqlConn.close(conn);

            return resultado > 0 ? "Dados atualizados com sucesso!" : "Erro ao atualizar dados do participante.";

        } catch (Exception e) {
            e.printStackTrace();
            return "Erro ao atualizar dados.";
        }
    }

    // Método para gerar um novo ID
    private Integer getNewId() {
        try {
            Integer id = 1;
            String sql = "SELECT MAX(id) AS max_id FROM Participante";
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
            System.err.println("Erro no método getNewId() da classe ParticipanteDao ao executar SELECT: " + e.getMessage());
            e.printStackTrace();
            return -1;
        }
    }

    public boolean emailJaExiste(String email) {
        try {
            String sql = "SELECT COUNT(*) FROM Participante WHERE email = ?";
            Connection conn = this.sqlConn.connect();
            PreparedStatement pstm = conn.prepareStatement(sql);
            pstm.setString(1, email);
            ResultSet rs = pstm.executeQuery();
                
            boolean existe = false;
            if (rs.next()) {
                existe = rs.getInt(1) > 0;
            }

            rs.close();
            pstm.close();
            this.sqlConn.close(conn);
            return existe;

        } catch (Exception e) {
             System.err.println("Erro no método emailJaExiste: " + e.getMessage());
             e.printStackTrace();
             return false; 

    }
        }

    public Participante login(String email, String senhaDigitada) {
    String sql = "SELECT * FROM Participante WHERE email = ?";
    try (Connection conn = sqlConn.connect();
         PreparedStatement pstm = conn.prepareStatement(sql)) {

        pstm.setString(1, email);
        ResultSet rs = pstm.executeQuery();

        if (rs.next()) {
            String senhaSalva = rs.getString("senha");
            if (BCrypt.checkpw(senhaDigitada, senhaSalva)) {
                Participante p = new Participante();
                p.setId(rs.getInt("id"));
                p.setNome(rs.getString("nome"));
                p.setEmail(email);
                p.setTipo(rs.getString("tipo"));
                return p;
            }
        }
        return null; // Login falhou
    } catch (Exception e) {
        e.printStackTrace();
        return null;
    }
}

}


    

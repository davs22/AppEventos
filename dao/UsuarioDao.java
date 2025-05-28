package dao;

import java.sql.*;

import table.Usuario;
import util.SQLiteConnection;

public class UsuarioDao {
    private SQLiteConnection sqlConn;
    
   public UsuarioDao(SQLiteConnection sqlConn) {
        this.sqlConn = sqlConn;
    }

    public boolean cadastrarUsuario(Usuario usuario) {
        String sql = "INSERT INTO Usuario (nome, email, senha, tipo) VALUES (?, ?, ?, ?)";
        try (Connection conn = this.sqlConn.connect();
            PreparedStatement pstm = conn.prepareStatement(sql)) {
            pstm.setString(1, usuario.getNome());
            pstm.setString(2, usuario.getEmail());
            pstm.setString(3, usuario.getSenha()); 
            pstm.setString(4, usuario.getTipo()); 
            pstm.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Erro ao cadastrar: " + e.getMessage());
            return false;
        }
    }

    public Usuario login(String email, String senha) {
        String sql = "SELECT * FROM Usuario WHERE email = ? AND senha = ?";
        try (Connection conn = this.sqlConn.connect();
            PreparedStatement pstm = conn.prepareStatement(sql)) {
            pstm.setString(1, email);
            pstm.setString(2, senha);
            ResultSet rs = pstm.executeQuery();

            if (rs.next()) {
                Usuario u = new Usuario();
                u.setId(rs.getInt("id"));
                u.setNome(rs.getString("nome"));
                u.setEmail(rs.getString("email"));
                u.setSenha(rs.getString("senha"));
                u.setTipo(rs.getString("tipo"));
                return u;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
}


    }


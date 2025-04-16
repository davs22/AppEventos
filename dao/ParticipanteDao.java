package dao;

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
        this.sqlconn = new SQLiteConnection();
    }

    public List<Participante> listarTodos() {
        try {
            List<Participante> lista = new ArrayList<Participante>();
            String sql = "SELECT * FROM participante";
            Connection conn = this.sqlConn.connect();
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery(sql);
            while (rs.next()) {
                Participante participante = new Participante(rs.getString("nome"), rs.getString("email"),
                        rs.getString("sexo"), rs.getInt("idade"));
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
}
package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLiteConnection {
    private static final String URL = "jdbc:sqlite:src/main/resources/database/eventos.db";
    private Connection conn = null;
    private boolean consoleON = false;
    public Connection connect() {
        try {
            
            Class.forName("org.sqlite.JDBC");

            
            this.conn = DriverManager.getConnection(URL);
            if(consoleON){
            System.out.println("Conexão com SQLite estabelecida.");}
        } catch (ClassNotFoundException e) {
            System.err.println("Driver do SQLite não encontrado: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("Erro ao conectar ao banco de dados: " + e.getMessage());
        }
        return this.conn;
    }

    public void close(Connection conn) {
        try {
            if (this.conn != null) {
                this.conn.close();
                if(consoleON){
                System.out.println("Conexão fechada.");}
            }
        } catch (SQLException e) {
            System.err.println("Erro ao fechar a conexão: " + e.getMessage());
        }
    }
}

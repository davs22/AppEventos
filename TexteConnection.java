import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;



import util.SQLiteConnection;

public class TexteConnection {
    public static void main(String[] args) {
        try {
            //String VerTabelas = "SELECT name FROM sqlite_master WHERE type = 'table' AND name NOT LIKE 'sqlite_%';";
            //String VerSeExiste = "SELECT name FROM sqlite_master WHERE type='table' AND name='Participante';";
            //String CheckColunass = "PRAGMA table_info(Palestrante);";
            
            String[] tabelas = {"Participante", "Palestrante", "Eventos", "Inscricao"};
            String[] CheckColunas = {"PRAGMA table_info(Participante);", "PRAGMA table_info(Palestrante);", 
                                      "PRAGMA table_info(Eventos);", "PRAGMA table_info(Inscricao);"};
    

            SQLiteConnection sqlConn = new SQLiteConnection();
            Connection conn = sqlConn.connect();
            Statement stm = conn.createStatement();
            
            /*ResultSet rs = stm.executeQuery(VerTabelas);
            System.out.println("--------------");
            while (rs.next()) {
                System.out.println("- " + rs.getString("name"));
            }
            System.out.println("--------------");*/

            for (int i = 0; i < CheckColunas.length; i++) {
                ResultSet rs = stm.executeQuery(CheckColunas[i]);
                System.out.println("-----"+tabelas[i]+"-----");
                while (rs.next()) {
                    System.out.println("- " + rs.getString("name"));
                }
                System.out.println("-------------------\n");
                
            }
            stm.close();
            sqlConn.close(conn);
        
        } catch (SQLException e) {
            System.err.println("Erro ao executar SELECT: " + e.getMessage());
        }

       
       
        

    }
    
}


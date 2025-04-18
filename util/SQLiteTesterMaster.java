package util;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
import java.io.IOException;

public class SQLiteTesterMaster {
    public static void main(String[] args) throws IOException {
        // Conectando ao DB
        SQLiteConnection sqlconn = new SQLiteConnection();
        Connection conn = sqlconn.connect();
        
        // Mostrar o diretório de execução (debug)
        System.out.println("Diretório de execução: " + System.getProperty("user.dir"));
        
        // Solicitar escolha do usuário
        System.out.println("Digite 1 para: criar DB\nDigite 2 para: Deletar DB\nDigite 3 para: deletar-DB e criar-DB");
        Scanner scanner = new Scanner(System.in);
        int opcao = scanner.nextInt();

        // Ação com base na escolha do usuário
        if (opcao == 1) {
            System.out.println("Criando DB");

            String sql = new String();
            
            // Executar SQL
            try (Statement stm = conn.createStatement()) {
                stm.execute(sql);  // Executa o comando SQL
                System.out.println("DB criado com sucesso!");
            } catch (SQLException e) {
                System.err.println("Erro ao executar SQL: ");
                e.printStackTrace();
            }
        }

        scanner.close();
    }
}

package util;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
import java.io.IOException;

public class SQLiteTesterMaster {
    public static void main(String[] args) throws IOException{
        // Conectando ao DB
        SQLiteConnection sqlconn = new SQLiteConnection();
        Connection conn = sqlconn.connect();
        
        // Solicitar escolha do usuário
        System.out.println("Digite 1 para: criar DB\nDigite 2 para: Deletar DB\nDigite 3 para: resetar DB\nDigite 4 para: inserir valores test DB");
        Scanner scanner = new Scanner(System.in);
        int opcao = scanner.nextInt();

        // Ação com base na escolha do usuário
        if (opcao == 1) {
            System.out.println("Criando DB");
            // Executar SQL
            try (Statement stm = conn.createStatement()) {
                
                stm.execute("CREATE TABLE Participante (id INTEGER PRIMARY KEY,nome TEXT NOT NULL,sexo TEXT,email TEXT,celular TEXT);");
                stm.execute("CREATE TABLE Palestrante (id INTEGER PRIMARY KEY,nome TEXT NOT NULL,curriculo TEXT,areaAtuacao TEXT);");
                stm.execute("CREATE TABLE Eventos (id INTEGER PRIMARY KEY,nome TEXT NOT NULL,descricao TEXT,data DATE,local TEXT,palestranteId INTEGER,capacidade INTEGER,FOREIGN KEY (palestranteId) REFERENCES Palestrante(id));");
                stm.execute("CREATE TABLE Inscricao (id INTEGER PRIMARY KEY,id_eventos INTEGER NOT NULL,id_participante INTEGER NOT NULL,FOREIGN KEY (id_eventos) REFERENCES Eventos(id) ON DELETE CASCADE,FOREIGN KEY (id_participante) REFERENCES Participante(id) ON DELETE CASCADE);");
                System.out.println("DB criado com sucesso!");

            } catch (SQLException e) {
                System.err.println("Erro ao executar SQL: ");
                e.printStackTrace();
            }
        }

        if (opcao == 2) {
            System.out.println("Deletado DB");
            // Executar SQL
            try (Statement stm = conn.createStatement()) {

                stm.execute("DROP TABLE IF EXISTS Inscricao;");
                stm.execute("DROP TABLE IF EXISTS Eventos;"); 
                stm.execute("DROP TABLE IF EXISTS Palestrante;"); 
                stm.execute("DROP TABLE IF EXISTS Participante;");  
                System.out.println("DB Deletado com sucesso!");

            } catch (SQLException e) {
                System.err.println("Erro ao executar SQL: ");
                e.printStackTrace();
            }
          
        }

        if (opcao == 3) {
            System.out.println("Resetar DB");
            // Executar SQL
            try (Statement stm = conn.createStatement()) {
               
                stm.execute("DROP TABLE IF EXISTS Inscricao;");
                stm.execute("DROP TABLE IF EXISTS Eventos;"); 
                stm.execute("DROP TABLE IF EXISTS Palestrante;"); 
                stm.execute("DROP TABLE IF EXISTS Participante;");  
                System.out.println("DB Deletado com sucesso!");

                stm.execute("CREATE TABLE Participante (id INTEGER PRIMARY KEY,nome TEXT NOT NULL,sexo TEXT,email TEXT,celular TEXT);");
                stm.execute("CREATE TABLE Palestrante (id INTEGER PRIMARY KEY,nome TEXT NOT NULL,curriculo TEXT,areaAtuacao TEXT);");
                stm.execute("CREATE TABLE Eventos (id INTEGER PRIMARY KEY,nome TEXT NOT NULL,descricao TEXT,data DATE,local TEXT,palestranteId INTEGER,capacidade INTEGER,FOREIGN KEY (palestranteId) REFERENCES Palestrante(id));");
                stm.execute("CREATE TABLE Inscricao (id INTEGER PRIMARY KEY,id_eventos INTEGER NOT NULL,id_participante INTEGER NOT NULL,FOREIGN KEY (id_eventos) REFERENCES Eventos(id) ON DELETE CASCADE,FOREIGN KEY (id_participante) REFERENCES Participante(id) ON DELETE CASCADE);");
                System.out.println("DB criado com sucesso!");
                
            } catch (SQLException e) {
                System.err.println("Erro ao executar SQL: ");
                e.printStackTrace();
            }
        }
        scanner.close();
    }
}

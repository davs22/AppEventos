package util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import service.ParticipanteService;

import java.io.IOException;

public class SQLiteTesterMaster {
    public static void main(String[] args) throws IOException {
        SQLiteConnection sqlconn = new SQLiteConnection();
        Connection conn = sqlconn.connect();

        System.out.println(
                "Digite 1 para: criar DB\nDigite 2 para: Deletar DB\nDigite 3 para: resetar DB\nDigite 4 para: pegar tabelas DB\nDigite 5 para: inserir adm no DB");
        Scanner scanner = new Scanner(System.in);
        int opcao = scanner.nextInt();

        if (opcao == 1) {
            System.out.println("Criando DB");

            try (Statement stm = conn.createStatement()) {

                stm.execute(
                        "CREATE TABLE Participante (id INTEGER PRIMARY KEY,nome TEXT NOT NULL,sexo TEXT,email TEXT,celular TEXT,senha TEXT NOT NULL,tipo TEXT NOT NULL);");
                stm.execute(
                        "CREATE TABLE Palestrante (id INTEGER PRIMARY KEY,nome TEXT NOT NULL,curriculo TEXT,areaAtuacao TEXT);");
                stm.execute(
                        "CREATE TABLE Eventos (id INTEGER PRIMARY KEY,nome TEXT NOT NULL,descricao TEXT,data DATE,local TEXT,palestranteId INTEGER,capacidade INTEGER,FOREIGN KEY (palestranteId) REFERENCES Palestrante(id));");
                stm.execute(
                        "CREATE TABLE Inscricao (id INTEGER PRIMARY KEY,id_eventos INTEGER NOT NULL,id_participante INTEGER NOT NULL,FOREIGN KEY (id_eventos) REFERENCES Eventos(id) ON DELETE CASCADE,FOREIGN KEY (id_participante) REFERENCES Participante(id) ON DELETE CASCADE);");
                System.out.println("DB criado com sucesso!");

            } catch (SQLException e) {
                System.err.println("Erro ao executar SQL: ");
                e.printStackTrace();
            }
        }

        if (opcao == 2) {
            System.out.println("Deletado DB");

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
            try (Statement stm = conn.createStatement()) {

                stm.execute("DROP TABLE IF EXISTS Inscricao;");
                stm.execute("DROP TABLE IF EXISTS Eventos;");
                stm.execute("DROP TABLE IF EXISTS Palestrante;");
                stm.execute("DROP TABLE IF EXISTS Participante;");
                System.out.println("DB Deletado com sucesso!");

                stm.execute(
                        "CREATE TABLE Participante (id INTEGER PRIMARY KEY,nome TEXT NOT NULL,sexo TEXT,email TEXT,celular TEXT,senha TEXT NOT NULL,tipo TEXT NOT NULL DEFAULT 'participante');");
                stm.execute(
                        "CREATE TABLE Palestrante (id INTEGER PRIMARY KEY,nome TEXT NOT NULL,curriculo TEXT,areaAtuacao TEXT);");
                stm.execute(
                        "CREATE TABLE Eventos (id INTEGER PRIMARY KEY,nome TEXT NOT NULL,descricao TEXT,data DATE,local TEXT,palestranteId INTEGER,capacidade INTEGER,FOREIGN KEY (palestranteId) REFERENCES Palestrante(id));");
                stm.execute(
                        "CREATE TABLE Inscricao (id INTEGER PRIMARY KEY,id_eventos INTEGER NOT NULL,id_participante INTEGER NOT NULL,FOREIGN KEY (id_eventos) REFERENCES Eventos(id) ON DELETE CASCADE,FOREIGN KEY (id_participante) REFERENCES Participante(id) ON DELETE CASCADE);");
                System.out.println("DB criado com sucesso!");

            } catch (SQLException e) {
                System.err.println("Erro ao executar SQL: ");
                e.printStackTrace();
            }
        }
        if (opcao == 4) {
            System.out.println("pegando tabelas DB");
            try (Statement stm = conn.createStatement()) {
                ResultSet rs = stm.executeQuery(
                        "SELECT name FROM sqlite_master WHERE type = 'table' AND name NOT LIKE 'sqlite_%';");
                System.out.println("--------------");
                while (rs.next()) {
                    System.out.println("- " + rs.getString("name"));
                }
                System.out.println("--------------");

            } catch (SQLException e) {
                System.err.println("Erro ao executar SQL: ");
                e.printStackTrace();
            }
        }
        if (opcao == 5) {
            System.out.println("Inserindo no adm via metodo inserirdao DB");
            try {
                ParticipanteService ps = new ParticipanteService();
                ps.inserir("adm", "m", "adm@gmail.com", "27997774441", "organizador123", "organizador");
            } catch (Exception e) {
                System.out.println("erro");
            }

        }

        scanner.close();
    }
}

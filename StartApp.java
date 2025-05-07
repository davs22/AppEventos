import java.util.List;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

import service.EventosService;
import service.InscricaoService;
import service.PalestranteService;
import service.ParticipanteService;
import table.Eventos;
import table.Inscricao;
import table.Palestrante;
import table.Participante;
public class StartApp {
    public static void main(String[] args) {
        
        Scanner scanner = new Scanner(System.in);
        ParticipanteService ps1 = new ParticipanteService();
        EventosService es = new EventosService();
        InscricaoService is = new InscricaoService();
        PalestranteService pale = new PalestranteService();
        
        int opcao = -1;

        while (opcao!=0) {
            System.out.println("\n===== MENU PRINCIPAL =====");
            System.out.println("1 - Listar");
            System.out.println("2 - Criar");
            System.out.println("3 - Atualizar");
            System.out.println("4 - Excluir");
            System.out.println("0 - Sair");
            System.out.print("Escolha uma opção: ");

            opcao = scanner.nextInt();
        

        switch (opcao) {
            case 1:
                
                int subOpcao1 = -1;
                while (subOpcao1!=0) {
                    System.out.println("\n===== SUB-MENU =====");
                    System.out.println("1 - Listar Eventos");
                    System.out.println("2 - Listar Participantes");
                    System.out.println("3 - Listar Inscricoes");
                    System.out.println("4 - Listar Palestrantes");
                    System.out.println("0 - Sair");
                    System.out.print("Escolha uma opção: ");
                    subOpcao1 = scanner.nextInt();
                    scanner.nextLine();
                
                switch (subOpcao1) {
                    case 1:
                    
                    try {
                        List<Eventos> eventos = es.listarEventos();
                        
                        for (Eventos e : eventos) {
                            System.out.println("ID: " + e.getId());
                            System.out.println("Nome: " + e.getNome());
                            System.out.println("Descrição: " + e.getDescricao());
                            System.out.println("Data: " + e.getData());
                            System.out.println("Local: " + e.getLocal());
                            System.out.println("Palestrante ID: " + e.getPalestranteId());
                            System.out.println("Capacidade: " + e.getCapacidade());
                            System.out.println("----------------------------------");
                        }
            
                    } catch (Exception e) {
                        System.err.println("Erro ao listar eventos: " + e.getMessage());
                    }
                        break;

                    case 2:
                    
                        List<Participante> lista = ps1.listarTodos();
                        for (Participante item : lista) {
                            System.out.println("Id "+item.getId() +"    Nome: "+ item.getNome() + "    E-mail: " + item.getEmail() +
                            "    Sexo: " + item.getSexo() + "    Celular: " + item.getCelular());
                        }
                        break;

                    case 3:
                    
                        int idParticipante = 1; 
                        List<Inscricao> inscricoes = is.listarInscricoesPorParticipante(idParticipante);
                
                        for (Inscricao i : inscricoes) {
                            System.out.println("ID da inscrição: " + i.getId());
                            System.out.println("Participante: " + i.getIdParticipante());
                            System.out.println("Evento: " + i.getIdEvento());
                            System.out.println("------------------------");
                        }
            
                        break;

                    case 4:
                                        
                        List<Palestrante> lista2 = pale.listarTodos();
                        for (Palestrante itemPale : lista2) {
                            System.out.println("Id "+itemPale.getId() +"    Nome: "+ itemPale.getNome() + "   curriculo" + itemPale.getCurriculo() +
                            "    areaAtuacao: " + itemPale.getAreaAtuacao());
}
                        break;

                    case 0:
                    System.out.println("Retornando ao Menu Principal...");
                    break;
                default:
                    System.out.println("Opção inválida.");
                }
            }
                break;
            
            
            case 2:
            
            int subOpcao2 = -1;
            while (subOpcao2!=0) {
                System.out.println("\n===== SUB-MENU =====");
                System.out.println("1 - Criar Evento");
                System.out.println("2 - Criar Participante");
                System.out.println("3 - Criar Inscricao");
                System.out.println("4 - Criar Palestrante");
                System.out.println("0 - Sair");
                System.out.print("Escolha uma opção: ");
                subOpcao2 = scanner.nextInt();
                scanner.nextLine();
                break;}
            
                switch (subOpcao2) {
                    case 1:
                       
                        System.out.print("Nome: ");
                        String nomeEvento = scanner.nextLine();
                        System.out.print("Descrição: ");
                        String descricao = scanner.nextLine();
                        System.out.print("Data (dd-MM-yyyy): ");
                        String data = scanner.nextLine();
                        System.out.print("Local: ");
                        String local = scanner.nextLine();
                        System.out.print("ID do Palestrante: ");
                        int palestranteId = scanner.nextInt();
                        System.out.print("Capacidade: ");
                        int capacidade = scanner.nextInt();
                        scanner.nextLine();
                        es.criarEvento(nomeEvento, descricao, data, local, palestranteId, capacidade);
                    break;

                    case 2:
                       
                        System.out.print("Nome: ");
                            String nome = scanner.nextLine();
                            System.out.print("Sexo (M/F): ");
                            String sexo = scanner.nextLine();
                            System.out.print("Email: ");
                            String email = scanner.nextLine();
                            System.out.print("Celular: ");
                            String celular = scanner.nextLine();
                            ps1.inserir(nome, sexo, email, celular);
                            break;
                    case 3:
                        
                        System.out.print("ID do Participante: ");
                        int idP = scanner.nextInt();
                        System.out.print("ID do Evento: ");
                        int idE = scanner.nextInt();
                        scanner.nextLine();
                        is.inscreverParticipante(idP, idE);
                        break;

                    case 4:
                        
                        System.out.print("Nome: ");
                        String nomeP = scanner.nextLine();
                        System.out.print("Currículo: ");
                        String curriculo = scanner.nextLine();
                        System.out.print("Área de Atuação: ");
                        String area = scanner.nextLine();
                        pale.inserir(nomeP, curriculo, area);
                        break;

                    case 0:
                        System.out.println("Retornando ao Menu Principal...");
                        break;
                    default:
                        System.out.println("Opção inválida.");
                }
            

            case 3:
            int subOpcao3 = -1;
            while (subOpcao3!=0) {
                System.out.println("\n===== SUB-MENU =====");
                System.out.println("1 - Atualizar Dados de Eventos");
                System.out.println("2 - Atualizar Dados de Participante");
                System.out.println("0 - Voltar");
                System.out.print("Escolha uma opção: ");
                subOpcao3 = scanner.nextInt();
                scanner.nextLine();
                break;}
            
                switch (subOpcao3) {
                    case 1:
                       
                        System.out.print("ID do Evento: ");
                        int idEv = scanner.nextInt();
                        scanner.nextLine();
                        System.out.print("Novo Nome: ");
                        String nomeEv = scanner.nextLine();
                        System.out.print("Nova Descrição: ");
                        String descEv = scanner.nextLine();
                        System.out.print("Nova Data (dd-MM-yyyy): ");
                        String dataString = scanner.nextLine();
                        Date dataEv = null;
                        try {
                            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                            dataEv = sdf.parse(dataString);
                        } catch (Exception e) {
                            System.out.println("Data inválida! Use o formato dd-MM-yyyy.");
                            break;}
                        System.out.print("Novo Local: ");
                        String localEv = scanner.nextLine();
                        System.out.print("Novo ID do Palestrante: ");
                        int novoPalestranteId = scanner.nextInt();
                        System.out.print("Nova Capacidade: ");
                        int novaCapacidade = scanner.nextInt();
                        scanner.nextLine();
                        es.editarEvento(idEv, nomeEv, descEv, dataEv, localEv, novoPalestranteId, novaCapacidade);
                        break;
                    case 2:
                       
                        System.out.print("ID do Participante: ");
                        int idPart = scanner.nextInt();
                        scanner.nextLine();
                        System.out.print("Novo Nome: ");
                        String nomeNovo = scanner.nextLine();
                        System.out.print("Sexo: ");
                        String sexoNovo = scanner.nextLine();
                        System.out.print("Email: ");
                        String emailNovo = scanner.nextLine();
                        System.out.print("Celular: ");
                        String celularNovo = scanner.nextLine();
                        ps1.atualizarParticipante(idPart, nomeNovo, sexoNovo, emailNovo, celularNovo);
                        break;

                    case 0:
                        System.out.println("Retornando ao Menu Principal...");
                        break;
                    default:
                        System.out.println("Opção inválida.");
                }
                break;
            
            case 4:
                
            int subOpcao4 = -1;
            while (subOpcao4!=0) {
                System.out.println("\n===== SUB-MENU =====");
                System.out.println("1 - Excluir Evento");
                System.out.println("2 - Excluir Inscricao");
                System.out.println("3 - Excluir Palestrante");
                System.out.println("0 - Voltar");
                System.out.print("Escolha uma opção: ");
                subOpcao4 = scanner.nextInt();
                scanner.nextLine();
                break;}
            
                switch (subOpcao4) {
                    case 1:
                        
                        System.out.print("ID do Evento a excluir: ");
                        int idExcluirE = scanner.nextInt();
                        scanner.nextLine();
                        es.excluirEvento(idExcluirE);
                        break;
                    case 2:
                        
                        System.out.print("ID do Participante: ");
                        int idPartEx = scanner.nextInt();
                        System.out.print("ID do Evento: ");
                        int idEvtEx = scanner.nextInt();
                        scanner.nextLine();
                        is.excluirInscricao(idPartEx, idEvtEx);
                        break;
                    case 3:
                        
                    System.out.print("ID do Palestrante a excluir: ");
                        int idExcluirPal = scanner.nextInt();
                        scanner.nextLine();
                        pale.excluirPalestrante(idExcluirPal);
                        break;
                    default:
                        System.out.println("Opção inválida.");
                }
                break;

            case 0:
            System.out.println("Encerrando o programa...");
            break;

                    default:
                System.out.println("Opção inválida.");
                break;
                }
        }
    scanner.close();
    }
}



    

  

    


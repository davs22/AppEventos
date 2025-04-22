import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

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
        ParticipanteService ps = new ParticipanteService();
        List<Participante> lista = ps.listarTodos();
        for (Participante item : lista) {
            System.out.println("Nome: " + item.getNome() + "    E-mail: " + item.getEmail());
        }
        lista = ps.listarPorParamentro("Vito", null);
        for (Participante item : lista) {
            System.out.println("Nome: " + item.getNome() + "    Celular: " + item.getCelular());
        }
        Participante p = new Participante();
        p = ps.buscarPorId(1);
        System.out.println("Id: " + p.getId() + "   Nome: " + p.getNome());

        // System.out.println(ps.inserir("Ana", "F", "ana@gmail.com", "(27) 99734
        // 9870"));


          try {
            EventosService eventosService = new EventosService();

            // Criando um novo evento
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Date dataEvento = sdf.parse("10/05/2025");
            String msgCriacao = eventosService.criarEvento("Tech Summit", "Evento de tecnologia", dataEvento, "Auditório A", "100");
            System.out.println(msgCriacao);

              // Editar evento
              String msgEdicao = eventosService.editarEvento(1, "Tech Summit Atualizado", "Evento de tecnologia atualizado", sdf.parse("11/06/2025"), "Auditório B", "150");
              System.out.println(msgEdicao);
  
          } catch (Exception e) {
              System.out.println("Erro no sistema: " + e.getMessage());
              e.printStackTrace();
          }

            // Listando todos os eventos
            List<Eventos> listaEventos = eventosService.listarEventos();
            System.out.println("\n--- Lista de Eventos ---");
            for (Eventos e : listaEventos) {
                System.out.println("ID: " + e.getId() + " | Nome: " + e.getNome() + " | Data: " + e.getData());
            }

            // Buscar evento por parâmetro (exemplo: nome + data string)
            Eventos eventoFiltrado = eventosService.listarPorParametro("Eduardo lindo", "29/01/2023" );
            if (eventoFiltrado != null) {
                System.out.println("\nEvento encontrado: " + eventoFiltrado.getNome() + " em " + eventoFiltrado.getLocal());
            } else {
                System.out.println("\nEvento não encontrado.");
            }

            // Associar um palestrante ao evento
            int eventoId = 1; // ID que você quer testar
            int palestranteId = 2;

            if (eventosService.palestranteExiste(palestranteId)) {
                String msgAssoc = eventosService.associarPalestrante(eventoId, palestranteId);
                System.out.println(msgAssoc);
            } else {
                System.out.println("Palestrante com ID " + palestranteId + " não existe.");
            }

            // Excluir evento
            int eventoExcluirId = 3; // exemplo
            String msgExclusao = eventosService.excluirEvento(eventoExcluirId);
            System.out.println(msgExclusao);

        } catch (Exception e) {
            System.out.println("Erro no sistema: " + e.getMessage());
            e.printStackTrace();
        }


           InscricaoService inscricaoService = new InscricaoService();

        int idParticipante = 1;
        int idEvento = 2;

        // 1. Inscrever participante em um evento
        String msgInscricao = inscricaoService.inscreverParticipante(idParticipante, idEvento);
        System.out.println("Inscrição: " + msgInscricao);

        // 2. Verificar se o participante está inscrito
        boolean inscrito = inscricaoService.verificarInscricao(idParticipante, idEvento);
        System.out.println("Participante está inscrito? " + inscrito);

        // 3. Listar todas as inscrições de um participante
        List<Inscricao> inscricoes = inscricaoService.listarInscricoesPorParticipante(idParticipante);
        System.out.println("\nInscrições do participante " + idParticipante + ":");
        for (Inscricao i : inscricoes) {
            System.out.println("Evento ID: " + i.getIdEvento() + " | Participante ID: " + i.getIdParticipante());
        }

        // 4. Solicitar certificado
        String certificado = inscricaoService.solicitarCertificado(idParticipante, idEvento);
        System.out.println("Solicitação de certificado: " + certificado);

        // 5. Excluir uma inscrição
        String msgExclusao = inscricaoService.excluirInscricao(idParticipante, idEvento);
        System.out.println("Exclusão da inscrição: " + msgExclusao);


           // Criando instância do PalestranteService
        PalestranteService palestranteService = new PalestranteService();

        // Listando todos os palestrantes
        List<Palestrante> lista = palestranteService.listarTodos();
        System.out.println("--- Lista de Palestrantes ---");
        for (Palestrante item : lista) {
            System.out.println("Nome: " + item.getNome() + " | Currículo: " + item.getCurriculo());
        }

        // Listando palestrantes por parâmetro (nome)
        lista = palestranteService.listarPorParamentro("João", null); // Substitua por parâmetros válidos
        System.out.println("\n--- Palestrantes Filtrados ---");
        for (Palestrante item : lista) {
            System.out.println("Nome: " + item.getNome() + " | Área de Atuação: " + item.getAreaAtuacao());
        }

        // Buscando palestrante por ID
        Palestrante p = palestranteService.buscarPorId(1); // Substitua por ID válido
        System.out.println("\n--- Palestrante Encontrado ---");
        System.out.println("ID: " + p.getId() + " | Nome: " + p.getNome());

        // Buscando palestrante por Currículo
        Palestrante pCurriculo = palestranteService.buscarPorCurriculo("Currículo de Exemplo"); // Substitua por currículo válido
        System.out.println("\n--- Palestrante por Currículo ---");
        System.out.println("Nome: " + pCurriculo.getNome() + " | Currículo: " + pCurriculo.getCurriculo());

        // Buscando palestrante por Área de Atuação
        Palestrante pAreaAtuacao = palestranteService.buscarPorAreaAtuacao("Tecnologia"); // Substitua por área válida
        System.out.println("\n--- Palestrante por Área de Atuação ---");
        System.out.println("Nome: " + pAreaAtuacao.getNome() + " | Área de Atuação: " + pAreaAtuacao.getAreaAtuacao());

        // Inserindo um novo palestrante
        String msgInsercao = palestranteService.inserir("Carlos", "Currículo de Carlos", "Desenvolvimento de Software");
        System.out.println("\n--- Inserção de Palestrante ---");
        System.out.println(msgInsercao);
    }
    }
  

    


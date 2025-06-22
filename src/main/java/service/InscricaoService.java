package service;

import dao.InscricaoDao;
import java.util.List;
import table.Inscricao;
import util.SQLiteConnection;

public class InscricaoService {

    private InscricaoDao dao;
    private ParticipanteService participanteService;
    private EventosService eventosService;

    public InscricaoService() {
        this.dao = new InscricaoDao(new SQLiteConnection());
        this.participanteService = new ParticipanteService();
        this.eventosService = new EventosService();
    }

    public InscricaoService(InscricaoDao dao, ParticipanteService participanteService, EventosService eventosService) {
        this.dao = dao;
        this.participanteService = participanteService;
        this.eventosService = eventosService;
    }

    public String inscreverParticipante(int idParticipante, int idEventos) {
        // Verifica se participante existe
        if (!participanteService.participanteExiste(idParticipante)) {
            return "Erro: Participante não encontrado.";
        }
        // Verifica se evento existe
        if (!eventosService.eventoExiste(idEventos)) {
            return "Erro: Evento não encontrado.";
        }
        // Verifica se participante já está inscrito no evento
        if (dao.verificarInscricao(idParticipante, idEventos)) {
            return "Participante já inscrito neste evento!";
        }

        // Se passou nas verificações, realiza a inscrição
        return dao.inscreverParticipante(idParticipante, idEventos);
    }

    public List<Inscricao> listarInscricoesComDetalhes() {
    return dao.listarInscricoesComDetalhes();
    }


    public boolean verificarInscricao(int idParticipante, int idEvento) {
        return dao.verificarInscricao(idParticipante, idEvento);
    }

    public String excluirInscricao(int idParticipante, int idEvento) {
        // Verifica se participante existe
        if (!participanteService.participanteExiste(idParticipante)) {
            return "Erro: Participante não encontrado.";
        }
        // Verifica se evento existe
        if (!eventosService.eventoExiste(idEvento)) {
            return "Erro: Evento não encontrado.";
        }
        // Realiza exclusão
        return dao.excluirInscricao(idParticipante, idEvento);
    }

    public String solicitarCertificado(int idParticipante, int idEvento) {
        // Verifica se participante existe
        if (!participanteService.participanteExiste(idParticipante)) {
            return "Erro: Participante não encontrado.";
        }
        // Verifica se evento existe
        if (!eventosService.eventoExiste(idEvento)) {
            return "Erro: Evento não encontrado.";
        }
        // Solicita certificado via DAO
        return dao.solicitarCertificado(idParticipante, idEvento);
    }

    public String gerarCertificadoRepresentativo(String nomeParticipante, String nomeEvento) {
        return "\n---------------------------\n" +
                "        CERTIFICADO        \n" +
                "---------------------------\n" +
                "Certificamos que " + nomeParticipante +
                " participou com êxito do evento:\n\"" + nomeEvento + "\"\n\n" +
                "Data: " + java.time.LocalDate.now() + "\n" +
                "Assinatura: ____________________\n";
    }
}

package service;

import dao.InscricaoDao;

import java.time.LocalDate;
import java.util.List;
import table.Eventos;
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
        if (!participanteService.participanteExiste(idParticipante)) {
            return "Erro: Participante não encontrado.";
        }
        if (!eventosService.eventoExiste(idEventos)) {
            return "Erro: Evento não encontrado.";
        }
        if (dao.verificarInscricao(idParticipante, idEventos)) {
            return "Participante já inscrito neste evento!";
        }

        return dao.inscreverParticipante(idParticipante, idEventos);
    }

    public List<Inscricao> listarInscricoesComDetalhes() {
    return dao.listarInscricoesComDetalhes();
    }


    public boolean verificarInscricao(int idParticipante, int idEvento) {
        return dao.verificarInscricao(idParticipante, idEvento);
    }

    public String excluirInscricao(int idParticipante, int idEvento) {
        if (!participanteService.participanteExiste(idParticipante)) {
            return "Erro: Participante não encontrado.";
        }
        if (!eventosService.eventoExiste(idEvento)) {
            return "Erro: Evento não encontrado.";
        }
        return dao.excluirInscricao(idParticipante, idEvento);
    }

    public String solicitarCertificado(int idParticipante, int idEvento) {
    if (!participanteService.participanteExiste(idParticipante)) {
        return "Erro: Participante não encontrado.";
    }

    if (!eventosService.eventoExiste(idEvento)) {
        return "Erro: Evento não encontrado.";
    }

    try {
        java.util.Date dataEventoUtil = eventosService.buscarEventoPorId(idEvento).getData();
        LocalDate dataEventoLocal = new java.sql.Date(dataEventoUtil.getTime()).toLocalDate();

        if (dataEventoLocal.isAfter(LocalDate.now())) {
            return "O evento ainda não aconteceu. O certificado estará disponível após a data do evento.";
        }

        return dao.solicitarCertificado(idParticipante, idEvento);

    } catch (Exception e) {
        e.printStackTrace();
        return "Erro ao gerar certificado: " + e.getMessage();
    }
}

    public List<Eventos> listarEventosPorParticipante(int idParticipante) {
    return dao.listarEventosPorParticipante(idParticipante);
    }

    public List<Inscricao> listarInscricoesComDetalhesPorParticipante(int idParticipante) {
    return dao.listarInscricoesComDetalhesPorParticipante(idParticipante);
    }

}

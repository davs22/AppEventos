package service;

import dao.InscricaoDao;
import java.util.List;
import table.Inscricao;
import util.SQLiteConnection;

public class InscricaoService {
    private InscricaoDao dao;

    public InscricaoService() {
        this.dao = new InscricaoDao(new SQLiteConnection());
    }

    public String inscreverParticipante(int idParticipante, int idEvento) {
        return dao.inscreverParticipante(idParticipante, idEvento);
    }

    public List<Inscricao> listarInscricoesPorParticipante(int idParticipante) {
        return dao.listarInscricoesPorParticipante(idParticipante);
    }

    public boolean verificarInscricao(int idParticipante, int idEvento) {
        return dao.verificarInscricao(idParticipante, idEvento);
    }

    public String excluirInscricao(int idParticipante, int idEvento) {
        return dao.excluirInscricao(idParticipante, idEvento);
    }

    public String solicitarCertificado(int idParticipante, int idEvento) {
        return dao.solicitarCertificado(idParticipante, idEvento);
    }
}

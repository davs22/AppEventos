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
    
    public InscricaoService(InscricaoDao dao) {
        this.dao = dao;
    }
    
    public String inscreverParticipante(int idParticipante, int idEventos) {
        return dao.inscreverParticipante(idParticipante, idEventos);
    }

    public List<Inscricao> listarInscricoesPorParticipante(int idParticipante) {
        return this.dao.listarInscricoesPorParticipante(idParticipante);
    }

    public boolean verificarInscricao(int idParticipante, int idEvento) {
        return dao.verificarInscricao(idParticipante, idEvento);
    }

    public String excluirInscricao(int idParticipante, int idEvento) {
        return dao.excluirInscricao(idParticipante, idEvento);
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

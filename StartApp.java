import java.sql.SQLException;
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
        ps.inserir(null, null, null, null);

        EventosService es = new EventosService();
       
        es.criarEvento(null, null, null, null, null);
        es.editarEvento(0, null, null, null, null, null);
        es.associarPalestrante(0, 0);
        es.excluirEvento(0);
        
        InscricaoService is = new InscricaoService();
        is.inscreverParticipante(0, 0);
        is.excluirInscricao(0, 0);
        is.solicitarCertificado(0, 0);

        PalestranteService ps2 = new PalestranteService();
        ps2.inserir(null, null, null);
    }
}
  

    


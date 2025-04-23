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
    
    }
}
  

    


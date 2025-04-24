import java.util.List;
import java.text.SimpleDateFormat;
import java.util.Date;

import service.EventosService;
import service.ParticipanteService;
import table.Participante;

public class StartApp {
    public static void main(String[] args) {
        
        ParticipanteService ps1 = new ParticipanteService();
        
        ps1.atualizarParticipante(1, "Victor", "M", "victor@gmail.com", "(27) 99982552");
        
        List<Participante> lista = ps1.listarTodos();
        for (Participante item : lista) {
            System.out.println("Id "+item.getId() +"    Nome: "+ item.getNome() + "    E-mail: " + item.getEmail() +
            "    Sexo: " + item.getSexo() + "    Celular: " + item.getCelular());
        }
    
        /*lista = ps1.listarPorParamentro("Vito", null);
        for (Participante item : lista) {
            System.out.println("Nome: " + item.getNome() + "    Celular: " + item.getCelular());
        }
        Participante p = new Participante();
        p = ps1.buscarPorId(1);
        System.out.println("Id: " + p.getId() + "   Nome: " + p.getNome());*/

        //System.out.println(ps1.inserir("Ana", "F", "ana@gmail.com", "(27) 99734676"
        //));
        
        //ps.inserir("Matheus", "M", "matheus@gmail.com", "(27) 99563224");

        EventosService es = new EventosService();

        es.criarEvento("GamesCon", "Encontro de jogadores", "22-05-2025", "Sao Paulo", 1, "50");
        //es.editarEvento(0, null, null, null, null, null);
        //es.associarPalestrante(0, 0);
        //es.excluirEvento(0);
        
        //InscricaoService is = new InscricaoService();
        //is.inscreverParticipante(0, 0);
        //is.excluirInscricao(0, 0);
        //is.solicitarCertificado(0, 0); 

        //PalestranteService ps2 = new PalestranteService();
        //ps2.inserir("Matheus", "Empresario, full stack e gamer", "Tecnologia");
    }
}
  

    


import java.util.List;
import java.text.SimpleDateFormat;
import java.util.Date;

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
        
        ParticipanteService ps1 = new ParticipanteService();
        ps1.atualizarParticipante(1, "Victor", "M", "victor@gmail.com", "(27) 99982552");
        
        List<Participante> lista = ps1.listarTodos();
        for (Participante item : lista) {
            System.out.println("Id "+item.getId() +"    Nome: "+ item.getNome() + "    E-mail: " + item.getEmail() +
            "    Sexo: " + item.getSexo() + "    Celular: " + item.getCelular());
        }
    
        EventosService es = new EventosService();
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

        InscricaoService is = new InscricaoService();
        int idParticipante = 1; 
        List<Inscricao> inscricoes = is.listarInscricoesPorParticipante(idParticipante);

        for (Inscricao i : inscricoes) {
            System.out.println("ID da inscrição: " + i.getId());
            System.out.println("Participante: " + i.getIdParticipante());
            System.out.println("Evento: " + i.getIdEvento());
            System.out.println("------------------------");
        }

        PalestranteService pale = new PalestranteService();

        List<Palestrante> lista2 = pale.listarTodos();
        for (Palestrante itemPale : lista2) {
            System.out.println("Id "+itemPale.getId() +"    Nome: "+ itemPale.getNome() + "   curriculo" + itemPale.getCurriculo() +
            "    areaAtuacao: " + itemPale.getAreaAtuacao());
}
        ps1.inserir(null, null, null, null);
        ps1.atualizarParticipante(idParticipante, null, null, null, null);
        es.criarEvento(null, null, null, null, idParticipante, idParticipante);
        es.editarEvento(idParticipante, null, null, null, null, idParticipante, idParticipante);
        es.associarPalestrante(idParticipante, idParticipante);
        es.excluirEvento(idParticipante);
        is.inscreverParticipante(idParticipante, idParticipante);
        is.verificarInscricao(idParticipante, idParticipante);
        is.excluirInscricao(idParticipante, idParticipante);
        pale.inserir(null, null, null);
        pale.excluirPalestrante(null);
        
}

    }

  

    


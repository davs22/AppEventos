import java.util.ArrayList;

import service.*;
import table.Inscricao;


public class StartApp {

      public static void main(String[] args) {
        
        EventosService es = new EventosService();
        //es.criarEvento("computação", "computadores e escritorio", "27-02-2025", "shopping vitorio - reserv", 1,50);
        if(es.eventoExiste(2)){
            System.out.println("opa");
        }else{System.out.println("vish");}
        
        PalestranteService ps = new PalestranteService();
        if (ps.palestranteExiste(2)) {
            System.out.println("slameas");
        }else{System.out.println("aaaaa");}
        
        //ps.inserir("clever", "tecnico Ti", "informatica");
        InscricaoService is = new InscricaoService();
        //is.inscreverParticipante(2, 1);

        
ArrayList<Inscricao> asd = (ArrayList<Inscricao>) is.listarInscricoesPorParticipante(2);   
for(Inscricao item : asd){
    System.out.println(item.getId()+": --> event): "+item.getIdEvento());
}     
      }
}
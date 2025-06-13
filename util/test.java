package util;

import java.util.List;

import service.*;
import table.Palestrante;
import table.Participante;

public class test {
    public static void main(String[] args) {

EventosService es = new EventosService();
PalestranteService palS = new PalestranteService();
InscricaoService is = new InscricaoService();
ParticipanteService parS = new ParticipanteService();

//criando particpantes e exibindo
    parS.inserir("cleber", "M", "cleber@gmail.com", "(27)888882222", "cleber123");
    parS.inserir("rosangela", "F", "rosangela@gmail.com", "(27)333330000", "rosangela123");

    List<Participante> lista = parS.listarPorParamentro("id", "2");
    List<Participante> lista2 = parS.listarPorParamentro("id", "3");
        System.out.println(lista.get(0).getNome());
        System.out.println(lista2.get(0).getNome());
    
//criando palestrantes
    palS.inserir("romulo", "ciencias", "astronomia");
    palS.inserir("rita", "portugues", "professora");
    
    List<Palestrante> pale1 = palS.listarPorParamentro("areaAtuacao", "astronomia");
    List<Palestrante> pale2 = palS.listarPorParamentro("curriculo", "portugues");
        System.out.println(pale1.get(0).getNome());
        System.out.println(pale2.get(0).getNome());

//criando eventos
    es.criarEvento("orientção das estrelas", "aula teorica abordando gravidade e direções", "23-11-2025","plaza", 1, 200);
    es.criarEvento("evolução do portugues", "como se formou o portugus de hoje em dia que conehcemos", "22-12-2025", "plaza", 2, 200);

if(es.eventoExiste(1)){System.out.println("evento existe OK");}else{System.out.println("evento não existe");}
if(es.eventoExiste(2)){System.out.println("evento existe OK");}else{System.out.println("evento não existe");}

is.inscreverParticipante(1, 1);
is.inscreverParticipante(1, 2);
is.inscreverParticipante(2, 1);
is.inscreverParticipante(2, 2);

is.excluirInscricao(1, 2);

if(is.verificarInscricao(1, 1)){
    System.out.println("esta inscrito");
}else{System.out.println("não esta inscrito");}
if(is.verificarInscricao(1, 2)){
    System.out.println("esta inscrito");
}else{System.out.println("não esta inscrito");}
if(is.verificarInscricao(2, 1)){
    System.out.println("esta inscrito");
}else{System.out.println("não esta inscrito");}
if(is.verificarInscricao(2, 2)){
    System.out.println("esta inscrito");
}else{System.out.println("não esta inscrito");}

      }
}
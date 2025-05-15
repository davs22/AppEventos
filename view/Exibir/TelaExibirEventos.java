package view.Exibir;

import service.EventosService;
import table.Eventos;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class TelaExibirEventos extends JFrame {

    public TelaExibirEventos() {
        setTitle("Lista de eventos");
        setSize(500, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        
        try {
        EventosService es = new EventosService();
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
            
                    
       
        String[] colunas = {"Id", "Nome", "Descricao", "Data", "Local", "Palestrante ID", "Capacidade"};
        Object[][] dados = new Object[eventos.size()][colunas.length];

        for (int i = 0; i < eventos.size(); i++) {
            Eventos p = eventos.get(i);
            dados[i][0] = p.getId();
            dados[i][1] = p.getNome();
            dados[i][2] = p.getDescricao();
            dados[i][3] = p.getData();
            dados[i][4] = p.getLocal();
            dados[i][5] = p.getPalestranteId();
            dados[i][6] = p.getCapacidade();
        }
        

        JTable tabela = new JTable(dados, colunas);
        JScrollPane scrollPane = new JScrollPane(tabela);

        add(scrollPane, BorderLayout.CENTER);
        setVisible(true);
    } catch (Exception e) {
        System.err.println("Erro ao listar eventos: " + e.getMessage());
    }
    }
}

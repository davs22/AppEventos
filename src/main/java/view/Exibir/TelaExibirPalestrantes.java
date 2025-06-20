package view.Exibir;

import service.PalestranteService;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import table.Palestrante;

public class TelaExibirPalestrantes extends JFrame {

    public TelaExibirPalestrantes() {
        setTitle("Lista de palestrantes");
        setSize(500, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        
        try {
            PalestranteService ps = new PalestranteService();
        List<Palestrante> palestrantes = ps.listarTodos();
                        for (Palestrante itemPale : palestrantes) {
                            System.out.println("Id "+itemPale.getId() +"    Nome: "+ itemPale.getNome() + "   curriculo" + itemPale.getCurriculo() +
                            "    areaAtuacao: " + itemPale.getAreaAtuacao());
}
            
        String[] colunas = {"Nome", "Curriculo", "areaAtuacao"};
        Object[][] dados = new Object[palestrantes.size()][colunas.length];

        for (int i = 0; i < palestrantes.size(); i++) {
            Palestrante p = palestrantes.get(i);
            dados[i][0] = p.getNome();
            dados[i][1] = p.getCurriculo();
            dados[i][2] = p.getAreaAtuacao();
        }

        JTable tabela = new JTable(dados, colunas);
        JScrollPane scrollPane = new JScrollPane(tabela);

        add(scrollPane, BorderLayout.CENTER);
        setVisible(true);
    } catch (Exception e) {
        System.err.println("Erro ao listar palestrantes: " + e.getMessage());
    }
    }
}

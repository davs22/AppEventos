package view.Exibir;

import service.ParticipanteService;
import table.Participante;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class TelaExibirParticipantes extends JFrame {

    public TelaExibirParticipantes() {
        setTitle("Lista de participantes");
        setSize(500, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        
        try {
            ParticipanteService ps = new ParticipanteService();
        List<Participante> participante = ps.listarTodos();
                       for (Participante item : participante) {
                            System.out.println("Id "+item.getId() +"    Nome: "+ item.getNome() + "    E-mail: " + item.getEmail() +
                            "    Sexo: " + item.getSexo() + "    Celular: " + item.getCelular());
                        }
            
                    
       
        String[] colunas = {"Id", "Nome", "Email", "Sexo", "Celular"};
        Object[][] dados = new Object[participante.size()][colunas.length];

        for (int i = 0; i < participante.size(); i++) {
            Participante p = participante.get(i);
            dados[i][0] = p.getId();
            dados[i][1] = p.getNome();
            dados[i][2] = p.getEmail();
            dados[i][3] = p.getSexo();
            dados[i][4] = p.getCelular();
        }

        JTable tabela = new JTable(dados, colunas);
        JScrollPane scrollPane = new JScrollPane(tabela);

        add(scrollPane, BorderLayout.CENTER);
        setVisible(true);
    } catch (Exception e) {
        System.err.println("Erro ao listar participantes: " + e.getMessage());
    }
    }
}

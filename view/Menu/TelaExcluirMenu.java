package view.Menu;

import javax.swing.*;
import view.Excluir.TelaExcluirEventos;
import view.Excluir.TelaExcluirInscricao;
import view.Excluir.TelaExcluirPalestrante;

public class TelaExcluirMenu extends JFrame {
    public TelaExcluirMenu() {
        setTitle("Menu de Exclusão");
        setSize(400, 300);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        JButton btnExcluirEvento = new JButton("Excluir Evento");
        JButton btnExcluirInscricao = new JButton("Excluir Inscrição");
        JButton btnExcluirPalestrante = new JButton("Excluir Palestrante");

        btnExcluirEvento.setBounds(100, 30, 200, 30);
        btnExcluirInscricao.setBounds(100, 80, 200, 30);
        btnExcluirPalestrante.setBounds(100, 130, 200, 30);

        add(btnExcluirEvento);
        add(btnExcluirInscricao);
        add(btnExcluirPalestrante);

        btnExcluirEvento.addActionListener(e -> new TelaExcluirEventos().setVisible(true));
        btnExcluirInscricao.addActionListener(e -> new TelaExcluirInscricao().setVisible(true));
        btnExcluirPalestrante.addActionListener(e -> new TelaExcluirPalestrante().setVisible(true));
    }
}

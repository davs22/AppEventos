package view.Menu;

import javax.swing.*;
import view.Excluir.TelaExcluirEventos;
import view.Excluir.TelaExcluirPalestrante;

public class MenuExcluirOrganizador extends JFrame {
    public MenuExcluirOrganizador() {
        setTitle("Menu de Criação");
        setSize(400, 300);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        JButton btnExcluirEvento = new JButton("Cancelar Evento");
        JButton btnExcluirPalestrante = new JButton("Excluir Palestrante");

        btnExcluirEvento.setBounds(100, 30, 200, 30);
        btnExcluirPalestrante.setBounds(100, 80, 200, 30);

        add(btnExcluirEvento);
        add(btnExcluirPalestrante);

        btnExcluirEvento.addActionListener(e -> new TelaExcluirEventos().setVisible(true));
        btnExcluirPalestrante.addActionListener(e -> new TelaExcluirPalestrante().setVisible(true));
    }
}

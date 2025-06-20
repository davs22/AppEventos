package view.Menu;

import javax.swing.*;
import view.Exibir.TelaExibirParticipantes;
import view.Exibir.TelaExibirEventos;
import view.Exibir.TelaExibirPalestrantes;


public class MenuExibirOrganizador extends JFrame {
    public MenuExibirOrganizador() {
        setTitle("Menu de Exibição");
        setSize(400, 300);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        JButton btnExibirParticipantes = new JButton("Visualizar participantes");
        JButton btnExibirEventos = new JButton("Visualizar eventos");
        JButton btnExibirPalestrantes = new JButton("Visualizar palestrantes");


        btnExibirParticipantes.setBounds(100, 30, 200, 30);
        btnExibirEventos.setBounds(100, 80, 200, 30);
        btnExibirPalestrantes.setBounds(100, 130, 200, 30);

        add(btnExibirParticipantes);
        add(btnExibirEventos);
        add(btnExibirPalestrantes);

        btnExibirParticipantes.addActionListener(e -> new TelaExibirParticipantes().setVisible(true));
        btnExibirEventos.addActionListener(e -> new TelaExibirEventos().setVisible(true));
        btnExibirPalestrantes.addActionListener(e -> new TelaExibirPalestrantes().setVisible(true));
    }
}
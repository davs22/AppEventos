package view.Menu;

import javax.swing.*;
import view.Exibir.TelaExibirEventos;
import view.Exibir.TelaExibirParticipantes;
import view.Exibir.TelaExibirPalestrantes;

public class TelaExibirMenu extends JFrame {
    public TelaExibirMenu() {
        setTitle("Menu de Exibir");
        setSize(400, 300);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        JButton btnExibirEventos = new JButton("Exibir Eventos");
        JButton btnExibirParticipantes = new JButton("Exibir Participantes");
        JButton btnExibirPalestrantes = new JButton("Exibir Palestrantes");

        btnExibirEventos.setBounds(100, 30, 200, 30);
        btnExibirParticipantes.setBounds(100, 80, 200, 30);
        btnExibirPalestrantes.setBounds(100, 130, 200, 30);

        add(btnExibirEventos);
        add(btnExibirParticipantes);
        add(btnExibirPalestrantes);

        btnExibirEventos.addActionListener(e -> new TelaExibirEventos().setVisible(true));
        btnExibirParticipantes.addActionListener(e -> new TelaExibirParticipantes().setVisible(true));
        btnExibirPalestrantes.addActionListener(e -> new TelaExibirPalestrantes().setVisible(true));
    }
}

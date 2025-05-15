package view.Menu;

import javax.swing.*;
import view.Atualizar.TelaAtualizarEventos;
import view.Atualizar.TelaAtualizarParticipantes;

public class TelaAtualizarMenu extends JFrame {
    public TelaAtualizarMenu() {
        setTitle("Menu de Atualizar");
        setSize(400, 300);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        JButton btnAtualizarEventos = new JButton("Atualizar Eventos");
        JButton btnAtualizarParticipantes = new JButton("Atualizar Participantes");

        btnAtualizarEventos.setBounds(100, 30, 200, 30);
        btnAtualizarParticipantes.setBounds(100, 80, 200, 30);

        add(btnAtualizarEventos);
        add(btnAtualizarParticipantes);

        btnAtualizarEventos.addActionListener(e -> new TelaAtualizarEventos().setVisible(true));
        btnAtualizarParticipantes.addActionListener(e -> new TelaAtualizarParticipantes().setVisible(true));
}
}
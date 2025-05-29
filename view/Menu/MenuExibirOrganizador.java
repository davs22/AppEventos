package view.Menu;

import javax.swing.*;
import view.Exibir.TelaExibirParticipantes;
import view.Exibir.TelaExibirEventos;
import view.Exibir.TelaExibirPalestrantes;


public class MenuExibirOrganizador extends JFrame {
    public MenuExibirOrganizador() {
        setTitle("Menu de Criação");
        setSize(400, 300);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        JButton btnExibirParticipantes = new JButton("Cadastrar");
        JButton btnExibirEventos = new JButton("Se inscrever");
        JButton btnExibirPalestrantes = new JButton("Se inscrever");


        btnExibirParticipantes.setBounds(100, 30, 200, 30);
        btnExibirEventos.setBounds(100, 80, 200, 30);
        btnExibirPalestrantes.setBounds(100, 80, 200, 30);

        add(btnExibirParticipantes);
        add(btnExibirEventos);
        add(btnExibirPalestrantes);

        btnExibirParticipantes.addActionListener(e -> new TelaExibirParticipantes().setVisible(true));
        btnExibirEventos.addActionListener(e -> new TelaExibirEventos().setVisible(true));
        btnExibirPalestrantes.addActionListener(e -> new TelaExibirPalestrantes().setVisible(true));
    }
}
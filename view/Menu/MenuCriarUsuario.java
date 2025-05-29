package view.Menu;

import javax.swing.*;
import view.Criar.TelaInserirParticipante;
import view.Criar.TelaCriarInscricao;

public class MenuCriarUsuario extends JFrame {
    public MenuCriarUsuario() {
        setTitle("Menu de Criação");
        setSize(400, 300);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        JButton btnCriarParticipante = new JButton("Cadastrar");
        JButton btnCriarInscricao = new JButton("Se inscrever");

        btnCriarParticipante.setBounds(100, 30, 200, 30);
        btnCriarInscricao.setBounds(100, 80, 200, 30);

        add(btnCriarParticipante);
        add(btnCriarInscricao);

        btnCriarParticipante.addActionListener(e -> new TelaInserirParticipante().setVisible(true));
        btnCriarInscricao.addActionListener(e -> new TelaCriarInscricao().setVisible(true));
    }
}
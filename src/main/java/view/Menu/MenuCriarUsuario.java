package view.Menu;

import javax.swing.*;
import view.Criar.TelaCriarInscricao;

public class MenuCriarUsuario extends JFrame {
    public MenuCriarUsuario() {
        setTitle("Menu de Criação");
        setSize(400, 300);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        JButton btnCriarInscricao = new JButton("Se inscrever");

        btnCriarInscricao.setBounds(100, 30, 200, 30);

        add(btnCriarInscricao);

        btnCriarInscricao.addActionListener(e -> new TelaCriarInscricao().setVisible(true));
    }
}
package view;

import javax.swing.*;
import java.awt.*;

import table.Participante;
import utils.SessaoUsuario;
import view.Inicio.TelaCadastrar;
import view.Inicio.TelaOrganizador;
import view.Inicio.TelaUsuario;
import service.ParticipanteService;

public class TelaInicial extends JFrame {
    private JTextField emailField;
    private JPasswordField senhaField;
    private JButton loginBtn, cadastroBtn;
    private ParticipanteService participanteService;

    public TelaInicial() {
        setTitle("Sistema de Eventos");
        setSize(350, 200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridBagLayout());
        setLocationRelativeTo(null); 

        participanteService = new ParticipanteService();

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        add(new JLabel("Email:"), gbc);

        emailField = new JTextField(20);
        gbc.gridx = 1;
        add(emailField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        add(new JLabel("Senha:"), gbc);

        senhaField = new JPasswordField(20);
        gbc.gridx = 1;
        add(senhaField, gbc);

        loginBtn = new JButton("Login");
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(loginBtn, gbc);

        cadastroBtn = new JButton("Cadastro");
        gbc.gridx = 1;
        add(cadastroBtn, gbc);

        loginBtn.addActionListener(e -> login());
        cadastroBtn.addActionListener(e -> abrirCadastro());
    }

    private void login() {
    String email = emailField.getText();
    String senha = new String(senhaField.getPassword());

    Participante participante = participanteService.login(email, senha);

    if (participante != null) {
        SessaoUsuario.idParticipanteLogado = participante.getId();

        JOptionPane.showMessageDialog(this, "Bem-vindo, " + participante.getNome());

        if ("organizador".equalsIgnoreCase(participante.getTipo())) {
            new TelaOrganizador().setVisible(true);
        } else {
            new TelaUsuario().setVisible(true);
        }

        dispose();
    } else {
        JOptionPane.showMessageDialog(this, "Login falhou!");
    }
}

    private void abrirCadastro() {
        new TelaCadastrar().setVisible(true);
        dispose();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new TelaInicial().setVisible(true);
        });
    }
}

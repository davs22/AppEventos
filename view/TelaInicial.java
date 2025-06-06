package view;
import javax.swing.*;

import table.Participante;
import service.ParticipanteService;
public class TelaInicial extends JFrame {
    private JTextField emailField;
    private JPasswordField senhaField;
    private JButton loginBtn, cadastroBtn;
    private ParticipanteService participanteService;

    public TelaInicial() {
        setTitle("Sistema de Eventos");
        setSize(300, 200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        participanteService = new ParticipanteService();

        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setBounds(30, 20, 80, 25);
        add(emailLabel);

        emailField = new JTextField();
        emailField.setBounds(100, 20, 150, 25);
        add(emailField);

        JLabel senhaLabel = new JLabel("Senha:");
        senhaLabel.setBounds(30, 60, 80, 25);
        add(senhaLabel);

        senhaField = new JPasswordField();
        senhaField.setBounds(100, 60, 150, 25);
        add(senhaField);

        loginBtn = new JButton("Login");
        loginBtn.setBounds(30, 100, 100, 30);
        loginBtn.addActionListener(e -> login());
        add(loginBtn);

        cadastroBtn = new JButton("Cadastro");
        cadastroBtn.setBounds(150, 100, 100, 30);
        cadastroBtn.addActionListener(e -> abrirCadastro());
        add(cadastroBtn);
    }

    private void login() {
        String email = emailField.getText();
        String senha = new String(senhaField.getPassword());

        Participante participante = participanteService.login(email, senha);

        if (participante != null) {
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
        new TelaInicial().setVisible(true);
    }
}

package view.Inicio;

import javax.swing.*;
import java.awt.*;
import service.ParticipanteService;
import table.Participante;
import view.TelaInicial;

public class TelaCadastrar extends JFrame {

    public TelaCadastrar() {
        setTitle("Cadastrar Participante");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel painelFormulario = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        painelFormulario.add(new JLabel("Nome:"), gbc);
        gbc.gridx = 1;
        JTextField txtNome = new JTextField(20);
        painelFormulario.add(txtNome, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        painelFormulario.add(new JLabel("Sexo:"), gbc);
        gbc.gridx = 1;
        JTextField txtSexo = new JTextField(20);
        painelFormulario.add(txtSexo, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        painelFormulario.add(new JLabel("Email:"), gbc);
        gbc.gridx = 1;
        JTextField txtEmail = new JTextField(20);
        painelFormulario.add(txtEmail, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        painelFormulario.add(new JLabel("Celular:"), gbc);
        gbc.gridx = 1;
        JTextField txtCelular = new JTextField(20);
        painelFormulario.add(txtCelular, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        painelFormulario.add(new JLabel("Senha:"), gbc);
        gbc.gridx = 1;
        JPasswordField txtSenha = new JPasswordField(20);
        painelFormulario.add(txtSenha, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        JButton btnSalvar = new JButton("Salvar");
        painelFormulario.add(btnSalvar, gbc);

        JScrollPane scrollPane = new JScrollPane(painelFormulario);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        add(scrollPane);

        btnSalvar.addActionListener(e -> {
            try {
                String nome = txtNome.getText().trim();
                String sexo = txtSexo.getText().trim();
                String email = txtEmail.getText().trim();
                String celular = txtCelular.getText().trim();
                String senha = new String(txtSenha.getPassword()).trim();
                String tipo = "comum"; // Valor fixo ou pode ser selecionado por dropdown futuramente

                // Verifica se todos os campos estão preenchidos
                if (nome.isEmpty() || sexo.isEmpty() || email.isEmpty() || celular.isEmpty() || senha.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Preencha todos os campos!", "Campos obrigatórios",
                            JOptionPane.WARNING_MESSAGE);
                    return;
                }

                ParticipanteService ps = new ParticipanteService();
                String erro = ps.inserir(nome, sexo, email, celular, senha, tipo);

                switch (erro.toLowerCase()) {
                    case "sucesso":
                        JOptionPane.showMessageDialog(this, "Participante cadastrado com sucesso!");
                        dispose();
                        new TelaInicial().setVisible(true);
                        break;
                    case "email_existe":
                        JOptionPane.showMessageDialog(this, "Erro: o e-mail informado já está em uso.", "Erro",
                                JOptionPane.ERROR_MESSAGE);
                        break;
                    case "erro_insercao":
                        JOptionPane.showMessageDialog(this, "Erro ao salvar no banco de dados.", "Erro",
                                JOptionPane.ERROR_MESSAGE);
                        break;
                    case "erro_excecao":
                        JOptionPane.showMessageDialog(this, "Erro inesperado ao cadastrar. Tente novamente.", "Erro",
                                JOptionPane.ERROR_MESSAGE);
                        break;
                    default:
                        JOptionPane.showMessageDialog(this, "Erro desconhecido: " + erro, "Erro",
                                JOptionPane.ERROR_MESSAGE);
                        break;
                }

            } catch (Exception er) {
                System.err.println("Erro: " + er.getMessage());
                JOptionPane.showMessageDialog(this, "Erro inesperado: " + er.getMessage(), "Erro",
                        JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}

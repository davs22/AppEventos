package view;
import javax.swing.*;
import java.awt.*;
import service.ParticipanteService;
import table.Participante;
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

        gbc.gridx = 0; gbc.gridy = 0;
        painelFormulario.add(new JLabel("Nome:"), gbc);
        gbc.gridx = 1;
        JTextField txtNome = new JTextField(20);
        painelFormulario.add(txtNome, gbc);

        gbc.gridx = 0; gbc.gridy++;
        painelFormulario.add(new JLabel("Email:"), gbc);
        gbc.gridx = 1;
        JTextField txtEmail = new JTextField(20);
        painelFormulario.add(txtEmail, gbc);

        gbc.gridx = 0; gbc.gridy++;
        painelFormulario.add(new JLabel("Senha:"), gbc);
        gbc.gridx = 1;
        JPasswordField txtSenha = new JPasswordField(20);
        painelFormulario.add(txtSenha, gbc);

        gbc.gridx = 0; gbc.gridy++;
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

                if (nome.isEmpty() ||sexo.isEmpty() || email.isEmpty() ||celular.isEmpty() || senha.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Preencha todos os campos!");
                    return;
                }

                Participante participante = new Participante();
                participante.setNome(nome);
                participante.setSexo(sexo);
                participante.setEmail(email);
                participante.setCelular(celular);
                participante.setSenha(senha);

                ParticipanteService ps = new ParticipanteService();
                String erro = ps.inserir(nome, sexo, email, celular);

                if (erro == null) {
                    JOptionPane.showMessageDialog(this, "Participante cadastrado com sucesso!");
                    dispose(); 
                    new TelaInicial().setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(this, "Erro: " + erro);
                }

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro ao inserir participante: " + ex.getMessage());
            }
        });
    }
}

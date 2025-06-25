package view.CRUD.Criar;

import javax.swing.*;
import java.awt.*;
import service.ParticipanteService;
import view.Inicio.TelaOrganizador;

public class TelaInserirParticipante extends JFrame {

    private TelaOrganizador telaPrincipal;

    public TelaInserirParticipante(TelaOrganizador telaPrincipal) {
        this.telaPrincipal = telaPrincipal; 
        setTitle("Inserir Participante");
        setSize(400, 400);
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
        painelFormulario.add(new JLabel("Sexo:"), gbc);
        gbc.gridx = 1;
        JTextField txtSexo = new JTextField(20);
        painelFormulario.add(txtSexo, gbc);

        gbc.gridx = 0; gbc.gridy++;
        painelFormulario.add(new JLabel("Email:"), gbc);
        gbc.gridx = 1;
        JTextField txtEmail = new JTextField(20);
        painelFormulario.add(txtEmail, gbc);

        gbc.gridx = 0; gbc.gridy++;
        painelFormulario.add(new JLabel("Celular:"), gbc);
        gbc.gridx = 1;
        JTextField txtCelular = new JTextField(20);
        painelFormulario.add(txtCelular, gbc);

        gbc.gridx = 0; gbc.gridy++;
        painelFormulario.add(new JLabel("Senha:"), gbc);
        gbc.gridx = 1;
        JTextField txtSenha = new JTextField(20);
        painelFormulario.add(txtSenha, gbc);

        gbc.gridx = 0; gbc.gridy++;
        painelFormulario.add(new JLabel("Tipo:"), gbc);
        gbc.gridx = 1;
        JTextField txtTipo = new JTextField(20);
        painelFormulario.add(txtTipo, gbc);

        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0)); 
        JButton btnSalvar = new JButton("Salvar");
        JButton btnVoltar = new JButton("Voltar");

        painelBotoes.add(btnSalvar);
        painelBotoes.add(btnVoltar);
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2; 
        gbc.fill = GridBagConstraints.NONE; 
        gbc.anchor = GridBagConstraints.CENTER; 
        painelFormulario.add(painelBotoes, gbc); 

        JScrollPane scrollPane = new JScrollPane(painelFormulario);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        add(scrollPane);

        btnSalvar.addActionListener(e -> {
            try {
                String nome = txtNome.getText();
                String sexo = txtSexo.getText();
                String email = txtEmail.getText();
                String celular = txtCelular.getText();
                String senha = txtSenha.getText();
                String tipo = txtTipo.getText();

                ParticipanteService ps = new ParticipanteService();
                ps.inserir(nome, sexo, email, celular, senha, tipo);
                
                JOptionPane.showMessageDialog(this, "Participante inserido com sucesso!");

                voltarParaTelaPrincipal();

            } catch (NullPointerException ex) {
                JOptionPane.showMessageDialog(this, "Por favor, preencha todos os campos obrigatórios", "Campos Vazios", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace(); 
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro ao inserir participante: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace(); 
            }
        });

        btnVoltar.addActionListener(e -> {
            voltarParaTelaPrincipal();
        });
    }

    private void voltarParaTelaPrincipal() {
        this.dispose(); 
        if (telaPrincipal != null) {
            telaPrincipal.setVisible(true); 
            telaPrincipal.atualizarTabelaParticipantes(); 
        }
    }
}
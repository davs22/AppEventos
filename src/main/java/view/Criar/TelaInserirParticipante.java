package view.Criar;

import javax.swing.*;
import java.awt.*;
import service.ParticipanteService;
import view.TelaOrganizador;

public class TelaInserirParticipante extends JFrame {

    // 1. Campo para armazenar a referência da tela principal
    private TelaOrganizador telaPrincipal;

    // 2. Construtor que recebe a referência da tela principal
    public TelaInserirParticipante(TelaOrganizador telaPrincipal) {
        this.telaPrincipal = telaPrincipal; // Armazena a referência
        setTitle("Inserir Participante");
        setSize(400, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE); // Fecha apenas esta janela, não o aplicativo

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

        // Painel para os botões "Salvar" e "Voltar"
        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0)); // Alinha à direita
        JButton btnSalvar = new JButton("Salvar");
        JButton btnVoltar = new JButton("Voltar");

        painelBotoes.add(btnSalvar);
        painelBotoes.add(btnVoltar);

        // Adiciona o painel de botões ao painel principal do formulário
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2; // Ocupa duas colunas
        gbc.fill = GridBagConstraints.NONE; // Não expande horizontalmente
        gbc.anchor = GridBagConstraints.CENTER; // Centraliza os botões
        painelFormulario.add(painelBotoes, gbc); // Adiciona o painel de botões

        JScrollPane scrollPane = new JScrollPane(painelFormulario);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        add(scrollPane);

        // Ação para o botão SALVAR
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

                // Após salvar o evento, voltamos para a tela principal e atualizamos
                voltarParaTelaPrincipal();

            } catch (NullPointerException ex) {
                JOptionPane.showMessageDialog(this, "Por favor, preencha todos os campos obrigatórios", "Campos Vazios", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace(); // Para depuração
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro ao inserir participante: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace(); // Para depuração
            }
        });

        // Ação para o botão VOLTAR
        btnVoltar.addActionListener(e -> {
            voltarParaTelaPrincipal();
        });
    }

    // Novo método para encapsular a lógica de voltar
    private void voltarParaTelaPrincipal() {
        this.dispose(); // Fecha a janela atual (TelaCriarEvento)
        if (telaPrincipal != null) {
            telaPrincipal.setVisible(true); // Torna a tela principal visível novamente
            telaPrincipal.atualizarTabelaParticipantes(); // Chama o método para atualizar os dados na tela principal
        }
    }
}
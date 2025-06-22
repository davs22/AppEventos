package view.CRUD.Atualizar; // Mantenha este pacote se preferir, ou renomeie para 'view.Editar' para consistência

import service.ParticipanteService;
import table.Participante;
import view.Inicio.TelaUsuario;

import javax.swing.*;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.text.NumberFormat;
public class TelaAtualizarParticipantesUsuario extends JFrame {

    private TelaUsuario telaPrincipal; // Referência à sua tela principal
    private int participanteIdParaEditar; // Armazena o ID do evento que será editado

    // Os campos agora podem ser preenchidos por um método
    private JLabel lblIdValor; // Para exibir o ID sem que seja editável
    private JTextField txtNome, txtSexo, txtEmail, txtCelular, txtSenha;

    private ParticipanteService ps = new ParticipanteService(); // Instância do serviço

    // NOVO CONSTRUTOR: Recebe a tela principal e o ID do evento
    public TelaAtualizarParticipantesUsuario(TelaUsuario telaPrincipal, int participanteId) {
        this.telaPrincipal = telaPrincipal;
        this.participanteIdParaEditar = participanteId;
        
        setTitle("Atualizar Participante (ID: " + participanteId + ")");
        setSize(400, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE); // Apenas esta janela será fechada

        JPanel painelFormulario = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        NumberFormat integerFormat = NumberFormat.getIntegerInstance();
        integerFormat.setGroupingUsed(false);
        NumberFormatter numberFormatter = new NumberFormatter(integerFormat);
        numberFormatter.setValueClass(Integer.class);
        numberFormatter.setAllowsInvalid(false);
        numberFormatter.setMinimum(0);

        // Campo ID: Agora é um JLabel para apenas exibir o ID
        gbc.gridx = 0; gbc.gridy = 0;
        painelFormulario.add(new JLabel("ID:"), gbc);
        gbc.gridx = 1;
        lblIdValor = new JLabel(String.valueOf(participanteId)); // Exibe o ID
        painelFormulario.add(lblIdValor, gbc);

        gbc.gridx = 0; gbc.gridy++;
        painelFormulario.add(new JLabel("Nome:"), gbc);
        gbc.gridx = 1;
        txtNome = new JTextField(20);
        painelFormulario.add(txtNome, gbc);

        gbc.gridx = 0; gbc.gridy++;
        painelFormulario.add(new JLabel("Sexo:"), gbc);
        gbc.gridx = 1;
        txtSexo = new JTextField(20);
        painelFormulario.add(txtSexo, gbc);

        gbc.gridx = 0; gbc.gridy++;
        painelFormulario.add(new JLabel("Email:"), gbc);
        gbc.gridx = 1;
        txtEmail = new JTextField(20);
        painelFormulario.add(txtEmail, gbc);

        gbc.gridx = 0; gbc.gridy++;
        painelFormulario.add(new JLabel("Celular:"), gbc);
        gbc.gridx = 1;
        txtCelular = new JTextField(20);
        painelFormulario.add(txtCelular, gbc);

        gbc.gridx = 0; gbc.gridy++;
        painelFormulario.add(new JLabel("Senha:"), gbc);
        gbc.gridx = 1;
        txtSenha = new JTextField(20);
        painelFormulario.add(txtSenha, gbc);

        // Painel para os botões "Atualizar" e "Voltar"
        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        JButton btnAtualizar = new JButton("Atualizar");
        JButton btnVoltar = new JButton("Voltar"); // Renomeado de "Cancelar" para "Voltar"

        painelBotoes.add(btnAtualizar);
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

        // Ação para o botão ATUALIZAR
        btnAtualizar.addActionListener(e -> atualizarParticipante());

        // Ação para o botão VOLTAR
        btnVoltar.addActionListener(e -> voltarParaTelaPrincipal());

        // Carrega os dados do evento ao iniciar a tela
        carregarDadosDoParticipante();
    }

    private void atualizarParticipante() {
        try {
            // O ID agora é pego da variável de instância, não do campo de texto
            int id = this.participanteIdParaEditar; 
            String nome = txtNome.getText();
            String sexo = txtSexo.getText();
            String email = txtEmail.getText();
            String celular = txtCelular.getText();
            String senha = txtSenha.getText();

            // Chama o serviço de eventos com o ID correto e os novos dados
            ps.atualizarParticipante(id, nome, sexo, email, celular, senha);

            JOptionPane.showMessageDialog(this, "Participante atualizado com sucesso!");
            voltarParaTelaPrincipal(); // Volta para a tela principal e atualiza
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao atualizar participante: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    // NOVO MÉTODO: Carrega os dados do evento usando o ID
    private void carregarDadosDoParticipante() {
        try {
            Participante participante = ps.buscarParticipantePorId(this.participanteIdParaEditar);
            if (participante != null) {
                // Preenche os campos com os dados do evento
                lblIdValor.setText(String.valueOf(participante.getId())); // Atualiza o JLabel com o ID
                txtNome.setText(participante.getNome());
                txtSexo.setText(participante.getSexo());
                txtEmail.setText(participante.getEmail());
                txtCelular.setText(participante.getCelular());
                txtSenha.setText("");

               
            } else {
                JOptionPane.showMessageDialog(this, "Participante não encontrado com o ID: " + participanteIdParaEditar, "Erro", JOptionPane.ERROR_MESSAGE);
                voltarParaTelaPrincipal(); // Volta se o evento não for encontrado
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar dados do participante: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            voltarParaTelaPrincipal(); // Volta se houver erro ao carregar
        }
    }

    // Método para voltar à tela principal e atualizá-la
    private void voltarParaTelaPrincipal() {
        this.dispose(); // Fecha a janela atual
        if (telaPrincipal != null) {
            telaPrincipal.setVisible(true); // Torna a tela principal visível
            telaPrincipal.atualizarTabelaPerfil(); // Chama o método de atualização
        }
    }
}
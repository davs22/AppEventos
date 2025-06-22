package view.Atualizar; // Mantenha este pacote se preferir, ou renomeie para 'view.Editar' para consistência

import service.PalestranteService;
import service.ParticipanteService;
import table.Palestrante;
import table.Participante;
import view.TelaOrganizador; // Importe sua tela principal

import javax.swing.*;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.text.NumberFormat;
public class TelaAtualizarPalestrantes extends JFrame {

    private TelaOrganizador telaPrincipal; // Referência à sua tela principal
    private int palestranteIdParaEditar; // Armazena o ID do evento que será editado

    // Os campos agora podem ser preenchidos por um método
    private JLabel lblIdValor; // Para exibir o ID sem que seja editável
    private JTextField txtNome, txtCurriculo, txtAreaAtuacao;

    private PalestranteService ps1 = new PalestranteService(); // Instância do serviço

    // NOVO CONSTRUTOR: Recebe a tela principal e o ID do evento
    public TelaAtualizarPalestrantes(TelaOrganizador telaPrincipal, int palestranteId) {
        this.telaPrincipal = telaPrincipal;
        this.palestranteIdParaEditar = palestranteId;
        
        setTitle("Atualizar Palestrante (ID: " + palestranteId + ")");
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
        lblIdValor = new JLabel(String.valueOf(palestranteId)); // Exibe o ID
        painelFormulario.add(lblIdValor, gbc);

        gbc.gridx = 0; gbc.gridy++;
        painelFormulario.add(new JLabel("Nome:"), gbc);
        gbc.gridx = 1;
        txtNome = new JTextField(20);
        painelFormulario.add(txtNome, gbc);

        gbc.gridx = 0; gbc.gridy++;
        painelFormulario.add(new JLabel("Curriculo:"), gbc);
        gbc.gridx = 1;
        txtCurriculo = new JTextField(20);
        painelFormulario.add(txtCurriculo, gbc);

        gbc.gridx = 0; gbc.gridy++;
        painelFormulario.add(new JLabel("Área de atuação:"), gbc);
        gbc.gridx = 1;
        txtAreaAtuacao = new JTextField(20);
        painelFormulario.add(txtAreaAtuacao, gbc);

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
        btnAtualizar.addActionListener(e -> atualizarPalestrante());

        // Ação para o botão VOLTAR
        btnVoltar.addActionListener(e -> voltarParaTelaPrincipal());

        // Carrega os dados do evento ao iniciar a tela
        carregarDadosDoPalestrante();
    }

    private void atualizarPalestrante() {
        try {
            // O ID agora é pego da variável de instância, não do campo de texto
            int id = this.palestranteIdParaEditar; 
            String nome = txtNome.getText();
            String curriculo = txtCurriculo.getText();
            String areaAtuacao = txtAreaAtuacao.getText();

            // Chama o serviço de eventos com o ID correto e os novos dados
            ps1.atualizarPalestrante(id, nome, curriculo, areaAtuacao);

            JOptionPane.showMessageDialog(this, "Palestrante atualizado com sucesso!");
            voltarParaTelaPrincipal(); // Volta para a tela principal e atualiza
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao atualizar palestrante: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    // NOVO MÉTODO: Carrega os dados do evento usando o ID
    private void carregarDadosDoPalestrante() {
        try {
            Palestrante palestrante = ps1.buscarPalestrantePorId(this.palestranteIdParaEditar);
            if (palestrante != null) {
                // Preenche os campos com os dados do evento
                lblIdValor.setText(String.valueOf(palestrante.getId())); // Atualiza o JLabel com o ID
                txtNome.setText(palestrante.getNome());
                txtCurriculo.setText(palestrante.getCurriculo());
                txtAreaAtuacao.setText(palestrante.getAreaAtuacao());
                
            } else {
                JOptionPane.showMessageDialog(this, "Palestrante não encontrado com o ID: " + palestranteIdParaEditar, "Erro", JOptionPane.ERROR_MESSAGE);
                voltarParaTelaPrincipal(); // Volta se o evento não for encontrado
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar dados do palestrante: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            voltarParaTelaPrincipal(); // Volta se houver erro ao carregar
        }
    }

    // Método para voltar à tela principal e atualizá-la
    private void voltarParaTelaPrincipal() {
        this.dispose(); // Fecha a janela atual
        if (telaPrincipal != null) {
            telaPrincipal.setVisible(true); // Torna a tela principal visível
            telaPrincipal.atualizarTabelaPalestrantes();// Chama o método de atualização
        }
    }
}
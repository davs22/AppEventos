package view.CRUD.Atualizar; // Mantenha este pacote se preferir, ou renomeie para 'view.Editar' para consistência

import service.ParticipanteService;
import table.Participante;
import view.Inicio.TelaOrganizador;

import javax.swing.*;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.text.NumberFormat;
public class TelaAtualizarParticipantesOrganizador extends JFrame {

    private TelaOrganizador telaPrincipal; 
    private int participanteIdParaEditar; 

    private JLabel lblIdValor; 
    private JTextField txtNome, txtSexo, txtEmail, txtCelular, txtSenha;

    private ParticipanteService ps = new ParticipanteService(); 

    public TelaAtualizarParticipantesOrganizador(TelaOrganizador telaPrincipal, int participanteId) {
        this.telaPrincipal = telaPrincipal;
        this.participanteIdParaEditar = participanteId;
        
        setTitle("Atualizar Participante (ID: " + participanteId + ")");
        setSize(400, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE); 

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

        gbc.gridx = 0; gbc.gridy = 0;
        painelFormulario.add(new JLabel("ID:"), gbc);
        gbc.gridx = 1;
        lblIdValor = new JLabel(String.valueOf(participanteId)); 
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

        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        JButton btnAtualizar = new JButton("Atualizar");
        JButton btnVoltar = new JButton("Voltar"); 

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

        btnAtualizar.addActionListener(e -> atualizarParticipante());

        btnVoltar.addActionListener(e -> voltarParaTelaPrincipal());

        carregarDadosDoParticipante();
    }

    private void atualizarParticipante() {
        try {
            int id = this.participanteIdParaEditar; 
            String nome = txtNome.getText();
            String sexo = txtSexo.getText();
            String email = txtEmail.getText();
            String celular = txtCelular.getText();
            String senha = txtSenha.getText();

            ps.atualizarParticipante(id, nome, sexo, email, celular, senha);

            JOptionPane.showMessageDialog(this, "Participante atualizado com sucesso!");
            voltarParaTelaPrincipal(); 
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao atualizar participante: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    private void carregarDadosDoParticipante() {
        try {
            Participante participante = ps.buscarParticipantePorId(this.participanteIdParaEditar);
            if (participante != null) {
                lblIdValor.setText(String.valueOf(participante.getId())); 
                txtNome.setText(participante.getNome());
                txtSexo.setText(participante.getSexo());
                txtEmail.setText(participante.getEmail());
                txtCelular.setText(participante.getCelular());
                txtSenha.setText("");
               
            } else {
                JOptionPane.showMessageDialog(this, "Participante não encontrado com o ID: " + participanteIdParaEditar, "Erro", JOptionPane.ERROR_MESSAGE);
                voltarParaTelaPrincipal(); 
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar dados do participante: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            voltarParaTelaPrincipal(); 
        }
    }

    private void voltarParaTelaPrincipal() {
        this.dispose(); 
        if (telaPrincipal != null) {
            telaPrincipal.setVisible(true); 
            telaPrincipal.atualizarTabelaParticipantes(); 
        }
    }
}
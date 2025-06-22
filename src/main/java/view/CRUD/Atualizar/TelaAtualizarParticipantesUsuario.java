package view.CRUD.Atualizar;

import service.ParticipanteService;

import javax.swing.*;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.NumberFormat;

public class TelaAtualizarParticipantesUsuario extends JFrame {

    private JTextField txtnovoNome, txtnovoSexo, txtnovoEmail, txtnovoTelefone;
    private JFormattedTextField txtidParticipante;

    public TelaAtualizarParticipantesUsuario() {
        setTitle("Atualizar Participante");
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
        painelFormulario.add(new JLabel("ID Participante:"), gbc);
        gbc.gridx = 1;
        txtidParticipante = new JFormattedTextField(numberFormatter);
        txtidParticipante.setColumns(20);
        painelFormulario.add(txtidParticipante, gbc);

        gbc.gridx = 0; gbc.gridy++;
        painelFormulario.add(new JLabel("Nome:"), gbc);
        gbc.gridx = 1;
        txtnovoNome = new JTextField(20);
        painelFormulario.add(txtnovoNome, gbc);

        gbc.gridx = 0; gbc.gridy++;
        painelFormulario.add(new JLabel("Sexo:"), gbc);
        gbc.gridx = 1;
        txtnovoSexo = new JTextField(20);
        painelFormulario.add(txtnovoSexo, gbc);

        gbc.gridx = 0; gbc.gridy++;
        painelFormulario.add(new JLabel("Email:"), gbc);
        gbc.gridx = 1;
        txtnovoEmail = new JTextField(20);
        painelFormulario.add(txtnovoEmail, gbc);

        gbc.gridx = 0; gbc.gridy++;
        painelFormulario.add(new JLabel("Telefone:"), gbc);
        gbc.gridx = 1;
        txtnovoTelefone = new JTextField(20);

        txtnovoTelefone.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!Character.isDigit(c)) {
                    e.consume();
                }
            }
        });

        painelFormulario.add(txtnovoTelefone, gbc);

        gbc.gridx = 0; gbc.gridy++;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        JButton btnAtualizar = new JButton("Atualizar");
        painelFormulario.add(btnAtualizar, gbc);

        gbc.gridy++;
        JButton btnCancelar = new JButton("Cancelar");
        painelFormulario.add(btnCancelar, gbc);

        add(painelFormulario);

        btnAtualizar.addActionListener(e -> atualizarParticipante());
        btnCancelar.addActionListener(e -> dispose());
    }

    private void atualizarParticipante() {
        try {
            int idParticipante = ((Number) txtidParticipante.getValue()).intValue();
            String novoNome = txtnovoNome.getText();
            String novoSexo = txtnovoSexo.getText();
            String novoEmail = txtnovoEmail.getText();
            String novoTelefone = txtnovoTelefone.getText(); 

            ParticipanteService ps = new ParticipanteService();
            String resultado = ps.atualizarParticipante(idParticipante, novoNome, novoSexo, novoEmail, novoTelefone, novoTelefone);

            JOptionPane.showMessageDialog(this, resultado);
            dispose();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao atualizar participante: " + ex.getMessage());
        }
    }
}
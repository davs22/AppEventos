package view.Criar;

import javax.swing.*;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.text.NumberFormat;

import service.ParticipanteService;

public class TelaInserirParticipante extends JFrame {
    public TelaInserirParticipante() {
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

        NumberFormat integerFormat = NumberFormat.getIntegerInstance();
        integerFormat.setGroupingUsed(false);
        NumberFormatter numberFormatter = new NumberFormatter(integerFormat);
        numberFormatter.setValueClass(Integer.class);
        numberFormatter.setAllowsInvalid(false);
        numberFormatter.setMinimum(0);

        gbc.gridx = 0; gbc.gridy++;
        painelFormulario.add(new JLabel("Celular:"), gbc);
        gbc.gridx = 1;
        JFormattedTextField txtCelular = new JFormattedTextField(numberFormatter);
        txtCelular.setColumns(20);
        painelFormulario.add(txtCelular, gbc);

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
                String nome = txtNome.getText();
                String sexo = txtSexo.getText();
                String email = txtEmail.getText();
                String celular = txtCelular.getText();

                int Celular = ((Number) txtCelular.getValue()).intValue();

                ParticipanteService ps = new ParticipanteService();
                ps.inserir(nome, sexo, email, celular);

                String msg = "Participante salvo:\n" +
                "Nome: " + nome +
                "\nSexo: " + sexo +
                "\nEmail: " + email +
                "\nCelular: " + celular;

                JOptionPane.showMessageDialog(this, "Participante inserido com sucesso!");
                dispose();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro ao inserir participante: " + ex.getMessage());
            }
        });
    }
}

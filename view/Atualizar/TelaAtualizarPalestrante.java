package view.Atualizar;

import service.PalestranteService;

import javax.swing.*;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.text.NumberFormat;

public class TelaAtualizarPalestrante extends JFrame {

    private JTextField txtnovoNome, txtnovoCurriculo, txtnovaAreaAtuacao;
    private JFormattedTextField txtidPalestrante;

    public TelaAtualizarPalestrante() {
        setTitle("Atualizar Palestrante");
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
        painelFormulario.add(new JLabel("ID Palestrante:"), gbc);
        gbc.gridx = 1;
        txtidPalestrante = new JFormattedTextField(numberFormatter);
        txtidPalestrante.setColumns(20);
        painelFormulario.add(txtidPalestrante, gbc);

        gbc.gridx = 0; gbc.gridy++;
        painelFormulario.add(new JLabel("Nome:"), gbc);
        gbc.gridx = 1;
        txtnovoNome = new JTextField(20);
        painelFormulario.add(txtnovoNome, gbc);

        gbc.gridx = 0; gbc.gridy++;
        painelFormulario.add(new JLabel("Curriculo:"), gbc);
        gbc.gridx = 1;
        txtnovoCurriculo = new JTextField(20);
        painelFormulario.add(txtnovoCurriculo, gbc);

        gbc.gridx = 0; gbc.gridy++;
        painelFormulario.add(new JLabel("Area de Atuação:"), gbc);
        gbc.gridx = 1;
        txtnovaAreaAtuacao = new JTextField(20);
        painelFormulario.add(txtnovaAreaAtuacao, gbc);

        gbc.gridx = 0; gbc.gridy++;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        JButton btnAtualizar = new JButton("Atualizar");
        painelFormulario.add(btnAtualizar, gbc);

        gbc.gridy++;
        JButton btnCancelar = new JButton("Cancelar");
        painelFormulario.add(btnCancelar, gbc);

        add(painelFormulario);

        btnAtualizar.addActionListener(e -> atualizarPalestrante());
        btnCancelar.addActionListener(e -> dispose());
    }

    private void atualizarPalestrante() {
        try {
            int idPalestrante = ((Number) txtidPalestrante.getValue()).intValue();
            String novoNome = txtnovoNome.getText();
            String novoCurriculo = txtnovoCurriculo.getText();
            String novaAreaAtuacao = txtnovaAreaAtuacao.getText();
           
            PalestranteService ps1 = new PalestranteService();
            String resultado = ps1.atualizarPalestrante(idPalestrante, novoNome, novoCurriculo, novaAreaAtuacao);
           
            JOptionPane.showMessageDialog(this, resultado);
            dispose();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao atualizar palestrante: " + ex.getMessage());
        }
    }
}

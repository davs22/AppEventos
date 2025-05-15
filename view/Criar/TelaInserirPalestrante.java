package view.Criar;

import javax.swing.*;
import java.awt.*;

import service.PalestranteService;

public class TelaInserirPalestrante extends JFrame {
    public TelaInserirPalestrante() {
        setTitle("Inserir Palestrante");
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
        painelFormulario.add(new JLabel("Curriculo:"), gbc);
        gbc.gridx = 1;
        JTextField txtCurriculo = new JTextField(20);
        painelFormulario.add(txtCurriculo, gbc);

        gbc.gridx = 0; gbc.gridy++;
        painelFormulario.add(new JLabel("Area de Atuacao:"), gbc);
        gbc.gridx = 1;
        JTextField txtAreaAtuacao = new JTextField(20);
        painelFormulario.add(txtAreaAtuacao, gbc);

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
                String curriculo = txtCurriculo.getText();
                String areaAtuacao = txtAreaAtuacao.getText();

                PalestranteService ps1 = new PalestranteService();
                ps1.inserir(nome, curriculo, areaAtuacao);

                String msg = "Participante salvo:\n" +
                "Nome: " + nome +
                "\nCurriculo: " + curriculo +
                "\nArea de Atuacao: " + areaAtuacao;
                
                JOptionPane.showMessageDialog(this, "Participante inserido com sucesso!");
                dispose();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro ao inserir participante: " + ex.getMessage());
            }
        });
    }
}

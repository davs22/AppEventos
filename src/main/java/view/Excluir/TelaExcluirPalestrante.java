package view.Excluir;

import service.PalestranteService;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class TelaExcluirPalestrante extends JFrame {
    private JTextField txtIdPalestrante;
    private JButton btnExcluir, btnCancelar;

    public TelaExcluirPalestrante() {
        setTitle("Excluir Palestrante");
        setSize(400, 200);
        setLayout(null);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JLabel lblIdp = new JLabel("ID do Palestrante:");
        lblIdp.setBounds(50, 40, 100, 25);
        add(lblIdp);

        txtIdPalestrante = new JTextField();
        txtIdPalestrante.setBounds(150, 40, 180, 25);
        add(txtIdPalestrante);

        btnExcluir = new JButton("Excluir");
        btnExcluir.setBounds(80, 100, 100, 30);
        btnExcluir.addActionListener(this::excluirPalestrante);
        add(btnExcluir);

        btnCancelar = new JButton("Cancelar");
        btnCancelar.setBounds(200, 100, 100, 30);
        btnCancelar.addActionListener(e -> dispose());
        add(btnCancelar);
    }

    private void excluirPalestrante(ActionEvent e) {
        try {
            int idPalestrante = Integer.parseInt(txtIdPalestrante.getText());

            int opcao = JOptionPane.showConfirmDialog(
                this,
                "Tem certeza que deseja excluir o Palestrante com ID " + idPalestrante + "?",
                "Confirmação",
                JOptionPane.YES_NO_OPTION
            );

            if (opcao == JOptionPane.YES_OPTION) {
                PalestranteService ps1 = new PalestranteService();
                String resultado = ps1.excluirPalestrante(null);

            JOptionPane.showMessageDialog(this, resultado);
            dispose();
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "ID inválido. Digite um número.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao excluir: " + ex.getMessage());
        }
    }
}

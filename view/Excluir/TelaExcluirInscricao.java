package view.Excluir;

import service.InscricaoService;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class TelaExcluirInscricao extends JFrame {
    private JTextField txtidParticipante, txtIdEvento;
    private JButton btnExcluir, btnCancelar;

    public TelaExcluirInscricao() {
        setTitle("Excluir Inscricao");
        setSize(400, 250);
        setLayout(null);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JLabel lblIdp = new JLabel("ID do Participante:");
        lblIdp.setBounds(50, 40, 100, 25);
        add(lblIdp);

        txtidParticipante = new JTextField();
        txtidParticipante.setBounds(150, 40, 180, 25);
        add(txtidParticipante);

        JLabel lblIde = new JLabel("ID do Evento:");
        lblIde.setBounds(50, 90, 100, 25);
        add(lblIde);

        txtIdEvento = new JTextField();
        txtIdEvento.setBounds(150, 90, 180, 25);
        add(txtIdEvento);

        btnExcluir = new JButton("Excluir");
        btnExcluir.setBounds(80, 150, 100, 30);
        btnExcluir.addActionListener(this::excluirEvento);
        add(btnExcluir);

        btnCancelar = new JButton("Cancelar");
        btnCancelar.setBounds(200, 150, 100, 30);
        btnCancelar.addActionListener(e -> dispose());
        add(btnCancelar);
    }

    private void excluirEvento(ActionEvent e) {
        try {
            int idParticipante = Integer.parseInt(txtidParticipante.getText());
            int idEvento = Integer.parseInt(txtIdEvento.getText());

            int opcao = JOptionPane.showConfirmDialog(
                this,
                "Tem certeza que deseja excluir a inscrição com IdParticipante " + idParticipante + "e IdEvento " + idEvento + " ?",
                "Confirmação",
                JOptionPane.YES_NO_OPTION
            );

            if (opcao == JOptionPane.YES_OPTION) {
                InscricaoService is = new InscricaoService();
                String resultado = is.excluirInscricao(idParticipante, idEvento);

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

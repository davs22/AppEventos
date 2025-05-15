package view.Excluir;

import service.EventosService;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class TelaExcluirEventos extends JFrame {
    private JTextField txtId;
    private JButton btnExcluir, btnCancelar;

    public TelaExcluirEventos() {
        setTitle("Excluir Evento");
        setSize(400, 200);
        setLayout(null);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JLabel lblId = new JLabel("ID do Evento:");
        lblId.setBounds(50, 40, 100, 25);
        add(lblId);

        txtId = new JTextField();
        txtId.setBounds(150, 40, 180, 25);
        add(txtId);

        btnExcluir = new JButton("Excluir");
        btnExcluir.setBounds(80, 100, 100, 30);
        btnExcluir.addActionListener(this::excluirEvento);
        add(btnExcluir);

        btnCancelar = new JButton("Cancelar");
        btnCancelar.setBounds(200, 100, 100, 30);
        btnCancelar.addActionListener(e -> dispose());
        add(btnCancelar);
    }

    private void excluirEvento(ActionEvent e) {
        try {
            int id = Integer.parseInt(txtId.getText());

            int opcao = JOptionPane.showConfirmDialog(
                this,
                "Tem certeza que deseja excluir o evento com ID " + id + "?",
                "Confirmação",
                JOptionPane.YES_NO_OPTION
            );

            if (opcao == JOptionPane.YES_OPTION) {
                EventosService es = new EventosService();
                String resultado = es.excluirEvento(id);

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

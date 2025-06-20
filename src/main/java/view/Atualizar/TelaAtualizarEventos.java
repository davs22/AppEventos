package view.Atualizar;

import service.EventosService;
import javax.swing.*;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.sql.Date;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;

public class TelaAtualizarEventos extends JFrame {

    private JFormattedTextField txtId, txtIdPalestrante, txtCapacidade;
    private JTextField txtNome, txtData, txtLocal;
    private JTextArea txtDescricao;

    public TelaAtualizarEventos() {
        setTitle("Atualizar Evento");
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
        txtId = new JFormattedTextField(numberFormatter);
        txtId.setColumns(20);
        painelFormulario.add(txtId, gbc);

        gbc.gridx = 0; gbc.gridy++;
        painelFormulario.add(new JLabel("Nome:"), gbc);
        gbc.gridx = 1;
        txtNome = new JTextField(20);
        painelFormulario.add(txtNome, gbc);

        gbc.gridx = 0; gbc.gridy++;
        painelFormulario.add(new JLabel("Descrição:"), gbc);
        gbc.gridx = 1;
        txtDescricao = new JTextArea(3, 20);
        txtDescricao.setLineWrap(true);
        txtDescricao.setWrapStyleWord(true);
        JScrollPane scrollDescricao = new JScrollPane(txtDescricao);
        painelFormulario.add(scrollDescricao, gbc);

        gbc.gridx = 0; gbc.gridy++;
        painelFormulario.add(new JLabel("Data (DD-MM-YYYY):"), gbc);
        gbc.gridx = 1;
        txtData = new JTextField(20);
        painelFormulario.add(txtData, gbc);

        gbc.gridx = 0; gbc.gridy++;
        painelFormulario.add(new JLabel("Local:"), gbc);
        gbc.gridx = 1;
        txtLocal = new JTextField(20);
        painelFormulario.add(txtLocal, gbc);

        gbc.gridx = 0; gbc.gridy++;
        painelFormulario.add(new JLabel("ID Palestrante:"), gbc);
        gbc.gridx = 1;
        txtIdPalestrante = new JFormattedTextField(numberFormatter);
        txtIdPalestrante.setColumns(20);
        painelFormulario.add(txtIdPalestrante, gbc);

        gbc.gridx = 0; gbc.gridy++;
        painelFormulario.add(new JLabel("Capacidade:"), gbc);
        gbc.gridx = 1;
        txtCapacidade = new JFormattedTextField(numberFormatter);
        txtCapacidade.setColumns(20);
        painelFormulario.add(txtCapacidade, gbc);

        gbc.gridx = 0; gbc.gridy++;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        JButton btnAtualizar = new JButton("Atualizar");
        painelFormulario.add(btnAtualizar, gbc);

        gbc.gridy++;
        JButton btnCancelar = new JButton("Cancelar");
        painelFormulario.add(btnCancelar, gbc);

        add(painelFormulario);
 
        btnAtualizar.addActionListener(e -> atualizarEvento());

        btnCancelar.addActionListener(e -> dispose());
    }

    private void atualizarEvento() {
        try {
            int id = ((Number) txtId.getValue()).intValue();
            String nome = txtNome.getText();
            String descricao = txtDescricao.getText();
            String dataString = txtData.getText();
            String local = txtLocal.getText();
            int palestranteId = ((Number) txtIdPalestrante.getValue()).intValue();
            int capacidade = ((Number) txtCapacidade.getValue()).intValue();

            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            java.util.Date data = sdf.parse(dataString);
            Date sqlDate = new Date(data.getTime());

            EventosService es = new EventosService();
            String resultado = es.editarEvento(id, nome, descricao, sqlDate, local, palestranteId, capacidade);

            JOptionPane.showMessageDialog(this, resultado);
            dispose();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao atualizar evento: " + ex.getMessage());
        }
    }
}

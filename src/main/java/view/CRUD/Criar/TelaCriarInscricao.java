package view.CRUD.Criar;

import javax.swing.*;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.text.NumberFormat;

import service.InscricaoService;
import view.Inicio.TelaUsuario;

public class TelaCriarInscricao extends JFrame {
    private TelaUsuario telaPrincipal;

    
    public TelaCriarInscricao(TelaUsuario telaPrincipal) {
        this.telaPrincipal = telaPrincipal; 
        setTitle("Inscricao");
        setSize(400, 400);
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

        gbc.gridx = 0; gbc.gridy=0;
        painelFormulario.add(new JLabel("Id Participante:"), gbc);
        gbc.gridx = 1;
        JFormattedTextField txtIdParticipante = new JFormattedTextField(numberFormatter);
        txtIdParticipante.setColumns(20);
        painelFormulario.add(txtIdParticipante, gbc);

        gbc.gridx = 0; gbc.gridy++;
        painelFormulario.add(new JLabel("Id Evento:"), gbc);
        gbc.gridx = 1;
        JFormattedTextField txtIdEvento = new JFormattedTextField(numberFormatter);
        txtIdEvento.setColumns(20);
        painelFormulario.add(txtIdEvento, gbc);

        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0)); 
        JButton btnSalvar = new JButton("Salvar");
        JButton btnVoltar = new JButton("Voltar");

        painelBotoes.add(btnSalvar);
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

        btnSalvar.addActionListener(e -> {
            try {
            
                String idParticipante = txtIdParticipante.getText();
                String idEvento = txtIdEvento.getText();

                int IdParticipante = ((Number) txtIdParticipante.getValue()).intValue();
                int IdEvento = ((Number) txtIdEvento.getValue()).intValue();

                InscricaoService is = new InscricaoService();
                is.inscreverParticipante(IdParticipante, IdEvento);

                String msg = "Participante salvo:\n" +
                "Id Participante: " + IdParticipante +
                "\nId Evento: " + IdEvento;

                JOptionPane.showMessageDialog(this, "Participante inserido com sucesso!");
                dispose();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro ao inserir participante: " + ex.getMessage());
            }
        });

        btnVoltar.addActionListener(e -> {
            voltarParaTelaPrincipal();
        });
    }
        private void voltarParaTelaPrincipal() {
        this.dispose(); 
        if (telaPrincipal != null) {
            telaPrincipal.setVisible(true); 
            telaPrincipal.atualizarTabelaEventos(); 
        }
    }
    
}

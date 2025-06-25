package view.CRUD.Criar;

import javax.swing.*;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import service.EventosService;
import view.Inicio.TelaOrganizador;

public class TelaCriarEvento extends JFrame {

   
    private TelaOrganizador telaPrincipal;

   
    public TelaCriarEvento(TelaOrganizador telaPrincipal) {
        this.telaPrincipal = telaPrincipal;
        setTitle("Criar Evento");
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
        painelFormulario.add(new JLabel("Descrição:"), gbc);
        gbc.gridx = 1;
        JTextArea txtDescricao = new JTextArea(3, 20);
        txtDescricao.setLineWrap(true);
        txtDescricao.setWrapStyleWord(true);
        JScrollPane scrollDescricao = new JScrollPane(txtDescricao);
        painelFormulario.add(scrollDescricao, gbc);

        gbc.gridx = 0; gbc.gridy++;
        painelFormulario.add(new JLabel("Data (DD-MM-YYYY):"), gbc);
        gbc.gridx = 1;
        JTextField txtData = new JTextField(20);
        painelFormulario.add(txtData, gbc);

        gbc.gridx = 0; gbc.gridy++;
        painelFormulario.add(new JLabel("Local:"), gbc);
        gbc.gridx = 1;
        JTextField txtLocal = new JTextField(20);
        painelFormulario.add(txtLocal, gbc);

        NumberFormat integerFormat = NumberFormat.getIntegerInstance();
        integerFormat.setGroupingUsed(false);
        NumberFormatter numberFormatter = new NumberFormatter(integerFormat);
        numberFormatter.setValueClass(Integer.class);
        numberFormatter.setAllowsInvalid(false);
        numberFormatter.setMinimum(0);

        gbc.gridx = 0; gbc.gridy++;
        painelFormulario.add(new JLabel("Capacidade:"), gbc);
        gbc.gridx = 1;
        JFormattedTextField txtCapacidade = new JFormattedTextField(numberFormatter);
        txtCapacidade.setColumns(20);
        painelFormulario.add(txtCapacidade, gbc);

        gbc.gridx = 0; gbc.gridy++;
        painelFormulario.add(new JLabel("ID Palestrante:"), gbc);
        gbc.gridx = 1;
        JFormattedTextField txtIdPalestrante = new JFormattedTextField(numberFormatter);
        txtIdPalestrante.setColumns(20);
        painelFormulario.add(txtIdPalestrante, gbc);

        
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
                String nome = txtNome.getText();
                String descricao = txtDescricao.getText();
                String dataString = txtData.getText();
                String local = txtLocal.getText();

                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

                sdf.setLenient(false); 
                java.util.Date data = sdf.parse(dataString); 

                int capacidade = 0;
                if (txtCapacidade.getValue() != null) {
                    capacidade = ((Number) txtCapacidade.getValue()).intValue();
                }

                int idPalestrante = 0;
                if (txtIdPalestrante.getValue() != null) {
                    idPalestrante = ((Number) txtIdPalestrante.getValue()).intValue();
                }

                EventosService es = new EventosService();
               
                es.criarEvento(nome, descricao, dataString, local, idPalestrante, capacidade);

                JOptionPane.showMessageDialog(this, "Evento criado com sucesso!");

                voltarParaTelaPrincipal();

            } catch (ParseException ex) {
                JOptionPane.showMessageDialog(this, "Formato de data inválido. Use DD-MM-YYYY.", "Erro de Data", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace(); 
            } catch (NullPointerException ex) {
                JOptionPane.showMessageDialog(this, "Por favor, preencha todos os campos obrigatórios, especialmente os numéricos.", "Campos Vazios", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace(); 
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro ao criar evento: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace(); 
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
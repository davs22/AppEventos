package view.CRUD.Atualizar; 

import service.EventosService;
import table.Eventos; 
import view.Inicio.TelaOrganizador;

import javax.swing.*;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.sql.Date; 
import java.text.NumberFormat;
import java.text.ParseException; 
import java.text.SimpleDateFormat;
import java.util.List;

public class TelaAtualizarEventos extends JFrame {

    private TelaOrganizador telaPrincipal; 
    private int eventoIdParaEditar; 

    private JLabel lblIdValor; 
    private JTextField txtNome, txtData, txtLocal;
    private JTextArea txtDescricao;
    private JFormattedTextField txtIdPalestrante, txtCapacidade;

    private EventosService eventosService = new EventosService(); 

    public TelaAtualizarEventos(TelaOrganizador telaPrincipal, int eventoId) {
        this.telaPrincipal = telaPrincipal;
        this.eventoIdParaEditar = eventoId;
        
        setTitle("Atualizar Evento (ID: " + eventoId + ")");
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
        lblIdValor = new JLabel(String.valueOf(eventoId));
        painelFormulario.add(lblIdValor, gbc);

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

        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        JButton btnAtualizar = new JButton("Atualizar");
        JButton btnVoltar = new JButton("Voltar"); 

        painelBotoes.add(btnAtualizar);
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

        btnAtualizar.addActionListener(e -> atualizarEvento());

        btnVoltar.addActionListener(e -> voltarParaTelaPrincipal());

        carregarDadosDoEvento();
    }

    private void atualizarEvento() {
        try {
            int id = this.eventoIdParaEditar; 
            String nome = txtNome.getText();
            String descricao = txtDescricao.getText();
            String dataString = txtData.getText();
            String local = txtLocal.getText();
            
            int palestranteId = 0;
            if (txtIdPalestrante.getValue() != null) {
                palestranteId = ((Number) txtIdPalestrante.getValue()).intValue();
            } else {
                JOptionPane.showMessageDialog(this, "Por favor, preencha o ID do Palestrante.", "Campo Obrigatório", JOptionPane.WARNING_MESSAGE);
                return; 
            }

            int capacidade = 0;
            if (txtCapacidade.getValue() != null) {
                capacidade = ((Number) txtCapacidade.getValue()).intValue();
            } else {
                JOptionPane.showMessageDialog(this, "Por favor, preencha a Capacidade.", "Campo Obrigatório", JOptionPane.WARNING_MESSAGE);
                return;
            }

            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            sdf.setLenient(false); 
            java.util.Date dataUtil = sdf.parse(dataString);
            Date sqlDate = new Date(dataUtil.getTime()); 

            eventosService.editarEvento(id, nome, descricao, sqlDate, local, palestranteId, capacidade);

            JOptionPane.showMessageDialog(this, "Evento atualizado com sucesso!");
            voltarParaTelaPrincipal();
        } catch (ParseException ex) {
            JOptionPane.showMessageDialog(this, "Formato de data inválido. Use DD-MM-YYYY.", "Erro de Data", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao atualizar evento: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    private void carregarDadosDoEvento() {
    try {
        List<Eventos> eventos = eventosService.listarPorParametro("id", String.valueOf(eventoIdParaEditar));
        
        if (eventos != null && !eventos.isEmpty()) {
            Eventos evento = eventos.get(0); 

            lblIdValor.setText(String.valueOf(evento.getId()));
            txtNome.setText(evento.getNome());
            txtDescricao.setText(evento.getDescricao());

            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            txtData.setText(sdf.format(evento.getData()));

            txtLocal.setText(evento.getLocal());
            txtIdPalestrante.setValue(evento.getPalestranteId());
            txtCapacidade.setValue(evento.getCapacidade());

        } else {
            JOptionPane.showMessageDialog(this, "Evento não encontrado com o ID: " + eventoIdParaEditar, "Erro", JOptionPane.ERROR_MESSAGE);
            voltarParaTelaPrincipal();
        }
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Erro ao carregar dados do evento: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        e.printStackTrace();
        voltarParaTelaPrincipal();
    }
}


    private void voltarParaTelaPrincipal() {
        this.dispose(); 
        if (telaPrincipal != null) {
            telaPrincipal.setVisible(true);
            telaPrincipal.atualizarTabelaEventos(); 
        }
    }
}
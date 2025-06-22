package view.Atualizar; // Mantenha este pacote se preferir, ou renomeie para 'view.Editar' para consistência

import service.EventosService;
import table.Eventos; // Importe a classe Eventos (seu modelo)
import view.TelaOrganizador; // Importe sua tela principal

import javax.swing.*;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.sql.Date; // Para java.sql.Date
import java.text.NumberFormat;
import java.text.ParseException; // Importe para tratamento de exceção de data
import java.text.SimpleDateFormat;

public class TelaAtualizarEventos extends JFrame {

    private TelaOrganizador telaPrincipal; // Referência à sua tela principal
    private int eventoIdParaEditar; // Armazena o ID do evento que será editado

    // Os campos agora podem ser preenchidos por um método
    private JLabel lblIdValor; // Para exibir o ID sem que seja editável
    private JTextField txtNome, txtData, txtLocal;
    private JTextArea txtDescricao;
    private JFormattedTextField txtIdPalestrante, txtCapacidade;

    private EventosService eventosService = new EventosService(); // Instância do serviço

    // NOVO CONSTRUTOR: Recebe a tela principal e o ID do evento
    public TelaAtualizarEventos(TelaOrganizador telaPrincipal, int eventoId) {
        this.telaPrincipal = telaPrincipal;
        this.eventoIdParaEditar = eventoId;
        
        setTitle("Atualizar Evento (ID: " + eventoId + ")");
        setSize(400, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE); // Apenas esta janela será fechada

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

        // Campo ID: Agora é um JLabel para apenas exibir o ID
        gbc.gridx = 0; gbc.gridy = 0;
        painelFormulario.add(new JLabel("ID:"), gbc);
        gbc.gridx = 1;
        lblIdValor = new JLabel(String.valueOf(eventoId)); // Exibe o ID
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

        // Painel para os botões "Atualizar" e "Voltar"
        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        JButton btnAtualizar = new JButton("Atualizar");
        JButton btnVoltar = new JButton("Voltar"); // Renomeado de "Cancelar" para "Voltar"

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

        // Ação para o botão ATUALIZAR
        btnAtualizar.addActionListener(e -> atualizarEvento());

        // Ação para o botão VOLTAR
        btnVoltar.addActionListener(e -> voltarParaTelaPrincipal());

        // Carrega os dados do evento ao iniciar a tela
        carregarDadosDoEvento();
    }

    private void atualizarEvento() {
        try {
            // O ID agora é pego da variável de instância, não do campo de texto
            int id = this.eventoIdParaEditar; 
            String nome = txtNome.getText();
            String descricao = txtDescricao.getText();
            String dataString = txtData.getText();
            String local = txtLocal.getText();
            
            // Validação de null para campos formatados
            int palestranteId = 0;
            if (txtIdPalestrante.getValue() != null) {
                palestranteId = ((Number) txtIdPalestrante.getValue()).intValue();
            } else {
                // Opcional: exija que o campo seja preenchido ou defina um valor padrão
                JOptionPane.showMessageDialog(this, "Por favor, preencha o ID do Palestrante.", "Campo Obrigatório", JOptionPane.WARNING_MESSAGE);
                return; 
            }

            int capacidade = 0;
            if (txtCapacidade.getValue() != null) {
                capacidade = ((Number) txtCapacidade.getValue()).intValue();
            } else {
                // Opcional: exija que o campo seja preenchido ou defina um valor padrão
                JOptionPane.showMessageDialog(this, "Por favor, preencha a Capacidade.", "Campo Obrigatório", JOptionPane.WARNING_MESSAGE);
                return;
            }

            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            sdf.setLenient(false); // Torna o parse rigoroso
            java.util.Date dataUtil = sdf.parse(dataString);
            Date sqlDate = new Date(dataUtil.getTime()); // Converte para java.sql.Date

            // Chama o serviço de eventos com o ID correto e os novos dados
            eventosService.editarEvento(id, nome, descricao, sqlDate, local, palestranteId, capacidade);

            JOptionPane.showMessageDialog(this, "Evento atualizado com sucesso!");
            voltarParaTelaPrincipal(); // Volta para a tela principal e atualiza
        } catch (ParseException ex) {
            JOptionPane.showMessageDialog(this, "Formato de data inválido. Use DD-MM-YYYY.", "Erro de Data", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao atualizar evento: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    // NOVO MÉTODO: Carrega os dados do evento usando o ID
    private void carregarDadosDoEvento() {
        try {
            Eventos evento = eventosService.buscarEventoPorId(this.eventoIdParaEditar);
            if (evento != null) {
                // Preenche os campos com os dados do evento
                lblIdValor.setText(String.valueOf(evento.getId())); // Atualiza o JLabel com o ID
                txtNome.setText(evento.getNome());
                txtDescricao.setText(evento.getDescricao());
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                txtData.setText(sdf.format(evento.getData())); // Formata a data para exibir
                txtLocal.setText(evento.getLocal());
                txtIdPalestrante.setValue(evento.getPalestranteId());
                txtCapacidade.setValue(evento.getCapacidade());
            } else {
                JOptionPane.showMessageDialog(this, "Evento não encontrado com o ID: " + eventoIdParaEditar, "Erro", JOptionPane.ERROR_MESSAGE);
                voltarParaTelaPrincipal(); // Volta se o evento não for encontrado
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar dados do evento: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            voltarParaTelaPrincipal(); // Volta se houver erro ao carregar
        }
    }

    // Método para voltar à tela principal e atualizá-la
    private void voltarParaTelaPrincipal() {
        this.dispose(); // Fecha a janela atual
        if (telaPrincipal != null) {
            telaPrincipal.setVisible(true); // Torna a tela principal visível
            telaPrincipal.atualizarTabelaEventos(); // Chama o método de atualização
        }
    }
}
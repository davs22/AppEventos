package view.Criar;

import javax.swing.*;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import service.EventosService;// Importa sua tela principal
import view.TelaOrganizador;

public class TelaCriarEvento extends JFrame {

    // 1. Campo para armazenar a referência da tela principal
    private TelaOrganizador telaPrincipal;

    // 2. Construtor que recebe a referência da tela principal
    public TelaCriarEvento(TelaOrganizador telaPrincipal) {
        this.telaPrincipal = telaPrincipal; // Armazena a referência
        setTitle("Criar Evento");
        setSize(400, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE); // Fecha apenas esta janela, não o aplicativo

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
        painelFormulario.add(new JLabel("ID Palestrante:"), gbc);
        gbc.gridx = 1;
        JFormattedTextField txtIdPalestrante = new JFormattedTextField(numberFormatter);
        txtIdPalestrante.setColumns(20);
        painelFormulario.add(txtIdPalestrante, gbc);

        gbc.gridx = 0; gbc.gridy++;
        painelFormulario.add(new JLabel("Capacidade:"), gbc);
        gbc.gridx = 1;
        JFormattedTextField txtCapacidade = new JFormattedTextField(numberFormatter);
        txtCapacidade.setColumns(20);
        painelFormulario.add(txtCapacidade, gbc);

        // Painel para os botões "Salvar" e "Voltar"
        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0)); // Alinha à direita
        JButton btnSalvar = new JButton("Salvar");
        JButton btnVoltar = new JButton("Voltar");

        painelBotoes.add(btnSalvar);
        painelBotoes.add(btnVoltar);

        // Adiciona o painel de botões ao painel principal do formulário
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2; // Ocupa duas colunas
        gbc.fill = GridBagConstraints.NONE; // Não expande horizontalmente
        gbc.anchor = GridBagConstraints.CENTER; // Centraliza os botões
        painelFormulario.add(painelBotoes, gbc); // Adiciona o painel de botões

        JScrollPane scrollPane = new JScrollPane(painelFormulario);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        add(scrollPane);

        // Ação para o botão SALVAR
        btnSalvar.addActionListener(e -> {
            try {
                String nome = txtNome.getText();
                String descricao = txtDescricao.getText();
                String dataString = txtData.getText();
                String local = txtLocal.getText();

                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                // Validação básica da data antes de tentar o parse
                sdf.setLenient(false); // Torna o parse rigoroso
                java.util.Date data = sdf.parse(dataString);
                // java.sql.Date sqlDate = new java.sql.Date(data.getTime()); // Não é estritamente necessário para o serviço

                // Certifique-se de que o valor não é nulo antes de chamar intValue()
                int idPalestrante = 0;
                if (txtIdPalestrante.getValue() != null) {
                    idPalestrante = ((Number) txtIdPalestrante.getValue()).intValue();
                }

                int capacidade = 0;
                if (txtCapacidade.getValue() != null) {
                    capacidade = ((Number) txtCapacidade.getValue()).intValue();
                }

                EventosService es = new EventosService();
                es.criarEvento(nome, descricao, dataString, local, idPalestrante, capacidade);

                JOptionPane.showMessageDialog(this, "Evento criado com sucesso!");
                
                // Após salvar o evento, voltamos para a tela principal e atualizamos
                voltarParaTelaPrincipal();

            } catch (ParseException ex) {
                JOptionPane.showMessageDialog(this, "Formato de data inválido. Use DD-MM-YYYY.", "Erro de Data", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace(); // Para depuração
            } catch (NullPointerException ex) {
                JOptionPane.showMessageDialog(this, "Por favor, preencha todos os campos obrigatórios, especialmente os numéricos.", "Campos Vazios", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace(); // Para depuração
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro ao criar evento: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace(); // Para depuração
            }
        });

        // Ação para o botão VOLTAR
        btnVoltar.addActionListener(e -> {
            voltarParaTelaPrincipal();
        });
    }

    // Novo método para encapsular a lógica de voltar
    private void voltarParaTelaPrincipal() {
        this.dispose(); // Fecha a janela atual (TelaCriarEvento)
        if (telaPrincipal != null) {
            telaPrincipal.setVisible(true); // Torna a tela principal visível novamente
            telaPrincipal.atualizarTabelaEventos(); // Chama o método para atualizar os dados na tela principal
        }
    }
}
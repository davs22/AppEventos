package view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import service.EventosService;
import service.PalestranteService;
import service.ParticipanteService;
import table.Eventos;
import view.Criar.TelaCriarEvento;
import table.Participante;
import table.Palestrante;
// Certifique-se de que este import está correto

public class TelaOrganizador extends JFrame {

    private JTable tabelaEventos;
    private DefaultTableModel modeloTabelaEventos;
    private JTable tabelaParticipantes;
    private DefaultTableModel modeloTabelaParticipantes;
    private JTable tabelaPalestrantes;
    private DefaultTableModel modeloTabelaPalestrantes;

    private EventosService es = new EventosService();
    private ParticipanteService ps = new ParticipanteService();
    private PalestranteService ps1 = new PalestranteService();
    

    public TelaOrganizador() {
        super("Sistema de Eventos");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null);

        JPanel painelPrincipal = new JPanel(new BorderLayout(10, 10));
        painelPrincipal.setBorder(new EmptyBorder(10, 10, 10, 10));

        JTabbedPane abas = new JTabbedPane();
        abas.addTab("Eventos", criarPainelEventos());
        abas.addTab("Participantes", criarPainelParticipantes());
        abas.addTab("Palestrantes", criarPainelPalestrantes());

        painelPrincipal.add(abas, BorderLayout.CENTER);
        add(painelPrincipal);
    }

    private JPanel criarPainelEventos() {
        JPanel painel = new JPanel(new BorderLayout(10, 10));

        // Define colunas da tabela
        String[] colunas = {"ID", "Nome", "Descrição", "Data", "Local", "Capacidade", "Palestrante ID"};
        modeloTabelaEventos = new DefaultTableModel(colunas, 0) {
            public boolean isCellEditable(int row, int column) {
                return false; // evita edição direta
            }
        };

        tabelaEventos = new JTable(modeloTabelaEventos);
        JScrollPane scroll = new JScrollPane(tabelaEventos);
        painel.add(scroll, BorderLayout.CENTER);

        // Botões
        JPanel botoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        JButton btnCriar = new JButton("Criar");
        JButton btnEditar = new JButton("Editar");
        JButton btnExcluir = new JButton("Excluir");

        botoes.add(btnCriar);
        botoes.add(btnEditar);
        botoes.add(btnExcluir);
        painel.add(botoes, BorderLayout.SOUTH);

        btnCriar.addActionListener(e -> {
            TelaCriarEvento tela = new TelaCriarEvento(this); // passa referência da principal
            tela.setVisible(true);
            this.setVisible(false); // esconde a tela principal enquanto a de criação está aberta
        });

        carregarEventos(); // Carrega os eventos ao iniciar a tela pela primeira vez

        return painel;
    }

    // Este método será chamado pela TelaCriarEvento para atualizar a tabela
    public void atualizarTabelaEventos() {
        carregarEventos(); // Simplesmente chama o método que já carrega os eventos
    }

    private JPanel criarPainelParticipantes() {
        JPanel painelBusca = new JPanel(new BorderLayout(10, 10));
        JTextField buscaParticipante = new JTextField(15);
        JButton btnBuscar = new JButton("Buscar");
        JList<String> listaParticipantes = new JList<>(new DefaultListModel<>());

        JPanel topoBusca = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topoBusca.add(new JLabel("Pesquisar Participante:"));
        topoBusca.add(buscaParticipante);
        topoBusca.add(btnBuscar);
        painelBusca.add(topoBusca, BorderLayout.NORTH);

        painelBusca.add(new JScrollPane(listaParticipantes), BorderLayout.CENTER);

        JPanel painel = new JPanel(new BorderLayout(10, 10));

        String[] colunas = {"ID", "Nome", "Sexo", "Email", "Celular", "Senha", "Tipo"};
        modeloTabelaParticipantes = new DefaultTableModel(colunas, 0) {
            public boolean isCellEditable(int row, int column) {
                return false; // evita edição direta
            }
        };

        tabelaParticipantes = new JTable(modeloTabelaParticipantes);
        JScrollPane scroll = new JScrollPane(tabelaParticipantes);
        painel.add(scroll, BorderLayout.CENTER);

        // Botões
        JPanel botoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        JButton btnCriar = new JButton("Criar");
        JButton btnEditar = new JButton("Editar");
        JButton btnExcluir = new JButton("Excluir");

        botoes.add(btnCriar);
        botoes.add(btnEditar);
        botoes.add(btnExcluir);
        painel.add(botoes, BorderLayout.SOUTH);

        carregarParticipantes(); // Carrega os eventos ao iniciar a tela pela primeira vez

        return painel;
    }

    private JPanel criarPainelPalestrantes() {
        JPanel painelBusca = new JPanel(new BorderLayout(10, 10));
        JTextField buscaPalestrante = new JTextField(15);
        JButton btnBuscar = new JButton("Buscar");
        JList<String> listaPalestrante = new JList<>(new DefaultListModel<>());

        JPanel topoBusca = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topoBusca.add(new JLabel("Pesquisar Palestrante:"));
        topoBusca.add(buscaPalestrante);
        topoBusca.add(btnBuscar);
        painelBusca.add(topoBusca, BorderLayout.NORTH);

        painelBusca.add(new JScrollPane(listaPalestrante), BorderLayout.CENTER);

        JPanel painel = new JPanel(new BorderLayout(10, 10));

        String[] colunas = {"ID", "Nome", "Curriculo", "Área de Atuação"};
        modeloTabelaPalestrantes = new DefaultTableModel(colunas, 0) {
            public boolean isCellEditable(int row, int column) {
                return false; // evita edição direta
            }
        };

        tabelaPalestrantes = new JTable(modeloTabelaPalestrantes);
        JScrollPane scroll = new JScrollPane(tabelaPalestrantes);
        painel.add(scroll, BorderLayout.CENTER);

        // Botões
        JPanel botoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        JButton btnCriar = new JButton("Criar");
        JButton btnEditar = new JButton("Editar");
        JButton btnExcluir = new JButton("Excluir");

        botoes.add(btnCriar);
        botoes.add(btnEditar);
        botoes.add(btnExcluir);
        painel.add(botoes, BorderLayout.SOUTH);

        carregarPalestrantes(); // Carrega os eventos ao iniciar a tela pela primeira vez

        return painel;
    }


    private void carregarEventos() {
        modeloTabelaEventos.setRowCount(0); // limpa tabela
        try {
            List<Eventos> eventos = es.listarEventos();
            for (Eventos e : eventos) {
                modeloTabelaEventos.addRow(new Object[]{
                        e.getId(),
                        e.getNome(),
                        e.getDescricao(),
                        e.getData(),
                        e.getLocal(),
                        e.getCapacidade(),
                        e.getPalestranteId()
                });
            }
        } catch (Exception e) {
            // Em vez de System.err, considere usar um JOptionPane para exibir o erro ao usuário
            JOptionPane.showMessageDialog(this, "Erro ao listar eventos: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace(); // Para depuração, ainda imprima no console
        }
    }

    private void carregarParticipantes() {
        modeloTabelaParticipantes.setRowCount(0); // limpa tabela
        try {
            List<Participante> participante = ps.listarTodos();
            for (Participante p : participante) {
                modeloTabelaParticipantes.addRow(new Object[]{
                        p.getId(),
                        p.getNome(),
                        p.getSexo(),
                        p.getEmail(),
                        p.getCelular(),
                        p.getSenha(),
                        p.getTipo()
                });
            }
        } catch (Exception e) {
            // Em vez de System.err, considere usar um JOptionPane para exibir o erro ao usuário
            JOptionPane.showMessageDialog(this, "Erro ao listar participantes: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace(); // Para depuração, ainda imprima no console
        }
    }

     private void carregarPalestrantes() {
        modeloTabelaPalestrantes.setRowCount(0); // limpa tabela
        try {
            List<Palestrante> palestrante = ps1.listarTodos();
            for (Palestrante p1 : palestrante) {
                modeloTabelaPalestrantes.addRow(new Object[]{
                        p1.getId(),
                        p1.getNome(),
                        p1.getCurriculo(),
                        p1.getAreaAtuacao(),
                });
            }
        } catch (Exception e) {
            // Em vez de System.err, considere usar um JOptionPane para exibir o erro ao usuário
            JOptionPane.showMessageDialog(this, "Erro ao listar participantes: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace(); // Para depuração, ainda imprima no console
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ignored) {}
            new TelaOrganizador().setVisible(true);
        });
    }
}
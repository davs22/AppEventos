package view.Inicio;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import service.EventosService;
import service.InscricaoService;
import service.PalestranteService;
import service.ParticipanteService;
import table.Eventos;
import table.Inscricao;
import table.Participante;
import utils.SessaoUsuario;
import table.Palestrante;
import view.CRUD.Atualizar.TelaAtualizarEventos;
import view.CRUD.Atualizar.TelaAtualizarPalestrantes;
import view.CRUD.Atualizar.TelaAtualizarParticipantesUsuario;
import view.CRUD.Criar.TelaCriarEvento;
import view.CRUD.Criar.TelaCriarInscricao;
import view.CRUD.Criar.TelaInserirPalestrante;
import view.CRUD.Criar.TelaInserirParticipante;
import view.CRUD.Excluir.TelaExcluirInscricao;

public class TelaUsuario extends JFrame {

    private JTable tabelaEventos;
    private JTable tabelaEventosInscritos;
    private DefaultTableModel modeloTabelaEventos;
    private DefaultTableModel modeloTabelaEventosInscritos;
    private JTable tabelaParticipantes;
    private DefaultTableModel modeloTabelaParticipantes;
    private EventosService es = new EventosService();
    private ParticipanteService ps = new ParticipanteService();
    private PalestranteService ps1 = new PalestranteService();
    private InscricaoService is = new InscricaoService();

    public TelaUsuario() {
        super("Sistema de Eventos");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null);

        JPanel painelPrincipal = new JPanel(new BorderLayout(10, 10));
        painelPrincipal.setBorder(new EmptyBorder(10, 10, 10, 10));

        JTabbedPane abas = new JTabbedPane();
        abas.addTab("Eventos", criarPainelEventos());
        abas.addTab("Perfil", criarPainelPerfil());
        abas.addTab("Eventos Inscritos", criarPainelEventosInscritos());

        painelPrincipal.add(abas, BorderLayout.CENTER);
        add(painelPrincipal);
    }

    private JPanel criarPainelEventos() {
        JPanel painel = new JPanel(new BorderLayout(10, 10));
        String[] colunas = {"ID", "Nome", "Descrição", "Data", "Local", "Capacidade", "Palestrante ID"};
        modeloTabelaEventos = new DefaultTableModel(colunas, 0) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabelaEventos = new JTable(modeloTabelaEventos);
        tabelaEventos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scroll = new JScrollPane(tabelaEventos);
        painel.add(scroll, BorderLayout.CENTER);

        JPanel botoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        JButton btnInscrever = new JButton("Inscrever");
        JButton btnCancelarInscricao = new JButton("Cancelar Inscrição");
        botoes.add(btnInscrever);
        botoes.add(btnCancelarInscricao);
        painel.add(botoes, BorderLayout.SOUTH);

        btnInscrever.addActionListener(e -> {
        int selectedRow = tabelaEventos.getSelectedRow();
        if (selectedRow >= 0) {
            int eventoId = (int) modeloTabelaEventos.getValueAt(selectedRow, 0);
            try {
                String resposta = is.inscreverParticipante(SessaoUsuario.idParticipanteLogado, eventoId);
                JOptionPane.showMessageDialog(this, resposta);
                atualizarEventosInscritos();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro ao se inscrever: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Selecione um evento para se inscrever.", "Aviso", JOptionPane.WARNING_MESSAGE);
        }
    });


        btnCancelarInscricao.addActionListener(e -> {
            TelaExcluirInscricao telaExcluir = new TelaExcluirInscricao();
            telaExcluir.setVisible(true);
            this.setVisible(false);
        });

        carregarEventos();

        return painel;
    }

    private JPanel criarPainelPerfil() {
        JPanel painel = new JPanel(new BorderLayout(10, 10));
        String[] colunas = {"ID", "Nome", "Sexo", "Email", "Celular", "Senha", "Tipo"};
        modeloTabelaParticipantes = new DefaultTableModel(colunas, 0) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabelaParticipantes = new JTable(modeloTabelaParticipantes);
        tabelaParticipantes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scroll = new JScrollPane(tabelaParticipantes);
        painel.add(scroll, BorderLayout.CENTER);

        JPanel botoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        JButton btnEditar = new JButton("Atualizar Dados");
        botoes.add(btnEditar);
        painel.add(botoes, BorderLayout.SOUTH);

        btnEditar.addActionListener(e -> {
            TelaAtualizarParticipantesUsuario telaAtualizar = new TelaAtualizarParticipantesUsuario();
            telaAtualizar.setVisible(true);
            this.setVisible(false);
        });

        carregarParticipantes();
        return painel;
    }

    private JPanel criarPainelEventosInscritos() {
        JPanel painel = new JPanel(new BorderLayout(10, 10));

        String[] colunas = {"ID", "ID Participante", "Nome Participante", "ID Evento", "Nome Evento"};
        modeloTabelaEventosInscritos = new DefaultTableModel(colunas, 0) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tabelaEventosInscritos = new JTable(modeloTabelaEventosInscritos);
        tabelaEventosInscritos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scroll = new JScrollPane(tabelaEventosInscritos);
        painel.add(scroll, BorderLayout.CENTER);

        JButton btnBuscar = new JButton("Buscar Eventos Inscritos");
        btnBuscar.addActionListener(e -> carregarEventosInscritosPorId());

        JPanel painelBusca = new JPanel(new FlowLayout(FlowLayout.CENTER));
        painelBusca.add(btnBuscar);
        painel.add(painelBusca, BorderLayout.SOUTH);

        return painel;
    }

    public void atualizarTabelaEventos() {
        carregarEventos();
    }

    public void atualizarTabelaPerfil() {
        carregarParticipantes();
    }

    public void atualizarEventosInscritos() {
        carregarEventosInscritosPorId();
    }

    private void carregarEventos() {
        modeloTabelaEventos.setRowCount(0);
        try {
            List<Eventos> eventos = es.listarEventos();
            for (Eventos e : eventos) {
                modeloTabelaEventos.addRow(new Object[]{
                        e.getId(), e.getNome(), e.getDescricao(), e.getData(),
                        e.getLocal(), e.getCapacidade(), e.getPalestranteId()
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao listar eventos: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void carregarParticipantes() {
        modeloTabelaParticipantes.setRowCount(0);
        try {
            List<Participante> participantes = ps.listarTodos();
            for (Participante p : participantes) {
                modeloTabelaParticipantes.addRow(new Object[]{
                        p.getId(), p.getNome(), p.getSexo(), p.getEmail(),
                        p.getCelular(), p.getSenha(), p.getTipo()
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao listar participantes: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void carregarEventosInscritosPorId() {
        modeloTabelaEventosInscritos.setRowCount(0);
        try {
            List<Inscricao> inscricoes = is.listarInscricoesComDetalhesPorParticipante(SessaoUsuario.idParticipanteLogado = 1
);
            for (Inscricao i : inscricoes) {
                modeloTabelaEventosInscritos.addRow(new Object[]{
                        i.getId(), i.getIdParticipante(), i.getNomeParticipante(),
                        i.getIdEvento(), i.getNomeEvento()
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao listar eventos inscritos: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ignored) {}

            TelaUsuario tela = new TelaUsuario();
            SessaoUsuario.idParticipanteLogado = 3;
            tela.setVisible(true);
        });
    }
}
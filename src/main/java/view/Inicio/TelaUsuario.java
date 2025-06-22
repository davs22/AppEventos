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
import view.TelaInicial;
import view.CRUD.Atualizar.TelaAtualizarParticipantesUsuario;

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

    // 🔍 Painel de busca
    JPanel painelBusca = new JPanel(new FlowLayout(FlowLayout.LEFT));
    JTextField txtBuscaEvento = new JTextField(20);
    JButton btnBuscarEvento = new JButton("Pesquisar");
    painelBusca.add(new JLabel("Buscar Evento:"));
    painelBusca.add(txtBuscaEvento);
    painelBusca.add(btnBuscarEvento);
    painel.add(painelBusca, BorderLayout.NORTH); // Adiciona a barra de busca no topo

    // 📋 Tabela de eventos
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

    // ✅ Botão de inscrição
    JPanel botoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
    JButton btnInscrever = new JButton("Inscrever");
    botoes.add(btnInscrever);
    painel.add(botoes, BorderLayout.SOUTH);

    // 📌 Ação do botão de inscrição
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
            int selectedRow = tabelaParticipantes.getSelectedRow();
            if (selectedRow >= 0) { // Verifica se alguma linha foi selecionada
                // Obtém o ID do evento da primeira coluna (índice 0) da linha selecionada
                int participanteId = (int) modeloTabelaParticipantes.getValueAt(selectedRow, 0);

                // Instancia a TelaAtualizarEventos (sua tela de edição) passando o ID e a referência da tela principal
                TelaAtualizarParticipantesUsuario telaAtualizar = new TelaAtualizarParticipantesUsuario(this, participanteId);
                telaAtualizar.setVisible(true);
                this.setVisible(false); // Esconde a tela principal
            } else {
                JOptionPane.showMessageDialog(this, "Por favor, selecione um participante para editar.",
                                              "Nenhum Participante Selecionado", JOptionPane.WARNING_MESSAGE);
            }
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

        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        JButton btnBuscar = new JButton("Buscar Eventos Inscritos");
        JButton btnCancelarInscricao = new JButton("Cancelar Inscrição");

        btnBuscar.addActionListener(e -> carregarEventosInscritosPorId());
        btnCancelarInscricao.addActionListener(e -> cancelarInscricaoSelecionada());

        painelBotoes.add(btnBuscar);
        painelBotoes.add(btnCancelarInscricao);
        painel.add(painelBotoes, BorderLayout.SOUTH);

        return painel;
    }

    private void cancelarInscricaoSelecionada() {
        int selectedRow = tabelaEventosInscritos.getSelectedRow();

        if (selectedRow >= 0) {
            int idParticipante = SessaoUsuario.idParticipanteLogado;
            int idEvento = (int) modeloTabelaEventosInscritos.getValueAt(selectedRow, 3); // coluna ID Evento

            int opcao = JOptionPane.showConfirmDialog(
                this,
                "Tem certeza que deseja cancelar sua inscrição no evento ID " + idEvento + "?",
                "Confirmação",
                JOptionPane.YES_NO_OPTION
            );

            if (opcao == JOptionPane.YES_OPTION) {
                try {
                    InscricaoService is = new InscricaoService();
                    String resultado = is.excluirInscricao(idParticipante, idEvento);
                    JOptionPane.showMessageDialog(this, resultado);
                    atualizarEventosInscritos();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Erro ao cancelar inscrição: " + ex.getMessage());
                    ex.printStackTrace();
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Selecione uma inscrição para cancelar.");
        }
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
    modeloTabelaParticipantes.setRowCount(0); // Limpa a tabela

    try {
        Participante participante = ps.buscarParticipantePorId(SessaoUsuario.idParticipanteLogado);
        if (participante != null) {
            modeloTabelaParticipantes.addRow(new Object[]{
                participante.getId(),
                participante.getNome(),
                participante.getSexo(),
                participante.getEmail(),
                participante.getCelular(),
                "******", // Opcional: não mostrar a senha
                participante.getTipo()
            });
        } else {
            JOptionPane.showMessageDialog(this, "Participante não encontrado.");
        }
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Erro ao carregar participante: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        e.printStackTrace();
    }
}

    private void carregarEventosInscritosPorId() {
        modeloTabelaEventosInscritos.setRowCount(0);
        try {
            List<Inscricao> inscricoes = is.listarInscricoesComDetalhesPorParticipante(SessaoUsuario.idParticipanteLogado);
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
            new TelaInicial().setVisible(true); // Começa pela tela de login
        });
    }
}

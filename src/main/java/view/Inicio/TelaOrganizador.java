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
import table.Palestrante;
import view.CRUD.Atualizar.TelaAtualizarEventos;
import view.CRUD.Atualizar.TelaAtualizarPalestrantesOrganizador;
import view.CRUD.Atualizar.TelaAtualizarParticipantesOrganizador;
import view.CRUD.Criar.TelaCriarEvento;
import view.CRUD.Criar.TelaInserirPalestrante;
import view.CRUD.Criar.TelaInserirParticipante;

public class TelaOrganizador extends JFrame {

    private JTable tabelaEventos;
    private DefaultTableModel modeloTabelaEventos;
    private JTable tabelaParticipantes;
    private DefaultTableModel modeloTabelaParticipantes;
    private JTable tabelaPalestrantes;
    private DefaultTableModel modeloTabelaPalestrantes;
    private JTable tabelaInscricoes;
    private DefaultTableModel modeloTabelaInscricoes;

    private JComboBox<String> comboFiltroParticipantes;
    private JTextField txtBuscaParticipantes;
    private JButton btnBuscaParticipantes;

    

    private EventosService es = new EventosService();
    private ParticipanteService ps = new ParticipanteService();
    private PalestranteService ps1 = new PalestranteService();
    private InscricaoService is = new InscricaoService();

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
        abas.addTab("Inscricoes", criarPainelInscricoes());

        painelPrincipal.add(abas, BorderLayout.CENTER);
        add(painelPrincipal);
    }

    private JPanel criarPainelEventos() {
    JPanel painel = new JPanel(new BorderLayout(10, 10));

    // Painel de busca (combo + campo + botão)
    JPanel painelBusca = new JPanel(new FlowLayout(FlowLayout.LEFT));
    JComboBox<String> comboFiltroEventos = new JComboBox<>(new String[]{
        "nome", "descricao", "data", "local", "palestranteid"
    });
    JTextField txtBuscaEventos = new JTextField(20);
    JButton btnBuscaEventos = new JButton("Buscar");
    painelBusca.add(new JLabel("Filtrar por: "));
    painelBusca.add(comboFiltroEventos);
    painelBusca.add(txtBuscaEventos);
    painelBusca.add(btnBuscaEventos);

    painel.add(painelBusca, BorderLayout.NORTH);

    // Tabela de eventos
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
        TelaCriarEvento telaCriar = new TelaCriarEvento(this);
        telaCriar.setVisible(true);
        this.setVisible(false);
    });

    btnEditar.addActionListener(e -> {
        int selectedRow = tabelaEventos.getSelectedRow();
        if (selectedRow >= 0) {
            int eventoId = (int) modeloTabelaEventos.getValueAt(selectedRow, 0);
            TelaAtualizarEventos telaAtualizar = new TelaAtualizarEventos(this, eventoId);
            telaAtualizar.setVisible(true);
            this.setVisible(false);
        } else {
            JOptionPane.showMessageDialog(this, "Selecione um evento para editar.", "Aviso", JOptionPane.WARNING_MESSAGE);
        }
    });

    btnExcluir.addActionListener(e -> {
        int selectedRow = tabelaEventos.getSelectedRow();
        if (selectedRow >= 0) {
            int eventoId = (int) modeloTabelaEventos.getValueAt(selectedRow, 0);
            int confirm = JOptionPane.showConfirmDialog(this,
                    "Tem certeza que deseja excluir o evento ID: " + eventoId + "?",
                    "Confirmar Exclusão", JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                try {
                    es.excluirEvento(eventoId);
                    JOptionPane.showMessageDialog(this, "Evento excluído com sucesso!");
                    atualizarTabelaEventos();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Erro ao excluir evento: " + ex.getMessage());
                    ex.printStackTrace();
                }
            }
        }
    });

    // Listener do botão buscar
    btnBuscaEventos.addActionListener(e -> {
        String filtro = (String) comboFiltroEventos.getSelectedItem();
        String valor = txtBuscaEventos.getText();
        carregarEventos(filtro, valor);
    });

    // Carregar todos os eventos inicialmente
    carregarEventos();

    return painel;
}



private JPanel criarPainelParticipantes() {
    JPanel painel = new JPanel(new BorderLayout(10, 10));

    // Painel de busca (filtro + campo + botão)
    JPanel painelBusca = new JPanel(new FlowLayout(FlowLayout.LEFT));
    comboFiltroParticipantes = new JComboBox<>(new String[] {
        "nome", "sexo", "email", "celular", "tipo"
    });
    txtBuscaParticipantes = new JTextField(20);
    btnBuscaParticipantes = new JButton("Buscar");
    painelBusca.add(new JLabel("Filtrar por: "));
    painelBusca.add(comboFiltroParticipantes);
    painelBusca.add(txtBuscaParticipantes);
    painelBusca.add(btnBuscaParticipantes);

    painel.add(painelBusca, BorderLayout.NORTH);

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
    JButton btnCriar = new JButton("Criar");
    JButton btnEditar = new JButton("Editar");
    JButton btnExcluir = new JButton("Excluir");

    botoes.add(btnCriar);
    botoes.add(btnEditar);
    botoes.add(btnExcluir);
    painel.add(botoes, BorderLayout.SOUTH);

    btnCriar.addActionListener(e -> {
        TelaInserirParticipante tela = new TelaInserirParticipante(this);
        tela.setVisible(true);
        this.setVisible(false);
    });

    btnEditar.addActionListener(e -> {
        int row = tabelaParticipantes.getSelectedRow();
        if (row >= 0) {
            int id = (int) modeloTabelaParticipantes.getValueAt(row, 0);
            TelaAtualizarParticipantesOrganizador tela = new TelaAtualizarParticipantesOrganizador(this, id);
            tela.setVisible(true);
            this.setVisible(false);
        } else {
            JOptionPane.showMessageDialog(this, "Selecione um participante para editar.");
        }
    });

    btnExcluir.addActionListener(e -> {
        int row = tabelaParticipantes.getSelectedRow();
        if (row >= 0) {
            int id = (int) modeloTabelaParticipantes.getValueAt(row, 0);
            int confirm = JOptionPane.showConfirmDialog(this, "Deseja excluir participante ID: " + id + "?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                try {
                    ps.excluirParticipante(id);
                    JOptionPane.showMessageDialog(this, "Participante excluído.");
                    atualizarTabelaParticipantes();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage());
                }
            }
        }
    });

    // Listener do botão buscar, chama carregarParticipantes com filtro
    btnBuscaParticipantes.addActionListener(e -> {
        String filtro = (String) comboFiltroParticipantes.getSelectedItem();
        String valor = txtBuscaParticipantes.getText();
        carregarParticipantes(filtro, valor);
    });

    // Carrega tudo inicialmente
    carregarParticipantes();

    return painel;
}

    private JPanel criarPainelPalestrantes() {
    JPanel painel = new JPanel(new BorderLayout(10, 10));

    // Painel de busca
    JPanel painelBusca = new JPanel(new FlowLayout(FlowLayout.LEFT));
    JLabel lblBuscar = new JLabel("Buscar por:");
    String[] opcoesBusca = {"nome", "curriculo", "area_atuacao"};
    JComboBox<String> comboFiltro = new JComboBox<>(opcoesBusca);
    JTextField txtBusca = new JTextField(20);
    JButton btnBuscar = new JButton("Buscar");

    painelBusca.add(lblBuscar);
    painelBusca.add(comboFiltro);
    painelBusca.add(txtBusca);
    painelBusca.add(btnBuscar);
    painel.add(painelBusca, BorderLayout.NORTH);

    // Tabela
    String[] colunas = {"ID", "Nome", "Curriculo", "Área de Atuação"};
    modeloTabelaPalestrantes = new DefaultTableModel(colunas, 0) {
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };

    tabelaPalestrantes = new JTable(modeloTabelaPalestrantes);
    tabelaPalestrantes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
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

    // Ações dos botões CRUD
    btnCriar.addActionListener(e -> {
        TelaInserirPalestrante tela = new TelaInserirPalestrante(this);
        tela.setVisible(true);
        this.setVisible(false);
    });

    btnEditar.addActionListener(e -> {
        int row = tabelaPalestrantes.getSelectedRow();
        if (row >= 0) {
            int id = (int) modeloTabelaPalestrantes.getValueAt(row, 0);
            TelaAtualizarPalestrantesOrganizador tela = new TelaAtualizarPalestrantesOrganizador(this, id);
            tela.setVisible(true);
            this.setVisible(false);
        } else {
            JOptionPane.showMessageDialog(this, "Selecione um palestrante para editar.");
        }
    });

    btnExcluir.addActionListener(e -> {
        int row = tabelaPalestrantes.getSelectedRow();
        if (row >= 0) {
            int id = (int) modeloTabelaPalestrantes.getValueAt(row, 0);
            int confirm = JOptionPane.showConfirmDialog(this, "Excluir palestrante ID: " + id + "?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                try {
                    ps1.excluirPalestrante(id);
                    JOptionPane.showMessageDialog(this, "Palestrante excluído.");
                    atualizarTabelaPalestrantes();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage());
                }
            }
        }
    });

    // Ação do botão Buscar
    btnBuscar.addActionListener(e -> {
        String tipo = comboFiltro.getSelectedItem().toString().toLowerCase();
        String valor = txtBusca.getText().trim();
        if (valor.isEmpty()) {
            carregarPalestrantes(); // Sem filtro
        } else {
            carregarPalestrantes(tipo, valor); // Com filtro
        }
    });

    carregarPalestrantes(); // Carregamento inicial
    return painel;
}


    private JPanel criarPainelInscricoes() {
        JPanel painel = new JPanel(new BorderLayout(10, 10));
        String[] colunas = {"ID", "ID Participante", "Nome", "ID Evento", "Nome"};
        modeloTabelaInscricoes = new DefaultTableModel(colunas, 0) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tabelaInscricoes = new JTable(modeloTabelaInscricoes);
        tabelaInscricoes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scroll = new JScrollPane(tabelaInscricoes);
        painel.add(scroll, BorderLayout.CENTER);

        carregarInscricoes();
        return painel;
    }

    public void atualizarTabelaEventos() {
        carregarEventos();
    }

    public void atualizarTabelaParticipantes() {
        carregarParticipantes();
    }

    public void atualizarTabelaPalestrantes() {
        carregarPalestrantes();
    }

    public void atualizarTabelaInscricoes() {
        carregarInscricoes();
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
            JOptionPane.showMessageDialog(this, "Erro ao listar eventos: " + e.getMessage());
        }
    }
    private void carregarEventos(String tipo, String valor) {
    modeloTabelaEventos.setRowCount(0);
    try {
        List<Eventos> eventos;

        if (tipo == null || tipo.isBlank() || valor == null || valor.isBlank()) {
            eventos = es.listarEventos();
        } else {
            eventos = es.listarPorParametro(tipo, valor);
        }

        for (Eventos e : eventos) {
            modeloTabelaEventos.addRow(new Object[]{
                e.getId(), e.getNome(), e.getDescricao(), e.getData(),
                e.getLocal(), e.getCapacidade(), e.getPalestranteId()
            });
        }
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Erro ao listar eventos: " + e.getMessage());
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
        JOptionPane.showMessageDialog(this, "Erro ao listar participantes: " + e.getMessage());
    }
}

private void carregarParticipantes(String tipo, String valor) {
    modeloTabelaParticipantes.setRowCount(0);
    try {
        List<Participante> participantes;

        if (tipo == null || tipo.isBlank() || valor == null || valor.isBlank()) {
            participantes = ps.listarTodos();
        } else {
            participantes = ps.listarPorParamentro(tipo, valor);
        }

        for (Participante p : participantes) {
            modeloTabelaParticipantes.addRow(new Object[]{
                p.getId(), p.getNome(), p.getSexo(), p.getEmail(),
                p.getCelular(), p.getSenha(), p.getTipo()
            });
        }
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Erro ao listar participantes: " + e.getMessage());
    }
}


    private void carregarPalestrantes() {
        modeloTabelaPalestrantes.setRowCount(0);
        try {
            List<Palestrante> palestrantes = ps1.listarTodos();
            for (Palestrante p : palestrantes) {
                modeloTabelaPalestrantes.addRow(new Object[]{
                        p.getId(), p.getNome(), p.getCurriculo(), p.getAreaAtuacao()
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao listar palestrantes: " + e.getMessage());
        }
    }
    private void carregarPalestrantes(String tipo, String valor) {
    modeloTabelaPalestrantes.setRowCount(0);
    try {
        List<Palestrante> palestrantes;

        if (tipo == null || tipo.isBlank() || valor == null || valor.isBlank()) {
            palestrantes = ps1.listarTodos();
        } else {
            if(tipo.equals("area_atuacao")){
                palestrantes = ps1.listarPorParametro("areaAtuacao", valor);
            }else
            palestrantes = ps1.listarPorParametro(tipo, valor);
        }

        for (Palestrante p : palestrantes) {
            modeloTabelaPalestrantes.addRow(new Object[]{
                p.getId(), p.getNome(), p.getCurriculo(), p.getAreaAtuacao()
            });
        }
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Erro ao listar palestrantes: " + e.getMessage());
    }
}

    private void carregarInscricoes() {
        modeloTabelaInscricoes.setRowCount(0);
        try {
            List<Inscricao> inscricoes = is.listarInscricoesComDetalhes();
            for (Inscricao i : inscricoes) {
                modeloTabelaInscricoes.addRow(new Object[]{
                        i.getId(), i.getIdParticipante(), i.getNomeParticipante(),
                        i.getIdEvento(), i.getNomeEvento()
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao listar inscrições: " + e.getMessage());
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

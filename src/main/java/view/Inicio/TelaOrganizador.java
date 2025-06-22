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
import view.CRUD.Atualizar.TelaAtualizarPalestrantes;
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

        // Define colunas da tabela
        String[] colunas = {"ID", "Nome", "Descrição", "Data", "Local", "Capacidade", "Palestrante ID"};
        modeloTabelaEventos = new DefaultTableModel(colunas, 0) {
            @Override // Sobrescrita para garantir que células não sejam editáveis diretamente na tabela
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tabelaEventos = new JTable(modeloTabelaEventos);
        // Permite selecionar apenas uma linha por vez, crucial para edição/exclusão
        tabelaEventos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scroll = new JScrollPane(tabelaEventos);
        painel.add(scroll, BorderLayout.CENTER);

        // Botões de Ação
        JPanel botoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        JButton btnCriar = new JButton("Criar");
        JButton btnEditar = new JButton("Editar");
        JButton btnExcluir = new JButton("Excluir");

        botoes.add(btnCriar);
        botoes.add(btnEditar);
        botoes.add(btnExcluir);
        painel.add(botoes, BorderLayout.SOUTH);

        // --- ActionListeners para os botões da aba de Eventos ---

        // Ação para o botão CRIAR Evento
        btnCriar.addActionListener(e -> {
            TelaCriarEvento telaCriar = new TelaCriarEvento(this); // Passa referência da TelaOrganizador
            telaCriar.setVisible(true);
            this.setVisible(false); // Esconde a tela principal enquanto a de criação está aberta
        });

        // Ação para o botão EDITAR Evento
        btnEditar.addActionListener(e -> {
            int selectedRow = tabelaEventos.getSelectedRow();
            if (selectedRow >= 0) { // Verifica se alguma linha foi selecionada
                // Obtém o ID do evento da primeira coluna (índice 0) da linha selecionada
                int eventoId = (int) modeloTabelaEventos.getValueAt(selectedRow, 0);

                // Instancia a TelaAtualizarEventos (sua tela de edição) passando o ID e a referência da tela principal
                TelaAtualizarEventos telaAtualizar = new TelaAtualizarEventos(this, eventoId);
                telaAtualizar.setVisible(true);
                this.setVisible(false); // Esconde a tela principal
            } else {
                JOptionPane.showMessageDialog(this, "Por favor, selecione um evento para editar.",
                                              "Nenhum Evento Selecionado", JOptionPane.WARNING_MESSAGE);
            }
        });

        // Ação para o botão EXCLUIR Evento
        btnExcluir.addActionListener(e -> {
            int selectedRow = tabelaEventos.getSelectedRow();
            if (selectedRow >= 0) {
                int eventoId = (int) modeloTabelaEventos.getValueAt(selectedRow, 0);
                int confirm = JOptionPane.showConfirmDialog(this,
                                                            "Tem certeza que deseja excluir o evento ID: " + eventoId + "?",
                                                            "Confirmar Exclusão", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (confirm == JOptionPane.YES_OPTION) {
                    try {
                        es.excluirEvento(eventoId); // Chama o serviço para excluir o evento
                        JOptionPane.showMessageDialog(this, "Evento excluído com sucesso!");
                        atualizarTabelaEventos(); // Atualiza a tabela após a exclusão
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(this, "Erro ao excluir evento: " + ex.getMessage(),
                                                      "Erro", JOptionPane.ERROR_MESSAGE);
                        ex.printStackTrace(); // Para depuração
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Por favor, selecione um evento para excluir.",
                                              "Nenhum Evento Selecionado", JOptionPane.WARNING_MESSAGE);
            }
        });

        // Carrega os eventos ao iniciar a tela pela primeira vez
        carregarEventos();

        return painel;
    }

    // --- Métodos de Atualização das Tabelas ---

    // Este método será chamado pelas telas de CRUD (Criar/Atualizar) para Eventos
    public void atualizarTabelaEventos() {
        carregarEventos(); // Recarrega todos os eventos na tabela
    }

    // Método para atualizar a tabela de Participantes
    public void atualizarTabelaParticipantes() {
        carregarParticipantes();
    }

    // Método para atualizar a tabela de Palestrantes
    public void atualizarTabelaPalestrantes() {
        carregarPalestrantes();
    }

    public void atualizarTabelaInscricoes() {
        carregarInscricoes();
    }

    // --- Métodos para Criar Paineis de Outras Abas ---

    private JPanel criarPainelParticipantes() {
        JPanel painel = new JPanel(new BorderLayout(10, 10));

        String[] colunas = {"ID", "Nome", "Sexo", "Email", "Celular", "Senha", "Tipo"};
        modeloTabelaParticipantes = new DefaultTableModel(colunas, 0) {
            @Override
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

        // Ação para o botão CRIAR Evento
        btnCriar.addActionListener(e -> {
            TelaInserirParticipante telaCriar = new TelaInserirParticipante(this); // Passa referência da TelaOrganizador
            telaCriar.setVisible(true);
            this.setVisible(false); // Esconde a tela principal enquanto a de criação está aberta
        });

        // Ação para o botão EDITAR Evento
        btnEditar.addActionListener(e -> {
            int selectedRow = tabelaParticipantes.getSelectedRow();
            if (selectedRow >= 0) { // Verifica se alguma linha foi selecionada
                // Obtém o ID do evento da primeira coluna (índice 0) da linha selecionada
                int participanteId = (int) modeloTabelaParticipantes.getValueAt(selectedRow, 0);

                // Instancia a TelaAtualizarEventos (sua tela de edição) passando o ID e a referência da tela principal
                TelaAtualizarParticipantesOrganizador telaAtualizar = new TelaAtualizarParticipantesOrganizador(this, participanteId);
                telaAtualizar.setVisible(true);
                this.setVisible(false); // Esconde a tela principal
            } else {
                JOptionPane.showMessageDialog(this, "Por favor, selecione um participante para editar.",
                                              "Nenhum Participante Selecionado", JOptionPane.WARNING_MESSAGE);
            }
        });

        // Ação para o botão EXCLUIR Evento
        btnExcluir.addActionListener(e -> {
            int selectedRow = tabelaParticipantes.getSelectedRow();
            if (selectedRow >= 0) {
                int participanteId = (int) modeloTabelaParticipantes.getValueAt(selectedRow, 0);
                int confirm = JOptionPane.showConfirmDialog(this,
                                                            "Tem certeza que deseja excluir o participante ID: " + participanteId + "?",
                                                            "Confirmar Exclusão", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (confirm == JOptionPane.YES_OPTION) {
                    try {
                        ps.excluirParticipante(participanteId); // Chama o serviço para excluir o evento
                        JOptionPane.showMessageDialog(this, "Participante excluído com sucesso!");
                        atualizarTabelaParticipantes();// Atualiza a tabela após a exclusão
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(this, "Erro ao excluir participante: " + ex.getMessage(),
                                                      "Erro", JOptionPane.ERROR_MESSAGE);
                        ex.printStackTrace(); // Para depuração
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Por favor, selecione um participante para excluir.",
                                              "Nenhum Participante Selecionado", JOptionPane.WARNING_MESSAGE);
            }
        });

        carregarParticipantes();

        return painel;
    }

    private JPanel criarPainelPalestrantes() {
        JPanel painel = new JPanel(new BorderLayout(10, 10));

        String[] colunas = {"ID", "Nome", "Curriculo", "Área de Atuação"};
        modeloTabelaPalestrantes = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tabelaPalestrantes = new JTable(modeloTabelaPalestrantes);
        tabelaPalestrantes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scroll = new JScrollPane(tabelaPalestrantes);
        painel.add(scroll, BorderLayout.CENTER);

        JPanel botoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        JButton btnCriar = new JButton("Criar");
        JButton btnEditar = new JButton("Editar");
        JButton btnExcluir = new JButton("Excluir");

        botoes.add(btnCriar);
        botoes.add(btnEditar);
        botoes.add(btnExcluir);
        painel.add(botoes, BorderLayout.SOUTH);

        // Ação para o botão CRIAR Evento
        btnCriar.addActionListener(e -> {
            TelaInserirPalestrante telaCriar = new TelaInserirPalestrante(this); // Passa referência da TelaOrganizador
            telaCriar.setVisible(true);
            this.setVisible(false); // Esconde a tela principal enquanto a de criação está aberta
        });

        // Ação para o botão EDITAR Evento
        btnEditar.addActionListener(e -> {
            int selectedRow = tabelaPalestrantes.getSelectedRow();
            if (selectedRow >= 0) { // Verifica se alguma linha foi selecionada
                // Obtém o ID do evento da primeira coluna (índice 0) da linha selecionada
                int palestranteId = (int) modeloTabelaPalestrantes.getValueAt(selectedRow, 0);

                // Instancia a TelaAtualizarEventos (sua tela de edição) passando o ID e a referência da tela principal
                TelaAtualizarPalestrantes telaAtualizar = new TelaAtualizarPalestrantes(this, palestranteId);
                telaAtualizar.setVisible(true);
                this.setVisible(false); // Esconde a tela principal
            } else {
                JOptionPane.showMessageDialog(this, "Por favor, selecione um palestrante para editar.",
                                              "Nenhum Palestrante Selecionado", JOptionPane.WARNING_MESSAGE);
            }
        });

        // Ação para o botão EXCLUIR Evento
        btnExcluir.addActionListener(e -> {
            int selectedRow = tabelaPalestrantes.getSelectedRow();
            if (selectedRow >= 0) {
                int palestranteId = (int) modeloTabelaPalestrantes.getValueAt(selectedRow, 0);
                int confirm = JOptionPane.showConfirmDialog(this,
                                                            "Tem certeza que deseja excluir o palestrante ID: " + palestranteId + "?",
                                                            "Confirmar Exclusão", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (confirm == JOptionPane.YES_OPTION) {
                    try {
                        ps1.excluirPalestrante(palestranteId); // Chama o serviço para excluir o evento
                        JOptionPane.showMessageDialog(this, "Palestrante excluído com sucesso!");
                        atualizarTabelaPalestrantes(); // Atualiza a tabela após a exclusão
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(this, "Erro ao excluir palestrante: " + ex.getMessage(),
                                                      "Erro", JOptionPane.ERROR_MESSAGE);
                        ex.printStackTrace(); // Para depuração
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Por favor, selecione um palestrante para excluir.",
                                              "Nenhum Palestrante Selecionado", JOptionPane.WARNING_MESSAGE);
            }
        });

        carregarPalestrantes();

        return painel;
    }

    private JPanel criarPainelInscricoes() {
        JPanel painel = new JPanel(new BorderLayout(10, 10));

        // Define colunas da tabela
        String[] colunas = {"ID", "ID Participante", "Nome", "ID Evento", "Nome"};
        modeloTabelaInscricoes = new DefaultTableModel(colunas, 0) {
            @Override // Sobrescrita para garantir que células não sejam editáveis diretamente na tabela
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tabelaInscricoes = new JTable(modeloTabelaInscricoes);
        // Permite selecionar apenas uma linha por vez, crucial para edição/exclusão
        tabelaInscricoes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scroll = new JScrollPane(tabelaInscricoes);
        painel.add(scroll, BorderLayout.CENTER);

         carregarInscricoes();

        return painel;
    }

    // --- Métodos de Carregamento de Dados para as Tabelas ---
    private void carregarEventos() {
        modeloTabelaEventos.setRowCount(0); // Limpa a tabela antes de recarregar
        try {
            List<Eventos> eventos = es.listarEventos();
            for (Eventos e : eventos) {
                modeloTabelaEventos.addRow(new Object[]{
                        e.getId(),
                        e.getNome(),
                        e.getDescricao(),
                        e.getData(), // Sua data já deve ser formatada ou ser um tipo compatível para exibição
                        e.getLocal(),
                        e.getCapacidade(),
                        e.getPalestranteId()
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao listar eventos: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace(); // Para depuração, imprima a stack trace
        }
    }

    private void carregarParticipantes() {
        modeloTabelaParticipantes.setRowCount(0); // Limpa a tabela
        try {
            List<Participante> participantes = ps.listarTodos();
            for (Participante p : participantes) {
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
            JOptionPane.showMessageDialog(this, "Erro ao listar participantes: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void carregarPalestrantes() {
        modeloTabelaPalestrantes.setRowCount(0); // Limpa a tabela
        try {
            List<Palestrante> palestrantes = ps1.listarTodos();
            for (Palestrante p1 : palestrantes) {
                modeloTabelaPalestrantes.addRow(new Object[]{
                        p1.getId(),
                        p1.getNome(),
                        p1.getCurriculo(),
                        p1.getAreaAtuacao(),
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao listar palestrantes: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void carregarInscricoes() {
        modeloTabelaInscricoes.setRowCount(0); // Limpa a tabela antes de recarregar
        try {
            List<Inscricao> inscricao = is.listarInscricoesComDetalhes();
            for (Inscricao i : inscricao) {
                modeloTabelaInscricoes.addRow(new Object[]{
                        i.getId(),
                        i.getIdParticipante(),
                        i.getNomeParticipante(),
                        i.getIdEvento(), // Sua data já deve ser formatada ou ser um tipo compatível para exibição
                        i.getNomeEvento(),
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao listar inscrições: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace(); // Para depuração, imprima a stack trace
        }
    }

    // --- Método Main para Iniciar a Aplicação ---

    public static void main(String[] args) {
        // Garante que a GUI seja executada na Event Dispatch Thread (EDT)
        SwingUtilities.invokeLater(() -> {
            try {
                // Tenta aplicar o Look and Feel nativo do sistema operacional
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ignored) {
                // Ignora exceções, caso não consiga aplicar o Look and Feel
            }
            new TelaOrganizador().setVisible(true); // Cria e mostra a janela principal
        });
    }
}
package view.CRUD.Atualizar;

import service.PalestranteService;
import table.Palestrante;
import view.Inicio.TelaOrganizador;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class TelaAtualizarPalestrantesOrganizador extends JFrame {

    private TelaOrganizador telaPrincipal;
    private int palestranteIdParaEditar;

    private JLabel lblIdValor;
    private JTextField txtNome, txtCurriculo, txtAreaAtuacao;

    private PalestranteService ps = new PalestranteService();

    public TelaAtualizarPalestrantesOrganizador(TelaOrganizador telaPrincipal, int palestranteId) {
        this.telaPrincipal = telaPrincipal;
        this.palestranteIdParaEditar = palestranteId;

        setTitle("Atualizar Palestrante (ID: " + palestranteId + ")");
        setSize(400, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel painelFormulario = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5,5,5,5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // ID (não editável)
        gbc.gridx = 0; gbc.gridy = 0;
        painelFormulario.add(new JLabel("ID:"), gbc);
        gbc.gridx = 1;
        lblIdValor = new JLabel(String.valueOf(palestranteId));
        painelFormulario.add(lblIdValor, gbc);

        // Nome
        gbc.gridx = 0; gbc.gridy++;
        painelFormulario.add(new JLabel("Nome:"), gbc);
        gbc.gridx = 1;
        txtNome = new JTextField(20);
        painelFormulario.add(txtNome, gbc);

        // Currículo
        gbc.gridx = 0; gbc.gridy++;
        painelFormulario.add(new JLabel("Currículo:"), gbc);
        gbc.gridx = 1;
        txtCurriculo = new JTextField(20);
        painelFormulario.add(txtCurriculo, gbc);

        // Área de Atuação
        gbc.gridx = 0; gbc.gridy++;
        painelFormulario.add(new JLabel("Área de Atuação:"), gbc);
        gbc.gridx = 1;
        txtAreaAtuacao = new JTextField(20);
        painelFormulario.add(txtAreaAtuacao, gbc);

        // Botões Atualizar e Voltar
        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        JButton btnAtualizar = new JButton("Atualizar");
        JButton btnVoltar = new JButton("Voltar");

        painelBotoes.add(btnAtualizar);
        painelBotoes.add(btnVoltar);

        gbc.gridx = 0; gbc.gridy++;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        painelFormulario.add(painelBotoes, gbc);

        JScrollPane scrollPane = new JScrollPane(painelFormulario);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        add(scrollPane);

        // Ações dos botões
        btnAtualizar.addActionListener(e -> atualizarPalestrante());
        btnVoltar.addActionListener(e -> voltarParaTelaPrincipal());

        // Carregar dados do palestrante no início
        carregarDadosDoPalestrante();
    }

    private void atualizarPalestrante() {
        try {
            int id = this.palestranteIdParaEditar;
            String nome = txtNome.getText();
            String curriculo = txtCurriculo.getText();
            String areaAtuacao = txtAreaAtuacao.getText();

            ps.atualizarPalestrante(id, nome, curriculo, areaAtuacao);

            JOptionPane.showMessageDialog(this, "Palestrante atualizado com sucesso!");
            voltarParaTelaPrincipal();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao atualizar palestrante: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    private void carregarDadosDoPalestrante() {
    try {
        List<Palestrante> lista = ps.listarPorParametro("id", String.valueOf(palestranteIdParaEditar));
        Palestrante p = lista.get(0);
        
        if (lista != null && !lista.isEmpty()) {
           
            txtNome.setText(p.getNome());
            txtCurriculo.setText(p.getCurriculo());
            txtAreaAtuacao.setText(p.getAreaAtuacao());
        } else {
            JOptionPane.showMessageDialog(this, "Palestrante não encontrado com o ID: " + palestranteIdParaEditar, "Erro", JOptionPane.ERROR_MESSAGE);
            voltarParaTelaPrincipal();
        }
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Erro ao carregar dados do palestrante: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        e.printStackTrace();
        voltarParaTelaPrincipal();
    }
}

    private void voltarParaTelaPrincipal() {
        this.dispose();
        if (telaPrincipal != null) {
            telaPrincipal.setVisible(true);
            telaPrincipal.atualizarTabelaPalestrantes();
        }
    }
}

package view.CRUD.Criar;

import javax.swing.*;
import java.awt.*;
import service.PalestranteService;
import view.Inicio.TelaOrganizador;

public class TelaInserirPalestrante extends JFrame {

    private TelaOrganizador telaPrincipal;

    
    public TelaInserirPalestrante(TelaOrganizador telaPrincipal) {
        this.telaPrincipal = telaPrincipal; 
        setTitle("Inserir Palestrante");
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
        painelFormulario.add(new JLabel("Curriculo:"), gbc);
        gbc.gridx = 1;
        JTextField txtCurriculo = new JTextField(20);
        painelFormulario.add(txtCurriculo, gbc);

        gbc.gridx = 0; gbc.gridy++;
        painelFormulario.add(new JLabel("Área de Atuação:"), gbc);
        gbc.gridx = 1;
        JTextField txtAreaAtuacao = new JTextField(20);
        painelFormulario.add(txtAreaAtuacao, gbc);

        
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
                String curriculo = txtCurriculo.getText();
                String AreaAtuacao = txtAreaAtuacao.getText();

                PalestranteService ps1 = new PalestranteService();
                ps1.inserir(nome, curriculo, AreaAtuacao);
                
                JOptionPane.showMessageDialog(this, "Palestrante inserido com sucesso!");

                voltarParaTelaPrincipal();

            } catch (NullPointerException ex) {
                JOptionPane.showMessageDialog(this, "Por favor, preencha todos os campos obrigatórios", "Campos Vazios", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace(); 
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro ao inserir palestrante: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
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
            telaPrincipal.atualizarTabelaPalestrantes(); 
        }
    }
}
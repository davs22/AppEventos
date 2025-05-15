

import javax.swing.*;
import java.awt.*;
import view.Menu.TelaCriarMenu;
import view.Menu.TelaExibirMenu;
import view.Menu.TelaAtualizarMenu;
import view.Menu.TelaExcluirMenu;

public class TelaPrincipal extends JFrame {
    public TelaPrincipal() {
        setTitle("Sistema de Eventos");
        setSize(500, 350);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel painel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 0, 10, 0); 
        gbc.fill = GridBagConstraints.NONE;
        gbc.gridx = 0;
        gbc.gridy = 0;

        Dimension botaoTamanho = new Dimension(250, 40);

        JButton btnCriar = new JButton("Criar");
        JButton btnExibir = new JButton("Exibir");
        JButton btnAtualizar = new JButton("Atualizar");
        JButton btnExcluir = new JButton("Excluir");

        btnCriar.setPreferredSize(botaoTamanho);
        btnExibir.setPreferredSize(botaoTamanho);
        btnAtualizar.setPreferredSize(botaoTamanho);
        btnExcluir.setPreferredSize(botaoTamanho);

        painel.add(btnCriar, gbc);
        gbc.gridy++;
        painel.add(btnExibir, gbc);
        gbc.gridy++;
        painel.add(btnAtualizar, gbc);
        gbc.gridy++;
        painel.add(btnExcluir, gbc);

        add(painel, BorderLayout.CENTER);

        btnCriar.addActionListener(e -> new TelaCriarMenu().setVisible(true));
        btnExibir.addActionListener(e -> new TelaExibirMenu().setVisible(true));
        btnAtualizar.addActionListener(e -> new TelaAtualizarMenu().setVisible(true));
        btnExcluir.addActionListener(e -> new TelaExcluirMenu().setVisible(true));
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TelaPrincipal().setVisible(true));
    }
}

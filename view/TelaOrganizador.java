package view;
import javax.swing.*;

import view.Button.EstiloBotao;
import view.Menu.MenuCriarOrganizador;
import view.Menu.MenuExcluirOrganizador;
import view.Menu.MenuExibirOrganizador;
import view.Atualizar.TelaAtualizarPalestrante;
public class TelaOrganizador extends JFrame {
    public TelaOrganizador() {
        setTitle("Tela do ADM");
        setSize(400, 300);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        JButton btnCriar = new JButton("Inserir e Criar");
        EstiloBotao.aplicarEstilo(btnCriar);
        JButton btnExcluir = new JButton("Excluir");
        JButton btnExibir = new JButton("Visualizar");
        JButton btnAtualizar = new JButton("Atualizar palestrante");

        btnCriar.setBounds(100, 30, 200, 30);
        btnExcluir.setBounds(100, 80, 200, 30);
        btnExibir.setBounds(100, 130, 200, 30);
        btnAtualizar.setBounds(100, 180, 200, 30);

        add(btnCriar);
        add(btnExcluir);
        add(btnExibir);
        add(btnAtualizar);

        btnCriar.addActionListener(e -> new MenuCriarOrganizador().setVisible(true));
        btnExcluir.addActionListener(e -> new MenuExcluirOrganizador().setVisible(true));
        btnExibir.addActionListener(e -> new MenuExibirOrganizador().setVisible(true));
        btnAtualizar.addActionListener(e -> new TelaAtualizarPalestrante().setVisible(true));
    }
}

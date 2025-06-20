package view.Menu;

import javax.swing.*;
import view.Criar.TelaCriarEvento;
import view.Criar.TelaInserirPalestrante;

public class MenuCriarOrganizador extends JFrame {
    public MenuCriarOrganizador() {
        setTitle("Menu de Criação");
        setSize(400, 300);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        JButton btnCriarEvento = new JButton("Criar Evento");
        JButton btnCriarPalestrante = new JButton("Inserir Palestrante");

        btnCriarEvento.setBounds(100, 30, 200, 30);
        btnCriarPalestrante.setBounds(100, 80, 200, 30);

        add(btnCriarEvento);
        add(btnCriarPalestrante);

        btnCriarEvento.addActionListener(e -> new TelaCriarEvento().setVisible(true));
        btnCriarPalestrante.addActionListener(e -> new TelaInserirPalestrante().setVisible(true));
    }
}

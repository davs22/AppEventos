package view.Menu;

import javax.swing.*;
import view.Criar.TelaCriarEvento;
import view.Criar.TelaInserirParticipante;
import view.Criar.TelaCriarInscricao;
import view.Criar.TelaInserirPalestrante;

public class TelaCriarMenu extends JFrame {
    public TelaCriarMenu() {
        setTitle("Menu de Criação");
        setSize(400, 300);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        JButton btnCriarEvento = new JButton("Criar Evento");
        JButton btnCriarParticipante = new JButton("Criar Participante");
        JButton btnCriarInscricao = new JButton("Criar Inscrição");
        JButton btnCriarPalestrante = new JButton("Criar Palestrante");

        btnCriarEvento.setBounds(100, 30, 200, 30);
        btnCriarParticipante.setBounds(100, 80, 200, 30);
        btnCriarInscricao.setBounds(100, 130, 200, 30);
        btnCriarPalestrante.setBounds(100, 180, 200, 30);

        add(btnCriarEvento);
        add(btnCriarParticipante);
        add(btnCriarInscricao);
        add(btnCriarPalestrante);

        btnCriarEvento.addActionListener(e -> new TelaCriarEvento().setVisible(true));
        btnCriarParticipante.addActionListener(e -> new TelaInserirParticipante().setVisible(true));
        btnCriarInscricao.addActionListener(e -> new TelaCriarInscricao().setVisible(true));
        btnCriarPalestrante.addActionListener(e -> new TelaInserirPalestrante().setVisible(true));
    }
}

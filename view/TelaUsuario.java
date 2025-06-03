package view;

import javax.swing.*;
import view.Excluir.TelaExcluirInscricao;
import view.Menu.MenuCriarUsuario;
import view.Atualizar.TelaAtualizarParticipantes;
import view.Button.EstiloBotao;
import view.Exibir.TelaExibirEventos;
public class TelaUsuario extends JFrame {
    public TelaUsuario() {
        setTitle("Tela Usuario");
        setSize(400, 300);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        JButton btnCriar = new JButton("Cadastro e Inscrição");
        EstiloBotao.aplicarEstilo(btnCriar);
        JButton btnExcluirInscricao = new JButton("Cancelar Inscrição");
        JButton btnAtualizarPartticipante = new JButton("Atualizar Dados");
        JButton btnExibirEventos = new JButton("Eventos Disponiveis");

        btnCriar.setBounds(100, 30, 200, 30);
        btnExcluirInscricao.setBounds(100, 80, 200, 30);
        btnAtualizarPartticipante.setBounds(100, 130, 200, 30);
        btnExibirEventos.setBounds(100, 180, 200, 30);
        
        add(btnCriar);
        add(btnExcluirInscricao);
        add(btnAtualizarPartticipante);
        add(btnExibirEventos);

        btnCriar.addActionListener(e -> new MenuCriarUsuario().setVisible(true));
        btnExcluirInscricao.addActionListener(e -> new TelaExcluirInscricao().setVisible(true));
        btnAtualizarPartticipante.addActionListener(e -> new TelaAtualizarParticipantes().setVisible(true));
        btnExibirEventos.addActionListener(e -> new TelaExibirEventos().setVisible(true));
    }
}

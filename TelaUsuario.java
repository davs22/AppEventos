import javax.swing.*;
import view.Criar.TelaCriarInscricao;
import view.Criar.TelaInserirParticipante;

public class TelaUsuario extends JFrame {
    public TelaUsuario() {
        setTitle("Tela Usuario");
        setSize(400, 300);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        JButton btnCriarParticipante = new JButton("Criar Participante");
        JButton btnCriarInscricao = new JButton("Criar Inscrição");

        btnCriarParticipante.setBounds(100, 30, 200, 30);
        btnCriarInscricao.setBounds(100, 80, 200, 30);

        add(btnCriarParticipante);
        add(btnCriarInscricao);

        btnCriarParticipante.addActionListener(e -> new TelaCriarInscricao().setVisible(true));
        btnCriarInscricao.addActionListener(e -> new TelaInserirParticipante().setVisible(true));
    }
}

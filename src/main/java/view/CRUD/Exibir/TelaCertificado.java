package view.CRUD.Exibir;

import javax.swing.*;
import java.awt.*;

public class TelaCertificado extends JFrame {

    public TelaCertificado(String conteudoCertificado) {
        setTitle("Certificado");
        setSize(500, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JTextArea textArea = new JTextArea(conteudoCertificado);
        textArea.setEditable(false);
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        textArea.setMargin(new Insets(10, 10, 10, 10));

        JScrollPane scrollPane = new JScrollPane(textArea);
        add(scrollPane);
    }
}

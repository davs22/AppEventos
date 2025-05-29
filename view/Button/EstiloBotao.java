package view.Button;

import javax.swing.JButton;
import java.awt.Color;
import java.awt.Font;
import java.awt.Cursor;
import java.awt.Dimension;

public class EstiloBotao {
    
    public static void aplicarEstilo(JButton botao) {
        botao.setBackground(new Color(30, 144, 255));
        botao.setForeground(Color.WHITE);
        botao.setFont(new Font("Arial", Font.BOLD, 16));
        botao.setFocusPainted(false);
        botao.setBorder(new RoundedBorder(15));
        botao.setCursor(new Cursor(Cursor.HAND_CURSOR));
        botao.setPreferredSize(new Dimension(160, 40));
    }

    static class RoundedBorder implements javax.swing.border.Border {
        private int radius;

        public RoundedBorder(int radius) {
            this.radius = radius;
        }

        public java.awt.Insets getBorderInsets(java.awt.Component c) {
            return new java.awt.Insets(radius+1, radius+1, radius+2, radius);
        }

        public boolean isBorderOpaque() {
            return true;
        }

        public void paintBorder(java.awt.Component c, java.awt.Graphics g, int x, int y, int width, int height) {
            g.setColor(Color.WHITE);
            g.drawRoundRect(x, y, width - 1, height - 1, radius, radius);
        }
    }
}

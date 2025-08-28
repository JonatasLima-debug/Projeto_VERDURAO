package Interface;

import javax.swing.*;
import java.awt.*;

public class FundoPrincipal extends JPanel {
    private Image imagem;

    public FundoPrincipal() {
        imagem = new ImageIcon(getClass().getResource("/imagens_projetos/Principal.png")).getImage();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(imagem, 0, 0, getWidth(), getHeight(), this);
    }
}

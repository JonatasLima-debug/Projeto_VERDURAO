package Interface;

import javax.swing.*;
import java.awt.*;

public class FundoLogin extends JPanel {
    private Image imagem;

    public FundoLogin() {
        imagem = new ImageIcon(getClass().getResource("/imagens_projetos/TelaLogin.png")).getImage();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(imagem, 0, 0, getWidth(), getHeight(), this);
    }
}


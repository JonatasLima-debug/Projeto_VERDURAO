package Interface;

import javax.swing.*;
import java.awt.*;

public class FundoCadastro extends JPanel {
    private Image imagem;

    public FundoCadastro() {
        imagem = new ImageIcon(getClass().getResource("/imagens_projetos/Produtos.png")).getImage();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(imagem, 0, 0, getWidth(), getHeight(), this);
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Interface;

import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class FundoCaixa extends JPanel {
    private Image imagem;

    public FundoCaixa() {
        // carrega a imagem do resources
        imagem = new ImageIcon(getClass().getResource("/imagens_projetos/Caixa.png")).getImage();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // desenha a imagem ocupando todo o tamanho do painel
        g.drawImage(imagem, 0, 0, getWidth(), getHeight(), this);
    }
}

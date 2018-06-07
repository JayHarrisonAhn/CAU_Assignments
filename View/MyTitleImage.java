package View;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class MyTitleImage extends JPanel{
    private ImageIcon icon = new ImageIcon("src/Image/main.jpg");
    private int width;
    private int height;

    public void paintComponent(Graphics g) {
            width = getWidth();
            height = getHeight();
            System.out.println("InpaintComponent" + width + "and" + height);
            super.paintComponent(g);
            g.drawImage(icon.getImage(), 0, 0,getWidth(),getHeight(), this);

        }
}


import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Image extends JPanel {
    public BufferedImage img = null;

    public Image(String imgName){
        try {
            img = ImageIO.read(new File("/home/renato/IdeaProjects/Dama/images/corona.png"));
        } catch (IOException ex) {
            System.out.println( ex.getMessage() );
        }
    }

    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        g.drawImage(img,0,0,this);
    }
}

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Image extends JPanel {
    public BufferedImage img = null;
    String filePath = new File("").getAbsolutePath();
///home/renato/IdeaProjects/Dama/images/pedina.png
    public Image(String imgName){
        filePath = filePath.concat(imgName);
        try {
            img = ImageIO.read(new File(filePath));
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

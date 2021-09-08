package Game.Image;
import javax.swing.*;

import java.awt.*;
import java.awt.image.BufferedImage;
/**
 * <h1>ComponentImage class</h1>
 * Represents an image as a JComponent 
 */
public class ComponentImage extends JComponent implements ImageFunctions {
    private BufferedImage img;

    public ComponentImage(){};
    public ComponentImage(String pathImg, Dimension dim){ setImg(pathImg, dim);}

    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        g.drawImage(img, 0, 0,null);
     }

     public BufferedImage getImg() {
        return this.img;
    }

    public void setImg(String pathImg, Dimension dim) {
        this.img = ImageFunctions.scale(pathImg, dim.width, dim.height);
        setPreferredSize(dim);
    }
}

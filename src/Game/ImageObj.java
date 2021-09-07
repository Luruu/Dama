package Game;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class ImageObj extends JComponent implements ImageFunctions{
    private BufferedImage img;

    public ImageObj(){};
    public ImageObj(String pathImg, Dimension dim){ setImg(pathImg, dim);}

    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        g.drawImage(img, 0, 0,null);
     }

     public BufferedImage getImg() {
        return this.img;
    }

    public void setImg(String pathImg, Dimension dim) {
        this.img = scale(readFile(pathImg), dim.width, dim.height);
        setPreferredSize(dim);
    }
}

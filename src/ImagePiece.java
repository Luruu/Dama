import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class ImagePiece extends JPanel {
    private BufferedImage img;
    
    public ImagePiece(String imgName){
       img = ImageFunctions.readFile(imgName);
    }

    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        g.drawImage(img, 0, 0, this);
    }


    public BufferedImage getImg() {
        return img;
    }

    public void setImg(BufferedImage img) {
        this.img = img;
    }

   
}

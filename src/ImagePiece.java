import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImagePiece extends JPanel {
    public BufferedImage img = null;
    String filePath = new File("").getAbsolutePath();

    public ImagePiece(String imgName){
        filePath = filePath.concat(imgName);
        try {
            img = ImageIO.read(new File(filePath));
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static BufferedImage scale(BufferedImage imgIn, int wid, int hei) {
        if (imgIn == null)
            return null;
        BufferedImage imgOut = new BufferedImage(wid, hei, imgIn.getType());
        Graphics2D g2D = imgOut.createGraphics();
        
        //Increase image quality
        g2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2D.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2D.drawImage(imgIn, 0, 0, wid, hei, null);
        g2D.dispose();
        return imgOut;
    }

    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        g.drawImage(img, 0, 0, this);
    }

}

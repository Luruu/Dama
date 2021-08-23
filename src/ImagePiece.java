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
        BufferedImage imgOut = null;
        if (imgIn != null) {
            imgOut = new BufferedImage(wid, hei, imgIn.getType());
            Graphics2D g = imgOut.createGraphics();
            g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g.drawImage(imgIn, 0, 0, wid, hei, null);
            g.dispose();
        }
        return imgOut;
    }

    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        g.drawImage(img, 0, 0, this);
    }

}

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public interface ImageFunctions {

    public static BufferedImage readFile(String pathRelative){
        String absPath = getAbsolutePath(pathRelative);
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File(absPath));
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return img;
    }
    
    //this method is necessary to access file on the file system
    public static String getAbsolutePath(String pathRelative){
        String filePath = new File("").getAbsolutePath();
        return filePath.concat(pathRelative);
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
}
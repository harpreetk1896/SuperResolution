/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package superresolution;

import java.awt.image.BufferedImage;
import java.awt.image.PixelGrabber;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
/**
 *
 * @author hp
 */
public class ReadWriteJPG {

    private BufferedImage img;
    
    public  static int[] ReadPixels(BufferedImage image,int w, int h)
    {
        int [] data = new int[w*h];
        PixelGrabber grabber = new PixelGrabber(image, 0, 0, w, h,data,0,w);
        try {
            grabber.grabPixels();
        } catch (InterruptedException ex) {
            Logger.getLogger(ReadWriteJPG.class.getName()).log(Level.SEVERE, null, ex);
        }
        return data;
    }
    
    public static BufferedImage ConvertToImg (int[] arr, int w, int h,String name)
    {
        BufferedImage img = new BufferedImage(w,h,BufferedImage.TYPE_INT_RGB);
        img.setRGB(0, 0, w, h, arr, 0, w);
        File outputfile = new File(name);
        try {
            ImageIO.write(img, "jpg", outputfile);
        } catch (IOException ex) {
            Logger.getLogger(ReadWriteJPG.class.getName()).log(Level.SEVERE, null, ex);
        }
        return img;
    }
    
public static int[] getPixelData(int argb) 
{
    //int argb = img.getRGB(x, y);

    int rgb[] = new int[] {
        (argb >> 16) & 0xff, //red
        (argb >>  8) & 0xff, //green
        (argb      ) & 0xff  //blue
    };
    return rgb;
}

}


/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package superresolution;

import java.awt.image.BufferedImage;

/**
 *
 * @author hp
 */
public class MeanInterpolation {
    
    private BufferedImage img;
    
    public MeanInterpolation(BufferedImage img)
    {
        this.img=img;
        int h = img.getHeight();
        int w=img.getWidth();
        int[][][] pixelData = new int[2*w-1][2*h-1][];
        int[] rgb;

        //int counter = 0;
        for(int i = 0; i < w; i++){
            for(int j = 0; j < h; j++){
                rgb = getPixelData(img, i, j);

                for(int k = 0; k < rgb.length; k++){
                    pixelData[i][j][k] = rgb[k];
                }
                interpolate(pixelData,img.getWidth(),img.getHeight());
                //counter++;
            }
        }
 }
    
    public BufferedImage interpolate(int a[][][],int w,int h)
    {
        int[] pixels = new int [(2*w-1)*(2*h-1)];
        return img;
    }

private static int[] getPixelData(BufferedImage img, int x, int y) 
{
    int argb = img.getRGB(x, y);

    int rgb[] = new int[] {
        (argb >> 16) & 0xff, //red
        (argb >>  8) & 0xff, //green
        (argb      ) & 0xff  //blue
    };

    //System.out.println("rgb: " + rgb[0] + " " + rgb[1] + " " + rgb[2]);
    return rgb;
}
    
}

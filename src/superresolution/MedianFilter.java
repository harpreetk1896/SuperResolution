
package superresolution;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.*;

 
class MedianFilter{
    static BufferedImage img;
    static int outpixel[];
    public static int[] median(BufferedImage img){
        //File f=new File("d:\\123.jpg");  
        int counter=0;
        Color[] pixel=new Color[9];
        outpixel=new int[DemoPanel.height*DemoPanel.width];
        int[] R=new int[9];
        int[] B=new int[9];
        int[] G=new int[9];
        
        File output=new File("noise-.jpg");
        //BufferedImage  img=ImageIO.read(f);
        for(int j=DemoPanel.Y;j<DemoPanel.height+DemoPanel.Y;j++)
            for(int i=DemoPanel.X;i<DemoPanel.width+DemoPanel.X;i++)
           {
                
               pixel[0]=new Color(img.getRGB(i-1,j-1));
               pixel[1]=new Color(img.getRGB(i-1,j));
               pixel[2]=new Color(img.getRGB(i-1,j+1));
               pixel[3]=new Color(img.getRGB(i,j+1));
               pixel[4]=new Color(img.getRGB(i+1,j+1));
               pixel[5]=new Color(img.getRGB(i+1,j));
               pixel[6]=new Color(img.getRGB(i+1,j-1));
               pixel[7]=new Color(img.getRGB(i,j-1));
               pixel[8]=new Color(img.getRGB(i,j));
               for(int k=0;k<9;k++){
                   R[k]=pixel[k].getRed();
                   B[k]=pixel[k].getBlue();
                   G[k]=pixel[k].getGreen();
               }
               Arrays.sort(R);
               Arrays.sort(G);
               Arrays.sort(B);
               
               outpixel[counter]=255<<24|R[4]<<16|G[4]<<8|B[4];
               counter++;
               
               img.setRGB(i,j,new Color(R[4],G[4],B[4]).getRGB());
               
            }
        try {
            ImageIO.write(img,"jpg",output);
        } catch (IOException ex) {
            Logger.getLogger(MedianFilter.class.getName()).log(Level.SEVERE, null, ex);
        }
        ReadWriteJPG.ConvertToImg(outpixel, DemoPanel.width,DemoPanel.height ,"New.jpg");
        
        return outpixel;
    }
}
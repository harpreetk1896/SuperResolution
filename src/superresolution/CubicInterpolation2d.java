/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package superresolution;

/**
 *
 * @author Daljit Bhalla
 */
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class CubicInterpolation2d {

    BSplines bS;   
    int width,height;
    
    int [][] red,green ,blue;
    public CubicInterpolation2d(){
        bS = new BSplines();
    }
    
    public int heightGet(){
        return height;
    }
            
    
    public int widthGet()
    {return width;}
    
     public int [] bicube (int [] pixel,int w, int h){
        BufferedImage image = new BufferedImage(w,h,BufferedImage.TYPE_INT_RGB);
        image.setRGB(0, 0, w, h, pixel, 0, w);
        
//            ImageView imageView = new ImageView();
//            imageView.drawImage(image);
            CubicInterpolation2d cubicInterpolation2d = 
                    new CubicInterpolation2d();
        double [][] red, green,blue;
            red= cubicInterpolation2d.imageToDoubleArray(image,0);         
            green= cubicInterpolation2d.imageToDoubleArray(image,1); 
            blue= cubicInterpolation2d.imageToDoubleArray(image,2); 
            double [][] red_interp = 
                    cubicInterpolation2d.interpolate(red,2);
            double [][] green_interp = 
                    cubicInterpolation2d.interpolate(green,2);
            double [][] blue_interp = 
                    cubicInterpolation2d.interpolate(blue,2);
            BufferedImage imageInterp = 
                    cubicInterpolation2d.doubleArrayToImage(red_interp,green_interp,blue_interp);
            width = imageInterp.getWidth(); height = imageInterp.getHeight();
            int a[] = ReadWriteJPG.ReadPixels(imageInterp, imageInterp.getWidth(), imageInterp.getHeight());
            
//            ReadWriteJPG.ConvertToImg(a,imageInterp.getWidth(), imageInterp.getHeight(), "Bicubic.jpg");
//            
//            ImageView imageView2 = new ImageView();
//                            imageView2.drawImage(imageInterp);  
        return a;
        
    }
    
    private double[][] mirrorW2d( double [][] s){

        double [][] s_mirror = new double[s.length+3][s[0].length+3];
        for(int i=0; i<s.length; i++){
            for(int j=0; j<s[0].length; j++){
                s_mirror[i+1][j+1] = s[i][j];
            }
        }
        
        /*########## mirror rows  1 and N-2 ################*/
        for(int j=0; j<s[0].length; j++){
            s_mirror[0][j+1] = s[1][j];
            s_mirror[s_mirror.length-2][j+1] = s[s.length-2][j];
        }
        
        /*########## mirror columns 1 and  N-2 ##############*/ 
        for(int i=0; i<s.length; i++){
            s_mirror[i+1][0] = s[i][1];
            s_mirror[i+1][s_mirror[0].length-2] = s[i][s[0].length-2];
        }   
        
        s_mirror[0][0] = s[1][1];
        s_mirror[0][s_mirror[0].length-2] = s[1][s[0].length-2];            
        s_mirror[s_mirror.length-2][0] = s[s.length-2][1];
        s_mirror[s_mirror.length-2 ][s_mirror[0].length-2] = 
                s[s.length-2][s[0].length-2];
                                        
        return s_mirror;
    }
    
    public double[][] cubicCoeff2d(double[][] s){
        DirectBsplFilter2d directFilter2d;
        directFilter2d = new DirectBsplFilter2d(s.length, s[0].length);
        double[][] coeffs = directFilter2d.filter(s);
        double[][] coeffs_mirror = mirrorW2d(coeffs);
        return coeffs_mirror;
    }
    

    public double cubicInterp2d(double[][] coeffs_mirror, 
                                double row, 
                                double col){    
        int k = (int)Math.floor(row);   
        int l = (int)Math.floor(col);       
        double interp_value =   
        coeffs_mirror[k+0][l+0]*bS.bspline(3,row-k+1)*bS.bspline(3,col-l+1)+ 
        coeffs_mirror[k+1][l+0]*bS.bspline(3,row-k+0)*bS.bspline(3,col-l+1)+
        coeffs_mirror[k+2][l+0]*bS.bspline(3,row-k-1)*bS.bspline(3,col-l+1)+
        coeffs_mirror[k+3][l+0]*bS.bspline(3,row-k-2)*bS.bspline(3,col-l+1)+
                                                                    
        coeffs_mirror[k+0][l+1]*bS.bspline(3,row-k+1)*bS.bspline(3,col-l+0)+ 
        coeffs_mirror[k+1][l+1]*bS.bspline(3,row-k+0)*bS.bspline(3,col-l+0)+
        coeffs_mirror[k+2][l+1]*bS.bspline(3,row-k-1)*bS.bspline(3,col-l+0)+
        coeffs_mirror[k+3][l+1]*bS.bspline(3,row-k-2)*bS.bspline(3,col-l+0)+
                                                                                                
        coeffs_mirror[k+0][l+2]*bS.bspline(3,row-k+1)*bS.bspline(3,col-l-1)+ 
        coeffs_mirror[k+1][l+2]*bS.bspline(3,row-k+0)*bS.bspline(3,col-l-1)+
        coeffs_mirror[k+2][l+2]*bS.bspline(3,row-k-1)*bS.bspline(3,col-l-1)+
        coeffs_mirror[k+3][l+2]*bS.bspline(3,row-k-2)*bS.bspline(3,col-l-1)+
                                                
        coeffs_mirror[k+0][l+3]*bS.bspline(3,row-k+1)*bS.bspline(3,col-l-2)+ 
        coeffs_mirror[k+1][l+3]*bS.bspline(3,row-k+0)*bS.bspline(3,col-l-2)+
        coeffs_mirror[k+2][l+3]*bS.bspline(3,row-k-1)*bS.bspline(3,col-l-2)+
        coeffs_mirror[k+3][l+3]*bS.bspline(3,row-k-2)*bS.bspline(3,col-l-2);                                        
        return interp_value;
    }
    
    
    public double[][] interpolate(double[][] s, int rate){
        double [][] coeffs_mirror = cubicCoeff2d(s);        
        int M = rate*s.length - (rate-1);
        int N = rate*s[0].length - (rate-1);
        double [][] s_interp = new double[M][N];        
        for(int k=0; k<s_interp.length; k++){
            for(int l=0; l<s_interp[0].length; l++){
                s_interp[k][l] = 
                    cubicInterp2d(coeffs_mirror, k*(1.0/rate), l*(1.0/rate));
            }
        }                                                                                                       
        return s_interp;                    
    }
    
    
    private double[][] imageToDoubleArray(BufferedImage image ,int band){   
 //       int [][] red,green,blue;
      //  red =new int [image.getHeight() ][image.getWidth()];
     //   blue =new int [image.getHeight() ][image.getWidth()];
     //   green =new int [image.getHeight() ][image.getWidth()];
        
        double[][] array = new double[image.getHeight()][image.getWidth()];     
        Raster raster = image.getData();
        for( int y = 0; y < image.getHeight(); y++ ){
            for( int x = 0; x < image.getWidth(); x++ ){
                array[y][x] = raster.getSampleDouble(x, y, band);                      
          // red[y][x]= image.getRGB()
            }            
        }           
        return array;       
    }
    
    private BufferedImage doubleArrayToImage(double[][] red,double[][] green  , double[][] blue){     
       BufferedImage image = 
            new BufferedImage(red[0].length,red.length,
                              BufferedImage.TYPE_INT_RGB); 
        System.out.println(" size of double "+ Double.SIZE);
        for( int y = 0; y < red.length; y++ ){
            for( int x = 0; x < red[0].length; x++ ){     
                 int value = 
       (int)red[y][x] << 16 | 
                    (int)green[y][x] << 8 | 
                    (int)blue[y][x];
                 image.setRGB(x, y, value);                                                   
            }
        }       
        return image;
    }
    
   
}


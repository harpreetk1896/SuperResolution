/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package superresolution;

/**
 *
 * @author hp
 */
public class ImageFusion {
    
    int pixel[],rgb1[],rgb2[],rgb3[]=new int[3];
    double weight=0.1;
    
    public int[] ImageFuse( int img1[], int img2[],int width,int height,double weight)
    {
        
         int k=0;
         if(weight<10)
            this.weight=weight*-0.1;
         else
             this.weight=(weight-10)*0.1;
         
         pixel= new int[width*height];
        for(int i=0;i<height;i++)
            for(int j=0;j<width;j++)
            {
                if(img1[k]==(int)0xffffffff)
                {
                   //img1[k]=0x00000000;
                    pixel[k]=weightedAvgMethod(img1[k],img2[k]);
                
                }//System.out.println("img::"+img2[k]);}
                else
                    pixel[k]=img2[k];
                k++;
            }
        
        //ReadWriteJPG.ConvertToImg(pixel,width,height,"Fuse.jpg");
        return pixel;
    }
    
    private int weightedAvgMethod(int a,int b)
    {
            rgb1=ReadWriteJPG.getPixelData(a);
            rgb2=ReadWriteJPG.getPixelData(b);
            rgb3[0]=(int) (weight*rgb1[0]+(1-weight)*rgb2[0]); 
            rgb3[1]=(int) (weight*rgb1[1]+(1-weight)*rgb2[1]); 
            rgb3[2]=(int) (weight*rgb1[2]+(1-weight)*rgb2[2]); 
            int c=255<<24|rgb3[0]<<16|rgb3[1]<<8|rgb3[2];
            return c;
    }
    
    private int JugaaduMethod(int b)
    {
            int add=40;
           
            rgb2=ReadWriteJPG.getPixelData(b);
            for(int i =0;i<3;i++)
            {
                rgb3[i] = (int) rgb2[i]-add;
                if(rgb3[i]<0) rgb3[i]=0;
            }
            int c=255<<24|rgb3[0]<<16|rgb3[1]<<8|rgb3[2];
            //System.out.println("cc::"+c);
            return c;
    }

   
    
}

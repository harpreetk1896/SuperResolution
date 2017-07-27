package superresolution;
import java.awt.image.BufferedImage;
import java.awt.image.PixelGrabber;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

public class EdgeSegmentation {
    
		int[] input;
		int[] output;
		float[] template={-1,0,1,-2,0,2,-1,0,1};;
		int templateSize=3;
		int width;
		int height;
                int threshold =20;
		double[] direction;
                public int PixArray[] = null, res[];
                
               
                public EdgeSegmentation(){}
                public int[] EdgeSegment(int PixArray[], int w, int h)
                {
                    width = w;
                    height = h;
                    this.PixArray = PixArray;
                    processImage();
                    return res;
                }

		public void init(int[] original, int widthIn, int heightIn) {
			width=widthIn;
			height=heightIn;
			input = new int[width*height];
			output = new int[width*height];
			direction = new double[width*height];
			input=original;
		}
		public int[] process() {
			float[] GY = new float[width*height];
			float[] GX = new float[width*height];
			int[] total = new int[width*height];
			
			int sum=0;
			int max=0;

			for(int x=(templateSize-1)/2; x<width-(templateSize+1)/2;x++) {
				
				for(int y=(templateSize-1)/2; y<height-(templateSize+1)/2;y++) {
					sum=0;

					for(int x1=0;x1<templateSize;x1++) {
						for(int y1=0;y1<templateSize;y1++) {
							int x2 = (x-(templateSize-1)/2+x1);
							int y2 = (y-(templateSize-1)/2+y1);
							float value = (input[y2*width+x2] & 0xff) * (template[y1*templateSize+x1]);
							sum += value;
						}
					}
                                        //Here sobel is applied in y direction
					GY[y*width+x] = sum;
                                        sum=0;
					for(int x1=0;x1<templateSize;x1++) {
						for(int y1=0;y1<templateSize;y1++) {
							int x2 = (x-(templateSize-1)/2+x1);
							int y2 = (y-(templateSize-1)/2+y1);
							float value = (input[y2*width+x2] & 0xff) * (template[x1*templateSize+y1]);
							sum += value;
						}
					}
                                        //Here sobel os applied in X direction
					GX[y*width+x] = sum;

				}
			}
			for(int x=0; x<width;x++) {
				for(int y=0; y<height;y++) {
					total[y*width+x]=(int)Math.sqrt(GX[y*width+x]*GX[y*width+x]+GY[y*width+x]*GY[y*width+x]);
					direction[y*width+x] = Math.atan2(GX[y*width+x],GY[y*width+x]);
					if(max<total[y*width+x])
						max=total[y*width+x];
				}
			}
			float ratio=(float)max/255;
			for(int x=0; x<width;x++) {
				for(int y=0; y<height;y++) {
					sum=(int)(total[y*width+x]/ratio);
					output[y*width+x] = 0xff000000 | ((int)sum << 16 | (int)sum << 8 | (int)sum);
				}
			}
			
			return output;
		}

		public double[] getDirection() {
			return direction;
		}
                
        private void processImage() {
        
        init(PixArray, width, height);
        res = process();
        res = threshold(res, threshold);
       
        
    }

    public int[] threshold(int[] original, int value) {
        for (int x = 0; x < original.length; x++) {
            if ((original[x] & 0xff) >= value && (original[x] >> 8 & 0xff) >= value && (original[x] >> 16 & 0xff) >= value) {
                original[x] = 0xffffffff;
            } else {
                original[x] = 0xff000000;
            }
        }
        return original;
    }
		


}

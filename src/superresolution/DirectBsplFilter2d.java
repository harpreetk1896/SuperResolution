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
public class DirectBsplFilter2d {

    private DirectBsplFilter1d directFilter1dX; 
    private DirectBsplFilter1d directFilter1dY;         
    
    public DirectBsplFilter2d(int M, int N){
        directFilter1dX = new DirectBsplFilter1d(M);    
        directFilter1dY = new DirectBsplFilter1d(N);    
    }
    
    public double[][] filter(double [][] img){  
        double [] row = new double[img[0].length];
        double [] col = new double[img.length];
        double [] filt_row = new double[img[0].length];
        double [] filt_col = new double[img.length];
        double [][] coeffs = new double[img.length][img[0].length];
        
        /*#################### filtrations along y ##################*/
        for(int i=0; i<img.length; i++){
            for(int j=0; j<img[0].length; j++){
                row[j] = img[i][j];
            }
            filt_row = directFilter1dY.filter(row);
            for(int j=0; j<img[0].length; j++){
                coeffs[i][j] = filt_row[j];
            }
            directFilter1dY.reset();
        }
        /*#################### filtrations along y ##################*/
                    
        /*#################### filtrations along x ##################*/
        for(int j=0; j<img[0].length; j++){
            for(int i=0; i<img.length; i++){
                col[i] = coeffs[i][j];
            }
            filt_col = directFilter1dX.filter(col);
            for(int i=0; i<img.length; i++){
                coeffs[i][j] = filt_col[i];
            }
            directFilter1dX.reset();
        }
        /*#################### filtrations along x ##################*/             
        return coeffs;                                                  
    }

}
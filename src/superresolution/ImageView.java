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
import java.awt.*;
import javax.swing.*;
import java.awt.image.*;
import javax.swing.JFrame;

@SuppressWarnings("serial")
public class ImageView extends JFrame{

    private SignalPanel signalPanel;
    
    public ImageView(){                     
        setTitle("Image");      
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(200, 200);       
        signalPanel = new SignalPanel();                 
        getContentPane().add(signalPanel);          
        setVisible(true);        
    }
    
    public void drawImage(BufferedImage image){
        setSize(image.getWidth()+25, image.getHeight()+50);         
        signalPanel.setImage( image);
    }   
}

@SuppressWarnings("serial")
class SignalPanel extends JPanel {
    
   private BufferedImage image;
   
   @Override
   protected void paintComponent(Graphics graphics) {
       super.paintComponent(graphics);   
       if (image != null) {
           graphics.drawImage(image, 5, 5, this);
       }
   }
   
   public void setImage(BufferedImage image){
       this.image = image;
   }  
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package superresolution;

/**
 *
 * @author hp
 */

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Rectangle;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.awt.image.MemoryImageSource;
import java.awt.image.PixelGrabber;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JSlider;
import javax.swing.SwingUtilities;

public class ImageFusionFrame extends javax.swing.JFrame {

    static int PixelArray1[],PixelArray2[];
    ImageFusion eseg;
    Image outputImage;
    BufferedImage img,bimage;
    MediaTracker tracker = null;
    int width = 0, height = 0;
    //slider constraints
    int weight = 10;
    boolean thresholdActive = false;
    int imageNumber = 0;
    public int orig[] = null;
    int x=0,y=0,windowWidth=300,windowHeight=170;
    /**
     * Creates new form edgeSegFrame
     */
    public ImageFusionFrame(int pixelArray1[],int pixelArray2[],BufferedImage img) {
        initComponents();
        jTextFieldWeight.setText("" + 10);
        width = DemoPanel.width;
        height = DemoPanel.height;
        
        this.img=img;
        this.PixelArray1=pixelArray1;
        this.PixelArray2=pixelArray2;
        if( x+300 > width || y+170 > height)
        {
            windowWidth=width;
            windowHeight=height;
        }
        jLabelSourceImage.setIcon(new ImageIcon(img.getSubimage(x,y, windowWidth,windowHeight )));
        weightslider.setEnabled(true);
        eseg = new ImageFusion();
        processImage();

    }

    private void processImage() {
   
        int[] res = eseg.ImageFuse(PixelArray2, PixelArray1,width,height,weight);
        
        //conversion of image to buffered image
        bimage = ReadWriteJPG.ConvertToImg(res, width, height, "ImagefusionTemp.jpg");
        jLabelOutputFusion.setIcon(new ImageIcon(bimage.getSubimage(x,y, windowWidth,windowHeight )));

    }

   
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        weightslider = new javax.swing.JSlider();
        jButtonSave = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jTextFieldWeight = new javax.swing.JTextField();
        jLabelSourceImage = new javax.swing.JLabel();
        jLabelOutputFusion = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Image Fusion");
        setMinimumSize(new java.awt.Dimension(850, 600));
        setPreferredSize(new java.awt.Dimension(850, 600));
        getContentPane().setLayout(null);
       
        weightslider.setMajorTickSpacing(1);
        weightslider.setMaximum(20);
        weightslider.setMinorTickSpacing(1);
        weightslider.setPaintLabels(true);
        weightslider.setPaintTicks(true);
        weightslider.setSnapToTicks(true);
        weightslider.setValue(10);
        weightslider.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                weightsliderStateChanged(evt);
            }
        });
        getContentPane().add(weightslider);
        weightslider.setBounds(100, 400, 570, 50);

        jButtonSave.setText("Save");
        jButtonSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSaveActionPerformed(evt);
            }
        });
        getContentPane().add(jButtonSave);
        jButtonSave.setFont(new java.awt.Font("Tahoma",0, 15));
        jButtonSave.setBounds(710, 400, 70, 26);


        jLabel2.setText("Weight");
        getContentPane().add(jLabel2);
        jLabel2.setBounds(341, 360, 100, 14);

        jTextFieldWeight.setEditable(true);
        jTextFieldWeight.addKeyListener(new KeyAdapter(){
            public void keyPressed(java.awt.event.KeyEvent evt) {
                if(evt.getKeyCode()==KeyEvent.VK_ENTER)
                {
                     String temp= jTextFieldWeight.getText();
                     weight=Integer.parseInt(temp);
                     processImage();
                }
            }
        });
        getContentPane().add(jTextFieldWeight);
        jTextFieldWeight.setBounds(400, 355, 50, 30);

        jLabelSourceImage.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        getContentPane().add(jLabelSourceImage);
        jLabelSourceImage.setBounds(110, 100, 290, 170);
        jLabelSourceImage.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                x=evt.getX();
                y=evt.getY();
                if( x+300 < width || y+170 < height)
                {
                    jLabelSourceImage.setIcon(new ImageIcon(img.getSubimage(x,y,windowWidth,windowHeight)));
                    jLabelOutputFusion.setIcon(new ImageIcon(bimage.getSubimage(x,y,windowWidth,windowHeight )));
                }
            }
        });


        jLabelOutputFusion.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        getContentPane().add(jLabelOutputFusion);
        jLabelOutputFusion.setBounds(470, 100, 300, 170);

        pack();
    }// </editor-fold>                        

    private void weightsliderStateChanged(javax.swing.event.ChangeEvent evt) {                                        

        // TODO add your handling code here:
        JSlider source = (JSlider) evt.getSource();
        if (!source.getValueIsAdjusting()) {
            System.out.println("weight=" + source.getValue());
            weight = source.getValue();
            jTextFieldWeight.setText("" + source.getValue());
            processImage();

        }
    }                                       

    private void jButtonSaveActionPerformed(java.awt.event.ActionEvent evt) {      
        PixelArray1=ReadWriteJPG.ReadPixels(bimage, width, height);
        ReadWriteJPG.ConvertToImg(PixelArray1, width, height, "ImageFusion.jpg");
        dispose();
    }                                         

    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify                     
    private javax.swing.JTextField jTextFieldWeight;
    private javax.swing.JButton jButtonSave;
    private javax.swing.JLabel jLabelOutputFusion;
    private javax.swing.JSlider weightslider;
    private javax.swing.JLabel jLabelSourceImage;
    private javax.swing.JLabel jLabel2;
     
}


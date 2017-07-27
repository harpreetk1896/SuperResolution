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
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
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

public class EdgeSegmentFrame extends javax.swing.JFrame {

    static int PixelArray[],EdgeArray[];
    EdgeSegmentation eseg;
    Image outputImage;
    BufferedImage img,bimage;
    MediaTracker tracker = null;
    PixelGrabber grabber = null;
    int width = 0, height = 0;
    int x=0,y=0,windowWidth=300,windowHeight=170;
    //slider constraints
    int threshold = 30;
    int imageNumber = 0;
    public int orig[] = null;

    /**
     * Creates new form edgeSegFrame
     */
    public EdgeSegmentFrame(int pixelArray[],BufferedImage img) {
        initComponents();
        jTextFieldThreshold.setText("" + 30);
        width = DemoPanel.width;
        this.img=img;
        height = DemoPanel.height;
        System.out.println("widthll::"+DemoPanel.width+"heightll::"+DemoPanel.height);
        this.PixelArray=pixelArray;
        if( x+300 > width || y+170 > height)
        {
            windowWidth=width;
            windowHeight=height;
        }
        jLabelSourceImage.setIcon(new ImageIcon(img.getSubimage(x,y, windowWidth,windowHeight)));
        edgeslider.setEnabled(true);
        eseg = new EdgeSegmentation();
        processImage();

    }

    private void processImage() {
        
        eseg.init(PixelArray, width, height);
        int[] res = eseg.process();
        res = threshold(res, threshold);
        final Image output = createImage(new MemoryImageSource(width, height, res, 0, width));
        outputImage = output;
        //conversion of image to buffered image
        bimage = new BufferedImage(outputImage.getWidth(null), outputImage.getHeight(null), BufferedImage.TYPE_INT_RGB);
        bimage.setRGB(0, 0, width, height, res, 0, width);
        jLabelSegmentedlImage.setIcon(new ImageIcon(bimage.getSubimage(x,y, windowWidth,windowHeight )));

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

                 
    private void initComponents() {
        edgeslider = new javax.swing.JSlider();
        jButtonSave = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jTextFieldThreshold = new javax.swing.JTextField();
        jLabelSourceImage = new javax.swing.JLabel();
        jLabelSegmentedlImage = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Edge Segmentation");
        setMinimumSize(new java.awt.Dimension(850, 600));
        setPreferredSize(new java.awt.Dimension(850, 600));
        getContentPane().setLayout(null);

        edgeslider.setMajorTickSpacing(20);
        edgeslider.setMaximum(255);
        edgeslider.setMinorTickSpacing(5);
        edgeslider.setPaintLabels(true);
        edgeslider.setPaintTicks(true);
        edgeslider.setSnapToTicks(true);
        edgeslider.setValue(30);
        
        edgeslider.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                edgesliderStateChanged(evt);
            }
        });
        getContentPane().add(edgeslider);
        edgeslider.setBounds(100, 400, 570, 50);

        jButtonSave.setText("Save");
        jButtonSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSaveActionPerformed(evt);
            }
        });
        getContentPane().add(jButtonSave);
        jButtonSave.setFont(new java.awt.Font("Tahoma",0, 15));
        jButtonSave.setBounds(710, 400, 70, 26);

        jLabel2.setText("Threshold");
        getContentPane().add(jLabel2);
        jLabel2.setBounds(331, 360, 100, 14);

        jTextFieldThreshold.setEditable(true);
        jTextFieldThreshold.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                if(evt.getKeyCode()==KeyEvent.VK_ENTER)
                {
                     String temp= jTextFieldThreshold.getText();
                     threshold=Integer.parseInt(temp);
                     processImage();
                }
            }
        });
        getContentPane().add(jTextFieldThreshold);
        jTextFieldThreshold.setBounds(410, 355, 50, 30);

        jLabelSourceImage.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        getContentPane().add(jLabelSourceImage);
        jLabelSourceImage.setBounds(110, 100, 300, 170);
        jLabelSourceImage.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                x=evt.getX();
                y=evt.getY();
                if( x+300 < width || y+170 < height)
                {
                    jLabelSourceImage.setIcon(new ImageIcon(img.getSubimage(x,y,windowWidth,windowHeight)));
                    jLabelSegmentedlImage.setIcon(new ImageIcon(bimage.getSubimage(x,y,windowWidth,windowHeight )));
                }
                
            }
        });

        jLabelSegmentedlImage.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        getContentPane().add(jLabelSegmentedlImage);
        jLabelSegmentedlImage.setBounds(470, 100, 300, 170);

        pack();
    }// </editor-fold>                        

    private void edgesliderStateChanged(javax.swing.event.ChangeEvent evt) {                                        

        // TODO add your handling code here:
        JSlider source = (JSlider) evt.getSource();
        if (!source.getValueIsAdjusting()) {
            System.out.println("threshold=" + source.getValue());
            threshold = source.getValue();
            jTextFieldThreshold.setText("" + source.getValue());
            processImage();

        }
    }                                       

                      

    private void jButtonSaveActionPerformed(java.awt.event.ActionEvent evt) {      
        EdgeArray=ReadWriteJPG.ReadPixels(bimage, width, height);
        ReadWriteJPG.ConvertToImg(EdgeArray, width, height, "EdgeSegment.jpg");
        setVisible(false);
        (new ImageFusionFrame(PixelArray,EdgeArray,img)).setVisible(true);
    }                                         

    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify                     
    private javax.swing.JTextField jTextFieldThreshold;
    private javax.swing.JButton jButtonSave;
    private javax.swing.JLabel jLabelSegmentedlImage;
    private javax.swing.JSlider edgeslider;
    private javax.swing.JLabel jLabelSourceImage;
    private javax.swing.JLabel jLabel2;
        // End of variables declaration                   
}

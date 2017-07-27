/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package superresolution;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

/**
 *
 * @author hp
 */
class DemoPanel extends JPanel 
{
	static BufferedImage Imag;
        static String ImageName[] = new String[50];
	static int count = 0;
        static int bands,width,height,X,Y;
        private static final Color CURSOR_COLOR = new Color(100, 100, 100, 100);

        private Point p1;
        private Point p2;
        private Rectangle2D rectangle;
	
	public DemoPanel(){}

	public BufferedImage getpixdata()
	{
		return Imag;
	}
	

	public static void setImageName(String FNam)
	{
		ImageName[count++] = FNam;
	}

	public DemoPanel(BufferedImage Img)
	{
		System.gc();
		this.Imag = Img;
		setPreferredSize(new Dimension(Imag.getWidth(), Imag.getHeight()));
		addMouseListener(new MouseAdapter(){
            public void mousePressed(MouseEvent e) {
                p1 = e.getPoint();
                rectangle = new Rectangle2D.Double(p1.x, p1.y, p1.x - p1.x, p1.y - p1.y);
                
            }
        });
        addMouseMotionListener(new MouseMotionAdapter(){
            public void mouseDragged(MouseEvent e) {
                p2 = e.getPoint();
                if (isPointTwoInQuadOne(p1, p2)) {
                    rectangle.setRect(p2.x, p2.y, p1.x - p2.x, p1.y - p2.y);
                    DemoPanel.width=p1.x - p2.x;
                    DemoPanel.height=p1.y - p2.y;
                    System.out.println("dmwidth:"+DemoPanel.width+" dmheight:"+DemoPanel.height);
                } else {
                    rectangle.setRect(p1.x, p1.y, p2.x - p1.x, p2.y - p1.y);  
                }
                repaint();
            }
        });
	}

        public boolean isPointTwoInQuadOne(Point p1, Point p2) {
        return p1.x >= p2.x && p1.y >= p2.y;
    }


	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;
		g2.drawImage(Imag, 0, 0, this);
                if (rectangle != null) {
                g2.setColor(CURSOR_COLOR);
                g2.fill(rectangle);
                DemoPanel.width=(int)rectangle.getWidth();
                DemoPanel.height=(int)rectangle.getHeight();
                DemoPanel.X=(int)rectangle.getX();
                DemoPanel.Y=(int)rectangle.getY();
                
            }
		g2.dispose();
	}


}
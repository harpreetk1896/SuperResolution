/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package superresolution;

/**
 *
 * @author hp
 */
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.Rectangle2D;


import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;


public class SelectRectArea extends JPanel{

    private static final Color CURSOR_COLOR = new Color(100, 100, 100, 100);

    private Point p1;
    private Point p2;
    private Rectangle2D rectangle;
  

    public SelectRectArea() {
        
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

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        
        if (rectangle != null) {
            g2.setColor(CURSOR_COLOR);
            g2.fill(rectangle);
        }
    }

  
}
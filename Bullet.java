
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author kobed6328
 */
public class Bullet extends Rectangle{
    
    private double doublex, doubley;
    private int width = 10, height = 10;
    private Color c = Color.RED;
    
    private double xspd, yspd;
    private final double spd = 10;
    
    public Bullet(double x, double y, int mx, int my)
    {
        super((int)x, (int)y, 10, 10);
        
        this.doublex = x;
        this.doubley = y;
        
        double dx = mx-x;
        double dy = my - y;
        
        xspd = Math.cos(Math.atan2(dy, dx))*spd;
        yspd = Math.sin(Math.atan2(dy, dx))*spd;
        
    }
    
    public void move()
    {
        doublex += xspd;
        doubley += yspd;
        x = (int)doublex;
        y = (int)doubley;
    }
    
    public void draw(Graphics g)
    {
        g.setColor(c);
        g.fillOval((int)x, (int)y, width, height);
    }
    
}


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
public class Target extends Rectangle{
    
    private Color c = Color.WHITE;
    private double dx, dy;
    
    private final double speed;
    private double xspd;
    private double yspd;
    
    private Timer strafeTimer = new Timer(60);
    private boolean strafing;
    
    public Target(double x, double y, double speed)
    {
        super((int)x, (int)y, 25, 25);
        dx = x;
        dy = y;
        this.speed = speed;
        setXYSpeeds();
        strafeTimer.set(0);
    }
    
    public void move()
    {
        
        dx += xspd;
        dy += yspd;
        if (strafeTimer.isDone())
        {
            setXYSpeeds();
            if (strafing = generateStrafe())
            {
                strafeTimer.reset();
                xspd*=3;
            }
        }
        else
        {
            strafeTimer.tick();
        }
        x = (int)dx;
        y = (int)dy;
    }
    
    private void setXYSpeeds()
    {
        double diffX = Main.WIDTH/2 - x;
        double diffY = Main.HEIGHT/2 - y;
        xspd = Math.cos(Math.atan2(diffY, diffX))*speed;
        yspd = Math.sin(Math.atan2(diffY, diffX))*speed;
    }
    
    public void draw(Graphics g)
    {
        g.setColor(c);
        g.fillOval(x, y, width, height);
    }
    
    private boolean generateStrafe()
    {
        return (int)(Math.random()*700) == 1;
    }
    
    
}

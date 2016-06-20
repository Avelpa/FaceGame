import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.JComponent;
import javax.swing.JFrame;


// make sure you rename this class if you are doing a copy/paste
public class Main extends JComponent implements MouseMotionListener, MouseListener{

    // Height and Width of our game
    static final int WIDTH = 1000;
    static final int HEIGHT = 1000;
    
    Font scoreF = new Font("", 100, 100);
    
    int score = 0;
    
    Timer scoreTimer = new Timer(5);
    
    int[] targetSpeeds = {1, 2, 3};
    
    ArrayList<Bullet> bullets = new ArrayList();
    ArrayList<Target> targets = new ArrayList();
    
    // sets the framerate and delay for our game
    // you just need to select an approproate framerate
    long desiredFPS = 60;
    long desiredTime = (1000)/desiredFPS;
    
    Timer lineTimer = new Timer(5);
    int lineInterval = 0;
    int lineIntervalIncrease = 1;
    
    Timer circleWidthTimer = new Timer(3);
    int radius = 25;
    int circleWidth = WIDTH;
    int circleHeight = radius*2;
    int circleWidthInterval = 0;
    int circleWidthIntervalIncrease = -5;

    int xLeft = 100;
    int yLeft = 100;
    int rLeft = 10;
    int leftCenterX = 375;
    int leftCenterY = 385;
    
    int dx, dy;
    
    Color pColor = Color.YELLOW;
    int pWidth = 100;
    boolean dragged;
    
    boolean clicked;
    
    int mx, my;
    // drawing of the game happens in here
    // we use the Graphics object, g, to perform the drawing
    // NOTE: This is already double buffered!(helps with framerate/speed)
    @Override
    public void paintComponent(Graphics g)
    {
        // always clear the screen first!
        g.clearRect(0, 0, WIDTH, HEIGHT);
        
        
        if (mx < WIDTH/2)
        {
            if (my > HEIGHT/2)
                g.setColor(Color.BLUE);
            else
                g.setColor(Color.GREEN);
        }
        else if (my > HEIGHT/2)
        {
            g.setColor(Color.CYAN);
        }
        else
            g.setColor(Color.MAGENTA);
        
        if (dragged)
        {
            g.setColor(Color.ORANGE);
            pColor = Color.WHITE;
        }
        else
        {
            pColor = Color.YELLOW;
        }
        g.fillRect(0, 0, WIDTH, HEIGHT);
        
        g.setColor(Color.BLACK);
        // GAME DRAWING GOES HERE 
        for (int i = 0; i < WIDTH; i += lineInterval)
        {
            g.drawLine(0, i, i, HEIGHT);
            g.drawLine(i, 0, WIDTH, i);
            g.drawLine(WIDTH-i, 0, 0, i);
            g.drawLine(i, HEIGHT, WIDTH, HEIGHT-i);
        }
        g.drawOval((WIDTH-circleWidth)/2, (HEIGHT-circleHeight)/2, circleWidth, circleHeight);
       // g.drawOval((HEIGHT-circleHeight)/2, (WIDTH-circleWidth)/2, circleHeight, circleWidth);
       // g.drawOval(WIDTH/2-(WIDTH-circleWidth), HEIGHT/2-(HEIGHT-circleWidth), circleWidth, circleHeight);
        
        //SCORE
        g.setFont(scoreF);
        g.drawString("" + score, WIDTH/2-50, HEIGHT/2-100);
        
        
        g.setColor(new Color(87, 44, 6));
        g.fillOval(WIDTH/2-pWidth/2-5, HEIGHT/2-pWidth/2-5, pWidth+10, pWidth+10);
        g.setColor(pColor);
        g.fillOval(WIDTH/2-pWidth/2, HEIGHT/2-pWidth/2, pWidth, pWidth);
        
        // eyes
        g.setColor(Color.BLACK);
        g.fillOval((int)(Math.cos(Math.atan2(dy, dx))*pWidth/5) + WIDTH/2 - pWidth/5, (int)(Math.sin(Math.atan2(dy, dx))*pWidth/5) + HEIGHT/2 - pWidth/5, pWidth/10, pWidth/10);
        g.fillOval((int)(Math.cos(Math.atan2(dy, dx))*pWidth/5) + WIDTH/2 + pWidth/5, (int)(Math.sin(Math.atan2(dy, dx))*pWidth/5) + HEIGHT/2 - pWidth/5, pWidth/10, pWidth/10);
        // GAME DRAWING ENDS HERE
        //g.drawLine(400, 425,(int)(Math.cos(Math.atan2(dy, dx))*100) + 400, (int)(Math.sin(Math.atan2(dy, dx))*100)+ 425);
        g.drawLine(WIDTH/2, HEIGHT/2, mx, my); //nose
        
        for (Bullet b: bullets)
        {
            b.draw(g);
        }
        for (Target t: targets)
        {
            t.draw(g);
        }
        
    }
    
    boolean on = false;
    
    // The main game loop
    // In here is where all the logic for my game will go
    public void run()
    {
        // Used to keep track of time used to draw and update the game
        // This is used to limit the framerate later on
        long startTime;
        long deltaTime;
        // the main game loop section
        // game will end if you set done = false;
        boolean done = false; 
        while(!done)
        {
            // determines when we started so we can keep a framerate
            startTime = System.currentTimeMillis();
            
            
            
            // all your game rules and move is done in here
            // GAME LOGIC STARTS HERE 
            lineTimer.tick();
            if (lineTimer.isDone())
            {
                lineTimer.reset();
                lineInterval += lineIntervalIncrease;
                if (lineInterval > 50 || Math.abs(lineInterval) < Math.abs(lineIntervalIncrease))
                {
                    lineIntervalIncrease *= -1;
                }
            }
            
            circleWidthTimer.tick();
            if (circleWidthTimer.isDone())
            {
                circleWidthTimer.reset();
                if (circleWidth > 2*radius)
                {
                    circleWidth += circleWidthInterval;
                    circleWidthInterval += circleWidthIntervalIncrease;
                    if (circleWidth <= 2*radius) // switch direction
                    {
                        //circleWidthIntervalIncrease *= -1;
                        circleWidthInterval *= -1;
                        circleWidth = 2*radius;
                    }
                    else if (circleWidth > WIDTH)
                    {
                        circleWidth = WIDTH;
                        circleWidthInterval = 0;
                    }
                }
                else
                {
                    circleHeight += circleWidthInterval;
                    circleWidthInterval += circleWidthIntervalIncrease;
                    if (circleHeight >= HEIGHT)
                    {
                        circleHeight = HEIGHT;
                        //circleWidthIntervalIncrease *= -1;
                        circleWidthInterval *= -1;
                    }
                    else if (circleHeight <= 2*radius)
                    {
                        circleHeight = 2*radius;
                        circleWidthInterval *= -1;
                        circleWidth = 2*radius + 2;
                    }
                }
            }
            
            dx = mx - WIDTH/2;
            dy = my - HEIGHT/2;
            
            if (clicked)
            {
                bullets.add(new Bullet(WIDTH/2, HEIGHT/2, mx, my));
                clicked = false;
            }
            
            Iterator<Target> tI = targets.iterator();
            while (tI.hasNext())
            {
                Target tempT = tI.next();
                tempT.move();
                if (tempT.x > WIDTH/2-pWidth/2 && tempT.x < WIDTH/2+pWidth/2)
                {
                    if (tempT.y > HEIGHT/2-pWidth/2 && tempT.y < HEIGHT/2+pWidth/2)
                    {
                        pWidth-=10;
                        tI.remove();
                    }
                }
                if (tempT.x + tempT.width < 0 || tempT.x > WIDTH)
                {
                    tI.remove();
                }
                else if (tempT.y + tempT.height < 0 || tempT.y > HEIGHT)
                {
                    tI.remove();
                }
            }
            
            Iterator<Bullet> bI = bullets.iterator();
            while (bI.hasNext())
            {
                Bullet tempB = bI.next();
                tempB.move();
                
                if (tempB.getX() + tempB.getWidth() < 0 || tempB.getX() > WIDTH)
                {
                    bI.remove();
                }
                else if (tempB.getY() + tempB.getHeight() < 0 || tempB.getY() > HEIGHT)
                {
                    bI.remove();
                }
                tI = targets.iterator();
                while (tI.hasNext())
                {
                    Target tempT = tI.next();
                    if (tempB.intersects(tempT))
                    {
                        score += 5;
                        tI.remove();
                        bI.remove();
                        
                    }
                }
            }
            generateTargets();
            
            if (pWidth == 0)
            {
                restart();
            }
            
            scoreTimer.tick();
            if (scoreTimer.isDone())
            {
                score ++;
                scoreTimer.reset();
            }
            // GAME LOGIC ENDS HERE 
            
            // update the drawing (calls paintComponent)
            repaint();
            
            
            
            // SLOWS DOWN THE GAME BASED ON THE FRAMERATE ABOVE
            // USING SOME SIMPLE MATH
            deltaTime = System.currentTimeMillis() - startTime;
            if(deltaTime > desiredTime)
            {
                //took too much time, don't wait
            }else
            {
                try
                {
                    Thread.sleep(desiredTime - deltaTime);
                }catch(Exception e){};
            }
        }
    }
    
    public void generateTargets()
    {
        while (targets.size() < 10)
        {
            int side = (int)(Math.random()*4);
            switch (side)
            {
                case 0: // left
                    targets.add(new Target(0, Math.random()*HEIGHT, targetSpeeds[(int)(Math.random()*3)]));
                    break;
                case 1: // top
                    targets.add(new Target(Math.random()*WIDTH, 0, targetSpeeds[(int)(Math.random()*3)]));
                    break;
                case 2: // right
                    targets.add(new Target(WIDTH, Math.random()*HEIGHT, targetSpeeds[(int)(Math.random()*3)]));
                    break;
                case 3:
                    targets.add(new Target(Math.random()*WIDTH, HEIGHT, targetSpeeds[(int)(Math.random()*3)]));
                    break;
                    
            }
            
        }
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // creates a windows to show my game
        JFrame frame = new JFrame("My Game");
       
        // creates an instance of my game
        Main game = new Main();
        // sets the size of my game
        game.setPreferredSize(new Dimension(WIDTH,HEIGHT));
        // adds the game to the window
        frame.add(game);
         
        // sets some options and size of the window automatically
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        // shows the window to the user
        frame.setVisible(true);
        game.addMouseMotionListener(game);
        game.addMouseListener(game);
        // starts my game loop
        game.run();
    }

    void restart()
    {
        targets.clear();
        bullets.clear();
        pWidth = 100;
        score = 0;
    }
    
    @Override
    public void mouseDragged(MouseEvent e) {
        mx = e.getX();
        my = e.getY();
        
        //System.out.print
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        mx = e.getX();
        my = e.getY();
        //System.out.println(mx + " " + my);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        
    }

    @Override
    public void mousePressed(MouseEvent e) {
        dragged = true;
        clicked = true;
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        dragged = false;
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
}

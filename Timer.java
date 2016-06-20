/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author kobed6328
 */
public class Timer {
    
    private final int defaultTime;
    private int time;
 
    public Timer(int time)
    {
        this.defaultTime = time;
        reset();
    }
    
    public void tick()
    {
        time --;
    }
    
    public void set(int newTime)
    {
        time = newTime;
    }
    
    public boolean isDone()
    {
        return time <= 0;
    }
    
    public void reset()
    {
        time = defaultTime;
    }
}

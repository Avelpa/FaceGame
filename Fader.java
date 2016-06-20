/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author kobed6328
 */
public class Fader {
    
    
    private double[] rgb;
    private double[] intervals; // rgb
    
    
    public Fader(double r, double g, double b, double toR, double toG, double toB, int time)
    {
        rgb = new double[] {r, g, b};
        intervals = setTicks(new double[] {toR, toG, toB}, time);
    }
    
   // public void tick()
    
    private double[] setTicks(double[] rgbs, int time)
    {
        double[] intervals = new double[3];
        for (int i = 0; i < 3; i ++)
        {
            intervals[i] = (rgb[i] - rgbs[i])/time;
        }
        
        return intervals;
    }
    
}

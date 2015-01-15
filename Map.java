/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package navigationalpha;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.*;

/**
 *
 * @author johnevans
 */
public class Map {
    double lon,lat;
    double scaleX;
    double scaleY;
    int pixX,pixY;
    
    BufferedImage img;
    BufferedImage img1;
    
    
    public Map(String f,double lati,double loni,double sx,double sy,int xp,int yp){
        

        
        
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsConfiguration gc = ge.getDefaultScreenDevice().getDefaultConfiguration();
        BufferedImage img = gc.createCompatibleImage(4832, 3628, Transparency.TRANSLUCENT);
        img.setAccelerationPriority(1);
        
        Graphics g3 =  img.getGraphics();
        
        g3.drawImage(img1, 0, 0, null);
        
        lon = loni;
        lat = lati;
        
        scaleX = sx;
        scaleY = sy;
        
        pixX = xp;
        pixY = yp;
    }
}

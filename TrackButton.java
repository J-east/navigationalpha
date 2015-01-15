/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package navigationalpha;

/**
 *
 * @author johnevans
 */

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.image.*;

public class TrackButton {
    int x,y;
    int w,h;
    int index;
    
    boolean pressed;
    boolean released;
    ViewCenter vCenter;
    
    BufferedImage image;
    BufferedImage darkImage;
    
    Ent toTrack;
    
    Boolean trackYou;
    
    public TrackButton(int x1, int y1, ViewCenter c){
        x = x1;
        y = y1;
        
        w = 50;
        h = 50;
        
       
        try {
            image = ImageIO.read(this.getClass().getResource("homeIn.png"));
        } catch (final IOException e) {
            e.printStackTrace();
        }
        try {
            darkImage = ImageIO.read(this.getClass().getResource("homeInDark.png"));
        } catch (final IOException e) {
            e.printStackTrace();
        }

        vCenter = c;
        trackYou = true;
        
    }
    
    public TrackButton(ViewCenter c, Ent t){
        
        w = 50;
        h = 50;
        
        try {
            image = ImageIO.read(this.getClass().getResource("homeInSmall.png"));
        } catch (final IOException e) {
            e.printStackTrace();
        }
        try {
            darkImage = ImageIO.read(this.getClass().getResource("homeInSmallDark.png"));
        } catch (final IOException e) {
            e.printStackTrace();
        }
        
        trackYou = false;
        vCenter = c;
        toTrack = t;
        
    }
    
    public void setLoc(int x1, int y1){
        x = x1;
        y = y1;
    }
    
    public BufferedImage getImage(){
        if (pressed){
            return darkImage;
        }
        return image;
    }
    
    public boolean testPress(int tx, int ty){
        if(tx > x && tx < (x+w) && ty > y && ty < (y+h)){
            pressed = true;
            return true;
        }
        return false;
    }
    
    public boolean testRelease(int tx, int ty){
        if(tx > x && tx < (x+w) && ty > y && ty < (y+h)){
            pressed = false;
            this.Action();
            return true;
        }
        return false;
    }
    
    public boolean testDrag(int tx, int ty){
        if(pressed == true){
            if(tx > x && tx < (x+w) && ty > y && ty < (y+h)){
                return true;
            }
        }
        pressed = false;
        
        return false;
    }
    
    public void Action(){
        if(trackYou){
            vCenter.centerYou();
        }
        else{
            vCenter.tie(toTrack);
        }
    }
    
}

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
import java.net.URISyntaxException;

public class PlayStopButton {
    int x,y;
    int w,h;
    int index;
    
    boolean pressed;
    boolean released;
    NavigationAlpha main;
    
    BufferedImage image;
    BufferedImage darkImage;
    
    Ent toTrack;
    
    boolean play;
    boolean restart;
    
    public PlayStopButton(int x1, int y1, NavigationAlpha m, boolean p, boolean r){
        x = x1;
        y = y1;
        
        w = 64;
        h = 64;
        
        main = m;
        play = p;
        restart = r;
        
       if(play){
            try {
                image = ImageIO.read(this.getClass().getResource("playButton.png"));
            } catch (final IOException e) {
                e.printStackTrace();
            }
            
            try {
                darkImage = ImageIO.read(this.getClass().getResource("playButtonDark.png"));
            } catch (final IOException e) {
                e.printStackTrace();
            }
       }
        
       else if(restart){
            try {
                image = ImageIO.read(this.getClass().getResource("restartButton.png"));
            } catch (final IOException e) {
                e.printStackTrace();
            }
            try {
                darkImage = ImageIO.read(this.getClass().getResource("restartButtonDark.png"));
            } catch (final IOException e) {
                e.printStackTrace();
            }
       }
       
       else{
           try {
                image = ImageIO.read(this.getClass().getResource("stopButton.png"));
            } catch (final IOException e) {
                e.printStackTrace();
            }
            try {
                darkImage = ImageIO.read(this.getClass().getResource("stopButtonDark.png"));
            } catch (final IOException e) {
                e.printStackTrace();
            }
       }
   
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
        //System.out.println("test pressed in play/stopButton: "+tx+" "+x+" "+w);
        if(tx > x && tx < (x+w) && ty > y && ty < (y+h)){
            pressed = true;
            return true;
        }
        return false;
    }
    
    public boolean testRelease(int tx, int ty){
        if(tx > x && tx < (x+w) && ty > y && ty < (y+h)){
            pressed = false;
            try{
                this.Action();
            } catch (final IOException e) {
                e.printStackTrace();
            } catch (final URISyntaxException e){
                e.printStackTrace();
            }
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
    
    public void Action()throws IOException,URISyntaxException {
        try {
                
            
        if(play){
            main.play();
        }
        else if(restart){
            main.restart();
        }
        else{
            main.stop();
        }
    }
     catch (final IOException e) {
                e.printStackTrace();
            }
    }
    
}

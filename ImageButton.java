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

public class ImageButton {
    int x,y;
    int w,h;
    int index;
    
    boolean pressed;
    boolean released;
    
    BufferedImage image;
    
    String fileName;
    
    public ImageButton(int x1, int y1, String f, int i){
        x = x1;
        y = y1;

        fileName = f;
        
        index = i;
        
        try {
            image = ImageIO.read(new File(f));
        } catch (final IOException e) {
            e.printStackTrace();
        } 
    }
    
    public BufferedImage getImage(){
        return image;
    }
    
    public void pressed(){
        pressed = true;
    }
    
    public void released(){
        pressed = false;
        
    }
    
    
    public void Action(){
        
    }
    
}

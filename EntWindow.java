/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author johnevans
 */
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package navigationalpha;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;


/**
 *
 * @author johnevans
 */
public class EntWindow implements Comparable{
    
    Hero hero;
    Display2D can;
    Ent ent;
    TrackButton trackButton;
    ViewCenter viewCenter;
    SideBar sideBar;
    
    Vector2d hv;
    Vector2d rv;
    Vector2d iv;
    
    int counter = 0;
    
    int height;
    int y;
    
    
    String airType;
    int callNum;
    double alt;
    double dist;
    double dist1;
    double closingT;
    double relation;
    double dirIntention;
    double altIntention;
    double bearing;
    int dispRel;
    
    boolean expanded = false;
    boolean wasRed = false;
    boolean redAlert = false;
    
    BufferedImage exp;
    BufferedImage rExp;
    
    public EntWindow(Ent n,Display2D c,Hero h,ViewCenter v,SideBar s){
        hero = h;
        ent = n;
        can = c;
        sideBar = s;
        viewCenter = v;
        
        trackButton = new TrackButton(viewCenter, ent);
        
        try {
            exp = ImageIO.read(this.getClass().getResource("expander.png"));
        } catch (final IOException e) {
            e.printStackTrace();
        }
        try {
            rExp = ImageIO.read(this.getClass().getResource("expanderRotate.png"));
        } catch (final IOException e) {
            e.printStackTrace();
        }
        
    }
    
    public int getHeight(){
        if(expanded || redAlert){
            return 150;
        }
        else{
            return 50;
        }
    }
    
    public void testClick(int x1, int y1){
        if(expanded){
            if(x1>40 && y1 > (y) && y1 < (y+150)){
                expanded = false;
            }
        }
        else{
            if(x1>40 && y1 > (y) && y1 < (y+50)){
                expanded = true;
            }
        }
    }
    
    public BufferedImage getImage(){
        if(redAlert){
            return drawBigImage();
        }
        if(expanded){
            return drawBigImage();
        }
        else{
            return drawImage();
        }
    }
    
    public BufferedImage drawImage(){
            BufferedImage image = new BufferedImage(200, 50, BufferedImage.TYPE_INT_ARGB);
                Graphics g3 =  image.getGraphics();
                if(trackButton.vCenter.tie == ent && trackButton.vCenter.tied){
                    this.expanded = true;
                    this.wasRed = true;
                    g3.setColor(Color.red);
                    g3.fillRect(0,0,200,50);
                }
                else{
                    g3.setColor(Color.yellow);
                    g3.fillRect(0,0,200,50);
                }
                
                g3.setColor(Color.MAGENTA);
                g3.drawRect(0, 0, 200,50);
                
                g3.drawImage(exp, 160, 10, sideBar);

                //DisplayEnt de = disEnts.get(i);

                g3.setColor(Color.blue);

                String lat1 = String.valueOf(String.format("%.5g%n", ent.coords.get(0).lat));
                String lon1 = String.valueOf(String.format("%.5g%n", ent.coords.get(0).lon));

                
                
                this.calcdistance();
                this.calcRelation();
                
                g3.drawString("dist:"+String.format("%.5g%n",dist)+" NM",50,25);
                g3.drawString("relation: "+dispRel+" o'clock",50,45);

                g3.drawString(ent.id, 10, 12);
                return image;
    }
    
    public void calcdistance(){
        if(counter > 60){
            dist1 = dist;
            dist = Math.sqrt(((hero.coords.get(0).lat-ent.coords.get(0).lat)*
                    (hero.coords.get(0).lat-ent.coords.get(0).lat)) + 
                    ((hero.coords.get(0).lon-ent.coords.get(0).lon)*
                    (hero.coords.get(0).lon-ent.coords.get(0).lon)));
            dist = dist * 60;
            
        }
        else if(counter < 61){
            counter++;
        }
        
        if(dist < 5.0 && closingT > 0){
            redAlert = true;
        }
        else{
            redAlert = false;  
        }
    }
    
    public void calcBearing(){
        double dLon = Math.toRadians(-ent.coords.get(0).lon+ent.coords.get(1).lon);
        double y = Math.sin(dLon) * Math.cos(Math.toRadians(ent.coords.get(0).lat));
        double x = Math.cos(Math.toRadians(ent.coords.get(1).lat))*Math.sin(Math.toRadians(ent.coords.get(0).lat)) - 
                Math.sin(Math.toRadians(ent.coords.get(1).lat))*Math.cos(Math.toRadians(ent.coords.get(0).lat))*Math.cos(dLon);
        
        bearing = (Math.toDegrees(Math.atan2(y, x)) + 360) % 360;
        
    }
    
    public void calcRelation(){
        hv = new Vector2d(hero.vecs.get(0).x,hero.vecs.get(0).y);
        rv = new Vector2d(hero.coords.get(0).lon-ent.coords.get(0).lon,hero.coords.get(0).lat-ent.coords.get(0).lat);
        
        relation = hv.angle(rv);
        
        relation = relation * (12/(2*Math.PI));
        relation = relation + 4;
        
        if(relation > 12){
            relation = relation - 12;
        }
        
        dispRel = (int)relation;

    }
    
    public void calcClosing(){
        double deltaD = dist -  Math.sqrt(((hero.coords.get(1).lat-ent.coords.get(1).lat)*
                    (hero.coords.get(1).lat-ent.coords.get(1).lat)) + 
                    ((hero.coords.get(1).lon-ent.coords.get(1).lon)*
                    (hero.coords.get(1).lon-ent.coords.get(1).lon)));
        closingT = deltaD*60;
    }
    
    public void calcIntention(){
        //iv = new Vector2d(ent.vecs.get(0).x,ent.vecs.get(0).y);
        
        dirIntention = hv.angle(new Vector2d(ent.vecs.get(1).x,ent.vecs.get(1).y));
        dirIntention = dirIntention * (360/(2*Math.PI));
        dirIntention = dirIntention/(60);
    }
    
    public BufferedImage drawBigImage(){
            BufferedImage image = new BufferedImage(200, 150, BufferedImage.TYPE_INT_ARGB);
                Graphics g3 = (Graphics2D) image.getGraphics();
                if(trackButton.vCenter.tie == ent && trackButton.vCenter.tied){
                    g3.setColor(Color.yellow);
                    g3.fillRect(0,0,200,150);
                    g3.setColor(Color.blue);
                    g3.drawRect(0,0,sideBar.getWidth()-1,150);
                    g3.drawRect(1,1,sideBar.getWidth()-3,148);
                    
                }
                else if(this.wasRed){
                    this.expanded = false;
                    this.wasRed = false;
                    g3.setColor(Color.red);
                    g3.fillRect(0,0,200,50);
                }
                else{
                    g3.setColor(Color.yellow);
                    g3.fillRect(0,0,200,150);
                }
                
                if(redAlert){
                    g3.setColor(Color.red);
                    g3.fillRect(0,0,200,150);
                }
                
                g3.setColor(Color.MAGENTA);
                g3.drawRect(0, 0, 200,150);
                
                g3.drawImage(rExp, 160, 10, sideBar);

                //DisplayEnt de = disEnts.get(i);

                g3.setColor(Color.blue);

                String lat1 = String.valueOf(String.format("%.5g%n", ent.coords.get(0).lat));
                String lon1 = String.valueOf(String.format("%.5g%n", ent.coords.get(0).lon));

                this.calcdistance();
                this.calcRelation();
                this.calcClosing();
                this.calcIntention();
                this.calcBearing();
                
                g3.drawString("dist:"+String.format("%.3g%n",dist)+" NM",50,25);
                g3.drawString("rel: "+dispRel+" o'clock",50,45);
                if(closingT > 0){
                    g3.drawString("closing:"+String.format("%.5g%n",closingT)+" NM/hr",30,65);
                }
                else{
                    g3.drawString("distancing:"+String.format("%.5g%n",closingT)+" NM/hr",30,65);
                }
                g3.drawString("Bearing: "+String.format("%.4g%n",bearing)+" degrees",30,85);
                g3.drawString("Intention:"+String.format("%.2g%n",dirIntention)+" deg/min",30,105);
                g3.drawString("altitude: "+ent.coords.get(0).alt+" feet",30,125);
                

                g3.drawString(ent.id, 10, 12);

                return image;
    }
    
    public int compareTo(Object o){
            EntWindow e = (EntWindow)o;
            return (int)(dist)-(int)(e.dist);
      }
    
}




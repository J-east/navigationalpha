/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package navigationalpha;

/**
 *
 * @author johnevans
 */

import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;

import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.*;
import java.awt.geom.*;

public class Hero {
    JFrame frame;

    private String heroImg = "hero.png";

    
    ArrayList<Vector2d> vecs = new ArrayList();
    ArrayList<HardGPS> coords = new ArrayList();
    ArrayList<Double> velocitys = new ArrayList();
    int timedif;
    int velocity;
    double compass;
    int dAlt;
    
    HardGPS cur = new HardGPS(43,72,0,0);
    
    Display2D can;
    DataSet bag;

    Image image;
    
    public Hero(Display2D d,DataSet b) {
        //ImageIcon ii = new ImageIcon(this.getClass().getResource(heroImg));
        //image = ii.getImage();
        can = d;
        bag = b;
        coords.add(cur);
    }

    
    //updates vector list and coords list
    public void move(double delta) {
        HardGPS tCur = new HardGPS(cur.lat + (vecs.get(0).y/60)*delta,cur.lon + (vecs.get(0).x/60)*delta,
                cur.alt,1);
        
        cur = tCur;
    }
    
    
    public void updateGPS(HardGPS h){
        coords.add(0, h);
        cur = h;
        vecs.add(0, new Vector2d(coords.get(0).lon-coords.get(1).lon, coords.get(0).lat-coords.get(1).lat));
        
        velocitys.add(0, Math.sqrt((coords.get(0).lon-coords.get(1).lon) * (coords.get(0).lon-coords.get(1).lon) + (coords.get(0).lat-coords.get(1).lat) * (coords.get(0).lat-coords.get(1).lat)));
        
        this.updateCompass();
        //this.cleanUp();
    }
    
    public void updateCompass(){
        double dLon = Math.toRadians(-coords.get(0).lon+coords.get(1).lon);
        double y = Math.sin(dLon) * Math.cos(Math.toRadians(coords.get(0).lat));
        double x = Math.cos(Math.toRadians(coords.get(1).lat))*Math.sin(Math.toRadians(coords.get(0).lat)) - 
                Math.sin(Math.toRadians(coords.get(1).lat))*Math.cos(Math.toRadians(coords.get(0).lat))*Math.cos(dLon);
        
        compass = (Math.toDegrees(Math.atan2(y, x)) + 360) % 360;
        
        int a = coords.get(0).alt;
        int b = coords.get(1).alt;
        
        dAlt = a - b;
    }
    
    public void cleanUp(){
        if(vecs.size() > 10){
            vecs.remove(9);
        }
        if(coords.size() > 10){
            coords.remove(9);
        }
        if(velocitys.size() > 10){
            velocitys.remove(9);
        }
    }
}


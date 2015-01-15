/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package navigationalpha;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.File;

import java.awt.*;
import java.util.*;

import javax.swing.*;

import java.awt.geom.*;

import java.net.*;
/**
 * 
 *
 * @author johnevans
 */
public class VirtualMap {
    
    Hero hero;
    ArrayList<HardGPS> heroGPS = new ArrayList();
    ArrayList<Ent> ents = new ArrayList();
    ArrayList<Map> maps = new ArrayList();
    
    DataSet bag;
    Display2D can;
    File file;
    
    Boolean toClose = true;
    Boolean trueClose = true;
    
    double scale;
    
    GPSUpdater gpsRead;
    FSKUpdater fskRead;
    
    public VirtualMap(DataSet b,Hero h,Display2D z)throws IOException,URISyntaxException{
        hero = h;
        can = z;
        bag = b;
        
        maps = bag.maps;
        
        gpsRead = new GPSUpdater("paths.csv",hero);
        fskRead = new FSKUpdater("paths_1.csv",this);
    }
    
   // public ArrayList<DisplayEnt> getEnts(){
     //   ArrayList<DisplayEnt> d = new ArrayList();
       // return d;
   // }
    
    public ArrayList<DisplayEnt> paintEnts(ViewCenter v){
        ArrayList<DisplayEnt> de = new ArrayList();
        HardGPS tempG = v.getGPS();
        
        
        for (int i = 0; i < ents.size(); i++){
            
                DisplayEnt d = new DisplayEnt(ents.get(i),(int) (4000+v.scalex*(tempG.lon-ents.get(i).cur.lon)),
                       (int) (4000+v.scaley*(tempG.lat-ents.get(i).cur.lat)));
                
                //System.out.println("coords of ent "+ ents.get(i).cur.lon);
                //System.out.println("coords of v "+ tempG.lon);
            
                //System.out.println("coords of Dis "+ d.x);
                
                if (d.x > 0 && d.x < 6000 && d.y > 0 && d.y < 6000){
                    de.add(d);
                }
        }
        return de;
    }
    
    public int paintRings(ViewCenter v,boolean xring){
        int[] ring = new int[2];
        HardGPS tempG = v.getGPS();
        
            ring[0] = (int) (4000+v.scalex*(tempG.lon-v.tie.cur.lon));
            ring[1] = (int) (4000+v.scaley*(tempG.lat-v.tie.cur.lat));
            
            System.out.println("printing int in virt"+ring[0]);
            
            if(xring)
                return ring[0];
            else
                return ring[1];
    }
    
    public ArrayList<DisplayEnt> paintEntTrail(ViewCenter v,int j){
        ArrayList<DisplayEnt> de = new ArrayList();
        HardGPS tempG = v.getGPS();
        
        for (int i = 0; i < ents.get(j).coords.size(); i++){
            
                DisplayEnt d = new DisplayEnt(ents.get(j),(int) (4000+v.scalex*(tempG.lon-ents.get(j).coords.get(i).lon)),
                       (int) (4000+v.scaley*(tempG.lat-ents.get(j).coords.get(i).lat)));
                
                //System.out.println("coords of ent "+ ents.get(i).cur.lon);
                //System.out.println("coords of v "+ tempG.lon);
            
                //System.out.println("coords of Dis "+ d.x);
                
                if (d.x > 0 && d.x < 6000 && d.y > 0 && d.y < 6000){
                    de.add(d);
                }
        }
        return de;
    }
    
    public ArrayList<DisplayMap> paintMaps(ViewCenter v){
        ArrayList<DisplayMap> de = new ArrayList();
        HardGPS tempG = v.getGPS();
        
        for (int i = 0; i < maps.size(); i++){
            DisplayMap d = new DisplayMap(maps.get(i),(int) (4000+v.scalex*(tempG.lon-maps.get(i).lon)),
                       (int) (4000+v.scaley*(tempG.lat-maps.get(i).lat)));
            
            
            
            
            if (d.x > 0 && d.x < 6000 && d.y > 0 && d.y < 6000){
                de.add(d);
            }
        }
        return de;
    }
    
    public DisplayEnt paintHero(ViewCenter v){
        DisplayEnt de;
        HardGPS tempG = v.getGPS();
        
        
                 de = new DisplayEnt(hero,(int) (4000+v.scalex*(tempG.lon-hero.cur.lon)),
                       (int) (4000+v.scaley*(tempG.lat-hero.cur.lat)));
                
                //System.out.println("coords of ent "+ ents.get(i).cur.lon);
                //System.out.println("coords of v "+ tempG.lon);
            
                //System.out.println("coords of Dis "+ d.x);
                
                if (de.x > 0 && de.x < 6000 && de.y > 0 && de.y < 6000){
                    return de;
                }
                
         return null;
    }
    
    public Point getDisPoint(HardGPS coord,ViewCenter v){
        HardGPS tempG = v.getGPS();
        Point disP = new Point((int)(4000+v.scalex*(tempG.lon-coord.lon)),
                (int)(4000+v.scaley*(tempG.lat-coord.lat)));
        return disP;
    }
    
    public void updatePos(double d,int c){
        gpsRead.Update(c);
        fskRead.Update(c);
        hero.move(1);
        for (int i = 0; i < ents.size(); i++){
            ents.get(0).move(1);
        }
    }
    
    
    
    /*
    //uses rectangle intersection to test collisions
    public void checkCollisions(double delta,boolean sixth) {
        toClose = false;
        Rectangle he = hero.getBounds();
        
        //check collisions with the planes and hero
        for (int j = 0; j<ents.size(); j++) {
            Ent a = (Ent) ents.get(j);
            
            Line2D l2 = new Line2D.Double(p.oldx,p.oldy,p.x,p.y);

            if (he.intersects(r2)) {
                if(sixth){
                    Sound laugh = new Sound("demonlaugh.wav");
                    laugh.playSound();
                }
                hero.removeHealth(a.base.pp);
            }
            else{
                a.move(hero,delta);
            }
        }
        */
    
        
        //check for collisions with all the entities
        /*
        for (int i = 0; i < hero.activeProjectiles.size() && i >= 0; i++) {
            for (int j = 0; j<zombies.size(); j++) {
                
                
                Ent a = (Ent) zombies.get(j);
                Rectangle r2 = new Rectangle(a.x,a.y,scale,scale);
                
                ActiveProjectile p = hero.activeProjectiles.get(i);
                Line2D l2 = new Line2D.Double(p.oldx,p.oldy,p.x,p.y);

                if (l2.intersects(r2)) {
                    Sound hit = new Sound("snowsplat.wav");
                    hit.playSound();
                    a.removeArmour(p.projectile.armourPP);
                    a.removeHealth(p.projectile.pp);
                    if(a.health<0){
                        System.out.println("removing zombie in virtmap");
                        zombies.remove(j);
                    }
                    hero.activeProjectiles.remove(i);
                    i--;
                    if(i < 0){
                        break;
                    }
                    
                }
            }
        }
        */
        
        
        /*
        //check for collisions with zombies and humans
        for (int i = 0; i < humans.size(); i++) {
            for (int j = 0; j<zombies.size(); j++) {
                ActiveZombie a = (ActiveZombie) zombies.get(j);
                Rectangle r2 = new Rectangle(a.x,a.y,10,10);

                if (he.intersects(r2)) {
                    hero.removeHealth(a.base.pp);
                    hero.x = hero.oldx;
                    hero.y = hero.oldy;
                }
            }
        }
        
        
        //check for collisions of zombies and humans with walls/objects
        for (int i = 0; i < objects.size(); i++) {
            for (int j = 0; j<zombies.size(); j++) {
                ActiveZombie a = (ActiveZombie) zombies.get(j);
                Rectangle r2 = new Rectangle(a.x,a.y,10,10);

                if (he.intersects(r2)) {
                    hero.removeHealth(a.base.pp);
                }
            }
        }
        
        for (int i = 0; i < objects.size(); i++) {
            Rectangle o = new Rectangle(objects.get(i).x,objects.get(i).y,10,10);
            for (int j = 0; j<humans.size(); j++) {
                ActiveHuman a =  humans.get(j);
                Rectangle r2 = new Rectangle(a.x,a.y,10,10);

                if (he.intersects(r2)) {
                    
                }
            }
        }
        */
}
        

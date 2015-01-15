/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package navigationalpha;



/**
 *
 * @author johnevans
 */
public class ViewCenter {
    
    Boolean tied = false;
    Boolean onYou = true;
    HardGPS gps;
    HardGPS ringGPS;
    Ent tie;
    Hero hero;
    double scalex;
    double scaley;
    boolean dragged = false;
    int draggedX = 0;
    int draggedY = 0;
    HardGPS startGPS;
    
    public ViewCenter(Hero h,double sx,double sy){
        scalex = sx;
        scaley = sy;
        hero = h;
        gps = hero.coords.get(0);
    }
    
    public ViewCenter(Ent en,double sx, double sy){
        scalex = sx;
        scaley = sy;
        tie = en;
        gps = tie.coords.get(0);
    }
    
    
    public void unTie(double tla,double tlo){
        tied = false;
        onYou = false;
        gps.setCoords(tla, tlo);
    }
    
    public void drag(int difX,int difY){
        
        if(!dragged){
            tied = false;
            onYou = false;
            dragged = true;
            startGPS = (HardGPS) gps.clone();
        }
        
        //System.out.println("printing startlat in viewCenter: "+gps.lat);
        //System.out.println("printing dify in viewCenter:     "+difY);
        
        gps.setCoords(startGPS.lat-((difY)/scaley),startGPS.lon-((difX)/scalex));
        
        //System.out.println("printing lat in viewCenter:      "+gps.lat);
        //System.out.println("");
        
        draggedY = difY;
        draggedX = difX;
    }
    
    
    public void tie(Ent t){
        tie = t;
        tied = true;
    }
    
    public void centerYou(){
        tied = false;
        onYou = true;
    }
    
    public HardGPS getGPS(){
        if(onYou){
            gps.setCoords(hero.cur.lat, hero.cur.lon);
            gps.setAlt(hero.cur.alt);
        }
        
        /*
        else if(tied){
            gps.setCoords(tie.cur.lat, tie.cur.lon);
            gps.setAlt(tie.cur.alt);
        }
        */
        
        return gps;
    }
    
    public HardGPS getRingGPS(){
        if(onYou){
            ringGPS.setCoords(hero.cur.lat, hero.cur.lon);
            ringGPS.setAlt(hero.cur.alt);
        }
        
        
        else if(tied){
            ringGPS.setCoords(tie.cur.lat, tie.cur.lon);
            ringGPS.setAlt(tie.cur.alt);
        }
        
        return ringGPS;
    }
    
    
}

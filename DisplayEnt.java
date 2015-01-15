/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package navigationalpha;

/**
 *
 * @author johnevans
 */
public class DisplayEnt {
    Hero hero;
    Ent ent;
    int x,y;
    
    double prox;
    double heading;
    double bearing;
    
    int intention;
    
    
    
    public DisplayEnt(Ent en,int tx,int ty){
        ent = en;
        x = tx;
        y = ty;
    }
    
    public DisplayEnt(Hero h,int tx,int ty){
        hero = h;
        x = tx;
        y = ty;
    }
    
    public void calculate(){
        
    }
}

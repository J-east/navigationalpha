/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package navigationalpha;

import java.util.ArrayList;

/**
 *
 * @author johnevans
 */
public class Ent {
    
    ArrayList<Vector2d> vecs = new ArrayList();
    ArrayList<HardGPS> coords = new ArrayList();
    ArrayList<Double> velocitys = new ArrayList();
    int velocity;
    
    int num;
    String id;
    String airCraft;
    HardGPS cur = new HardGPS(43,72,0,0);
    
    public Ent(int i,String d,String type){
        num = i;
        id = d;
        airCraft = type;
        coords.add(cur);
        vecs.add(new Vector2d(0,0));
    }
    
    public void updateGPS(HardGPS h){
        coords.add(0, h);
        cur = h;
        
        vecs.add(0, new Vector2d(coords.get(0).lon-coords.get(1).lon, coords.get(0).lat-coords.get(1).lat));
        
        velocitys.add(0, vecs.get(0).length());
        
        //this.cleanUp();
    }
    
    public void move(double delta) {
        //HardGPS tCur = new HardGPS(cur.lat + (vecs.get(0).y/60)*delta,cur.lon + (vecs.get(0).x/60)*delta,
          //      cur.alt + (int)((vecs.get(0).z/60)*delta),1);
        
        //cur = tCur;
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

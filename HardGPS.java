/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package navigationalpha;

/**
 *
 * @author johnevans
 */


import java.lang.Cloneable;


public class HardGPS implements Cloneable{
    
    public double lat;
    public double lon;
    public int alt;
    int time;
    
    public HardGPS(double l,double lg,int a,int t){
        lat = l;
        lon = lg;
        alt = a;
    }
    /*
    public HardGPS(Point3d p){
        lat = p.y;
        lon = p.x;
        alt = (int) p.z;
    }
    */
    public void setCoords(double la,double lo){
        lat = la;
        lon = lo;
    }
    
    public void setAlt(int a){
        alt = a;
    }
    /*
    public void setPoint(Point3d p){
        lat = p.y;
        lon = p.x;
        alt = (int) p.z;
    }
    */
    public Object clone()
      {
          try
      {
              return super.clone();
          }
      catch( CloneNotSupportedException e )
      {
              return null;
          }
      } 

}

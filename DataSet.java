/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package navigationalpha;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.*;
import java.io.*;

/**
 *
 * @author johnevans
 */
public class DataSet {
    /**
 *
 * @author jakepevans
 */

    int index;
    int max;
    ArrayList<Map> maps = new ArrayList();
    
    
    public DataSet(String mFile){
        
        
            // Read first line.
            // The while checks to see if the data is null. If 
            // it is, we've hit the end of the file. If not, 
            // process the data.

             
                 String img = "44_72.5.png";
                 
                 double lati = 44;
                 double loni = 72.5;
                 


                 int pixX = 1745;
                 int pixY = 2921;
                 

                 
                 int latScale = 1722;
                 int lonScale = 1264;

                 
                 Map tMap = new Map(img,lati,loni,lonScale,latScale,pixX,pixY);
                 maps.add(tMap);
             


            

            
            
    }
}


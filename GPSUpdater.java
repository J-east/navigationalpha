/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package navigationalpha;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.*;
import java.io.InputStream;
import java.io.Reader;
import java.io.InputStreamReader;

/**
 *
 * @author johnevans
 */
public class GPSUpdater {
    
    ArrayList<HardGPS> hg = new ArrayList();
    ArrayList<Integer> t = new ArrayList();
    Boolean rec = false;
    
    Hero hero;
    
    int counter = -1;
    
    BufferedReader CSVFile;
    InputStream is;
    
    
    public GPSUpdater(String pFile,Hero h)throws IOException{
        
        hero = h;
         
                
        is = GPSUpdater.class.getResourceAsStream(pFile);
        Reader reader = new InputStreamReader(is);
        CSVFile = new BufferedReader(reader);


        String dataRow =  CSVFile.readLine();
            // Read first line.
            // The while checks to see if the data is null. If 
            // it is, we've hit the end of the file. If not, 
            // process the data.


            while (dataRow != null){
                
             String[] dataArray = dataRow.split(",");

             if (dataArray[0].equals("You")){
                 rec = true;
                 dataRow = CSVFile.readLine(); // Read next line of data.
                 dataArray = dataRow.split(",");
             }
             
             if(dataArray[0].equals("Ent")){
                 rec = false;
                 return;
             }

             if (rec){
                 t.add(Integer.parseInt(dataArray[0]));
                 
                 double lat = (double) Double.parseDouble(dataArray[1]);
                 double lon = (double) Double.parseDouble(dataArray[2]);
                 int alt = Integer.parseInt(dataArray[3]);
                 HardGPS ha = new HardGPS(lat,lon,alt,0);
                 
                 hg.add(ha);
             }

             dataRow = CSVFile.readLine(); // Read next line of data.
            }
            // Close the file once all data has been read.
            CSVFile.close();
    } 
    
    public void Update(int count){
        if(counter != count){
            counter = count;
            hg.get(counter).time = counter;
            hero.updateGPS(hg.get(counter));
        }
    }
}

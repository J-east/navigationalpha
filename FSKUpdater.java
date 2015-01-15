/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package navigationalpha;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.*;
import java.net.*;


/**
 *
 * @author johnevans
 */
public class FSKUpdater {
    
    ArrayList<HardGPS> hg0 = new ArrayList();
    ArrayList<Integer> t0 = new ArrayList();
    
    
    ArrayList<HardGPS> hg1 = new ArrayList();
    ArrayList<Integer> t1 = new ArrayList();
    
    ArrayList<HardGPS> hg2 = new ArrayList();
    ArrayList<Integer> t2 = new ArrayList();
    
    ArrayList<HardGPS> hg3 = new ArrayList();
    ArrayList<Integer> t3 = new ArrayList();
    
    BufferedReader CSVFile;
    
    int rec = -1;
    
    int counter;
    
    VirtualMap virt;
        InputStream is;
    
    public FSKUpdater (String pFile,VirtualMap v)throws IOException, URISyntaxException 
                                         {
        
        virt = v;
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

             if (dataArray[0].equals("Ent")){
                 rec = Integer.parseInt(dataArray[1]);
                 virt.ents.add(new Ent(rec,dataArray[2],dataArray[3]));
                 dataRow = CSVFile.readLine(); // Read next line of data.
                 dataArray = dataRow.split(",");
             }

             if (rec == 0){
                 t0.add(Integer.parseInt(dataArray[0]));
                 
                 double lat = (double) Double.parseDouble(dataArray[1]);
                 double lon = (double) Double.parseDouble(dataArray[2]);

                 HardGPS ha = new HardGPS(lat,lon,0,0);
                 
                 hg0.add(ha);
             }
             
             if (rec == 1){
                 
                 t1.add(Integer.parseInt(dataArray[0]));
                 
                 double lat = (double) Double.parseDouble(dataArray[1]);
                 double lon = (double) Double.parseDouble(dataArray[2]);
                 int alt = Integer.parseInt(dataArray[3]);

                 HardGPS ha = new HardGPS(lat,lon,alt,0);
                 
                 hg1.add(ha);
             }
             
             if (rec == 2){
                 
                 t2.add(Integer.parseInt(dataArray[0]));
                 
                 double lat = (double) Double.parseDouble(dataArray[1]);
                 double lon = (double) Double.parseDouble(dataArray[2]);
                 int alt = Integer.parseInt(dataArray[3]);

                 HardGPS ha = new HardGPS(lat,lon,alt,0);
                 
                 hg2.add(ha);
             }

             dataRow = CSVFile.readLine(); // Read next line of data.
            }
            // Close the file once all data has been read.
            CSVFile.close();
            
    } 
    
    public void Update(int count){
        if(counter != count){
            counter = count;
            hg0.get(counter).time = counter;
            //hg1.get(counter).time = counter;
            System.out.println("printing hg0 in FSK"+hg0.get(counter).lon);
            
            virt.ents.get(0).updateGPS(hg0.get(counter));
            virt.ents.get(1).updateGPS(hg1.get(counter));
            virt.ents.get(2).updateGPS(hg2.get(counter));
        }
    }   
}
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package navigationalpha;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import javax.swing.JComponent;
import java.text.Format.*;

/**
 *
 * @author johnevans
 */

public class TopBar extends JComponent implements ActionListener,MouseListener,MouseMotionListener{
    
    DataSet bag;
    Hero hero;
    StatePanel state;
    Display2D can;
    VirtualMap virt;
    ViewCenter vCenter;
    NavigationAlpha main;
    boolean top;
    ArrayList<TrackButton> iButtons = new ArrayList();
    TrackButton trackButton;
    PlayStopButton play;
    PlayStopButton stop;
    PlayStopButton restart;
    int counter = 0;
    int time = 0;
    
    
    public TopBar(DataSet b,Display2D c, NavigationAlpha m){

            bag = b;
            can = c;
            main = m;
            
            addMouseListener(this);
            addMouseMotionListener(this);

            //rest of variables are initialized in this.addVirt
        }

        public void paintComponent(Graphics g) {
            g.setColor(Color.LIGHT_GRAY);
            g.fillRect(0, 0, getWidth(), getHeight());

                    //draw icons 
            BufferedImage button1 = this.drawButton(0);

            

                g.setColor(Color.BLUE);
                g.drawRect(this.getWidth()-198, 0, 198, 48);
                g.drawRect(this.getWidth()-199, 0, 199, 49);
                g.drawRect(this.getWidth()-197, 0, 199, 50);
                
                //display ship information
                    
                    g.drawImage(this.drawLatLon(), 60, 0, this);
                    g.drawImage(this.drawVec(), 350, 0, this);
                    g.drawImage(this.drawAlt(), 550, 0, this);
                    g.drawImage(trackButton.getImage(), 10, 0, this);
                    //g.drawImage(play.getImage(), 1000, 0, this);
                    //g.drawImage(stop.getImage(), 1050, 0, this);
                    //g.drawImage(restart.getImage(), 1100, 0, this);
                    
                    

                //ImageButton ib = new ImageButton(this.getWidth()-190, 10, "button.png", 0);

                //g.drawImage(ib.getImage(), ib.x, ib.y, this);
                //g.drawImage(this.drawButton(WIDTH), this.getWidth()-190, 10, this);    
                
                g.setColor(Color.BLUE);
                g.drawString("Time: "+Integer.toString(main.time), this.getWidth()-190, 12);
                g.drawImage(trackButton.getImage(), 10, 0, this);
                
        }
        
        public BufferedImage drawLatLon(){
            BufferedImage latLonImage = new BufferedImage(300, 50, BufferedImage.TYPE_INT_ARGB);
                Graphics g3 =  latLonImage.getGraphics();
                g3.setColor(Color.yellow);
                g3.fillRect(0,0,300,50);

                //DisplayEnt de = disEnts.get(i);

                g3.setColor(Color.blue);
                String lat = String.valueOf(String.format("%.7g%n", hero.cur.lat));
                String lon = String.valueOf(String.format("%.7g%n", hero.cur.lon));

                String lat1 = String.valueOf(String.format("%.5g%n", hero.coords.get(0).lat));
                String lon1 = String.valueOf(String.format("%.5g%n", hero.coords.get(0).lon));

                g3.drawString("Lat:  "+lat1,10,25);
                g3.drawString("Lon: "+lon1,10,40);

                g3.drawString("Estimated Lat:  "+lat,110,25);
                g3.drawString("Estimated Lon: "+lon,110,40);

                g3.drawString("GPS Coordinates", 10, 10);

                return latLonImage;
        }
        
        public BufferedImage drawVec(){
            
                BufferedImage latLonImage = new BufferedImage(300, 50, BufferedImage.TYPE_INT_ARGB);
                Graphics g4 =  latLonImage.getGraphics();
                g4.setColor(Color.yellow);
                g4.fillRect(0,0,300,50);

                //DisplayEnt de = disEnts.get(i);

                g4.setColor(Color.blue);
                String vec = String.valueOf(String.format("%.7g%n", hero.compass));
                String vel = String.valueOf(String.format("%.7g%n", hero.velocitys.get(0)*60*60*60));


                g4.drawString("Bearing:    "+vec+" "+this.getDir(hero.compass),10,25);
                g4.drawString("Velocity:   "+vel+" NM/hr",10,40);

                g4.drawString("Vector", 10, 10);

                return latLonImage;
        }
        
        public BufferedImage drawAlt(){
            
                BufferedImage latLonImage = new BufferedImage(300, 50, BufferedImage.TYPE_INT_ARGB);
                Graphics g5 =  latLonImage.getGraphics();
                g5.setColor(Color.yellow);
                g5.fillRect(0,0,300,50);

                //DisplayEnt de = disEnts.get(i);

                g5.setColor(Color.blue);
                int alt = hero.coords.get(0).alt;
                int dAlt =  hero.dAlt * 60;


                g5.drawString("Altitude:  "+alt+" feet",10,25);
                g5.drawString("Rise/Fall: "+dAlt+" feet/minute",10,40);

                g5.drawString("Altitude", 10, 10);

                return latLonImage;
        }
        
        public String getDir(double dir){
            String directions[] = {"N", "NE", "E", "SE", "S", "SW", "NW","N"};
            return directions[ (int)Math.round((  ((dir-45) % 360) / 45))];
        }

        public BufferedImage drawButton(int i){
            BufferedImage image = new BufferedImage(400, 50, BufferedImage.TYPE_INT_ARGB);
            
            

            return image;
        }
        
        public void update(){
            this.repaint();
        }
        /*
        public void testButtonsPress(int x, int y){
            for(int i = 0; i < iButtons.size(); i++){
                if(iButtons.get(i).testPress(x,y)){
                    pressedButton = iButtons.get(i);
                }
            }
        }


        public void testButtonsRelease(int x, int y){
            if(pressedTrackButton.testRelease(x, y)){
                pressedButton = null;
            }
        }
        */
        public void addVirt(VirtualMap v){
            virt = v;
            hero = virt.hero;
            vCenter = can.vCenter;
            trackButton = new TrackButton(10,0,vCenter);
            
            
            play = new PlayStopButton(1000, 0, main, true, false);
            stop = new PlayStopButton(1050, 0, main, false, false);
            restart = new PlayStopButton(1100, 0, main, false, true);

        }

        public void actionPerformed(ActionEvent e){

        }

       public void mousePressed(MouseEvent e){
           trackButton.testPress(e.getX(), e.getY());
           play.testPress(e.getX(), e.getY());
           stop.testPress(e.getX(), e.getY());
           restart.testPress(e.getX(), e.getY());
        }

        public void mouseReleased(MouseEvent e){
            trackButton.testRelease(e.getX(), e.getY());
            play.testRelease(e.getX(), e.getY());
            stop.testRelease(e.getX(), e.getY());
            restart.testRelease(e.getX(), e.getY());
        }

        public void mouseEntered(MouseEvent e){

        }

        public void mouseExited(MouseEvent e){

        }
        //end of mouse listener stuff


        //mouse motion stuff
        public void mouseDragged(MouseEvent e){
            trackButton.testDrag(e.getX(), e.getY());
            play.testDrag(e.getX(), e.getY());
            stop.testDrag(e.getX(), e.getY());
            restart.testDrag(e.getX(), e.getY());
        }

        public void mouseClicked(MouseEvent e){

        }

        public void mouseMoved(MouseEvent e){
        
        }
}

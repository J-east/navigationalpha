/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package navigationalpha;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Stroke;
import java.awt.BasicStroke;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import java.util.*;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.Transparency;
import java.awt.image.VolatileImage;
import java.awt.Image;
import java.net.*;
import javax.swing.ImageIcon;

/**
 *
 * @author johnevans
 */
public class Display2D extends JComponent implements MouseListener, MouseMotionListener{
    /*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

    VirtualMap virt;
    DataSet bag;
    ViewCenter vCenter;
    
    int mouseX = 0;
    int mouseY = 0;
    Vector2d vec;
    
    int mouseFireX = 0;
    int mouseFireY = 0;
    
    boolean w,a,s,d = false;
    
    boolean mousePressed = false;
    boolean mouseReleased = true;
    Dimension size = new Dimension(8000,8000);
    
    Hero hero;
    
    char key;
    boolean fire;
    
    int minx;
    int maxx;
    int miny;
    int maxy;
    
    private boolean dragged = false;
    Point start;
    
    
    BufferedImage img;
    VolatileImage vimg;
    
    boolean inZone = false;
    
    ArrayList<DisplayEnt> disEnts = new ArrayList();
    DisplayEnt heroEnt;
    BufferedImage img2;
    
    int counter = 0;
    int time = 0;
    
    public Display2D(DataSet b)throws IOException,URISyntaxException {
        super();
        addMouseListener(this);
	addMouseMotionListener(this);

        setSize(size);
        
        bag = b;
        hero = new Hero(this,bag);
        vCenter = new ViewCenter(hero,bag.maps.get(0).scaleX,bag.maps.get(0).scaleY);
        virt = new VirtualMap(bag,hero,this);
        /*
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsConfiguration gc = ge.getDefaultScreenDevice().getDefaultConfiguration();
        img2 = gc.createCompatibleImage(4832, 3628, Transparency.OPAQUE);
        img2.setAccelerationPriority(1);
        
        Graphics g3 =  img2.getGraphics();
        */
        try {
            img = ImageIO.read(this.getClass().getResource("44_72.png"));
        } catch (final IOException e) {
            e.printStackTrace();
        }
    }
    
    public void restart()throws IOException,URISyntaxException {
        hero = new Hero(this,bag);
        vCenter = new ViewCenter(hero,bag.maps.get(0).scaleX,bag.maps.get(0).scaleY);
        virt = new VirtualMap(bag,hero,this);
    }
    
    
    public void paintComponent(Graphics g) {
        
        //clear screen
        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(0, 0, getWidth(), getHeight());
        
        
        
        //paint maps
        ArrayList<DisplayMap> dm = virt.paintMaps(vCenter);
        for (int i = 0; i < dm.size(); i++){
            g.drawImage(img, dm.get(i).x, dm.get(i).y, this);
        }
        
            //this.reloadImage();
            counter++;
            if((counter%60) == 0){
                time++;
            }
        
        //ArrayList<DisplayEnt> det = virt.paintEntTrail(vCenter,0);
        //g.setColor(Color.YELLOW);
        //for (int i = 0; i < det.size(); i++){
          //  g.fillOval(det.get(i).x, det.get(i).y, 5,5);
        //}
        
        this.updateDisplayEnts();
        
        g.setColor(Color.RED);
        for (int i = 0; i < disEnts.size(); i++){
            g.fillOval(disEnts.get(i).x-5, disEnts.get(i).y-5, 10,10);
        }
        
        
        g.setColor(Color.magenta);
        if(!(heroEnt == null)){
            //Point[] pe = this.getPointer(heroEnt);
            g.fillOval(heroEnt.x-5, heroEnt.y-5,10,10);
            //g.drawLine(pe[1].x, pe[1].y, pe[2].x, pe[2].y);
            g.drawRect(heroEnt.x-7, heroEnt.y-7,14,14);
            g.drawRect(heroEnt.x-9, heroEnt.y-9,18,18);
            
        }
        Graphics2D g2 = (Graphics2D) g;
        
        Stroke stroke = new BasicStroke(2);
        g2.setStroke(stroke);
        
        g.setColor(Color.GRAY);
        g.fillRect(4009,3969,80,22);
        
        
        g.setColor(Color.yellow);
        g.drawRect(4008,3968,82,24);
        g.drawLine(4000, 3990, 4000,3998);
        g.drawLine(3990, 4000, 3998, 4000);
        
        
        g.drawOval(3990,3990,20,20);
        g.drawString("Lat: "+String.valueOf(String.format("%.6g%n", vCenter.gps.lat)), 4010, 3980);
        g.drawString("Lon:"+String.valueOf(String.format("%.6g%n", vCenter.gps.lon)), 4010, 3990);
        
        
        if(!vCenter.tied){
            g.drawOval(3855, 3855, 290, 290);
            g.drawString("5NM",3860,4000);
            g.drawOval(4000-290, 4000-290, 290*2, 290*2);
            g.drawString("10NM",4000-285,4000);
        }
        
        else{
            int ringx = virt.paintRings(vCenter,true);
            int ringy = virt.paintRings(vCenter, false);
            System.out.println("attemping to drawlatrings in display2D"+ringx);
            System.out.println("attemping to drawlonrings in display2D"+ringy);
            g.drawOval(ringx-145,  ringy-145, 290, 290);
            g.drawString("5NM",ringx-140,ringy);
            g.drawOval( ringx-290,  ringy-290, 290*2, 290*2);
            g.drawString("10NM",ringx-285,ringy);
        }
        
    }
    
    /*
    public void reloadImage(){
        
        if(counter%30 == 0){
            try {
                img1 = ImageIO.read(this.getClass().getResource("44_72.5.png"));
            } catch (final IOException e) {
                e.printStackTrace();
            }
        }
    }
    */
    
    //hadles the displayents
    public void updateDisplayEnts(){
        disEnts = virt.paintEnts(vCenter);
        heroEnt = virt.paintHero(vCenter);
    }
    
    
    
    public void render(){
        repaint();
    }
    
    public Dimension size(){
        return size;
    }
    
    public void addHero(Hero h){
        hero = h;
    }
    
    
    public void mouseClicked(MouseEvent e){
        mouseX = e.getX();
        mouseY = e.getY();
        
        for (int i = 0; i < disEnts.size(); i++){
            if (mouseX < disEnts.get(i).x + 20 && mouseX > disEnts.get(i).x - 20){
                if (mouseY < disEnts.get(i).y + 20 && mouseY > disEnts.get(i).y - 20){
                    vCenter.tie(disEnts.get(i).ent);
                }
            }
        }
        
        if (mouseX < heroEnt.x + 10 && mouseX > heroEnt.x - 10){
                if (mouseY < heroEnt.y + 10 && mouseY > heroEnt.y - 10){
                    vCenter.centerYou();
                }
        }
    }

    
    //points of the the arrow that represents ents
    //length aspect corresponds to speed
    //size corresponds to relative altitude
    public Point[] getPointer(DisplayEnt de,int d){
        Point[] pa = new Point[4];
        pa[0] = new Point(de.x,de.y);
        
        if(de.ent == null){
            Vector2d ve = new Vector2d(de.hero.vecs.get(0).x,de.hero.vecs.get(0).y);
            ve.normalize();

            pa[1] = new Point((int)(de.x+ve.x*3+ve.x*de.hero.velocity),(int)(de.y+ve.y*3+ve.y*de.hero.velocity));

            Vector2d tempV2 = this.rotate(Math.PI/3, ve);

            Vector2d tempV3 = this.rotate(-Math.PI/3, ve);

            pa[2] = new Point((int)(de.x+tempV2.x*3),(int)(tempV2.y+ve.y*3));
            pa[3] = new Point((int)(de.x+tempV3.x*3),(int)(tempV3.y+ve.y*3));

            return pa;
        
        }
        
        Vector2d ve = new Vector2d(de.ent.vecs.get(0).x,de.ent.vecs.get(0).y);
        ve.normalize();

        pa[1] = new Point((int)(de.x+ve.x*3+ve.x*de.ent.velocity),(int)(de.y+ve.y*3+ve.y*de.ent.velocity));
        
        Vector2d tempV2 = this.rotate(Math.PI/3, ve);
        
        Vector2d tempV3 = this.rotate(-Math.PI/3, ve);
        
        pa[2] = new Point((int)(de.x+tempV2.x*3),(int)(tempV2.y+ve.y*3));
        pa[3] = new Point((int)(de.x+tempV3.x*3),(int)(tempV3.y+ve.y*3));
        
        return pa;
    }
    
    public Vector2d rotate(double n,Vector2d t){
        double rx = (t.x * Math.cos(n)) - (t.y * Math.sin(n));
        double ry = (t.x * Math.sin(n)) + (t.y * Math.cos(n));
        return new Vector2d(rx,ry);
    }
    
    public void mousePressed(MouseEvent e){
        mouseX = e.getX();
        mouseY = e.getY();


        mousePressed = true;
        mouseReleased = false;
    }

    public void mouseReleased(MouseEvent e){
        dragged = false;
        vCenter.dragged = false;
    }

    public void mouseEntered(MouseEvent e){
        inZone = true;
    }

    public void mouseExited(MouseEvent e){
        inZone = false;
    }
    //end of mouse listener stuff


    //mouse motion stuff
    public void mouseDragged(MouseEvent e){
        if(!dragged){
            dragged = true;
            start = new Point(e.getX(),e.getY());
            vCenter.drag(0,0);
        }
        
        vCenter.drag(start.x-e.getX(), start.y-e.getY());
    }



    public void mouseMoved(MouseEvent e){
        mouseX = e.getX();
        mouseY = e.getY();
    }

     
     public void actionPerformed(ActionEvent e) {
        //Clear the text components.
        
         
        //Return the focus to the typing area.
    }
     
     public void Update(double delta,int c){
         virt.updatePos(delta,c);
         
         
     }
         
}
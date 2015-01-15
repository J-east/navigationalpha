/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package navigationalpha;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import java.util.*;
import javax.swing.JScrollPane;

/**
 *
 * @author johnevans
 */
public class SideBar extends JComponent implements ActionListener,MouseListener,MouseMotionListener{
    
    DataSet bag;
    Hero hero;
    StatePanel state;
    Display2D can;
    VirtualMap virt;
    ViewCenter vCenter;
    JScrollPane jScroll;
    int counter;
    
    
    ArrayList<EntWindow> entWindows;
    
    boolean dragged;
    int start;
    int dif;
    Point jPos;
    int startV;
    boolean first = true;
    
    
    
    int mouseY;
    
    
    
    public SideBar(DataSet b,Display2D c){
        super();
                
        bag = b;
        can = c;
        
        entWindows = new ArrayList();
        
        this.setSize(200, 2000);
        
        addMouseListener(this);
	addMouseMotionListener(this);
    }
    
    
    public void addJScroll(JScrollPane js){
        jScroll = js;
        jScroll.getViewport().setViewPosition(new Point(0,0));
    }
    
    
    public void paintComponent(Graphics g) {
        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(0, 0, getWidth(), getHeight());
        
        //draw icons 
        int height = 0;
        for (int i = 0; i < entWindows.size(); i++){
                g.drawImage(entWindows.get(i).getImage(), 0, height, this);
                g.drawImage(entWindows.get(i).trackButton.getImage(), 5, height+15, this);
                entWindows.get(i).trackButton.setLoc(5, height+15);
                entWindows.get(i).y = height;
                height += entWindows.get(i).getHeight();
            
        }
        
        //operations to perform if top
    }
    
    
    public void actionPerformed(ActionEvent e){
        
    }
    
    public void update(){
        if(dragged){
            if(mouseY != start){
                jPos.y = startV + (start - mouseY);
            }

            if(jPos.y < 0){
                System.out.println("jPos: "+jPos.y+" converted to zero in sidebar");
                jPos.y = 0;
            }

            if(jPos.y > 700){
                jPos.y = 700;
            }
            
            
            jScroll.getViewport().setViewPosition(jPos);
            
        }
        
        counter++;
        
        if(first){
            for (int i = 0; i < virt.ents.size(); i++){
                this.entWindows.add(new EntWindow(virt.ents.get(i),can,hero,vCenter,this));
            }
            first = false;
        }
        
        if(counter > 360){
            this.orderEnts();
            counter = 0;
        }
        
        repaint();
    }
    
    public void addVirt(VirtualMap v){
            virt = v;
            hero = virt.hero;
            vCenter = can.vCenter;
        }
    
    public void orderEnts(){
        if(entWindows.size()>1)
            Collections.sort(entWindows);
    }
    
    
    public void mousePressed(MouseEvent e){
       jPos = jScroll.getViewport().getViewPosition();
        if(!dragged){
            dragged = true;
            startV = jScroll.getViewport().getViewPosition().y;
            start = e.getY();
        }
        
        for (int i = 0; i < entWindows.size(); i++){
            entWindows.get(i).trackButton.testPress(e.getX(), e.getY());
        }
    }

        
    public void mouseReleased(MouseEvent e){
        dragged = false;
        for (int i = 0; i < entWindows.size(); i++){
            entWindows.get(i).trackButton.testRelease(e.getX(), e.getY());
        }
    }

    public void mouseEntered(MouseEvent e){
    }

    public void mouseExited(MouseEvent e){

    }
    //end of mouse listener stuff


    //mouse motion stuff
    public void mouseDragged(MouseEvent e){
        mouseY = e.getY();
    }

    public void mouseClicked(MouseEvent e){
        for (int i = 0; i < entWindows.size(); i++){
            entWindows.get(i).testClick(e.getX(), e.getY());
        }
    }

    public void mouseMoved(MouseEvent e){
        mouseY = e.getY();
        
        for (int i = 0; i < entWindows.size(); i++){
            entWindows.get(i).trackButton.testDrag(e.getX(), e.getY());
        }
    }
    
    
}

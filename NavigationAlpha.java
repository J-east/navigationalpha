/*
 * 
 *  Overview:   two visual classes
 *              one scael class (virtual map)
 *              other classes run on different thread
 */
package navigationalpha;

import java.awt.*;
import java.util.Random;
import javax.swing.JPanel;


import javax.swing.*;
import javax.swing.border.*;
import javax.swing.JScrollBar;

/**
 *
 * @author jakepevans
 */

import javax.swing.JFrame;
import java.io.FileInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.*;

import java.awt.event.*;
import java.net.*;


public class NavigationAlpha extends JFrame{
    
    DataSet bag;
    SideBarHolder side;
    TopBarHolder top;
    Display2D can;
    JScrollPane jScroll;
    
    int counter1 = 0;
    int time = 0;

    boolean first = true;
    
    Hero hero;
    
    int lastFpsTime;
    int lastSecond;
    int tenSecs;
    int fps;
    
    int key = 0;
    
    int music = 0;
    
    boolean updateTrue = true;
    
   public static void main(String[] args)throws IOException, URISyntaxException{
       DisplayMode dm = new DisplayMode(800,600,16,DisplayMode.REFRESH_RATE_UNKNOWN);
       
       NavigationAlpha m = new NavigationAlpha();
    }
   
   public NavigationAlpha()throws IOException, URISyntaxException  {
       
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	
	this.setSize(1200,1000);
	
        
        Container content = getContentPane();
	content.setLayout (new BorderLayout());
        
        
        bag = new DataSet("vfrCharts.csv");
        
        
        can = new Display2D(bag);
        side = new SideBarHolder(bag,can);
        top = new TopBarHolder(bag,can,this); 
        
        
        can.setPreferredSize(new Dimension(8000,8000));
        jScroll = new JScrollPane(can,
        JScrollPane.VERTICAL_SCROLLBAR_NEVER,
        JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        
        jScroll.setPreferredSize(new Dimension(1000,1000));
        side.setPreferredSize(new Dimension(200,1000));
        top.setPreferredSize(new Dimension(1000,50));
        
        hero = new Hero(can,bag);
                
        can.addHero(hero);
        
        
        
        content.add(jScroll , BorderLayout.CENTER);
        Point pt = new Point();
        pt.x = 3450;
        pt.y = 3650;
        jScroll.getViewport().setViewPosition(pt);
        
        content.add(top , BorderLayout.NORTH);
        content.add(side , BorderLayout.EAST);

        
        /*
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() { 
                jScroll.getVerticalScrollBar().setValue(1300);
                jScroll.getHorizontalScrollBar().setValue(700);
            }
        });
	*/
        this.setVisible(true);

        
        ActionMap actionMap = jScroll.getActionMap();
        InputMap inputMap = jScroll.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);

        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0), "Space bar");
        actionMap.put("Space bar", new AbstractAction() {
             
        public void actionPerformed(ActionEvent arg0) {
               
               System.out.println("pressed");
               
            }
        });
        
        
        
        this.gameLoop();
        
        
   }
   
   //takes the code from the text file and converts into an arraylist of instructions
   //includes every part of a classes definition, except for the map data
   //that will be initiated for each room
   
    public void gameLoop()
    {
       long lastLoopTime = System.nanoTime();
       final int TARGET_FPS = 60;
       final long OPTIMAL_TIME = 1000000000 / TARGET_FPS;  
       boolean gameRunning = true;

       int counter = 0;
       // keep looping round til the game ends
       while (gameRunning)
       {
           boolean sec = false;
           boolean sixth = false;
          // work out how long its been since the last update, this
          // will be used to calculate how far the entities should
          // move this loop
          long now = System.nanoTime();
          long updateLength = now - lastLoopTime;
          lastLoopTime = now;
          double delta = updateLength / ((double)OPTIMAL_TIME);

          // update the frame counter
          lastFpsTime += updateLength;
          lastSecond += updateLength;
          
          fps++;

          // update our FPS counter if a second has passed since
          // we last recorded
          if (lastFpsTime >= 1000000000)
          {
             System.out.println("(FPS: "+fps+")");
             fps = 0;
             sec = true;
             lastFpsTime = 0;
          }
          
          if (lastSecond >= (1000000000))
          {
              System.out.println("counter+1 "+counter);
             tenSecs++; 
                counter++;
             lastSecond = 0;
          }

          // update the game logic
          doGameUpdates(updateLength,counter);
          
          
          if(music == 160){
              music = 0;
              
          }
          
          
          //second timer
          sec = false;
          sixth = false;
          // draw everyting
          render();
          if(first){
              first = false;
          }
          try{
              Thread.sleep( (lastLoopTime-System.nanoTime() + OPTIMAL_TIME)/1000000);
          }catch(Exception e){e.printStackTrace();}
       }
    }

    private void doGameUpdates(double delta,int counter)
    {
        if(updateTrue){

            can.requestFocus();
            can.Update(delta,counter);

            side.requestFocus();
            side.update();
            side.requestFocus();
            
            //handle key inputs
            key = 0;
            
            counter1++;
                if((counter1%60) == 0){
                    time++;
                }    
        }
        
        top.update();
    }
    
    private void render(){
        can.render();
    }
    
    public void play(){
        updateTrue = true;
    }
    
    public void stop(){
        updateTrue = false;
    }
    
    public void restart()throws IOException,URISyntaxException {
        can.restart();
        side.can = can;
        top.can = can;
        
    }
}
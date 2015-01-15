/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package navigationalpha;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.LineBorder;

/**
 *
 * @author johnevans
 */
public class TopBarHolder extends JPanel{
    
    DataSet bag;
    Display2D can;
    SideBar sideBar;
    VirtualMap virt;
    boolean top;
    TopBar topBar;
    JScrollPane jScroll;
    NavigationAlpha main;
    
    
    public TopBarHolder(DataSet b, Display2D d, NavigationAlpha m){
        bag = b;
        can = d;
        main = m;
        
        this.setLayout(new BorderLayout());
        this.setBorder(new LineBorder(Color.BLUE,2));
        
        
            topBar = new TopBar(bag,can,main);
            this.add(topBar,BorderLayout.CENTER);
        
            
        topBar.addVirt(can.virt);
    }
    
    public void update(){
        topBar.update();
    }
    
    public void addVirt(VirtualMap v){
        virt = v;
    }
}
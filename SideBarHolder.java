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
public class SideBarHolder extends JPanel{
    
    DataSet bag;
    Display2D can;
    SideBar sideBar;
    VirtualMap virt;
    boolean top;
    TopBar topBar;
    JScrollPane jScroll;
    
    
    public SideBarHolder(DataSet b, Display2D d){
        bag = b;
        can = d;

        
        this.setLayout(new BorderLayout());
        this.setBorder(new LineBorder(Color.BLUE,2));


            sideBar = new SideBar(bag,can);
            jScroll = new JScrollPane(sideBar,
            JScrollPane.VERTICAL_SCROLLBAR_NEVER,
            JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
            this.add(jScroll,BorderLayout.CENTER);
            
            sideBar.addJScroll(jScroll);
            
            sideBar.addVirt(can.virt);
            
    }
    
    
    public void update(){
        sideBar.update();
    }
}
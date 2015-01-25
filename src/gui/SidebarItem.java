package gui;

import main.Game;
import static gui.StrandedApplet.*;

public class SidebarItem {
    
    protected String name;
    protected StrandedApplet applet;
    protected Game game;
    
    public SidebarItem(String name, StrandedApplet applet, Game game) {
        this.name = name;
        this.applet = applet;
        this.game = game;
    }
    
    public String getName() {
        return name;
    }
    
    /**
     * Draws the sidebar in the sidebar pane
     * @precondition The applet has translated to the top-left corner of the sidebar
     */
    public void draw() {
        //draw name of the sidebar
        applet.textAlign(applet.CENTER, applet.TOP);
        applet.textSize(24);
        applet.fill(0, 255, 0);
        applet.text(name, SIDEBAR_WIDTH / 2, 10);
        
        //prepare implementing classes for drawing
        applet.textSize(18);
        applet.textAlign(applet.LEFT, applet.TOP);
    }
}
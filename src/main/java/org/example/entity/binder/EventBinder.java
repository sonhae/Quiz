package org.example.entity.binder;

import org.bukkit.event.Event;
import org.bukkit.inventory.Inventory;
import org.example.entity.GUI;
import org.example.entity.MenuItem;

public abstract class EventBinder {
    protected GUI gui;
    public EventBinder(GUI gui) {
        this.gui = gui;
    }
    public abstract void handle(Event event, MenuItem source);

    public boolean checkGUI(Inventory inv){
        return gui.getInv().equals(inv);
    }
    public boolean equalsMenuItem(int slot, MenuItem source){
        return gui.getItems()[slot].equals(source);
    }
}

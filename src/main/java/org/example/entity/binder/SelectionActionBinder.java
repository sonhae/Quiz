package org.example.entity.binder;

import org.bukkit.event.Event;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.example.entity.GUI;
import org.example.entity.MenuItem;
import org.example.entity.RadioMenuItem;

import java.util.function.BiConsumer;

public class SelectionActionBinder extends EventBinder {

    private BiConsumer<InventoryClickEvent, RadioMenuItem> executor;

    public SelectionActionBinder(GUI igui) {
        super(igui);
    }

    public SelectionActionBinder onSelect(BiConsumer<InventoryClickEvent, RadioMenuItem> executor) {
        this.executor = executor;
        return this;
    }

    @Override
    public void handle(Event event, MenuItem source) {
        if (event instanceof InventoryClickEvent && checkGUI(((InventoryClickEvent) event).getInventory()) && equalsMenuItem(((InventoryClickEvent) event).getRawSlot(),source)) {
            RadioMenuItem radio = (RadioMenuItem) source;
            radio.select();
            gui.update();
            if (executor != null) {
                executor.accept((InventoryClickEvent) event, (RadioMenuItem) source);
            }
        }
    }

}

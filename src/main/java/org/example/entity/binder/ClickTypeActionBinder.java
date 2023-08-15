package org.example.entity.binder;

import org.bukkit.event.Event;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.example.entity.GUI;
import org.example.entity.MenuItem;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class ClickTypeActionBinder extends EventBinder {
    private final Map<ClickType, Consumer<InventoryClickEvent>> actions = new HashMap<>();

    public ClickTypeActionBinder(GUI gui) {
        super(gui);
    }

    public ClickTypeActionBinder onLeftClick(Consumer<InventoryClickEvent> action) {
        actions.put(ClickType.LEFT, action);
        return this;
    }

    public ClickTypeActionBinder onRightClick(Consumer<InventoryClickEvent> action) {
        actions.put(ClickType.RIGHT, action);
        return this;
    }


    @Override
    public void handle(Event event, MenuItem source) {
        if (event instanceof InventoryClickEvent && checkGUI(((InventoryClickEvent) event).getInventory()) && equalsMenuItem(((InventoryClickEvent) event).getRawSlot(),source)) {
            Consumer<InventoryClickEvent> action = actions.get(((InventoryClickEvent) event).getClick());
            if (action != null) {
                action.accept((InventoryClickEvent) event);

            }
        }
    }
}
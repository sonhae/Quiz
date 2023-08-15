package org.example.entity;

import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;
import org.example.Main;
import org.example.entity.binder.EventBinder;

import java.util.*;

public class MenuItem {

    private final int id;
    private ItemStack item;

    private GUI gui;
    private Map<Class<? extends Event>, List<EventBinder>> binders = new HashMap<>();

    public MenuItem(int id, ItemStack item) {
        this.id = id;
        this.item = item;
    }


    public void bindEvent(Class<? extends Event> eventType, EventBinder binder) {
        binders.computeIfAbsent(eventType, k -> new ArrayList<>()).add(binder);
        Main.getInstance().getGuiManager().registerEvent(eventType, this::triggerAction);
    }

    public void triggerAction(Event event) {
        List<EventBinder> eventBinders = binders.get(event.getClass());
        if (eventBinders != null) {
            for (EventBinder binder : eventBinders) {
                binder.handle(event, this);
            }
        }
    }

    public int getId() {
        return id;
    }

    public ItemStack getItem() {
        return item;
    }

    public void setItem(ItemStack item) {
        this.item = item;
    }


}

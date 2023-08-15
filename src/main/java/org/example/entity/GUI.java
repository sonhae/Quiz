package org.example.entity;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.stream.IntStream;

public abstract class GUI implements Listener {
    protected Inventory inv;
    private Player handler;

    protected final int size;

    private boolean visible;
    private MenuItem[] items;

    public GUI(Inventory inv, Player handler, int size) {
        this.inv = inv;
        this.handler = handler;
        this.size = size;
        this.items = new MenuItem[size];
        visible = true;
    }


    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public void setItems(MenuItem[] items) {
        this.items = items;
    }

    public Player getHandler() {
        return handler;
    }

    public MenuItem[] getItems() {
        return items;
    }

    public Inventory getInv() {
        return inv;
    }

    protected abstract void initialize();

    protected void updateInventory(int index, ItemStack itemStack) {
        this.inv.setItem(index, itemStack);
    }

    public boolean update() {
        IntStream.range(0, getItems().length).forEach(i -> {
            if (getItems()[i] != null) {
                updateInventory(i, getItems()[i].getItem());
            }
        });
        return false;
    }

    public void handleEvent(Event e) {
        if (e instanceof InventoryClickEvent && ((InventoryClickEvent) e).getClickedInventory().equals(getInv())) {
            ((InventoryClickEvent) e).setCancelled(true);
        }
        Arrays.stream(items).forEach(i -> {
            i.triggerAction(e);
        });
    }

    ;
}

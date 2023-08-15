package org.example.util;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.example.entity.GUI;
import org.example.entity.MenuItem;
import org.example.entity.binder.ClickTypeActionBinder;

import java.util.function.Consumer;

public class MenuBuilder {
    protected int id;
    protected ItemStack item;
    protected GUI gui;

    public MenuBuilder(GUI gui) {
        this.gui = gui;
        clickTypeActionBinder = new ClickTypeActionBinder(gui);
    }

    private ClickTypeActionBinder clickTypeActionBinder;

    public MenuBuilder onLeftClick(Consumer<InventoryClickEvent> action) {
        clickTypeActionBinder.onLeftClick(action);
        return this;
    }

    public MenuBuilder onRightClick(Consumer<InventoryClickEvent> action) {
        clickTypeActionBinder.onRightClick(action);
        return this;
    }
    public MenuBuilder item(ItemStack item) {
        this.item = item;
        return this;
    }

    public MenuBuilder id(int id) {
        this.id = id;
        return this;
    }


    public MenuItem make() {
        MenuItem menuItem = new MenuItem(id, item);
        menuItem.bindEvent(InventoryClickEvent.class, clickTypeActionBinder);

        return menuItem;

    }

}

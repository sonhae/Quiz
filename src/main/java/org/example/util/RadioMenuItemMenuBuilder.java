package org.example.util;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.example.entity.GUI;
import org.example.entity.RadioMenuItem;
import org.example.entity.binder.SelectionActionBinder;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class RadioMenuItemMenuBuilder extends MenuBuilder {
    private ItemStack deselectedItem;
    private List<RadioMenuItem> group = new ArrayList<>();


    private final SelectionActionBinder binder;

    public RadioMenuItemMenuBuilder(GUI gui) {
        super(gui);
        binder =  new SelectionActionBinder(gui);
    }

    public RadioMenuItemMenuBuilder deselectedItem(ItemStack deselectedItem) {
        this.deselectedItem = deselectedItem;
        return this;
    }

    @Override
    public RadioMenuItemMenuBuilder item(ItemStack item) {
        return (RadioMenuItemMenuBuilder) super.item(item);
    }

    @Override
    public RadioMenuItemMenuBuilder id(int id) {
        return (RadioMenuItemMenuBuilder) super.id(id);
    }

    public RadioMenuItemMenuBuilder addItem() {
        RadioMenuItem radioItem = new RadioMenuItem(id, item, deselectedItem, group);
        radioItem.bindEvent(InventoryClickEvent.class, binder);
        group.add(radioItem);
        return this;
    }


    public RadioMenuItemMenuBuilder onSelect(BiConsumer<InventoryClickEvent, RadioMenuItem> action) {
        binder.onSelect(action);
        return this;
    }

    @Override
    public RadioMenuItemMenuBuilder onLeftClick(Consumer<InventoryClickEvent> action) {
        return (RadioMenuItemMenuBuilder) super.onLeftClick(action);
    }

    @Override
    public RadioMenuItemMenuBuilder onRightClick(Consumer<InventoryClickEvent> action) {
        return (RadioMenuItemMenuBuilder) super.onRightClick(action);
    }

    @Override
    public RadioMenuItem make() {
        if (group.isEmpty()) {
            throw new IllegalStateException("No items added to the group.");
        }
        return group.get(0);
    }
}

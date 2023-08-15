package org.example.entity;

import org.bukkit.inventory.ItemStack;

import java.util.*;
import java.util.stream.IntStream;

public class RadioMenuItem extends MenuItem {

    private List<RadioMenuItem> group;
    private final ItemStack deselectedItem;
    private boolean selected = false;

    public boolean isSelected() {
        return selected;
    }

    public void select(){
        group.forEach(r ->{
            if (r != this) {
                r.deSelect();
            }
            else{
                this.selected = true;
            }

        });
    }

    public void deSelect() {
        this.selected = false;
    }
    public OptionalInt selectedIndex(){
        return IntStream.range(0,group.size())
                .filter(i -> group.get(i).isSelected())
                .findFirst();
    }
    public RadioMenuItem(int id, ItemStack item, ItemStack deselectedItem, List<RadioMenuItem> group) {
        super(id, item);
        this.deselectedItem = deselectedItem;
        this.group = group;
    }

    public List<RadioMenuItem> getGroup() {
        return group;
    }

    public void setGroup(List<RadioMenuItem> group) {
        this.group = group;
    }


    @Override
    public ItemStack getItem() {
        return selected ? super.getItem() : deselectedItem;
    }


}

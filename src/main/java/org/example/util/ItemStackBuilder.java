package org.example.util;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Consumer;
import java.util.List;

public class ItemStackBuilder {
    private final ItemStack itemStack;

    public ItemStackBuilder(Material material) {
        this.itemStack = new ItemStack(material);
    }

    public ItemStackBuilder(ItemStack item){
        this.itemStack = item;
    }

    private ItemStackBuilder updateStack(Consumer<ItemStack> item) {
        item.accept(this.itemStack);
        return this;
    }

    private ItemStackBuilder updateMeta(Consumer<ItemMeta> meta) {
        ItemMeta im = this.itemStack.getItemMeta();
        if (im != null) {
            meta.accept(im);
            this.itemStack.setItemMeta(im);
        }
        return this;
    }


    private String translateColor(char c, String s) {
        return ChatColor.translateAlternateColorCodes(c, s);
    }

    public ItemStackBuilder name(String s) {
      return  updateMeta(m -> {
            m.setDisplayName(translateColor('&', s));
        });
    }

    public ItemStackBuilder lore(String s) {
        return updateMeta(m -> {
            List<String> lore = m.getLore() != null ? m.getLore() : new ArrayList<>();
            lore.add(translateColor('&',s));
            m.setLore(lore);
        });
    }
    public ItemStackBuilder lore(Collection<String> s){
        s.forEach(this::lore);
        return this;
    }

    public ItemStackBuilder amount(int amount) {
      return  updateStack(i ->{
            i.setAmount(amount);
        });

    }

    public ItemStack make(){
        return itemStack;
    }
}

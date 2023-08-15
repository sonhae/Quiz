package org.example.gui;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventException;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.plugin.EventExecutor;
import org.example.Main;
import org.example.entity.GUI;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Consumer;

public class GUIManager implements Listener {
    private Map<Player, GUI> guis = new LinkedHashMap<>();


    public Map<Player, GUI> getGuis() {
        return guis;
    }

    public void registerEvent(Class<? extends Event> eventType, Consumer<Event> action) {
        Bukkit.getPluginManager().registerEvent(eventType, new Listener() {
        }, EventPriority.NORMAL, new EventExecutor() {
            @Override
            public void execute(@NotNull Listener listener, @NotNull Event event) throws EventException {
                action.accept(event);
            }
        }, Main.getInstance());
    }

    public void openGUI(Player p, GUI g) {
        guis.put(p, g);
        p.openInventory(g.getInv());

    }

    public void closeGUI(Player p) {
        if (isPlayerOpenGUI(p)) {
            if (getOpenedGUI(p).isVisible()) {
                guis.remove(p);
            }
            p.closeInventory();
        }
    }

    public boolean isPlayerOpenGUI(Player p){
        return getOpenedGUI(p) != null && p.getOpenInventory().getTopInventory() == getOpenedGUI(p).getInv();
    }
    public GUI getOpenedGUI(Player p) {
        if (guis.containsKey(p)) {
            return guis.get(p);
        }
        return null;
    }
}

package com.github.alekseyvideman.noadvancements;

import com.destroystokyo.paper.event.player.PlayerAdvancementCriterionGrantEvent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TranslatableComponent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerAdvancementDoneEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class NoAdvancements extends JavaPlugin implements Listener {

    @EventHandler
    public void onCriteriaDone(PlayerAdvancementCriterionGrantEvent e) {
        if (!("test".equals(e.getPlayer().getWorld().getName()))) return;
        
        e.setCancelled(true);
    }
    
    @EventHandler
    public void onFullTaskDone(PlayerAdvancementDoneEvent e) {
        
        var player = e.getPlayer();
        var display = e.getAdvancement().getDisplay();
        if (display == null) return;

        var adTitle = display.title().color(NamedTextColor.GREEN);

        player.sendMessage(Component.text("Вы получили достижение «")
            .append(adTitle)
            .append(Component.text("»"))
                .hoverEvent(((TranslatableComponent) display.description())));

        e.message(null); // Отменил отправку сообщения
    }

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
    }

//    private void processConfig() {
//        File file = new File(getDataFolder(), "config.yml");
//        if (file.exists()) {
//            config = YamlConfiguration.loadConfiguration(file);
//        }
//    }
}

package com.github.alekseyvideman.noadvancements;

import com.destroystokyo.paper.event.player.PlayerAdvancementCriterionGrantEvent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerAdvancementDoneEvent;

public class AdvancementListener implements Listener {
    private final NoAdvancements pl;

    public AdvancementListener(NoAdvancements pl) {
        this.pl = pl;
    }

    @EventHandler
    public void saveAdvancement(PlayerAdvancementCriterionGrantEvent e) {
        var playerWorld = e.getPlayer().getWorld().getName();
        var blockedWorlds = pl.getConfig().getStringList("blocked-worlds");
        for (var world : blockedWorlds) {
            if (!(world.equals(playerWorld))) {
                return;
            }
        }
        e.setCancelled(true);
    }

    @EventHandler
    public void broadcastAdvancement(PlayerAdvancementDoneEvent e) {
        var doBroadcast = pl.getConfig().getBoolean("broadcast-advancement-message");
        if (doBroadcast) {
            return;
        }

        var player = e.getPlayer();
        var advancement = e.getAdvancement();
        var firstPartResultMessage = pl.getConfig().getString("advancement-message");
        var display = advancement.displayName();
        var resultMessage = Component.text(firstPartResultMessage).append(display);
                
        player.sendMessage(resultMessage);
        e.message(null); // Cancel vanilla message sending
    }
}

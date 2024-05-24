package com.github.alekseyvideman.noadvancements;

import com.destroystokyo.paper.event.player.PlayerAdvancementCriterionGrantEvent;
import net.kyori.adventure.text.Component;
import org.bukkit.advancement.Advancement;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerAdvancementDoneEvent;

import java.util.Random;

public class AdvancementListener implements Listener {

    private final NoAdvancements plugin;

    public AdvancementListener(NoAdvancements plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void saveAdvancement(PlayerAdvancementCriterionGrantEvent e) {
        var playerWorld = e.getPlayer().getWorld().getName();
        var blockedWorlds = plugin.getConfig().getStringList("blocked-worlds");
        for (var world : blockedWorlds) {
            if (!(world.equals(playerWorld))) {
                return;
            }
        }
        e.setCancelled(true);
    }

    @EventHandler
    public void broadcastAdvancement(PlayerAdvancementDoneEvent e) {
        var broadcastVanillaMessage = plugin.getConfig().getBoolean("broadcast-advancement-message");
        if (broadcastVanillaMessage) {
            return;
        }
        if (e.getAdvancement().getDisplay() == null) {
            return;
        }
        e.message(null); // Cancel vanilla message sending

        var player = e.getPlayer();
        player.sendMessage(getAdvancementMessage(e.getAdvancement()));
    }

    private Component getAdvancementMessage(Advancement advancement) {
        System.out.println(advancement.getKey());
        var advancementMessages = plugin.getConfig().getStringList("advancement-message");
        var advancementMessage = advancementMessages.get(new Random().nextInt(advancementMessages.size()));

        return Component.text()
                .append(Component.text(advancementMessage))
                .append(Component.space())
                .append(advancement.getDisplay().displayName())
                .build();
    }
}

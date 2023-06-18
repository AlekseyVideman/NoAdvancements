package com.github.alekseyvideman.noadvancements;

import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class NoAdvancements extends JavaPlugin {

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new AdvancementListener(this), this);
        setupConfig();
    }

    private void setupConfig() {
        File file = new File(getDataFolder(), "config.yml");
        if (!file.exists()) {
            saveDefaultConfig();
        }
    }
}

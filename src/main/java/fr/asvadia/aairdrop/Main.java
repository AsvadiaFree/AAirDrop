package fr.asvadia.aairdrop;

import fr.asvadia.aairdrop.commands.AirDropCommands;
import fr.asvadia.aairdrop.utils.AirDropListeners;
import fr.asvadia.aairdrop.utils.file.FileManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;

public class Main extends JavaPlugin {
    private static Main instance;

    @Override
    public void onLoad() {
        instance = this;
        saveDefaultConfig();
        try {
            FileManager.init();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onEnable() {
        getCommand("aairdrop").setExecutor(new AirDropCommands());
        getServer().getPluginManager().registerEvents(new AirDropListeners(), this);
    }

    public static Main getInstance() {
        return instance;
    }
}

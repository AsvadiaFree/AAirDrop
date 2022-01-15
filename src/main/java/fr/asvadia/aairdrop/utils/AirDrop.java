package fr.asvadia.aairdrop.utils;

import fr.asvadia.aairdrop.Main;
import fr.asvadia.aairdrop.utils.file.FileManager;
import fr.asvadia.aairdrop.utils.file.Files;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class AirDrop {
    public static final HashMap<Location, List<String>> chests = new HashMap<>();
    public static final HashMap<Location, List<ItemStack>> chestItems = new HashMap<>();

    public static void play() {
        YamlConfiguration lang = FileManager.getValues().get(Files.Lang);
        YamlConfiguration config = FileManager.getValues().get(Files.Config);
        Random random = new Random();
        List<Location> locations = (List<Location>) config.getList("Locations");
        assert locations != null;
        List<String> loots = new ArrayList<>(config.getConfigurationSection("Loots").getKeys(false));

        new BukkitRunnable() {
            int n = config.getInt("Timer");

            @Override
            public void run() {
                if (lang.contains("Broadcast." + n))
                    Bukkit.broadcastMessage(lang.getString("Broadcast." + n));

                if (n <= 0) {
                    locations.forEach(location -> {
                        String loot = loots.get(random.nextInt(loots.size()));

                        List<ItemStack> items = (List<ItemStack>) config.getList("Loots." + loot + ".Items");
                        assert items != null;

                        location.getBlock().setType(Material.CHEST);
                        chests.put(location.getBlock().getLocation(), config.getStringList("Loots." + loot + ".Commands"));
                        chestItems.put(location.getBlock().getLocation(), items);

                    });
                    this.cancel();
                }
                n--;
            }
        }.runTaskTimer(Main.getInstance(), 0, 20);


    }

}

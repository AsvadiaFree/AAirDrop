package fr.asvadia.aairdrop.utils;

import fr.asvadia.aairdrop.utils.file.FileManager;
import fr.asvadia.aairdrop.utils.file.Files;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import me.clip.placeholderapi.PlaceholderAPI;

public class AirDropListeners implements Listener {
    @EventHandler
    private void onOpen(PlayerInteractEvent event) {
        if ((event.getAction() == Action.RIGHT_CLICK_BLOCK
                || event.getAction() == Action.LEFT_CLICK_BLOCK)
                && event.getClickedBlock() != null
                && AirDrop.chests.containsKey(event.getClickedBlock().getLocation())) {
            for (ItemStack item : AirDrop.chestItems.get(event.getClickedBlock().getLocation()))
                if (event.getPlayer().getInventory().getContents().length >= event.getPlayer().getInventory().getSize())
                    event.getPlayer().getWorld().dropItemNaturally(event.getClickedBlock().getLocation(), item);
                else
                    event.getPlayer().getInventory().addItem(item);

            AirDrop.chests.get(event.getClickedBlock().getLocation()).forEach(s ->
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), PlaceholderAPI.setPlaceholders(event.getPlayer(), s)));
            event.getClickedBlock().setType(Material.AIR);

            YamlConfiguration lang = FileManager.getValues().get(Files.Lang);

            event.getPlayer().sendMessage(lang.getString("TakeChest"));
            Bukkit.broadcastMessage(PlaceholderAPI.setPlaceholders(event.getPlayer(), lang.getString("PlayerHaveTakeChest")));
        }
    }
}

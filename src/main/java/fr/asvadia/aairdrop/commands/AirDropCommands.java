package fr.asvadia.aairdrop.commands;

import fr.asvadia.aairdrop.utils.AirDrop;
import fr.asvadia.aairdrop.utils.file.FileManager;
import fr.asvadia.aairdrop.utils.file.Files;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class AirDropCommands implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender p, Command cmd, String label, String[] args) {
        if (p.hasPermission("aairdrop")) {
            if (args.length == 0) return false;

            YamlConfiguration config = FileManager.getValues().get(Files.Config);
            YamlConfiguration lang = FileManager.getValues().get(Files.Lang);

            switch (args[0].toLowerCase()) {
                case "play" -> {
                    p.sendMessage(lang.getString("Play"));
                    AirDrop.play();
                }

                case "getitem" -> {
                    if (p instanceof Player player) {
                        List<ItemStack> itemStacks = (List<ItemStack>) config.getList("Items");
                        if (itemStacks == null)
                            itemStacks = new ArrayList<>();
                        itemStacks.add(player.getInventory().getItemInMainHand());
                        config.set("Items", itemStacks);
                        FileManager.save(Files.Config);
                        player.sendMessage(lang.getString("GetItem"));
                    }
                }

                case "addlocation" -> {
                    if (p instanceof Player player) {
                        List<Location> locations = (List<Location>) config.getList("Locations");
                        if (locations == null)
                            locations = new ArrayList<>();
                        locations.add(player.getLocation().getBlock().getLocation());
                        config.set("Locations", locations);
                        FileManager.save(Files.Config);
                        player.sendMessage(lang.getString("AddLocation"));
                    }
                }
            }
        }
        return false;
    }
}

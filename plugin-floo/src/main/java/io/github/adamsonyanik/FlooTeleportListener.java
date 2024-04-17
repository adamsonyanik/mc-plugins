package io.github.adamsonyanik;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

public class FlooTeleportListener implements Listener {

    private Plugin plugin;

    public FlooTeleportListener(Plugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerInteractEvent(PlayerInteractEvent event) {
        if (event.getMaterial() != Material.GLOWSTONE_DUST || event.getAction() != Action.RIGHT_CLICK_BLOCK)
            return;

        Block clickedBlock = event.getClickedBlock();
        if (clickedBlock == null || clickedBlock.getType() != Material.FURNACE)
            return;

        if (!NetworkRepo.get().has(Utils.getFurnaceLocationFromBlockWithYaw(clickedBlock)))
            return;

        chooseFurnace(event.getPlayer());
        event.setCancelled(true);
    }

    private void chooseFurnace(Player player) {
        FurnaceLocation[] allFurnaces = NetworkRepo.get().all();

        Inventory inv = Bukkit.createInventory(player, (int) Math.ceil(allFurnaces.length / 9f) * 9, "Floo Network");
        for (int i = 0; i < allFurnaces.length; i++) {
            ItemStack item = new ItemStack(ItemPicker.getItem(allFurnaces[i].name()));
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName(allFurnaces[i].name());
            item.setItemMeta(meta);
            inv.setItem(i, item);
        }
        player.openInventory(inv);
    }
}

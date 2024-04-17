package io.github.adamsonyanik;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockBurnEvent;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;

public class FurnaceManipulateListener implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!event.getView().getTitle().equals("Floo Network"))
            return;

        if (event.getClickedInventory() == null)
            return;

        if (event.getClickedInventory() != event.getInventory())
            return;

        int slot = event.getSlot();

        if (slot >= NetworkRepo.get().size())
            return;

        String name = event.getInventory().getItem(slot).getItemMeta().getDisplayName();
        Utils.teleportToFurnace(name, (Player) event.getWhoClicked());
        event.setCancelled(true);
    }

    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        removeBlock(event.getBlock());
    }

    @EventHandler
    public void onEntity(EntityChangeBlockEvent event) {
        if (event.getTo() != event.getBlock().getType())
            removeBlock(event.getBlock());
    }

    @EventHandler
    public void onExplode(BlockExplodeEvent event) {
        for (Block b : event.blockList())
            removeBlock(b);
    }

    @EventHandler
    public void onExplode(EntityExplodeEvent event) {
        for (Block b : event.blockList())
            removeBlock(b);
    }

    @EventHandler
    public void onBurn(BlockBurnEvent event) {
        removeBlock(event.getBlock());
    }

    private void removeBlock(Block block) {
        if (block.getType() != Material.FURNACE) return;

        NetworkRepo.get().remove(Utils.getFurnaceLocationFromBlockWithYaw(block));
    }
}

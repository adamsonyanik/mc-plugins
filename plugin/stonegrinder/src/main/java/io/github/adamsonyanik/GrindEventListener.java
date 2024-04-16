package io.github.adamsonyanik;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Directional;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPhysicsEvent;
import org.bukkit.inventory.ItemStack;

public class GrindEventListener implements Listener {

    private static final BlockFace[] faces = new BlockFace[]{BlockFace.EAST, BlockFace.WEST, BlockFace.SOUTH, BlockFace.NORTH};
    private static final ItemStack tool = new ItemStack(Material.IRON_PICKAXE);

    @EventHandler
    public void onPhysicsEvent(BlockPhysicsEvent event) {
        if (event.getBlock() != event.getSourceBlock()) return;

        Block block = event.getSourceBlock();
        if (block.getType() == Material.STONECUTTER)
            updateGrinder(block);

        if (block.getRelative(BlockFace.EAST).getType() != Material.STONECUTTER
                && block.getRelative(BlockFace.WEST).getType() != Material.STONECUTTER
                && block.getRelative(BlockFace.SOUTH).getType() != Material.STONECUTTER
                && block.getRelative(BlockFace.NORTH).getType() != Material.STONECUTTER)
            return;

        for (BlockFace face : faces) {
            if (block.getRelative(face).getType() == Material.STONECUTTER)
                updateGrinder(block.getRelative(face));
        }
    }

    private void updateGrinder(Block grinder) {
        Directional blockData = (Directional) grinder.getBlockData();
        Block breakBlock = grinder.getRelative(blockData.getFacing().getModZ(), 0, -blockData.getFacing().getModX());
        if (breakBlock.getDrops(tool).isEmpty()) return;

        BlockBreakEvent event = new BlockBreakEvent(breakBlock, null);
        Bukkit.getServer().getPluginManager().callEvent(event);
        if (event.isCancelled()) return;

        breakBlock.getWorld().playSound(breakBlock.getLocation(),
                breakBlock.getBlockData().getSoundGroup().getBreakSound(),
                breakBlock.getBlockData().getSoundGroup().getVolume(),
                breakBlock.getBlockData().getSoundGroup().getPitch());
        breakBlock.breakNaturally(tool);
    }
}

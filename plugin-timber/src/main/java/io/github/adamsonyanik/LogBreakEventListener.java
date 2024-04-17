package io.github.adamsonyanik;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.ArrayList;
import java.util.HashMap;

public class LogBreakEventListener implements Listener {

    @EventHandler
    public void onBlockBreakEvent(BlockBreakEvent event) {
        if (!isLog(event.getBlock().getType())) return;

        Material logType = event.getBlock().getType();

        int leafCount = 0;
        ArrayList<BlockAndLogDistance> openList = new ArrayList<>();
        HashMap<String, Block> treeBlocks = new HashMap<>();

        openList.add(new BlockAndLogDistance(event.getBlock().getX() + ";" + event.getBlock().getY() + ";" + event.getBlock().getZ(), event.getBlock(), 0));

        while (!openList.isEmpty()) {
            BlockAndLogDistance blockDist = openList.remove(0);
            treeBlocks.put(blockDist.id, blockDist.block);

            for (int y = 0; y <= 1; y++)
                for (int x = -1; x <= 1; x++)
                    for (int z = -1; z <= 1; z++) {
                        if (x == 0 && y == 0 && z == 0) continue;

                        Block newBlock = blockDist.block.getRelative(x, y, z);
                        int dist = newBlock.getType() != logType ? blockDist.distance + 1 : 0;
                        if (dist > 5) continue;

                        if (newBlock.getType() != blockDist.block.getType() && !isLeafOfLog(logType, newBlock.getType()))
                            continue;

                        String id = newBlock.getX() + ";" + newBlock.getY() + ";" + newBlock.getZ();
                        if (openList.stream().anyMatch(b -> b.id.equals(id)) || treeBlocks.get(id) != null) continue;

                        openList.add(new BlockAndLogDistance(id, newBlock, dist));

                        if (isLeafOfLog(logType, newBlock.getType()))
                            leafCount++;
                    }
        }

        if (leafCount < 10) return;

        for (Block block : treeBlocks.values())
            block.breakNaturally();
    }

    private boolean isLog(Material material) {
        return material == Material.ACACIA_LOG ||
                material == Material.BIRCH_LOG ||
                material == Material.CHERRY_LOG ||
                material == Material.OAK_LOG ||
                material == Material.SPRUCE_LOG ||
                material == Material.JUNGLE_LOG ||
                material == Material.DARK_OAK_LOG ||
                material == Material.MANGROVE_LOG;
    }

    private boolean isLeafOfLog(Material logMaterial, Material leafMaterial) {
        return (logMaterial == Material.ACACIA_LOG && leafMaterial == Material.ACACIA_LEAVES) ||
                (logMaterial == Material.BIRCH_LOG && leafMaterial == Material.BIRCH_LEAVES) ||
                (logMaterial == Material.CHERRY_LOG && leafMaterial == Material.CHERRY_LEAVES) ||
                (logMaterial == Material.OAK_LOG && (leafMaterial == Material.OAK_LEAVES || leafMaterial == Material.AZALEA_LEAVES || leafMaterial == Material.FLOWERING_AZALEA)) ||
                (logMaterial == Material.SPRUCE_LOG && leafMaterial == Material.SPRUCE_LEAVES) ||
                (logMaterial == Material.JUNGLE_LOG && leafMaterial == Material.JUNGLE_LEAVES) ||
                (logMaterial == Material.DARK_OAK_LOG && leafMaterial == Material.DARK_OAK_LEAVES) ||
                (logMaterial == Material.MANGROVE_LOG && leafMaterial == Material.MANGROVE_LEAVES);
    }

    record BlockAndLogDistance(String id, Block block, int distance) {

    }
}

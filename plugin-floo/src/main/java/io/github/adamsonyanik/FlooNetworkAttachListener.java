package io.github.adamsonyanik;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.Plugin;

public class FlooNetworkAttachListener implements Listener {

    private Plugin plugin;

    public FlooNetworkAttachListener(Plugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerInteractEvent(PlayerInteractEvent event) {
        if (event.getMaterial() != Material.ENDER_PEARL || event.getAction() != Action.RIGHT_CLICK_BLOCK)
            return;

        Block clickedBlock = event.getClickedBlock();
        if (clickedBlock == null || clickedBlock.getType() != Material.FURNACE)
            return;

        String name = event.getItem().getItemMeta().getDisplayName();
        if (name.isEmpty())
            return;

        event.setCancelled(true);

        if (NetworkRepo.get().has(name)) {
            event.getPlayer().sendMessage(name + " is already part of the network");
            return;
        }

        Location furnaceLocationFromBlockWithYaw = Utils.getFurnaceLocationFromBlockWithYaw(clickedBlock);

        NetworkRepo.get().add(name, furnaceLocationFromBlockWithYaw);
        event.getItem().setAmount(event.getItem().getAmount() - 1);

        Location particleLoc = furnaceLocationFromBlockWithYaw.add(0.5, 0.15, 0.5).add(Utils.yawToVector(furnaceLocationFromBlockWithYaw.getYaw()).multiply(0.5));
        Particle.DustTransition dustTransition = new Particle.DustTransition(Color.fromRGB(0, 255, 0), Color.fromRGB(255, 255, 255), 1.0F);
        clickedBlock.getWorld().spawnParticle(Particle.DUST_COLOR_TRANSITION, particleLoc, 50, 0.15f, 0.1f, 0.15f, dustTransition);
        clickedBlock.getWorld().playSound(particleLoc, Sound.ENTITY_GHAST_AMBIENT, 1f, 1f);
    }
}

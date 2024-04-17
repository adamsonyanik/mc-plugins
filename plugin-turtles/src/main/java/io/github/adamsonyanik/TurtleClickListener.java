package io.github.adamsonyanik;

import org.bukkit.NamespacedKey;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;

public class TurtleClickListener implements Listener {

    private boolean isTurtle(Entity entity) {
        return entity.getType() == EntityType.SLIME && entity.getPersistentDataContainer().has(new NamespacedKey(MCTurtles.plugin, "attached-armorstand"));
    }

    @EventHandler
    public void onPlayerInteractAtEntityEvent(PlayerInteractAtEntityEvent event) {
        if (!isTurtle(event.getRightClicked()))
            return;

        event.getPlayer().openInventory(Turtle.get(event.getRightClicked()).getInventory());
    }

    @EventHandler
    public void onEntityDamageEvent(EntityDamageEvent event) {
        if (!isTurtle(event.getEntity()))
            return;

        event.setCancelled(true);

        if (event.getCause() != EntityDamageEvent.DamageCause.ENTITY_ATTACK || event.getDamageSource().getCausingEntity() == null || !event.getDamageSource().getCausingEntity().getType().equals(EntityType.PLAYER))
            return;

        Turtle.get(event.getEntity()).destroy();
    }

    @EventHandler
    public void onEntityTargetEvent(EntityTargetEvent event) {
        if (event.getTarget() == null || !isTurtle(event.getTarget()))
            return;

        event.setCancelled(true);
    }
}

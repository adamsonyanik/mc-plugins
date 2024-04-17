package io.github.adamsonyanik;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Slime;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.EntitiesLoadEvent;
import org.bukkit.event.world.EntitiesUnloadEvent;
import org.bukkit.event.world.WorldSaveEvent;

import java.util.Collection;

public class TurtleLoadListener implements Listener {

    public TurtleLoadListener() {
        for (World w : Bukkit.getWorlds())
            loadEntities(w.getEntitiesByClass(Slime.class));
    }

    @EventHandler
    public void onChunkLoad(EntitiesLoadEvent event) {
        loadEntities(event.getEntities());
    }

    public static void loadEntities(Collection<? extends Entity> entities) {
        NamespacedKey attachedArmorStandNamespaceKey = new NamespacedKey(MCTurtles.plugin, "attached-armorstand");

        for (Entity e : entities) {
            if (e.getType() != EntityType.SLIME) continue;
            if (!e.getPersistentDataContainer().has(attachedArmorStandNamespaceKey)) continue;

            Turtle.load((Slime) e);
        }
    }

    @EventHandler
    public void onChunkUnload(EntitiesUnloadEvent event) {
        unloadEntities(event.getEntities());
    }

    public static void unloadEntities(Collection<? extends Entity> entities) {
        NamespacedKey attachedArmorStandNamespaceKey = new NamespacedKey(MCTurtles.plugin, "attached-armorstand");

        for (Entity e : entities) {
            if (e.getType() != EntityType.SLIME) continue;
            if (!e.getPersistentDataContainer().has(attachedArmorStandNamespaceKey)) continue;

            Turtle.get(e).unload();
        }
    }

    @EventHandler
    public void onWorldSave(WorldSaveEvent event) {
        Turtle.saveTurtles();
    }
}

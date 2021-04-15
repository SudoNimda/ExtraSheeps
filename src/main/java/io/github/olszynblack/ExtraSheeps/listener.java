package io.github.olszynblack.ExtraSheeps;

import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerShearEntityEvent;

public class listener implements Listener {



    @EventHandler
    public void shearsEvent(PlayerShearEntityEvent event) {
        if (event.getEntity().getType() == EntityType.SHEEP){
            Sheep sheep = ((Sheep) event.getEntity());
            main.sheep(event, sheep);
        }
    }
}

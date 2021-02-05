package me.ryzeon.rconnect.listener;

import me.ryzeon.rconnect.rConnect;
import me.ryzeon.rconnect.task.RebootTask;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

/**
 * Created by Ryzeon
 * Project: rFFAConnect
 * Date: 04/02/2021 @ 00:04
 */

public class PortalListener implements Listener {

    @EventHandler
    private void onPortalEvent(PlayerPortalEvent event) {
        Player player = event.getPlayer();
        if (event.getCause() != PlayerTeleportEvent.TeleportCause.NETHER_PORTAL) return;
        if (player.getWorld().getName().equals(rConnect.INSTANCE.getServer().getWorlds().get(0).getName())) {
            rConnect.sendToServer(player, "nether-ffa");
        } else {
            rConnect.sendToServer(player, "over-ffa");
        }
        event.setCancelled(true);
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        if (!event.getPlayer().isOp()) return;
        if (event.getMessage().equalsIgnoreCase("-restart"));
        event.setCancelled(true);
        String[] args = event.getMessage().split(" ");
        if (args.length == 0) return;
        new RebootTask(Integer.parseInt(args[1]));
    }
}

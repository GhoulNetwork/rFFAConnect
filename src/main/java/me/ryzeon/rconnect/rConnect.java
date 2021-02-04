package me.ryzeon.rconnect;

import lombok.Getter;
import me.ryzeon.rconnect.listener.PortalListener;
import me.ryzeon.rconnect.task.RebootTask;
import me.ryzeon.rconnect.utils.CC;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.spigotmc.SpigotConfig;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * Created by Ryzeon
 * Project: rFFAConnect
 * Date: 04/02/2021 @ 00:01
 */

@Getter
public class rConnect extends JavaPlugin {

    public static rConnect INSTANCE;

    private boolean over;

    @Override
    public void onEnable() {
        INSTANCE = this;
        logger("Enabling rConnect by @Ryzeon_");
        if (!SpigotConfig.bungee) {
            logger("First enabled bungeeCord mode");
            return;
        }
        Bukkit.getWorld("world").setAutoSave(false);
        over = this.getServer().getName().equalsIgnoreCase("over");
        logger("Register Listener.");
        this.getServer().getPluginManager().registerEvents(new PortalListener(), this);
        logger("Done loading.");

        if (over) {
            new BukkitRunnable() {
                @Override
                public void run() {
                    startTask();
                }
            }.runTaskLater(this, TimeUnit.HOURS.toSeconds(8) * 20);
        }
    }

    public static void sendToServer(Player player, String server) {
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
             DataOutputStream dos = new DataOutputStream(byteArrayOutputStream)) {
            dos.writeUTF("Connect");
            dos.writeUTF(server);
            player.sendPluginMessage(rConnect.INSTANCE, "BungeeCord", byteArrayOutputStream.toByteArray());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void startTask() {
        new BukkitRunnable() {
            @Override
            public void run() {
                new RebootTask(5);
            }
        }.runTaskTimer(this, 0, TimeUnit.HOURS.toSeconds(8) * 20);
    }

    public static void logger(String msg, String subMsg) {
        Bukkit.getConsoleSender().sendMessage(CC.translate("&7[&erConnect&7] &f" + subMsg + " &f" + msg));
    }

    public static void logger(String msg) {
        Bukkit.getConsoleSender().sendMessage(CC.translate("&7[&erConnect&7] &f" + msg));
    }
}

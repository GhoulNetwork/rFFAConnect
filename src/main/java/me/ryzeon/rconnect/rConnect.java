package me.ryzeon.rconnect;

import me.ryzeon.rconnect.listener.PortalListener;
import me.ryzeon.rconnect.utils.CC;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.spigotmc.SpigotConfig;

/**
 * Created by Ryzeon
 * Project: rFFAConnect
 * Date: 04/02/2021 @ 00:01
 */

public class rConnect extends JavaPlugin {

    public static rConnect INSTANCE;

    @Override
    public void onEnable() {
        INSTANCE = this;
        logger("Enabling rConnect by @Ryzeon_");
        if (!SpigotConfig.bungee) {
            logger("First enabled bungeeCord mode");
            return;
        }
        logger("Register Listener.");
        this.getServer().getPluginManager().registerEvents(new PortalListener(), this);
        logger("Done loading.");
    }

    public static void logger(String msg, String subMsg) {
        Bukkit.getConsoleSender().sendMessage(CC.translate("&7[&erConnect&7] &f" + subMsg + " &f" + msg));
    }

    public static void logger(String msg) {
        Bukkit.getConsoleSender().sendMessage(CC.translate("&7[&erConnect&7] &f" + msg));
    }
}

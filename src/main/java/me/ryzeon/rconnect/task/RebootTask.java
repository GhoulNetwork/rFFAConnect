package me.ryzeon.rconnect.task;

import me.ryzeon.rconnect.rConnect;
import me.ryzeon.rconnect.utils.CC;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Arrays;

/**
 * Created by Ryzeon
 * Project: rFFAConnect
 * Date: 04/02/2021 @ 00:29
 */

public class RebootTask {

    private int time;
    private final int startTime;

    public RebootTask(int time) {
        this.time = time * 60;
        this.startTime = time * 60;
        onStart();
    }

    private void onStart() {
        new BukkitRunnable() {
            @Override
            public void run() {
                if (time == startTime) {
                    Bukkit.broadcastMessage(CC.translate(CC.MEDIUM_CHAT_BAR));
                    Bukkit.broadcastMessage(CC.translate("&cServer will reboot in 5 minutes."));
                    Bukkit.broadcastMessage(CC.translate(CC.MEDIUM_CHAT_BAR));
                    Bukkit.getServer().getOnlinePlayers().forEach(player -> player.playSound(player.getLocation(), Sound.NOTE_PLING, 2F, 2F));
                }
                time--;
                if (time < 1) {
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            Bukkit.getServer().getOnlinePlayers().forEach(player -> player.kickPlayer(CC.translate("&cServer is rebooting \n Join back!")));
                            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "restart");
                        }
                    }.runTask(rConnect.INSTANCE);
                    cancel();
                    return;
                }
                int minutes = time / 60;

                if (Arrays.asList(3, 2, 1).contains(minutes) && time == (minutes * 60)) {
                    Bukkit.broadcastMessage(CC.translate(CC.MEDIUM_CHAT_BAR));
                    Bukkit.broadcastMessage(CC.translate("&cServer will reboot in " + minutes + getContext(true) + "."));
                    Bukkit.broadcastMessage(CC.translate(CC.MEDIUM_CHAT_BAR));
                    Bukkit.getServer().getOnlinePlayers().forEach(player -> {
                        player.playSound(player.getLocation(), Sound.NOTE_PLING, 2F, 2F);
                    });
                }
                if (Arrays.asList(30, 20, 10, 5, 4, 3, 2, 1).contains(time)) {
                    Bukkit.broadcastMessage(CC.translate(CC.MEDIUM_CHAT_BAR));
                    Bukkit.broadcastMessage(CC.translate("&cServer will reboot in " + time + getContext(false) + "."));
                    Bukkit.broadcastMessage(CC.translate(CC.MEDIUM_CHAT_BAR));
                    Bukkit.getServer().getOnlinePlayers().forEach(player -> {
                        player.playSound(player.getLocation(), Sound.NOTE_PLING, 2F, 2F);
                    });
                }
            }
        }.runTaskTimerAsynchronously(rConnect.INSTANCE, 0, 20);
    }

    public String getContext(boolean minutes) {
        if (minutes) {
            int minutesInt = time / 60;
            return minutesInt == 1 ? " minute" : " minutes";
        } else {
            return time == 1 ? " second" : " seconds";
        }
    }
}

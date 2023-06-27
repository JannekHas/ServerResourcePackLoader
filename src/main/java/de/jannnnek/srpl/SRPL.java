package de.jannnnek.srpl;

import de.jannnnek.srpl.util.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerResourcePackStatusEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class SRPL extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        Metrics metrics = new Metrics(this, 18891);
        getConfig().options().copyDefaults();
        saveDefaultConfig();
        Bukkit.getPluginManager().registerEvents(this, this);
    }

    @Override
    public void onDisable() {

    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        if (getConfig().getString("resource-pack") == null || getConfig().getString("resource-pack-sha1") == null) {
            return;
        }
        e.getPlayer().setResourcePack(getConfig().getString("resource-pack"), getConfig().getString("resource-pack-sha1"));
    }

    @EventHandler
    public void onResourcePack(PlayerResourcePackStatusEvent e) {
        if (getConfig().get("force-pack") == null) {
            return;
        }
        if (getConfig().getBoolean("force-pack")) {
            Player p = e.getPlayer();
            if (e.getStatus().equals(PlayerResourcePackStatusEvent.Status.DECLINED) || e.getStatus().equals(PlayerResourcePackStatusEvent.Status.FAILED_DOWNLOAD)) {
                if (getConfig().get("kick-message-on-decline") == null) {
                    p.kick();
                } else {
                    p.kickPlayer(getConfig().getString("kick-message-on-decline"));
                }
            }
        }
    }
}

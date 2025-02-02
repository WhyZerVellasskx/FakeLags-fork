package hw.zako.fakelags.trolling.impl;

import com.github.retrooper.packetevents.event.PacketReceiveEvent;
import com.github.retrooper.packetevents.event.PacketSendEvent;
import hw.zako.fakelags.FakeLags;
import hw.zako.fakelags.trolling.TrollingType;
import hw.zako.fakelags.trolling.AbstractTrolling;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.concurrent.atomic.AtomicBoolean;

public final class AllTrolling extends AbstractTrolling {
    private final Plugin plugin;
    private final AtomicBoolean isLagging = new AtomicBoolean(false);

    public AllTrolling() {
        super(TrollingType.ALL_CANCEL);
        this.plugin = FakeLags.getInstance();

        Bukkit.getGlobalRegionScheduler().runAtFixedRate(plugin, task -> {
            isLagging.set(!isLagging.get());
        }, 60L, 200L);
    }

    @Override
    public void packetReceive(PacketReceiveEvent e) {
        if (!isLagging.get()) return;

        Player player = (Player) e.getPlayer();
        int delay = 500;

        e.setCancelled(true);

        Bukkit.getGlobalRegionScheduler().runDelayed(plugin, task -> {
            e.setCancelled(false);
        }, delay);
    }

    @Override
    public void packetSend(PacketSendEvent e) {
        if (!isLagging.get()) return;

        Player player = (Player) e.getPlayer();
        int delay = 500;

        e.setCancelled(true);

        Bukkit.getGlobalRegionScheduler().runDelayed(plugin, task -> {
            e.setCancelled(false);
        }, delay);
    }
}

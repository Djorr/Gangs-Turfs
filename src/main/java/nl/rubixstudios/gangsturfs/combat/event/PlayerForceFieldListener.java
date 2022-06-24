package nl.rubixstudios.gangsturfs.combat.event;

import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import nl.rubixstudios.gangsturfs.GangsTurfs;
import nl.rubixstudios.gangsturfs.combat.CombatTagController;
import nl.rubixstudios.gangsturfs.utils.worldguard.WorldGuardUtil;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.*;

public class PlayerForceFieldListener implements Listener {

    private final CombatTagController combatTagController;
    private final Map<UUID, Set<Location>> worldGuardLocation;

    public PlayerForceFieldListener() {
        this.combatTagController = CombatTagController.getInstance();
        this.worldGuardLocation = new HashMap<>();

        Bukkit.getPluginManager().registerEvents(this, GangsTurfs.getInstance());
    }

    public void disable() {
        for (Player online : Bukkit.getServer().getOnlinePlayers())
            removeGlass(online);
        worldGuardLocation.clear();
    }

    private void updateWorldGuard(Player player, PlayerMoveEvent playerMoveEvent, List<ProtectedRegion> list) {
        final HashSet<Location> set = new HashSet<>();
        for (ProtectedRegion protectedRegion : list) {
            if (!WorldGuardUtil.isInsideTurfRegion(playerMoveEvent.getTo())) {
                if (protectedRegion.contains(playerMoveEvent.getTo().getBlockX(), playerMoveEvent.getTo().getBlockY(), playerMoveEvent.getTo().getBlockZ())) {
                    playerMoveEvent.setTo(playerMoveEvent.getFrom());
                }
            } else {
                if (!WorldGuardUtil.isInsideTurfRegion(playerMoveEvent.getTo())) {
                    playerMoveEvent.setTo(playerMoveEvent.getFrom());
                }
            }

            int closest = closest(player.getLocation().getBlockX(), protectedRegion.getMinimumPoint().getBlockX(), protectedRegion.getMaximumPoint().getBlockX());
            int closest2 = closest(player.getLocation().getBlockZ(), protectedRegion.getMinimumPoint().getBlockZ(), protectedRegion.getMaximumPoint().getBlockZ());
            boolean b = (Math.abs(player.getLocation().getX() - closest) < 6.0D);
            boolean b2 = (Math.abs(player.getLocation().getZ() - closest2) < 6.0D);
            if (!b && !b2)
                return;
            if (b)
                for (int i = -4; i < 5; i++) {
                    for (int j = -5; j < 6; j++) {
                        if (isInside(protectedRegion.getMinimumPoint().getBlockZ(), protectedRegion.getMaximumPoint().getBlockZ(), player.getLocation().getBlockZ() + j)) {
                            Location location = new Location(player.getLocation().getWorld(), closest, player.getLocation().getBlockY() + i, player.getLocation().getBlockZ() + j);
                            if (!set.contains(location) && !location.getBlock().getType().isOccluding())
                                set.add(location);
                        }
                    }
                }
            if (!b2)
                continue;
            for (int k = -4; k < 5; k++) {
                for (int l = -5; l < 6; l++) {
                    if (isInside(protectedRegion.getMinimumPoint().getBlockX(), protectedRegion.getMaximumPoint().getBlockX(), player.getLocation().getBlockX() + l)) {
                        Location location2 = new Location(player.getLocation().getWorld(), player.getLocation().getBlockX() + l, player.getLocation().getBlockY() + k, closest2);
                        if (!set.contains(location2) && !location2.getBlock().getType().isOccluding())
                            set.add(location2);
                    }
                }
            }
        }
        renderWorldGuard(player, set);
    }

    private int closest(int n, int... array) {
        int n2 = array[0];
        for (int j : array) {
            if (Math.abs(n - j) < Math.abs(n - n2))
                n2 = j;
        }
        return n2;
    }

    private boolean isInside(int n, int n2, int n3) {
        return (Math.abs(n - n2) == Math.abs(n3 - n) + Math.abs(n3 - n2));
    }

    private void renderWorldGuard(Player player, Set<Location> set) {
        if (worldGuardLocation.containsKey(player.getUniqueId())) {
            worldGuardLocation.get(player.getUniqueId()).addAll(set);
            for (Location location : worldGuardLocation.get(player.getUniqueId())) {
                if (!set.contains(location)) {
                    Block block = location.getBlock();
                    player.sendBlockChange(location, block.getTypeId(), block.getData());
                }
            }
            for (Location location : set) player.sendBlockChange(location, 95, (byte) 14);
        } else {
            for (Location location : set) player.sendBlockChange(location, 95, (byte) 14);
        }
        worldGuardLocation.put(player.getUniqueId(), set);
    }

    private void removeGlass(Player player) {
        if (this.worldGuardLocation.containsKey(player.getUniqueId())) {
            for (Location location : this.worldGuardLocation.get(player.getUniqueId())) {
                Block block = location.getBlock();
                player.sendBlockChange(location, block.getTypeId(), block.getData());
            }
            this.worldGuardLocation.remove(player.getUniqueId());
        }
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        final Player player = event.getPlayer();

        if (this.combatTagController.isInCombat(player) && !player.getGameMode().equals(GameMode.CREATIVE)) {
            this.updateWorldGuard(player, event, WorldGuardUtil.getNearestRegionsCT(player.getLocation()));
        } else {
            this.removeGlass(player);
        }
    }
}

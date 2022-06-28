package nl.rubixstudios.gangsturfs.turf;

import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import lombok.Getter;
import nl.rubixstudios.gangsturfs.GangsTurfs;
import nl.rubixstudios.gangsturfs.data.Config;
import nl.rubixstudios.gangsturfs.data.Language;
import nl.rubixstudios.gangsturfs.turf.menu.TurfMenuListener;
import nl.rubixstudios.gangsturfs.turf.task.TurfTask;
import nl.rubixstudios.gangsturfs.turf.task.TurfTaskImpl;
import nl.rubixstudios.gangsturfs.gang.GangManager;
import nl.rubixstudios.gangsturfs.gang.type.PlayerGang;
import nl.rubixstudios.gangsturfs.npc.NPCController;
import nl.rubixstudios.gangsturfs.npc.NPCData;
import nl.rubixstudios.gangsturfs.utils.Color;
import nl.rubixstudios.gangsturfs.utils.ManagerEnabler;
import nl.rubixstudios.gangsturfs.utils.item.PreBuildItems;
import nl.rubixstudios.gangsturfs.utils.worldguard.WorldGuardUtil;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
public class TurfController implements Listener, ManagerEnabler {

    private static @Getter TurfController instance;
    private TurfManager turfManager;

    private TurfTask turfTask;

    private Inventory npcInv;

    public TurfController() {
        instance = this;

        if (Config.TURF_ENABLED) {
            this.turfManager = TurfManager.getInstance();

            this.turfTask = new TurfTaskImpl(GangsTurfs.getInstance(), this);

            this.setupTasks();

            Bukkit.getPluginManager().registerEvents(this, GangsTurfs.getInstance());
            Bukkit.getPluginManager().registerEvents(new TurfMenuListener(), GangsTurfs.getInstance());
        }
    }

    public void disable() {
        turfManager.disable();

        if (turfTask != null) this.turfTask.cancel();
    }

    private void setupTasks() {
        this.npcInv = Bukkit.createInventory(null, 27, Color.translate("&e&lTurf Menu"));
    }

    // Turf NPC

    public void openTurfGameMenu(Player player) {
        final ProtectedRegion turfRegion = WorldGuardUtil.getTurfRegion(player.getLocation());
        if (turfRegion == null) return;

        final TurfData turfData = this.turfManager.getTurf(turfRegion.getId());
        if (turfData == null) return;

        final Inventory inventory = this.getNpcInv();
        if (turfData.isTurfStarted()) {
            inventory.setItem(13, PreBuildItems.alreadyActiveItem());
        } else {
            inventory.setItem(13, PreBuildItems.readyToStartGameItem(turfData));
        }

        player.openInventory(inventory);
    }

    // Turf Create/Remove

    public void createTurf(CommandSender sender, String name) {
        final Player player = (Player) sender;
        final ProtectedRegion protectedRegion = WorldGuardUtil.getTurfRegion(player.getLocation());
        if (protectedRegion == null) {
            sender.sendMessage(Language.TURF_PREFIX + Color.translate("&cYou are not standing on a worldguard region!"));
            return;
        }

        final TurfData turfData = new TurfData();
        turfData.setTurfName(name);
        turfData.setTurfRegion(player.getLocation());
        turfData.setTurfWinning(Config.TURF_WINNINGS_FOR_EACH_GANG);
        turfData.setTurfDuration(Config.TURF_GAME_DURATION);
        turfData.setTurfStarted(false);

        this.turfManager.createTurf(turfData);
        sender.sendMessage(Language.TURF_PREFIX + Color.translate("&aYou succesfully created an turf!"));

        // Creates the NPC
        NPCController.getInstance().createNPC(sender, turfData.getTurfName());
    }

    public void deleteTurf(CommandSender sender, String name) {
        final TurfData turfData = this.turfManager.getTurf(name);
        if (turfData == null) return;

        if (turfData.isTurfStarted()) {
            this.stopTurf(turfData);
        }

        this.turfManager.removeTurf(turfData);
        sender.sendMessage(Language.TURF_PREFIX + Color.translate("&aYou succesfully removed an turf!"));

        // Remove the NPC
        NPCController.getInstance().deleteNpc(sender, turfData.getTurfName());
    }

    public boolean isTurfStarted(String name) {
        return this.turfManager.getTurfs().stream().anyMatch(turfData -> turfData.getTurfName().equals(name));
    }

    public boolean anyTurfStartedAlready() {
        return this.turfManager.getTurfs().stream().anyMatch(TurfData::isTurfStarted);
    }

    // Turf start/stop

    public void startTurf(Player who, int timeInMinutes, double turfWinning, TurfData turfData) {
        if (timeInMinutes != 0) {
            turfData.setTurfDuration(timeInMinutes);
        }
        if (turfWinning != 0) {
            turfData.setTurfWinning(turfWinning);
        }

        turfData.setTurfStarted(true);
        turfData.setTurfStartedTime(System.currentTimeMillis());
        turfData.setLastMessageTime(System.currentTimeMillis());

        who.sendMessage(Language.TURF_PREFIX + Color.translate("&aYou've succesfully started the turf!"));

        Language.TURF_GAME_MESSAGES_TURF_STARTED_MESSAGE.forEach(message -> Bukkit.getOnlinePlayers().forEach(player -> {
            player.sendMessage(message
                    .replace("<whoStartedTurf>", who.getName())
                    .replace("<coords>", "x" + turfData.getTurfRegion().getBlockX() + " y" + turfData.getTurfRegion().getBlockY() + " z" + turfData.getTurfRegion().getBlockZ())
                    .replace("<x>", "x" + turfData.getTurfRegion().getBlockX())
                    .replace("<y>", "y" + turfData.getTurfRegion().getBlockY())
                    .replace("<z>", "z" + turfData.getTurfRegion().getBlockZ())
                    .replace("<city>", turfData.getTurfRegion().getWorld().getName())
            );
        }));

        turfData.getPlayersInTurf().add(who.getUniqueId());

        final NPCController npcController = NPCController.getInstance();
        final NPCData npcData = npcController.getNpcManager().getNpc(turfData.getTurfName());
        if (npcData == null) return;

        npcController.despawnNpc(npcData, false);
    }

    public void stopTurf(TurfData turfData) {
        turfData.setTurfStarted(false);
        turfData.setTurfEndedTime(System.currentTimeMillis());
        this.giveWinningsOnEnding(turfData);

        final NPCController npcController = NPCController.getInstance();
        final NPCData npcData = npcController.getNpcManager().getNpc(turfData.getTurfName());
        if (npcData == null) return;

        npcController.spawnNpc(npcData);
    }

    // Cooldown
    public boolean isTurfOnCooldown(TurfData turfData) {
        if (turfData.getTurfEndedTime() == 0) {
            return false;
        }
        return System.currentTimeMillis() - turfData.getTurfEndedTime() < Config.TURF_COOLDOWN_BETWEEN_STARTING_NEW_TURF * 1000L;
    }

    public void giveWinningsOnEnding(TurfData turfData) {
        final List<UUID> turfWinners = new ArrayList<>();

        if (!turfData.getPlayersInTurf().isEmpty()) {
            turfData.getPlayersInTurf().forEach(uuid -> {
                final Player player = Bukkit.getPlayer(uuid);
                if (player == null) return;

                final PlayerGang playerGang = GangManager.getInstance().getPlayerGang(player);
                if (playerGang == null) return;

                turfWinners.add(uuid);
            });

            turfWinners.forEach(uuid -> {
                final Player player = Bukkit.getPlayer(uuid);
                if (player == null) return;

                GangsTurfs.getInstance().getEconomy().depositPlayer(player, turfData.getTurfWinning());

                player.sendMessage(Language.TURF_PREFIX + Color.translate("&aJe hebt &e<money> &aontvangen!".replace("<money>", "€" + turfData.getTurfWinning())));
            });

            Language.TURF_GAME_MESSAGES_TURF_ENDED_MESSAGE.forEach(message -> Bukkit.getOnlinePlayers().forEach(player -> {
                player.sendMessage(message.replace("<aantal>", "" + turfWinners.size())
                );
            }));
        }

        turfData.getPlayersInTurf().clear();
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    public void onPlayerMove(PlayerMoveEvent event) {
        if (this.turfManager.getTurfs().isEmpty()) return;

        final TurfData turfData = this.turfManager.getTurfs().get(0);
        if (!turfData.isTurfStarted()) return;

        final Location from = event.getFrom();
        final Location to = event.getTo();

        if(from.getBlockX() == to.getBlockX()
                && from.getBlockY() == to.getBlockY()
                && from.getBlockZ() == to.getBlockZ()) return;

        final Player player = event.getPlayer();

        final UUID playerUuid = player.getUniqueId();
        if (WorldGuardUtil.isInsideTurfRegion(player.getLocation())) {
            if (!turfData.getPlayersInTurf().contains(playerUuid)) {
                turfData.getPlayersInTurf().add(playerUuid);
            }
        } else {
            turfData.getPlayersInTurf().remove(playerUuid);
        }
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    public void onPlayerTeleport(PlayerTeleportEvent event) {
        if (this.turfManager.getTurfs().isEmpty()) return;

        final TurfData turfData = this.turfManager.getTurfs().get(0);
        if (!turfData.isTurfStarted()) return;

        final Player player = event.getPlayer();
        if (WorldGuardUtil.isInsideTurfRegion(event.getTo())) {
            turfData.getPlayersInTurf().add(player.getUniqueId());
        } else {
            turfData.getPlayersInTurf().remove(player.getUniqueId());
        }
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        if (this.turfManager.getTurfs().isEmpty()) return;

        final TurfData turfData = this.turfManager.getTurfs().get(0);
        if (!turfData.isTurfStarted()) return;

        final Player player = event.getEntity();
        turfData.getPlayersInTurf().remove(player.getUniqueId());
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        if (this.turfManager.getTurfs().isEmpty()) return;

        final TurfData turfData = this.turfManager.getTurfs().get(0);
        if (!turfData.isTurfStarted()) return;

        final Player player = event.getPlayer();
        if (WorldGuardUtil.isInsideTurfRegion(player.getLocation())) {
            turfData.getPlayersInTurf().add(player.getUniqueId());
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        if (this.turfManager.getTurfs().isEmpty()) return;

        final TurfData turfData = this.turfManager.getTurfs().get(0);
        if (!turfData.isTurfStarted()) return;

        final Player player = event.getPlayer();
        turfData.getPlayersInTurf().remove(player.getUniqueId());
    }
}

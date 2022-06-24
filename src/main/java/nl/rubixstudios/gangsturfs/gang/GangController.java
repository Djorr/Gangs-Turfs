package nl.rubixstudios.gangsturfs.gang;

import lombok.Getter;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import nl.rubixstudios.gangsturfs.GangsTurfs;
import nl.rubixstudios.gangsturfs.data.Config;
import nl.rubixstudios.gangsturfs.data.Language;
import nl.rubixstudios.gangsturfs.gang.enums.Role;
import nl.rubixstudios.gangsturfs.gang.event.*;
import nl.rubixstudios.gangsturfs.gang.listener.GangMenuListener;
import nl.rubixstudios.gangsturfs.gang.object.CreatingGangObject;
import nl.rubixstudios.gangsturfs.gang.type.PlayerGang;
import nl.rubixstudios.gangsturfs.utils.*;
import nl.rubixstudios.gangsturfs.utils.item.PreBuildItems;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.scheduler.BukkitTask;

import java.util.*;

public class GangController implements Listener {

    @Getter private static GangController instance;

    @Getter private final List<CreatingGangObject> creatingGangCache;

    private final @Getter Inventory gangCreationMenu;

    private BukkitTask removeTask;

    public GangController() {
        instance = this;

        this.gangCreationMenu = Bukkit.createInventory(null, 27, Color.translate("&c&lGang Menu"));
        this.creatingGangCache = new ArrayList<>();

        this.startRemovingTask();

        Bukkit.getPluginManager().registerEvents(this, GangsTurfs.getInstance());
        Bukkit.getPluginManager().registerEvents(new GangMenuListener(), GangsTurfs.getInstance());
    }

    public void disable() {

    }

    private void startRemovingTask() {
        int interval = 10 * 60;

        this.removeTask = Tasks.asyncTimer(() -> {
            if (this.getCreatingGangCache().isEmpty()) return;

            final List<CreatingGangObject> removal = new ArrayList<>();
            this.getCreatingGangCache().forEach(creatingGangObject -> {
                if (System.currentTimeMillis() - creatingGangObject.getStartedTime() > (60 * 1000L)) {
                    removal.add(creatingGangObject);
                }
            });

            this.getCreatingGangCache().removeAll(removal);

            removal.forEach(creatingGangObject -> {
                final Player player = Bukkit.getPlayer(creatingGangObject.getPlayerId());
                if (player == null) return;

                player.sendMessage(Language.GANG_PREFIX + Color.translate("&cGang aanmaken is geannuleerd."));
            });

            removal.clear();
        }, interval, interval);
    }

    // Gang Menu

    public void openGangMenu(Player player) {
        final Inventory inventory = this.getGangCreationMenu();

        inventory.setItem(12, PreBuildItems.maakGang());
        inventory.setItem(14, PreBuildItems.verwijderGang());

        player.openInventory(inventory);
    }

    public boolean joinGang(Player player, PlayerGang gang) {
        final GangPlayer gPlayer = new GangPlayer(player.getUniqueId(), gang);
        final PlayerJoinGangEvent event = new PlayerJoinGangEvent(gPlayer, gang);

        if(event.isCancelled()) {
            GangManager.getInstance().players.remove(player.getUniqueId());
            return false;
        }

        gang.addMember(gPlayer);
        gang.getPlayerInvitations().remove(player.getName());
        GangManager.getInstance().players.put(gPlayer.getUuid(), gPlayer);
        return true;
    }

    public boolean leaveGang(Player player, PlayerGang gang) {
        final GangPlayer gPlayer = GangManager.getInstance().getPlayer(player);
        final PlayerLeaveGangEvent event = new PlayerLeaveGangEvent(gPlayer, gang, PlayerLeaveGangEvent.LeaveReason.LEAVE);

        if(event.isCancelled()) return false;

        gang.removeMember(gPlayer);
        GangManager.getInstance().players.remove(gPlayer.getUuid());
        return true;
    }

    public boolean kickPlayer(OfflinePlayer player, PlayerGang gang) {
        final GangPlayer gPlayer = GangManager.getInstance().getPlayer(player.getUniqueId());

        final PlayerLeaveGangEvent event = new PlayerLeaveGangEvent(gPlayer, gang, PlayerLeaveGangEvent.LeaveReason.KICK);
        if(event.isCancelled()) return false;

        gang.removeMember(gPlayer);
        GangManager.getInstance().players.remove(gPlayer.getUuid());
        return true;
    }

    public boolean createPlayerGang(String name, Player player) {
        final GangCreateEvent event = new GangCreateEvent(name, player, GangCreateEvent.GangType.PLAYER_GANG);
        if(event.isCancelled()) return false;

        final PlayerGang gang = new PlayerGang(name);

        final GangPlayer gPlayer = new GangPlayer(player.getUniqueId(), gang);
        gPlayer.setRole(Role.LEADER);

        new PlayerJoinGangEvent(gPlayer, gang);
        gang.addMember(gPlayer);

        GangManager.getInstance().gangs.put(gang.getId(), gang);
        GangManager.getInstance().gangNames.put(gang.getName(), gang.getId());
        GangManager.getInstance().players.put(player.getUniqueId(), gPlayer);

        return true;
    }

    public boolean disbandGang(UUID uuid, CommandSender sender) {
        final Gang toDisband = GangManager.getInstance().getGangByUuid(uuid);
        if(toDisband == null) return true;

        return this.disbandGang(toDisband, sender);
    }

    public boolean disbandGang(Gang toDisband, CommandSender sender) {
        final GangDisbandEvent disbandEvent = new GangDisbandEvent(toDisband, sender);
        if(disbandEvent.isCancelled()) return false;

        if(!(toDisband instanceof PlayerGang)) return true;

        final PlayerGang playerGang = (PlayerGang) toDisband;

        for (GangPlayer gangPlayer : playerGang.getMembers().values()) {
            GangManager.getInstance().players.remove(gangPlayer.getUuid());
        }

        GangManager.getInstance().gangs.remove(toDisband.getId());
        GangManager.getInstance().gangNames.remove(toDisband.getName());
        return true;
    }

    public ComponentBuilder convertMessageToComponent(String message) {
        return new ComponentBuilder(message);
    }

    // Gang Creating functions

    public void startCreation(Player player) {
        final CreatingGangObject creatingGangObject = new CreatingGangObject();
        creatingGangObject.setPlayerId(player.getUniqueId());
        creatingGangObject.setStartedTime(System.currentTimeMillis());
        this.addToCreatingCache(creatingGangObject);

        final TextComponent textComponent = new TextComponent(Language.GANG_PREFIX + Color.translate("Hoe moet jou gang heten? "));

        final TextComponent textComponent1 = new TextComponent(Color.translate("&8(&cAnnuleren&8)"));
        textComponent1.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Klik hier om te annuleren!").create()));
        textComponent1.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/gang cancel"));

        player.spigot().sendMessage(textComponent, textComponent1);
    }

    public CreatingGangObject getObject(Player player) {
        return this.creatingGangCache.stream().filter(creatingGangObject -> creatingGangObject.getPlayerId().equals(player.getUniqueId())).findFirst().orElse(null);
    }

    private boolean alreadyContainsCache(CreatingGangObject creatingGangObject) {
        return this.creatingGangCache.stream().anyMatch(creatingGangObject1 -> creatingGangObject1.getPlayerId().equals(creatingGangObject.getPlayerId()));
    }

    public void addToCreatingCache(CreatingGangObject creatingGangObject) {
        if (!alreadyContainsCache(creatingGangObject)) this.creatingGangCache.add(creatingGangObject);
    }

    public void removeFromCreatingCache(Player player) {
        final CreatingGangObject creatingGangObject = this.getObject(player);
        if (creatingGangObject == null) return;

        this.creatingGangCache.remove(creatingGangObject);
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onChat(AsyncPlayerChatEvent event) {
        final Player player = event.getPlayer();
        final CreatingGangObject creatingGangObject = this.getObject(player);
        if (!alreadyContainsCache(creatingGangObject)) return;
        event.setCancelled(true);

        final String[] args = event.getMessage().split(" ");
        if (args.length != 1 || args[0].length() > 6) {
            final TextComponent textComponent = new TextComponent(Language.GANG_PREFIX + Color.translate("&fJe gang naam mag geen spaties bevatten of is langer dan 6 characters! "));

            final TextComponent textComponent1 = new TextComponent(Color.translate("&8(&cAnnuleren&8)"));
            textComponent1.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Klik hier om te annuleren!").create()));
            textComponent1.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/gang cancel"));

            player.spigot().sendMessage(textComponent, textComponent1);
            return;
        }

        if (!GangsTurfs.getInstance().getEconomy().has(player, Config.GANG_PRIZE)) {
            this.creatingGangCache.remove(creatingGangObject);
            player.sendMessage(Language.GANG_PREFIX + Color.translate("&cJe hebt niet genoeg om een gang te starten.."));
            return;
        }

        if (GangManager.getInstance().getGangs().values().stream().anyMatch(gang -> gang.getName().equals(args[0]))) {
            player.sendMessage(Language.GANG_PREFIX + Color.translate("&cDeze gang naam bestaat al! Geef een nieuwe naam op."));
            return;
        }

        this.createPlayerGang(args[0], player);
        GangsTurfs.getInstance().getEconomy().withdrawPlayer(player, Config.GANG_PRIZE);
        player.sendMessage(Language.GANG_PREFIX + Color.translate("&fJe hebt succesvol de gang &a<gang> &faangemaakt! &8(&c-€<prijs>&8)"
                .replace("<gang>", args[0])
                .replace("<prijs>", "" + Config.GANG_PRIZE)));
        this.creatingGangCache.remove(creatingGangObject);
    }


    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerJoin(PlayerJoinEvent event) {
        final Player joinedPlayer = event.getPlayer();
        final PlayerGang gang = GangManager.getInstance().getPlayerGang(joinedPlayer);
        if(gang == null) return;

        gang.sendMessage(Language.GANGS_MEMBER_ONLINE.replace("<player>", joinedPlayer.getName()));
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        final PlayerGang gang = GangManager.getInstance().getPlayerGang(event.getPlayer());
        if(gang == null) return;

        gang.sendMessage(Language.GANGS_MEMBER_OFFLINE.replace("<player>", event.getPlayer().getName()));
    }

    @EventHandler
    public void onPre(PlayerCommandPreprocessEvent event) {
        final boolean cancelmessage = event.getMessage().startsWith("/gang cancel");

        final CreatingGangObject creatingGangObject = this.getObject(event.getPlayer());
        if (cancelmessage && creatingGangObject == null) {
            event.setCancelled(true);
        }
    }

}

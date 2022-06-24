package nl.rubixstudios.gangsturfs.gang;

import lombok.Getter;
import nl.rubixstudios.gangsturfs.GangsTurfs;
import nl.rubixstudios.gangsturfs.data.Config;
import nl.rubixstudios.gangsturfs.gang.type.PlayerGang;
import nl.rubixstudios.gangsturfs.npc.NPCManager;
import nl.rubixstudios.gangsturfs.utils.FileUtils;
import nl.rubixstudios.gangsturfs.utils.GsonUtils;
import nl.rubixstudios.gangsturfs.utils.Tasks;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitTask;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;

public class GangManager implements Listener {

    @Getter private static GangManager instance;

    private final File gangsFile;
    private final File playersFile;

    @Getter protected Map<UUID, Gang> gangs;
    protected final Map<String, UUID> gangNames;

    protected Map<UUID, GangPlayer> players;

    private BukkitTask saveTask;

    public GangManager() {
        instance = this;

        this.gangsFile = FileUtils.getOrCreateFile(Config.GANGS_DIR, "gangs.json");
        this.playersFile = FileUtils.getOrCreateFile(Config.GANGS_DIR, "players.json");

        this.gangNames = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);

        this.loadGangs();
        this.loadPlayers();

        this.startSaveTask();

        Bukkit.getPluginManager().registerEvents(this, GangsTurfs.getInstance());
    }

    public void disable() {
        this.saveGangs(true, true);
        this.savePlayers(true);

        this.gangNames.clear();
        this.gangs.clear();
        this.players.clear();

        this.saveTask.cancel();
    }

    protected void loadGangs() {
        final String content = FileUtils.readWholeFile(this.gangsFile);

        if (content == null) {
            this.gangs = new HashMap<>();
            return;
        }

        this.gangs = GangsTurfs.getInstance().getGson().fromJson(content, GsonUtils.GANG_TYPE);

        for (Gang gang : this.gangs.values()) {
            this.gangNames.put(gang.getName(), gang.getId());
        }

        GangsTurfs.getInstance().log(" ");
        GangsTurfs.getInstance().log("&eGame ");
        GangsTurfs.getInstance().log("- &7Loaded &e" + this.gangs.size() + " &7gangs.");
    }

    public void saveGangs(boolean log, boolean onDisable) {
        if (this.gangs == null) return;

        FileUtils.writeString(this.gangsFile, GangsTurfs.getInstance().getGson()
                .toJson(this.gangs, GsonUtils.GANG_TYPE));

        if(log) {
            GangsTurfs.getInstance().log("- &7Saved &e" + this.gangs.size() + " &7gangs.");
        }
    }

    protected void loadPlayers() {
        final String content = FileUtils.readWholeFile(this.playersFile);

        if(content == null) {
            this.players = new HashMap<>();
            return;
        }

        this.players = GangsTurfs.getInstance().getGson().fromJson(content, GsonUtils.PLAYER_TYPE);

        this.players.values().forEach(gPlayer -> {
            final PlayerGang playerGang = gPlayer.getGang();
            if(playerGang != null) playerGang.addMember(gPlayer);
        });

        GangsTurfs.getInstance().log("- &7Loaded &e" + this.players.size() + " &7players.");
    }

    public void savePlayers(boolean log) {
        if(this.players == null) return;

        FileUtils.writeString(this.playersFile, GangsTurfs.getInstance().getGson()
                .toJson(this.players, GsonUtils.PLAYER_TYPE));

        if(log) {
            GangsTurfs.getInstance().log("- &7Saved &e" + this.players.size() + " &7players.");
        }
    }

    public boolean isNameTaken(String name) {
        return this.gangNames.get(name) != null;
    }

    public GangPlayer getPlayer(Player player) {
        return this.getPlayer(player.getUniqueId());
    }

    public GangPlayer getPlayer(UUID uuid) {
        return this.players.get(uuid);
    }

    public Gang getAnyFGang(String argument) {
        final Gang gang = this.getGangByName(argument);
        return gang != null ? gang : this.searchForGang(argument);
    }

    public PlayerGang searchForGang(String argument) {
        final PlayerGang gang = this.getPlayerGangByName(argument);
        return gang != null ? gang : this.getPlayerGang(argument);
    }

    public Gang getGangByUuid(UUID uuid) {
        return this.gangs.get(uuid);
    }

    public Gang getGangByName(String name) {
        return this.getGangByUuid(this.gangNames.get(name));
    }

    public PlayerGang getPlayerGangByUuid(UUID uuid) {
        final Gang gang = this.getGangByUuid(uuid);
        return gang instanceof PlayerGang ? (PlayerGang) gang : null;
    }

    public PlayerGang getPlayerGangByName(String name) {
        final Gang gang = this.getGangByName(name);
        return gang instanceof PlayerGang ? (PlayerGang) gang : null;
    }

    public PlayerGang getPlayerGang(UUID uuid) {
        final GangPlayer fplayer = this.getPlayer(uuid);
        return fplayer == null ? null : fplayer.getGang();
    }

    public PlayerGang getPlayerGang(Player player) {
        return this.getPlayerGang(player.getUniqueId());
    }

    public PlayerGang getPlayerGang(String playerName) {
        final OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(playerName);

        return offlinePlayer.hasPlayedBefore() || offlinePlayer.isOnline()
                ? this.getPlayerGang(offlinePlayer.getUniqueId()) : null;
    }

    private void startSaveTask() {
        int interval = 10 * 60 * 20;

        this.saveTask = Tasks.asyncTimer(() -> {
            GangsTurfs.getInstance().log("&6===&e=============================================&6===");

            GangsTurfs.getInstance().log("&eSaving tasks");
            this.saveGangs(true, false);
            this.savePlayers(true);
            NPCManager.getInstance().saveNpcs(false, true);


            GangsTurfs.getInstance().log("&6===&e=============================================&6===");
        }, interval, interval);
    }

}

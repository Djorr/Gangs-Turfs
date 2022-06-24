package nl.rubixstudios.gangsturfs.userdata;

import lombok.Getter;
import nl.rubixstudios.gangsturfs.GangsTurfs;
import nl.rubixstudios.gangsturfs.data.Config;
import nl.rubixstudios.gangsturfs.data.Language;
import nl.rubixstudios.gangsturfs.utils.*;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.io.File;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class UserdataController implements Listener {

    protected final @Getter Map<UUID, Userdata> userdata;
    protected final @Getter Map<UUID, Userdata> offlineUserdata;

    private final File[] playerFiles;

    public UserdataController() {
        this.playerFiles = FileUtils.getAllFiles(Config.USERDATA_DIR);

        this.userdata = new ConcurrentHashMap<>();
        this.offlineUserdata = new ConcurrentHashMap<>();

        this.loadAllOfflineUserdatas();

        Bukkit.getOnlinePlayers().forEach(this::loadUserdata);
        Bukkit.getPluginManager().registerEvents(this, GangsTurfs.getInstance());
    }

    public void disable() {
        this.userdata.keySet().forEach(uuid -> this.saveUserdata(uuid, false));

        GangsTurfs.getInstance().log("- &7Saved &e" + this.userdata.size() + " &7userdatas.");
        GangsTurfs.getInstance().log("- &7Saved &e" + this.offlineUserdata.size() + " &7offline userdatas.");

        this.userdata.clear();
        this.offlineUserdata.clear();
    }

    public void resetStats(Userdata userdata) {
        userdata.setKills(0);
        userdata.setDeaths(0);
        userdata.setKdr(0.00);

        userdata.addStatReset();
    }

    public void loadAllOfflineUserdatas() {
        final List<Userdata> userdatas = new ArrayList<>(this.userdata.values());
        final List<Userdata> offlineUserdatas = UserdataUtil.loadUserdatas(this.playerFiles);

        if (offlineUserdatas == null) return;

        for (int i = 0; i < offlineUserdatas.size(); i++) {
            final Userdata userdata = offlineUserdatas.get(i);
            final Userdata onlineUserdata = UserdataUtil.getUserdataByUuid(userdata.getUuid(), userdatas);

            if (onlineUserdata != null) {
                offlineUserdatas.set(i, userdata);
            }
        }

        offlineUserdatas.forEach(userdata -> {
            this.offlineUserdata.put(userdata.getUuid(), userdata);
        });

        GangsTurfs.getInstance().log("&eData ");
        GangsTurfs.getInstance().log("&7- Loaded &e" + this.offlineUserdata.size() + " &7offline userdata(s).");
        GangsTurfs.getInstance().log("&7- Loaded &e" + this.userdata.size() + " &7online userdata(s).");
    }

    private void loadUserdata(final Player player) {
        this.loadUserdata(player.getUniqueId(), player.getName());
    }

    protected void loadUserdata(final UUID uuid, final String name) {
        if(this.userdata.containsKey(uuid)) return;
        this.offlineUserdata.remove(uuid);

        final File file = new File(Config.USERDATA_DIR, uuid + ".json");

        if(!file.exists()) {
            final Userdata userdata = new Userdata(uuid, name);
            userdata.setFirstTimeJoined(System.currentTimeMillis());

            this.userdata.put(uuid, userdata);
            return;
        }

        final String content = FileUtils.readWholeFile(file);
        if(content == null) return;

        this.userdata.put(uuid, GangsTurfs.getInstance().getGson().fromJson(content, Userdata.class));
    }

    public void saveUserdata(final UUID uuid, final boolean remove) {
        final Userdata userdata = this.getUserdata(uuid);
        if(userdata == null) return;

        final File file = FileUtils.getOrCreateFile(Config.USERDATA_DIR, uuid + ".json");

        userdata.setLastTimeLeft(System.currentTimeMillis());
        this.offlineUserdata.put(uuid, userdata);

        FileUtils.writeString(file, GangsTurfs.getInstance().getGson().toJson(userdata, Userdata.class));
        if(remove) {
            this.userdata.remove(uuid);
        }
    }

    public void saveUserdata(final Userdata userdata) {
        if(userdata == null) return;

        userdata.setLastTimeLeft(System.currentTimeMillis());
        this.offlineUserdata.put(userdata.getUuid(), userdata);

        final File file = FileUtils.getOrCreateFile(Config.USERDATA_DIR, userdata.getUuid() + ".json");
        FileUtils.writeString(file, GangsTurfs.getInstance().getGson().toJson(userdata, Userdata.class));
    }

    public Userdata getOfflineUserdata(final OfflinePlayer offlinePlayer) {
        return this.getOfflineUserdata(offlinePlayer.getUniqueId());
    }

    public Userdata getOfflineUserdata(final UUID uuid) {
        return this.offlineUserdata.get(uuid);
    }

    public Userdata getUserdata(final Player player) {
        return this.getUserdata(player.getUniqueId());
    }

    public Userdata getUserdata(final UUID uuid) {
        return this.userdata.get(uuid);
    }

    public Userdata getUserdata(final OfflinePlayer player) {
        if(this.userdata.containsKey(player.getUniqueId())) return this.getUserdata(player.getUniqueId());

        final File file = new File(Config.USERDATA_DIR, player.getUniqueId() + ".json");
        if(!file.exists()) return null;

        final String content = FileUtils.readWholeFile(file);
        if(content == null) return null;

        final Userdata userdata = GangsTurfs.getInstance().getGson().fromJson(content, Userdata.class);
        this.userdata.put(player.getUniqueId(), userdata);

        return userdata;
    }

    /**
     * Show online player statistics
     * @param player the player to send the message towards
     * @param userdata the userdata of the the player he wants the information from
     */
    public void showOnlinePlayerStatistics(Player player, Userdata userdata) {
        final Player target = Bukkit.getPlayer(userdata.getUuid());

        final List<String> showMessage = Language.IDENTITY_SEE_ID;

        showMessage.forEach(line -> player.sendMessage(Color.translate(line
                .replace("<playerName>", target.getName())

                .replace("<resetted>", userdata.getStatsResetted() + "")
                .replace("<lastresetted>", getDateFormat(userdata.getLastStatReset()) + "")

                .replace("<playtime>", convertPlaytime(userdata.getOnlinePlayerTime()) + "")
                .replace("<kills>", userdata.getKills() + "")
                .replace("<deaths>", userdata.getDeaths() + "")
                .replace("<kdr>", RoundUtil.roundDecimals(userdata.getKdr()) + "")
                .replace("<firsttimejoined>", getDateFormat(userdata.getFirstTimeJoined()) + "")))

        );
    }

    /**
     * Show offline player statistics
     * @param player the player to send the message towards
     * @param userdata the userdata of the the player he wants the information from
     */
    public void showOfflinePlayerStatistics(Player player, Userdata userdata) {
        final OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(userdata.getUuid());

        final List<String> showMessage = Language.IDENTITY_SEE_ID;

        showMessage.forEach(line -> player.sendMessage(Color.translate(line
                .replace("<playerName>", offlinePlayer.getName())

                .replace("<resetted>", userdata.getStatsResetted() + "")
                .replace("<lastresetted>", getDateFormat(userdata.getLastStatReset()) + "")

                .replace("<playtime>", convertPlaytime(userdata.getOfflinePlayerTime()) + "")
                .replace("<kills>", userdata.getKills() + "")
                .replace("<deaths>", userdata.getDeaths() + "")
                .replace("<kdr>", RoundUtil.roundDecimals(userdata.getKdr()) + "")
                .replace("<firsttimejoined>", getDateFormat(userdata.getFirstTimeJoined()) + "")))
        );
    }

    private String getDateFormat(long value) {
        if (value == 0) {
            return Color.translate("&eNone");
        }
        return new SimpleDateFormat("dd/MM/yyyy hh:mm a").format(new Timestamp(value));
    }

    /**
     * Translate the
     * @param time the time in long to translate
     * @return returns an string of time
     */
    public String convertPlaytime(final long time) {
        final int seconds = (int) (time / 1000) % 60 ;
        final int minutes = (int) ((time / (1000*60)) % 60);
        final int hours   = (int) ((time / (1000*60*60)) % 24);
        final int days =  (int) ((time / (1000*60*60*24)) % 30);


        if (time < 3600000L) {
            return minutes + "m, " + seconds + "s";
        } else if (time < 86400000L) {
            return hours + "h, " + minutes + "m, " + seconds + "s";
        } else {
            return days + "d, " + hours + "h, " + minutes + "m, " + seconds + "s";
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onAsyncPlayerPreLogin(AsyncPlayerPreLoginEvent event) {
        this.loadUserdata(event.getUniqueId(), event.getName());

        if(this.getUserdata(event.getUniqueId()) == null) {
            event.disallow(AsyncPlayerPreLoginEvent.Result.KICK_OTHER, Language.USERDATA_FAILED_TO_LOAD);
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onAsyncQUit(PlayerQuitEvent event) {
        final Player player = event.getPlayer();
        final Userdata userdata = this.getUserdata(player);
        Tasks.async(() -> this.saveUserdata(userdata));
    }
}

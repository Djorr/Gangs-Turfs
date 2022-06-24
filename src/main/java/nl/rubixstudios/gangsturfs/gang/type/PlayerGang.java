package nl.rubixstudios.gangsturfs.gang.type;

import lombok.Getter;
import lombok.Setter;
import nl.rubixstudios.gangsturfs.GangsTurfs;
import nl.rubixstudios.gangsturfs.data.Language;
import nl.rubixstudios.gangsturfs.gang.Gang;
import nl.rubixstudios.gangsturfs.gang.GangManager;
import nl.rubixstudios.gangsturfs.gang.GangPlayer;
import nl.rubixstudios.gangsturfs.gang.enums.Role;
import nl.rubixstudios.gangsturfs.userdata.Userdata;
import nl.rubixstudios.gangsturfs.utils.Color;
import nl.rubixstudios.gangsturfs.utils.RoundUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Getter
@Setter
public class PlayerGang extends Gang {

    private String announcement;

    private int kills;
    private double kdr;
    private int deaths;
    private int balance;
    private int points;

    private int turfsWon;

    private UUID createdByUuid;
    private UUID createdByName;

    private Timestamp creationDate;

    private Location safeHouse;

    private boolean friendlyFire;

    private transient Map<UUID, GangPlayer> members;
    private transient List<String> playerInvitations;

    private transient Location rallyLocation;

    public PlayerGang() {
        this.members = new HashMap<>();
        this.playerInvitations = new ArrayList<>();
    }

    public PlayerGang(String name) {
        super(name);

        this.members = new HashMap<>();
        this.playerInvitations = new ArrayList<>();
    }

    public void sendMessage(String message) {
        this.members.values().forEach(player -> player.sendMessage(Color.translate(message)));
    }

    public List<Player> getOnlineMembers() {
        return this.members.values().stream().map(member -> Bukkit.getPlayer(member.getUuid()))
                .filter(Objects::nonNull).collect(Collectors.toList());
    }

    private int getOnlineMembersCount(CommandSender sender) {
        Predicate<? super Player> condition = (player) -> Objects.nonNull(player) && (!(sender instanceof Player)
                || (((Player) sender).canSee(player)));

        return (int) this.members.values().stream().map(member -> Bukkit.getPlayer(member.getUuid())).filter(condition).count();
    }

    public GangPlayer getLeader() {
        return this.members.values().stream().filter(member -> member.getRole() == Role.LEADER).findFirst().orElse(null);
    }

    public GangPlayer getMember(OfflinePlayer player) {
        return player.hasPlayedBefore() || player.isOnline() ? this.getMember(player.getUniqueId()) : null;
    }

    public GangPlayer getMember(String name) {
        return this.getMember(Bukkit.getOfflinePlayer(name));
    }

    public GangPlayer getMember(Player player) {
        return this.getMember(player.getUniqueId());
    }

    public GangPlayer getMember(UUID uuid) {
        return this.members.get(uuid);
    }

    public void addMember(GangPlayer gangPlayer) {
        this.members.put(gangPlayer.getUuid(), gangPlayer);
    }

    public void removeMember(GangPlayer gangPlayer) {
        this.members.remove(gangPlayer.getUuid());
    }

    public String getSafeHouseString() {
        return this.safeHouse.getBlockX() + ", " + this.safeHouse.getBlockZ();
    }

    public int getTurfsWon() { return this.turfsWon; }

    public void addKill() { this.kills++; }
    public void addDeath() { this.deaths++; }
    public void addTurfWon() { this.turfsWon++; }

    public int getGangTotalKills() {
        return this.kills;
    }

    public int getGangTotalDeaths() {
        return this.deaths;
    }

    public double getKDR() {
        return this.kdr = (this.getDeaths() != 0) ? (double) this.getKills() / (double) this.getDeaths() : this.getKills();
    }

    public void onDeath(Player player) {
        final Player killer = player.getKiller();

        final PlayerGang playerGang = GangManager.getInstance().getPlayerGang(player.getUniqueId());

        PlayerGang killerGang = null;
        if (killer != null) killerGang = GangManager.getInstance().getPlayerGang(killer.getUniqueId());

        if (playerGang != null) playerGang.addDeath();
        if (killerGang != null) killerGang.addKill();

        this.sendMessage(Language.GANGS_MEMBER_DEATH);
    }

    public void showInformation(CommandSender sender) {
        AtomicReference<String> leader = new AtomicReference<>();
        StringJoiner coLeaders = new StringJoiner(", ");
        StringJoiner captains = new StringJoiner(", ");
        StringJoiner members = new StringJoiner(", ");
        StringJoiner allies = new StringJoiner(", ");

        AtomicInteger totalKills = new AtomicInteger();
        AtomicInteger totalDeaths = new AtomicInteger();

        this.members.values().forEach(member -> {
            OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(member.getUuid());
            Userdata userdata = GangsTurfs.getInstance().getUserdataController().getUserdata(offlinePlayer);

            if(userdata != null) {
                totalKills.getAndAdd(userdata.getKills());
                totalDeaths.getAndAdd(userdata.getDeaths());

                switch(member.getRole()) {
                    case LEADER: leader.set(this.getPlayerNameFormatted(member, userdata)); break;
                    case CAPTAIN: captains.add(this.getPlayerNameFormatted(member, userdata)); break;
                    case MEMBER: members.add(this.getPlayerNameFormatted(member, userdata)); break;
                }
            }
        });

        final List<String> showMessage = new ArrayList<>(Language.GANGS_PLAYER_GANG_SHOW);
        boolean isSameFaction = sender instanceof Player && GangManager.getInstance().getPlayerGang((Player) sender) == this;

        if (announcement == null) {
            announcement = "";
        }

        showMessage.removeIf(line -> line.contains("<co-leaders>") && coLeaders.length() == 0
                || line.contains("<autoRevive>") && !isSameFaction
                || line.contains("<lives>") && !isSameFaction
                || line.contains("<captains>") && captains.length() == 0
                || line.contains("<members>") && members.length() == 0
                || line.contains("<allies>") && allies.length() == 0);

        showMessage.forEach(line -> sender.sendMessage(line
                .replace("<gang>", this.getName(sender))
                .replace("<online-count>", String.valueOf(this.getOnlineMembersCount(sender)))
                .replace("<gang-size>", String.valueOf(this.members.size()))
                .replace("<safehouse-location>", this.safeHouse == null ? "Geen" : this.getSafeHouseString())
                .replace("<leader>", leader.get())
                .replace("<captains>", captains.toString())
                .replace("<members>", members.toString())
                .replace("<kills>", String.valueOf(this.getKills()))
                .replace("<deaths>", String.valueOf(this.getDeaths()))
                .replace("<gang-kdr>", String.valueOf(RoundUtil.roundDecimals(this.getKDR())))
                .replace("<turfswon>", String.valueOf(this.getTurfsWon()))
                .replace("<data>", new SimpleDateFormat("dd/MM/yyyy hh:mm a").format(this.getFounded()))));
        assert sender instanceof Player;
        Player p = (Player) sender;
        p.sendMessage(Color.translate("&7&m                                                                                "));
    }

    private String getPlayerNameFormatted(GangPlayer gPlayer, Userdata userdata) {
        final ChatColor color = ChatColor.GRAY;

        final OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(gPlayer.getUuid());

        return Language.GANGS_SHOW_NAME_FORMAT
                .replace("<player>", color + offlinePlayer
                        .getName()).replace("<kills>", String.valueOf(userdata.getKills()));
    }
}

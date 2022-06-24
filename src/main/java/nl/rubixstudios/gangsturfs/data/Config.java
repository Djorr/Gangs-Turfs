package nl.rubixstudios.gangsturfs.data;

import nl.rubixstudios.gangsturfs.GangsTurfs;

import java.io.File;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Config {

    public static long START_TIME;

    public static File GANGS_DIR;
    public static File USERDATA_DIR;
    public static File GAMES_DIR;
    public static File NPC_DIR;
    public static File UTILITIES_DIR;

    public static Set<String> DISABLED_LAZARUS_COMMANDS;
    public static Set<String> DISABLED_GANG_SUBCOMMANDS;

    public static int GANG_NAME_MINIMUM_LENGTH;
    public static int GANG_NAME_MAXIMUM_LENGTH;
    public static List<String> GANG_NAME_DISALLOWED_NAMES;

    public static int GANGS_AUTO_SAVE;

    public static int GANG_PLAYER_LIMIT;
    public static boolean GANG_JOIN_WHILE_FROZEN;
    public static boolean GANG_LEAVE_WHILE_FROZEN;
    public static boolean GANG_DISBAND_WHILE_FROZEN;
    public static boolean GANG_UNCLAIM_WHILE_FROZEN;
    public static boolean GANG_LEAVE_WHILE_IN_OWN_CLAIM;
    public static boolean GANG_PLAYERS_TAKE_OWN_DAMAGE;
    public static boolean GANG_ALLY_FRIENDLY_FIRE;
    public static int GANG_WARZONE_BREAK_AFTER_BUILD_AFTER;
    public static int GANG_WARZONE_BREAK_AFTER_OVERWORLD;
    public static int GANG_WARZONE_BREAK_AFTER_NETHER;

    public static int COMBAT_TAG_DURATION;
    public static List<String> COMBAT_TAG_DISABLED_COMMANDS;

    // Turf

    public static int TURF_COOLDOWN;
    public static int TURF_DURATION;
    public static int TURF_MINIMUM_PLAYERS_BEFORE_START;
    public static int TURF_MESSAGE_COOLDOWN;
    public static double TURF_WINNINGS;
    public static String TURF_REGION_START_WITH;

    // Gang

    public static double GANG_PRIZE;

    public Config() {
        final ConfigFile config = GangsTurfs.getInstance().getConfig();

        GANGS_DIR = new File(GangsTurfs.getInstance().getDataFolder(), "gangs");
        if (!GANGS_DIR.exists()) GANGS_DIR.mkdir();

        USERDATA_DIR = new File(GangsTurfs.getInstance().getDataFolder(), "userdata");
        if (!USERDATA_DIR.exists()) USERDATA_DIR.mkdir();

        GAMES_DIR = new File(GangsTurfs.getInstance().getDataFolder(), "games");
        if (!GAMES_DIR.exists()) GAMES_DIR.mkdir();

        NPC_DIR = new File(GangsTurfs.getInstance().getDataFolder(), "npcs");
        if (!NPC_DIR.exists()) NPC_DIR.mkdir();

        UTILITIES_DIR = new File(GangsTurfs.getInstance().getDataFolder(), "utilities");
        if (!UTILITIES_DIR.exists()) UTILITIES_DIR.mkdir();


        GANGS_AUTO_SAVE = config.getInt("GANGS_AUTO_SAVE");

        DISABLED_LAZARUS_COMMANDS = config.getStringList("DISABLED_LAZARUS_COMMANDS")
                .stream().map(String::toLowerCase).collect(Collectors.toSet());

        DISABLED_GANG_SUBCOMMANDS = config.getStringList("DISABLED_GANG_SUBCOMMANDS")
                .stream().map(String::toLowerCase).collect(Collectors.toSet());

        GANG_NAME_MINIMUM_LENGTH = config.getInt("GANG_NAME.MINIMUM_LENGTH");
        GANG_NAME_MAXIMUM_LENGTH = config.getInt("GANG_NAME.MAXIMUM_LENGTH");
        GANG_NAME_DISALLOWED_NAMES = config.getStringList("GANG_NAME.DISALLOWED_NAMES")
                .stream().map(String::toLowerCase).collect(Collectors.toList());

        GANG_PLAYER_LIMIT = config.getInt("GANG_PLAYER.GANG_LIMIT");
        GANG_JOIN_WHILE_FROZEN = config.getBoolean("GANG_PLAYER.JOIN_WHILE_FROZEN");
        GANG_LEAVE_WHILE_FROZEN = config.getBoolean("GANG_PLAYER.LEAVE_WHILE_FROZEN");
        GANG_DISBAND_WHILE_FROZEN = config.getBoolean("GANG_PLAYER.DISBAND_WHILE_FROZEN");
        GANG_UNCLAIM_WHILE_FROZEN = config.getBoolean("GANG_PLAYER.UNCLAIM_WHILE_FROZEN");
        GANG_LEAVE_WHILE_IN_OWN_CLAIM = config.getBoolean("GANG_PLAYER.LEAVE_WHILE_IN_OWN_CLAIM");
        GANG_PLAYERS_TAKE_OWN_DAMAGE = config.getBoolean("GANG_PLAYER.PLAYERS_TAKE_OWN_DAMAGE");
        GANG_ALLY_FRIENDLY_FIRE = config.getBoolean("GANG_PLAYER.ALLY_FRIENDLY_FIRE");
        GANG_WARZONE_BREAK_AFTER_BUILD_AFTER  = config.getInt("GANG_PLAYER.WARZONE_BREAK_AFTER.BUILD_AFTER");
        GANG_WARZONE_BREAK_AFTER_OVERWORLD = config.getInt("GANG_PLAYER.WARZONE_BREAK_AFTER.OVERWORLD");
        GANG_WARZONE_BREAK_AFTER_NETHER = config.getInt("GANG_PLAYER.WARZONE_BREAK_AFTER.NETHER");

        COMBAT_TAG_DURATION = config.getInt("COMBAT_TAG.DURATION");
        COMBAT_TAG_DISABLED_COMMANDS = config.getStringList("COMBAT_TAG.DISABLED_COMMANDS");

        TURF_COOLDOWN = config.getInt("TURF.COOLDOWN_BETWEEN_STARTING_NEW_TURF");
        TURF_DURATION = config.getInt("TURF.DURATION");
        TURF_MINIMUM_PLAYERS_BEFORE_START = config.getInt("TURF.MINIMUM_PLAYERS_BEFORE_STARTING");
        TURF_MESSAGE_COOLDOWN = config.getInt("TURF.MESSAGE_COOLDOWN");
        TURF_WINNINGS = config.getDouble("TURF.WINNINGS");
        TURF_REGION_START_WITH = config.getString("TURF.REGION_START_WITH");

        GANG_PRIZE = config.getDouble("GANG.PRIZE_CREATING_GANG");
    }
}

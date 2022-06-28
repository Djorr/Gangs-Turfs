package nl.rubixstudios.gangsturfs.data;

import nl.rubixstudios.gangsturfs.GangsTurfs;

import java.io.File;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Config {

    public static long START_TIME;

    // Directory Configs

    public static File GANGS_DIR;
    public static File USERDATA_DIR;
    public static File TURFS_DIR;
    public static File NPC_DIR;
    public static File UTILITIES_DIR;

    // Gang Config Data

    public static boolean GANG_ENABLED;

    public static double GANG_PRIZE_CREATING_GANG;

    public static boolean GANG_MESSAGES_SENT_MEMBER_ONLINE_MESSAGE;
    public static boolean GANG_MESSAGES_SENT_MEMBER_OFFLINE_MESSAGE;

    public static boolean GANG_OPTIONS_SHOW_COMMAND_ENABLE_FOR_PLAYERS;
    public static boolean GANG_OPTIONS_SHOW_COMMAND_ENABLE_FOR_STAFF;

    public static List<String> GANG_NAME_DISALLOWED_NAMES;

    public static int GANG_NAME_MINIMUM_LENGTH;
    public static int GANG_NAME_MAXIMUM_LENGTH;

    public static int GANG_MAXIMUM_MEMBERS;

    // Turf Config Data

    public static boolean TURF_ENABLED;

    public static int TURF_COOLDOWN_BETWEEN_STARTING_NEW_TURF;
    public static int TURF_MINIMUM_PLAYERS_BEFORE_STARTING;
    public static int TURF_TURF_ACTIVE_MESSAGE_COOLDOWN;

    public static double TURF_WINNINGS_FOR_EACH_GANG;
    public static int TURF_GAME_DURATION;

    public static String TURF_REGIONS_START_WITH;

    // Combat-Tag Config Data

    public static boolean COMBAT_TAG_ENABLED;

    public static int COMBAT_TAG_DURATION;

    public static boolean COMBAT_TAG_DISABLE_ENDERCHESTS;
    public static List<String> COMBAT_TAG_DISABLED_ITEMS;

    public static List<String> COMBAT_TAG_DISABLED_COMMANDS;
    public static boolean COMBAT_TAG_DISABLE_OP_COMMANDS;

    public Config() {
        // Directory configs

        final ConfigFile config = GangsTurfs.getInstance().getConfig();

        GANGS_DIR = new File(GangsTurfs.getInstance().getDataFolder(), "gangs");
        if (!GANGS_DIR.exists()) GANGS_DIR.mkdir();

        USERDATA_DIR = new File(GangsTurfs.getInstance().getDataFolder(), "userdata");
        if (!USERDATA_DIR.exists()) USERDATA_DIR.mkdir();

        TURFS_DIR = new File(GangsTurfs.getInstance().getDataFolder(), "turfs");
        if (!TURFS_DIR.exists()) TURFS_DIR.mkdir();

        NPC_DIR = new File(GangsTurfs.getInstance().getDataFolder(), "npcs");
        if (!NPC_DIR.exists()) NPC_DIR.mkdir();

        UTILITIES_DIR = new File(GangsTurfs.getInstance().getDataFolder(), "combat");
        if (!UTILITIES_DIR.exists()) UTILITIES_DIR.mkdir();

        // Gang Data Config

        GANG_ENABLED = config.getBoolean("GANG.ENABLED");

        GANG_PRIZE_CREATING_GANG = config.getDouble("GANG.PRIZE_CREATING_GANG");

        GANG_MESSAGES_SENT_MEMBER_ONLINE_MESSAGE = config.getBoolean("GANG.MESSAGES.SENT_ONLINE_MESSAGE");
        GANG_MESSAGES_SENT_MEMBER_OFFLINE_MESSAGE = config.getBoolean("GANG.MESSAGES.SENT_OFFLINE_MESSAGE");

        GANG_OPTIONS_SHOW_COMMAND_ENABLE_FOR_PLAYERS = config.getBoolean("GANG.OPTIONS.SHOW_COMMAND.ENABLED_FOR_PLAYERS");
        GANG_OPTIONS_SHOW_COMMAND_ENABLE_FOR_STAFF = config.getBoolean("GANG.OPTIONS.SHOW_COMMAND.ENABLED_FOR_STAFF");

        GANG_NAME_DISALLOWED_NAMES = config.getStringList("GANG.OPTIONS.NAMES.GANG_NAME_DISALLOWED_NAMES")
                .stream().map(String::toLowerCase).collect(Collectors.toList());

        GANG_NAME_MINIMUM_LENGTH = config.getInt("GANG.OPTIONS.NAMES.GANG_NAME_MINIMUM_LENGTH");
        GANG_NAME_MAXIMUM_LENGTH = config.getInt("GANG.OPTIONS.NAMES.GAME_NAME_MAXIMUM_LENGTH");

        GANG_MAXIMUM_MEMBERS = config.getInt("GANG.OPTIONS.MEMBERS.MAX_MEMBERS");

        // Turf Data Config

        TURF_ENABLED = config.getBoolean("TURF.ENABLED");

        TURF_COOLDOWN_BETWEEN_STARTING_NEW_TURF = config.getInt("TURF.COOLDOWN_BETWEEN_STARTING_NEW_TURF");
        TURF_MINIMUM_PLAYERS_BEFORE_STARTING = config.getInt("TURF.MINIMUM_PLAYERS_BEFORE_STARTING");
        TURF_TURF_ACTIVE_MESSAGE_COOLDOWN = config.getInt("TURF.TURF_ACTIVE_MESSAGE_COOLDOWN");

        TURF_WINNINGS_FOR_EACH_GANG = config.getDouble("TURF.WINNINGS_FOR_EACH_GANG");
        TURF_GAME_DURATION = config.getInt("TURF.GAME_DURATION");

        TURF_REGIONS_START_WITH = config.getString("TURF.REGIONS_START_WITH");

        // Combat Data Config

        COMBAT_TAG_ENABLED = config.getBoolean("COMBAT_TAG.ENABLED");

        COMBAT_TAG_DURATION = config.getInt("COMBAT_TAG.DURATION");
        COMBAT_TAG_DISABLE_ENDERCHESTS = config.getBoolean("COMBAT_TAG.DISABLE_ENDERCHESTS");
        COMBAT_TAG_DISABLED_COMMANDS = config.getStringList("COMBAT_TAG.DISABLED_COMMANDS")
                .stream().map(String::toLowerCase).collect(Collectors.toList());
        COMBAT_TAG_DISABLE_OP_COMMANDS = config.getBoolean("COMBAT_TAG.DISABLE_OP_COMMANDS");
    }
}

package nl.rubixstudios.gangsturfs.data;

import nl.rubixstudios.gangsturfs.GangsTurfs;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Language {

    public static String PREFIX;
    public static String GANG_PREFIX;
    public static String SERVICE_PREFIX;
    public static String TURF_PREFIX;
    
    public static String USERDATA_FAILED_TO_LOAD;
    public static String PLAYER_ALREADY_ONLINE;
    public static String SERVER_IS_STILL_STARTING;

    public static String COMMANDS_FOR_PLAYERS_ONLY;
    public static String COMMANDS_FOR_CONSOLE_ONLY;
    public static String COMMANDS_NO_PERMISSION;
    public static String COMMANDS_PLAYER_NOT_ONLINE;
    public static String COMMANDS_PLAYER_NOT_FOUND;
    public static String COMMANDS_COMMAND_NOT_FOUND;
    public static String COMMANDS_INVALID_NUMBER;
    public static String COMMANDS_INVALID_DURATION;
    public static String COMMANDS_COOLDOWN;

    public static String PLUGIN_RELOAD_MESSAGE;

    public static String GANGS_NAME_TOO_SHORT;
    public static String GANGS_NAME_TOO_BIG;
    public static String GANGS_BLOCKED_GANG_NAME;
    public static String GANGS_NAME_NOT_ALPHANUMERIC;
    public static String GANGS_GANG_ALREADY_EXISTS;
    public static String GANGS_GANG_DOESNT_EXIST;
    public static String GANGS_ALREADY_IN_GANG_SELF;
    public static String GANGS_ALREADY_IN_GANG_OTHERS;
    public static String GANGS_NOT_IN_GANG;
    public static String GANGS_NOT_IN_GANG_SELF;
    public static String GANGS_NOT_IN_GANG_OTHERS;
    public static String GANGS_MUST_BE_LEADER;
    public static String GANGS_NO_PERMISSION;
    public static String GANGS_NO_PERMISSION_ROLE;
    public static String GANGS_MEMBER_ONLINE;
    public static String GANGS_MEMBER_OFFLINE;
    public static String GANGS_MEMBER_DEATH;
    public static String GANGS_ALLIES_DISABLED;
    public static String GANGS_NO_LONGER_FROZEN;

    public static String GANGS_PROTECTION_DENY_BUILD;
    public static String GANGS_PROTECTION_DENY_INTERACT;

    public static String GANGS_BALANCE_MESSAGE;

    public static String GANGS_CREATE_USAGE;
    public static String GANGS_CREATED;
    public static String GANGS_CREATE_COOLDOWN;

    public static String GANGS_DEMOTE_USAGE;
    public static String GANGS_DEMOTE_DEMOTED;
    public static String GANGS_DEMOTE_MIN_DEMOTE;
    public static String GANGS_CANNOT_DEMOTE_SELF;

    public static String GANGS_DEPOSIT_USAGE;
    public static String GANGS_DEPOSIT_DEPOSITED;
    public static String GANGS_DEPOSIT_CAN_NOT_DEPOSIT_ZERO;
    public static String GANGS_DEPOSIT_NOT_ENOUGH_MONEY;

    public static String GANGS_DISBANDED;
    public static String GANGS_DISBAND_CLAIM_MONEY_REFUNDED;
    public static String GANGS_DISBAND_RAIDABLE_DENY;

    public static String GANGS_FORCE_DEMOTE_USAGE;
    public static String GANGS_FORCE_DEMOTED_SENDER;
    public static String GANGS_FORCE_DEMOTED_GANG;
    public static String GANGS_FORCE_DEMOTE_MIN_DEMOTE;
    public static String GANGS_FORCE_DEMOTE_CANNOT_DEMOTE_LEADER;

    public static String GANGS_FORCE_JOIN_USAGE;
    public static String GANGS_FORCE_JOINED;

    public static String GANGS_FORCE_KICK_USAGE;
    public static String GANGS_FORCE_KICK_CANNOT_KICK_LEADER;
    public static String GANGS_FORCE_KICKED_SENDER;
    public static String GANGS_FORCE_KICKED_SELF;
    public static String GANGS_FORCE_KICKED_OTHERS;

    public static String GANGS_FORCE_LEADER_USAGE;
    public static String GANGS_FORCE_LEADER_CHANGED_SENDER;
    public static String GANGS_FORCE_LEADER_CHANGED_GANG;
    public static String GANGS_FORCE_LEADER_ALREADY_LEADER;

    public static String GANGS_FORCE_PROMOTE_USAGE;
    public static String GANGS_FORCE_PROMOTED_SENDER;
    public static String GANGS_FORCE_PROMOTED_GANG;
    public static String GANGS_FORCE_PROMOTE_MAX_PROMOTE;

    public static String GANGS_FORCE_RENAME_USAGE;
    public static String GANGS_FORCE_RENAMED;
    public static String GANGS_FORCE_RENAME_SAME_NAME;

    public static String GANGS_HELP_PAGE_NOT_FOUND;
    public static Map<Integer, List<String>> GANGS_HELP_PAGES;

    public static String COMBAT_HELP_PAGE_NOT_FOUND;
    public static Map<Integer, List<String>> COMBAT_HELP_PAGES;

    public static String NPC_HELP_PAGE_NOT_FOUND;
    public static Map<Integer, List<String>> NPC_HELP_PAGES;

    public static String TURF_HELP_PAGE_NOT_FOUND;
    public static Map<Integer, List<String>> TURF_HELP_PAGES;

    public static String GANGS_INVITE_USAGE;
    public static String GANGS_INVITED_SELF;
    public static String GANGS_INVITED_OTHERS;
    public static String GANGS_INVITE_ALREADY_INVITED;
    public static String GANGS_INVITE_GANG_FULL;
    public static String GANGS_INVITE_GANG_MEMBERS_STILL_IN_COMBAT;
    public static String GANGS_INVITE_HOVER_TEXT;

    public static String GANGS_JOIN_USAGE;
    public static String GANGS_JOINED;
    public static String GANGS_NOT_INVITED;
    public static String GANGS_JOIN_GANG_FULL;
    public static String GANGS_CANNOT_JOIN_WHILE_REGENERATING;
    public static String GANGS_CANNOT_INVITE_WHILE_REGENERATING;

    public static String GANGS_KICK_USAGE;
    public static String GANGS_KICKED_SELF;
    public static String GANGS_KICKED_OTHERS;

    public static String GANGS_LEFT_SELF;
    public static String GANGS_LEFT_OTHERS;
    public static String GANGS_LEADER_LEAVE;

    public static String GANGS_LIST_PAGE_NOT_FOUND;
    public static String GANGS_LIST_GANG_FORMAT;
    public static List<String> GANGS_LIST_HEADER;
    public static List<String> GANGS_LIST_FOOTER;

    public static String GANGS_PROMOTE_USAGE;
    public static String GANGS_PROMOTE_PROMOTED;
    public static String GANGS_PROMOTE_MAX_PROMOTE;
    public static String GANGS_CANNOT_PROMOTE_SELF;

    public static String GANGS_SAVED;

    public static String GANGS_SET_BALANCE_USAGE;
    public static String GANGS_SET_BALANCE_CHANGED_SENDER;
    public static String GANGS_SET_BALANCE_CHANGED_GANG;

    public static String GANGS_SHOW_HOVER_TEXT;
    public static String GANGS_SHOW_NAME_FORMAT;
    public static List<String> GANGS_PLAYER_GANG_SHOW;
    public static String GANGS_SYSTEM_CLAIM_FORMAT;
    public static List<String> GANGS_SYSTEM_GANG_SHOW;

    public static String GANGS_TP_HERE_USAGE;
    public static String GANGS_TP_HERE_TELEPORTED_SENDER;
    public static String GANGS_TP_HERE_TELEPORTED_GANG;

    public static String GANGS_UNINVITE_USAGE;
    public static String GANGS_UNINVITE_UNINVITED;
    public static String GANGS_UNINVITE_ALL;
    public static String GANGS_UNINVITE_NOT_INVITED;

    public static String GANGS_WITHDRAW_USAGE;
    public static String GANGS_WITHDRAW_WITHDRAWN;
    public static String GANGS_WITHDRAW_NOT_ENOUGH_MONEY;

    public static String DISABLED_COMMANDS_COLON_DISABLED;
    public static String DISABLED_COMMANDS_MESSAGE;

    public static List<String> SERVICES_COMMAND_USAGE_PLAYER;
    public static List<String> SERVICES_COMMAND_USAGE_ADMIN;

    public static String SERVICES_APPLIED_OTHERS;
    public static String SERVICES_EQUIPPED;

    public static String SERVICES_EXCEPTION_ONE_TIME_ONLY;
    public static String SERVICES_EXCEPTION_COOLDOWN;
    public static String SERVICES_EXCEPTION_NO_PERMISSION;
    public static String SERVICES_EXCEPTION_ALREADY_EXISTS;
    public static String SERVICES_EXCEPTION_DOESNT_EXISTS;
    public static String SERVICES_EXCEPTION_FULL_INVENTORY;
    public static String SERVICES_EXCEPTION_KITMAP_ONLY_IN_SAFEZONE;

    public static String SERVICES_CREATE_USAGE;
    public static String SERVICES_CREATE_CREATED;
    public static String SERVICES_CREATE_INVALID_KIT_TYPE;

    public static String SERVICES_EDIT_USAGE;
    public static String SERVICES_EDIT_EDITED;

    public static String SERVICES_GIVE_USAGE;

    public static String SERVICES_LIST_NO_KITS;
    public static String SERVICES_LIST_FORMAT;

    public static String SERVICES_REMOVE_USAGE;
    public static String SERVICES_REMOVE_REMOVED;
    public static String SERVICES_REMOVE_CANNOT_REMOVE_SPECIAL_EVENT_KIT;

    public static String SERVICES_SET_DELAY_USAGE;
    public static String SERVICES_SET_DELAY_CHANGED;

    public static String LAG_COMMAND_WORLD_FORMAT;
    public static List<String> LAG_COMMAND_MESSAGE;

    public static List<String> LIST_COMMAND;

    public static List<String> REBOOT_USAGE;
    public static String REBOOT_NOT_RUNNING;
    public static String REBOOT_ALREADY_RUNNING;
    public static String REBOOT_PLAYER_KICK_MESSAGE;
    public static List<String> REBOOT_BROADCAST;
    public static List<String> REBOOT_CANCELED;

    public static String SERVICE_MODE_ENABLED;
    public static String SERVICE_MODE_DISABLED;

    public static List<String> IDENTITY_SEE_ID;

    public static String PLAYTIME_COMMAND_USAGE;
    public static String PLAYTIME_COMMAND_MESSAGE;

    public static String COMBAT_TAG_COMMAND_DENY;
    public static String COMBAT_TAG_EXPIRED;
    public static String COMBAT_TAG_END_PORTAL_TELEPORT_DENY;
    public static String COMBAT_TAG_ENDERCHEST_DENY;
    public static String COMBAT_TAG_BLOCK_BREAK_DENY;
    public static String COMBAT_TAG_BLOCK_PLACE_DENY;

    // NPC Messages

    public static String NPCS_PREFIX;
    public static String NPCS_NPC_CREATED;
    public static String NPCS_NPC_REMOVED;
    public static String NPCS_TYPE_ECONOMY_SHOP_DESC;
    public static String NPCS_TYPE_ECONOMY_ORES_SMELTER_DESC;
    public static String NPCS_TYPE_ECONOMY_POTIONS_TRADER_DESC;
    public static String NPCS_TYPE_STATISTIC_PLAYERS_DESC;
    public static String NPCS_TYPE_STATISTIC_FACTIONS_DESC;

    public Language() {
        final ConfigFile language = GangsTurfs.getInstance().getLanguage();

        PREFIX = language.getString("PREFIX");
        GANG_PREFIX = language.getString("GANG_PREFIX");
        SERVICE_PREFIX = language.getString("SERVICE_PREFIX");
        TURF_PREFIX = language.getString("TURF_PREFIX");

        USERDATA_FAILED_TO_LOAD = language.getString("KICK_REASONS.USERDATA_FAILED_TO_LOAD");
        PLAYER_ALREADY_ONLINE = language.getString("KICK_REASONS.PLAYER_ALREADY_ONLINE");
        SERVER_IS_STILL_STARTING = language.getString("KICK_REASONS.SERVER_IS_STILL_STARTING");

        COMMANDS_FOR_PLAYERS_ONLY = language.getString("COMMANDS.FOR_PLAYERS_ONLY");
        COMMANDS_FOR_CONSOLE_ONLY = language.getString("COMMANDS.FOR_CONSOLE_ONLY");
        COMMANDS_NO_PERMISSION = language.getString("COMMANDS.NO_PERMISSION");
        COMMANDS_PLAYER_NOT_ONLINE = language.getString("COMMANDS.PLAYER_NOT_ONLINE");
        COMMANDS_PLAYER_NOT_FOUND = language.getString("COMMANDS.PLAYER_NOT_FOUND");
        COMMANDS_COMMAND_NOT_FOUND = language.getString("COMMANDS.COMMAND_NOT_FOUND");
        COMMANDS_INVALID_NUMBER = language.getString("COMMANDS.INVALID_NUMBER");
        COMMANDS_INVALID_DURATION = language.getString("COMMANDS.INVALID_DURATION");
        COMMANDS_COOLDOWN = language.getString("COMMANDS.COOLDOWN");

        PLUGIN_RELOAD_MESSAGE = language.getString("PLUGIN_RELOAD_MESSAGE");

        GANGS_NAME_TOO_SHORT = language.getString("GANGS.NAME_TOO_SHORT");
        GANGS_NAME_TOO_BIG = language.getString("GANGS.NAME_TOO_BIG");
        GANGS_BLOCKED_GANG_NAME = language.getString("GANGS.BLOCKED_GANG_NAME");
        GANGS_NAME_NOT_ALPHANUMERIC = language.getString("GANGS.NAME_NOT_ALPHANUMERIC");
        GANGS_GANG_ALREADY_EXISTS = language.getString("GANGS.GANG_ALREADY_EXISTS");
        GANGS_GANG_DOESNT_EXIST = language.getString("GANGS.GANG_DOESNT_EXIST");
        GANGS_ALREADY_IN_GANG_SELF = language.getString("GANGS.ALREADY_IN_GANG_SELF");
        GANGS_ALREADY_IN_GANG_OTHERS = language.getString("GANGS.ALREADY_IN_GANG_OTHERS");
        GANGS_NOT_IN_GANG = language.getString("GANGS.NOT_IN_GANG");
        GANGS_NOT_IN_GANG_SELF = language.getString("GANGS.NOT_IN_GANG_SELF");
        GANGS_NOT_IN_GANG_OTHERS = language.getString("GANGS.NOT_IN_GANG_OTHERS");
        GANGS_MUST_BE_LEADER = language.getString("GANGS.MUST_BE_LEADER");
        GANGS_NO_PERMISSION = language.getString("GANGS.NO_PERMISSION");
        GANGS_NO_PERMISSION_ROLE = language.getString("GANGS.NO_PERMISSION_ROLE");
        GANGS_MEMBER_ONLINE = language.getString("GANGS.MEMBER_ONLINE");
        GANGS_MEMBER_OFFLINE = language.getString("GANGS.MEMBER_OFFLINE");
        GANGS_MEMBER_DEATH = language.getString("GANGS.MEMBER_DEATH");
        GANGS_ALLIES_DISABLED = language.getString("GANGS.ALLIES_DISABLED");
        GANGS_NO_LONGER_FROZEN = language.getString("GANGS.NO_LONGER_FROZEN");

        GANGS_PROTECTION_DENY_BUILD = language.getString("GANGS.PROTECTION_MESSAGES.DENY_BUILD");
        GANGS_PROTECTION_DENY_INTERACT= language.getString("GANGS.PROTECTION_MESSAGES.DENY_INTERACT");

        GANGS_BALANCE_MESSAGE = language.getString("GANGS.BALANCE_COMMAND.MESSAGE");

        GANGS_CREATE_USAGE = language.getString("GANGS.CREATE_COMMAND.USAGE");
        GANGS_CREATED = language.getString("GANGS.CREATE_COMMAND.CREATED");
        GANGS_CREATE_COOLDOWN = language.getString("GANGS.CREATE_COMMAND.COOLDOWN");

        GANGS_DEMOTE_USAGE = language.getString("GANGS.DEMOTE_COMMAND.USAGE");
        GANGS_DEMOTE_DEMOTED = language.getString("GANGS.DEMOTE_COMMAND.DEMOTED");
        GANGS_DEMOTE_MIN_DEMOTE = language.getString("GANGS.DEMOTE_COMMAND.MIN_DEMOTE");
        GANGS_CANNOT_DEMOTE_SELF = language.getString("GANGS.DEMOTE_COMMAND.CANNOT_DEMOTE_SELF");

        GANGS_DEPOSIT_USAGE = language.getString("GANGS.DEPOSIT_COMMAND.USAGE");
        GANGS_DEPOSIT_DEPOSITED = language.getString("GANGS.DEPOSIT_COMMAND.DEPOSITED");
        GANGS_DEPOSIT_CAN_NOT_DEPOSIT_ZERO = language.getString("GANGS.DEPOSIT_COMMAND.CAN_NOT_DEPOSIT_ZERO");
        GANGS_DEPOSIT_NOT_ENOUGH_MONEY = language.getString("GANGS.DEPOSIT_COMMAND.NOT_ENOUGH_MONEY");

        GANGS_DISBANDED = language.getString("GANGS.DISBAND_COMMAND.DISBANDED");
        GANGS_DISBAND_CLAIM_MONEY_REFUNDED = language.getString("GANGS.DISBAND_COMMAND.CLAIM_MONEY_REFUNDED");
        GANGS_DISBAND_RAIDABLE_DENY = language.getString("GANGS.DISBAND_COMMAND.RAIDABLE_DENY");

        GANGS_FORCE_DEMOTE_USAGE = language.getString("GANGS.FORCE_DEMOTE_COMMAND.USAGE");
        GANGS_FORCE_DEMOTED_SENDER = language.getString("GANGS.FORCE_DEMOTE_COMMAND.DEMOTED_SENDER");
        GANGS_FORCE_DEMOTED_GANG = language.getString("GANGS.FORCE_DEMOTE_COMMAND.DEMOTED_GANG");
        GANGS_FORCE_DEMOTE_MIN_DEMOTE = language.getString("GANGS.FORCE_DEMOTE_COMMAND.MIN_DEMOTE");
        GANGS_FORCE_DEMOTE_CANNOT_DEMOTE_LEADER = language.getString("GANGS.FORCE_DEMOTE_COMMAND.CANNOT_DEMOTE_LEADER");

        GANGS_FORCE_JOIN_USAGE = language.getString("GANGS.FORCE_JOIN_COMMAND.USAGE");
        GANGS_FORCE_JOINED = language.getString("GANGS.FORCE_JOIN_COMMAND.JOINED");

        GANGS_FORCE_KICK_USAGE = language.getString("GANGS.FORCE_KICK_COMMAND.USAGE");
        GANGS_FORCE_KICK_CANNOT_KICK_LEADER = language.getString("GANGS.FORCE_KICK_COMMAND.CANNOT_KICK_LEADER");
        GANGS_FORCE_KICKED_SENDER = language.getString("GANGS.FORCE_KICK_COMMAND.KICKED_SENDER");
        GANGS_FORCE_KICKED_SELF = language.getString("GANGS.FORCE_KICK_COMMAND.KICKED_SELF");
        GANGS_FORCE_KICKED_OTHERS = language.getString("GANGS.FORCE_KICK_COMMAND.KICKED_OTHERS");

        GANGS_FORCE_LEADER_USAGE = language.getString("GANGS.FORCE_LEADER_COMMAND.USAGE");
        GANGS_FORCE_LEADER_CHANGED_SENDER = language.getString("GANGS.FORCE_LEADER_COMMAND.CHANGED_SENDER");
        GANGS_FORCE_LEADER_CHANGED_GANG = language.getString("GANGS.FORCE_LEADER_COMMAND.CHANGED_GANG");
        GANGS_FORCE_LEADER_ALREADY_LEADER = language.getString("GANGS.FORCE_LEADER_COMMAND.ALREADY_LEADER");

        GANGS_FORCE_PROMOTE_USAGE = language.getString("GANGS.FORCE_PROMOTE_COMMAND.USAGE");
        GANGS_FORCE_PROMOTED_SENDER = language.getString("GANGS.FORCE_PROMOTE_COMMAND.PROMOTED_SENDER");
        GANGS_FORCE_PROMOTED_GANG = language.getString("GANGS.FORCE_PROMOTE_COMMAND.PROMOTED_GANG");
        GANGS_FORCE_PROMOTE_MAX_PROMOTE = language.getString("GANGS.FORCE_PROMOTE_COMMAND.MAX_PROMOTE");

        GANGS_FORCE_RENAME_USAGE = language.getString("GANGS.FORCE_RENAME_COMMAND.USAGE");
        GANGS_FORCE_RENAMED = language.getString("GANGS.FORCE_RENAME_COMMAND.RENAMED");
        GANGS_FORCE_RENAME_SAME_NAME = language.getString("GANGS.FORCE_RENAME_COMMAND.SAME_NAME");

        GANGS_HELP_PAGE_NOT_FOUND = language.getString("GANGS.HELP_COMMAND.PAGE_NOT_FOUND");
        GANGS_HELP_PAGES = new HashMap<>();
        language.getConfigurationSection("GANGS.HELP_COMMAND.PAGES").getKeys(false).forEach(key ->
                GANGS_HELP_PAGES.put(Integer.parseInt(key), language.getStringList("GANGS.HELP_COMMAND.PAGES." + key)));

        COMBAT_HELP_PAGE_NOT_FOUND = language.getString("COMBAT.HELP_COMMAND.PAGE_NOT_FOUND");
        COMBAT_HELP_PAGES = new HashMap<>();
        language.getConfigurationSection("COMBAT.HELP_COMMAND.PAGES").getKeys(false).forEach(key ->
                COMBAT_HELP_PAGES.put(Integer.parseInt(key), language.getStringList("COMBAT.HELP_COMMAND.PAGES." + key)));

        NPC_HELP_PAGE_NOT_FOUND = language.getString("NPCS.HELP_COMMAND.PAGE_NOT_FOUND");
        NPC_HELP_PAGES = new HashMap<>();
        language.getConfigurationSection("NPCS.HELP_COMMAND.PAGES").getKeys(false).forEach(key ->
                NPC_HELP_PAGES.put(Integer.parseInt(key), language.getStringList("NPCS.HELP_COMMAND.PAGES." + key)));

        TURF_HELP_PAGE_NOT_FOUND = language.getString("COMBAT.HELP_COMMAND.PAGE_NOT_FOUND");
        TURF_HELP_PAGES = new HashMap<>();
        language.getConfigurationSection("TURF.HELP_COMMAND.PAGES").getKeys(false).forEach(key ->
                TURF_HELP_PAGES.put(Integer.parseInt(key), language.getStringList("TURF.HELP_COMMAND.PAGES." + key)));

        GANGS_INVITE_USAGE = language.getString("GANGS.INVITE_COMMAND.USAGE");
        GANGS_INVITED_SELF = language.getString("GANGS.INVITE_COMMAND.INVITED_SELF");
        GANGS_INVITED_OTHERS = language.getString("GANGS.INVITE_COMMAND.INVITED_OTHERS");
        GANGS_INVITE_ALREADY_INVITED = language.getString("GANGS.INVITE_COMMAND.ALREADY_INVITED");
        GANGS_INVITE_GANG_FULL = language.getString("GANGS.INVITE_COMMAND.GANG_FULL");
        GANGS_INVITE_GANG_MEMBERS_STILL_IN_COMBAT = language.getString("GANGS.INVITE_COMMAND.GANG_MEMBERS_STILL_IN_COMBAT");
        GANGS_INVITE_HOVER_TEXT = language.getString("GANGS.INVITE_COMMAND.HOVER_TEXT");

        GANGS_JOIN_USAGE = language.getString("GANGS.JOIN_COMMAND.USAGE");
        GANGS_JOINED = language.getString("GANGS.JOIN_COMMAND.JOINED");
        GANGS_NOT_INVITED = language.getString("GANGS.JOIN_COMMAND.NOT_INVITED");
        GANGS_JOIN_GANG_FULL = language.getString("GANGS.JOIN_COMMAND.GANG_FULL");
        GANGS_CANNOT_JOIN_WHILE_REGENERATING = language.getString("GANGS.JOIN_COMMAND.CANNOT_JOIN_WHILE_REGENERATING");
        GANGS_CANNOT_INVITE_WHILE_REGENERATING = language.getString("GANGS.INVITE_COMMAND.CANNOT_INVITE_WHILE_REGENERATING");

        GANGS_KICK_USAGE = language.getString("GANGS.KICK_COMMAND.USAGE");
        GANGS_KICKED_SELF = language.getString("GANGS.KICK_COMMAND.KICKED_SELF");
        GANGS_KICKED_OTHERS = language.getString("GANGS.KICK_COMMAND.KICKED_OTHERS");


        GANGS_LEFT_SELF = language.getString("GANGS.LEAVE_COMMAND.LEFT_SELF");
        GANGS_LEFT_OTHERS = language.getString("GANGS.LEAVE_COMMAND.LEFT_OTHERS");
        GANGS_LEADER_LEAVE = language.getString("GANGS.LEAVE_COMMAND.LEADER_LEAVE");

        GANGS_LIST_PAGE_NOT_FOUND = language.getString("GANGS.LIST_COMMAND.PAGE_NOT_FOUND");
        GANGS_LIST_GANG_FORMAT = language.getString("GANGS.LIST_COMMAND.GANG_FORMAT");
        GANGS_LIST_HEADER = language.getStringList("GANGS.LIST_COMMAND.HEADER");
        GANGS_LIST_FOOTER = language.getStringList("GANGS.LIST_COMMAND.FOOTER");

        GANGS_PROMOTE_USAGE = language.getString("GANGS.PROMOTE_COMMAND.USAGE");
        GANGS_PROMOTE_PROMOTED = language.getString("GANGS.PROMOTE_COMMAND.PROMOTED");
        GANGS_PROMOTE_MAX_PROMOTE = language.getString("GANGS.PROMOTE_COMMAND.MAX_PROMOTE");
        GANGS_CANNOT_PROMOTE_SELF = language.getString("GANGS.PROMOTE_COMMAND.CANNOT_PROMOTE_SELF");

        GANGS_SAVED = language.getString("GANGS.SAVE_COMMAND.SAVED");

        GANGS_SET_BALANCE_USAGE = language.getString("GANGS.SET_BALANCE_COMMAND.USAGE");
        GANGS_SET_BALANCE_CHANGED_SENDER = language.getString("GANGS.SET_BALANCE_COMMAND.CHANGED_SENDER");
        GANGS_SET_BALANCE_CHANGED_GANG = language.getString("GANGS.SET_BALANCE_COMMAND.CHANGED_GANG");

        GANGS_SHOW_HOVER_TEXT = language.getString("GANGS.SHOW_COMMAND.HOVER_TEXT");
        GANGS_SHOW_NAME_FORMAT = language.getString("GANGS.SHOW_COMMAND.NAME_FORMAT");
        GANGS_PLAYER_GANG_SHOW = language.getStringList("GANGS.SHOW_COMMAND.PLAYER_GANG_MESSAGE");
        GANGS_SYSTEM_CLAIM_FORMAT = language.getString("GANGS.SHOW_COMMAND.SYSTEM_CLAIM_FORMAT");
        GANGS_SYSTEM_GANG_SHOW = language.getStringList("GANGS.SHOW_COMMAND.SYSTEM_GANG_MESSAGE");

        GANGS_TP_HERE_USAGE = language.getString("GANGS.TP_HERE_COMMAND.USAGE");
        GANGS_TP_HERE_TELEPORTED_SENDER = language.getString("GANGS.TP_HERE_COMMAND.TELEPORTED_SENDER");
        GANGS_TP_HERE_TELEPORTED_GANG = language.getString("GANGS.TP_HERE_COMMAND.TELEPORTED_GANG");

        GANGS_UNINVITE_USAGE = language.getString("GANGS.UNINVITE_COMMAND.USAGE");
        GANGS_UNINVITE_UNINVITED = language.getString("GANGS.UNINVITE_COMMAND.UNINVITED");
        GANGS_UNINVITE_ALL = language.getString("GANGS.UNINVITE_COMMAND.UNINVITE_ALL");
        GANGS_UNINVITE_NOT_INVITED = language.getString("GANGS.UNINVITE_COMMAND.NOT_INVITED");

        GANGS_WITHDRAW_USAGE = language.getString("GANGS.WITHDRAW_COMMAND.USAGE");
        GANGS_WITHDRAW_WITHDRAWN = language.getString("GANGS.WITHDRAW_COMMAND.WITHDRAWN");
        GANGS_WITHDRAW_NOT_ENOUGH_MONEY = language.getString("GANGS.WITHDRAW_COMMAND.NOT_ENOUGH_MONEY");

        DISABLED_COMMANDS_COLON_DISABLED = language.getString("BLOCKED_COMMANDS.COLON_DISABLED");
        DISABLED_COMMANDS_MESSAGE = language.getString("BLOCKED_COMMANDS.DISABLED_COMMAND_MESSAGE");

        SERVICES_COMMAND_USAGE_PLAYER = language.getStringList("SERVICES.COMMAND_USAGE_PLAYER");
        SERVICES_COMMAND_USAGE_ADMIN = language.getStringList("SERVICES.COMMAND_USAGE_ADMIN");

        SERVICES_APPLIED_OTHERS = language.getString("SERVICES.APPLIED_OTHERS");
        SERVICES_EQUIPPED = language.getString("SERVICES.EQUIPPED");

        SERVICES_EXCEPTION_ONE_TIME_ONLY = language.getString("SERVICES.EXCEPTION.ONE_TIME_ONLY");
        SERVICES_EXCEPTION_COOLDOWN = language.getString("SERVICES.EXCEPTION.COOLDOWN");
        SERVICES_EXCEPTION_NO_PERMISSION = language.getString("SERVICES.EXCEPTION.NO_PERMISSION");
        SERVICES_EXCEPTION_ALREADY_EXISTS = language.getString("SERVICES.EXCEPTION.ALREADY_EXISTS");
        SERVICES_EXCEPTION_DOESNT_EXISTS = language.getString("SERVICES.EXCEPTION.DOESNT_EXISTS");
        SERVICES_EXCEPTION_FULL_INVENTORY = language.getString("SERVICES.EXCEPTION.FULL_INVENTORY");
        SERVICES_EXCEPTION_KITMAP_ONLY_IN_SAFEZONE = language.getString("SERVICES.EXCEPTION.KITMAP_ONLY_IN_SAFEZONE");

        SERVICES_CREATE_USAGE = language.getString("SERVICES.CREATE_COMMAND.USAGE");
        SERVICES_CREATE_CREATED = language.getString("SERVICES.CREATE_COMMAND.CREATED");
        SERVICES_CREATE_INVALID_KIT_TYPE = language.getString("SERVICES.CREATE_COMMAND.INVALID_KIT_TYPE");


        SERVICES_EDIT_USAGE = language.getString("SERVICES.EDIT_COMMAND.USAGE");
        SERVICES_EDIT_EDITED = language.getString("SERVICES.EDIT_COMMAND.EDITED");

        SERVICES_GIVE_USAGE = language.getString("SERVICES.GIVE_COMMAND.USAGE");

        SERVICES_LIST_NO_KITS = language.getString("SERVICES.LIST_COMMAND.NO_KITS");
        SERVICES_LIST_FORMAT = language.getString("SERVICES.LIST_COMMAND.FORMAT");

        SERVICES_REMOVE_USAGE = language.getString("SERVICES.REMOVE_COMMAND.USAGE");
        SERVICES_REMOVE_REMOVED = language.getString("SERVICES.REMOVE_COMMAND.REMOVED");
        SERVICES_REMOVE_CANNOT_REMOVE_SPECIAL_EVENT_KIT = language.getString("SERVICES.REMOVE_COMMAND.CANNOT_REMOVE_SPECIAL_EVENT_KIT");

        SERVICES_SET_DELAY_USAGE = language.getString("SERVICES.SET_DELAY_COMMAND.USAGE");
        SERVICES_SET_DELAY_CHANGED = language.getString("SERVICES.SET_DELAY_COMMAND.CHANGED");

        LAG_COMMAND_WORLD_FORMAT = language.getString("LAG_COMMAND.WORLD_FORMAT");
        LAG_COMMAND_MESSAGE = language.getStringList("LAG_COMMAND.MESSAGE");

        LIST_COMMAND = language.getStringList("LIST_COMMAND");

        REBOOT_USAGE = language.getStringList("REBOOT_COMMAND.USAGE");
        REBOOT_NOT_RUNNING = language.getString("REBOOT_COMMAND.NOT_RUNNING");
        REBOOT_ALREADY_RUNNING = language.getString("REBOOT_COMMAND.ALREADY_RUNNING");
        REBOOT_PLAYER_KICK_MESSAGE = language.getString("REBOOT_COMMAND.PLAYER_KICK_MESSAGE");
        REBOOT_BROADCAST = language.getStringList("REBOOT_COMMAND.BROADCAST");
        REBOOT_CANCELED = language.getStringList("REBOOT_COMMAND.CANCELED");

        SERVICE_MODE_ENABLED = language.getString("SERVICE_MODE.ENABLED");
        SERVICE_MODE_DISABLED = language.getString("SERVICE_MODE.DISABLED");

        IDENTITY_SEE_ID = language.getStringList("IDENTITY.IDENTITY_SEE_ID");

        PLAYTIME_COMMAND_USAGE = language.getString("PLAYTIME_COMMAND.USAGE");
        PLAYTIME_COMMAND_MESSAGE = language.getString("PLAYTIME_COMMAND.MESSAGE");

        // Combat Messages

        COMBAT_TAG_COMMAND_DENY = language.getString("COMBAT_TAG.COMMAND_DENY");
        COMBAT_TAG_EXPIRED = language.getString("COMBAT_TAG.TAG_EXPIRED");
        COMBAT_TAG_END_PORTAL_TELEPORT_DENY = language.getString("COMBAT_TAG.END_PORTAL_TELEPORT_DENY");
        COMBAT_TAG_ENDERCHEST_DENY = language.getString("COMBAT_TAG.ENDERCHEST_DENY");
        COMBAT_TAG_BLOCK_BREAK_DENY = language.getString("COMBAT_TAG.BLOCK_BREAK_DENY");
        COMBAT_TAG_BLOCK_PLACE_DENY = language.getString("COMBAT_TAG.BLOCK_PLACE_DENY");

        // NPC Messages

        NPCS_PREFIX = language.getString("NPCS.PREFIX");
        NPCS_NPC_CREATED = language.getString("NPCS.NPC.CREATED");
        NPCS_NPC_REMOVED = language.getString("NPCS.NPC.REMOVED");
        NPCS_TYPE_ECONOMY_SHOP_DESC = language.getString("NPCS.TYPE.ECONOMY.SHOP.DESC");
        NPCS_TYPE_ECONOMY_ORES_SMELTER_DESC = language.getString("NPCS.TYPE.ECONOMY.ORES_SMELTER.DESC");
        NPCS_TYPE_ECONOMY_POTIONS_TRADER_DESC = language.getString("NPCS.TYPE.ECONOMY.POTIONS_TRADER.DESC");
        NPCS_TYPE_STATISTIC_PLAYERS_DESC = language.getString("NPCS.TYPE.STATISTIC.PLAYERS.DESC");
        NPCS_TYPE_STATISTIC_FACTIONS_DESC = language.getString("NPCS.TYPE.STATISTIC.FACTIONS.DESC");
    }
}

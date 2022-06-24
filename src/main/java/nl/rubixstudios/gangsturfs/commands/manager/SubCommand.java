package nl.rubixstudios.gangsturfs.commands.manager;

import lombok.Getter;
import lombok.Setter;
import nl.rubixstudios.gangsturfs.data.Language;
import nl.rubixstudios.gangsturfs.utils.StringUtils;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

@Getter
public abstract class SubCommand {

    protected final String name;
    protected final List<String> allies;
    protected final String permission;

    @Setter protected String prefix;
    @Setter private boolean executeAsync;

    protected final boolean forPlayersOnly;

    public SubCommand(String name) {
        this(name, new ArrayList<>());
    }

    public SubCommand(String name, List<String> allies) {
        this(name, allies, null);
    }

    public SubCommand(String name, boolean forPlayersOnly) {
        this(name, new ArrayList<>(), forPlayersOnly);
    }

    public SubCommand(String name, String permission) {
        this(name, new ArrayList<>(), permission);
    }

    public SubCommand(String name, List<String> allies, String permission) {
        this(name, allies, permission, false);
    }

    public SubCommand(String name, String permission, boolean forPlayersOnly) {
        this(name, new ArrayList<>(), permission, forPlayersOnly);
    }

    public SubCommand(String name, List<String> allies, boolean forPlayersOnly) {
        this(name, allies, null, forPlayersOnly);
    }

    public SubCommand(String name, List<String> allies, String permission, boolean forPlayersOnly) {
        this.name = name;
        this.allies = allies;
        this.permission = permission;
        this.forPlayersOnly = forPlayersOnly;
    }

    protected boolean checkConsoleSender(CommandSender sender) {
        if(sender instanceof ConsoleCommandSender) {
            sender.sendMessage(this.prefix + Language.COMMANDS_FOR_PLAYERS_ONLY);
            return false;
        }
        return true;
    }

    protected boolean checkOfflinePlayer(CommandSender sender, OfflinePlayer offlinePlayer, String name) {
        if(!offlinePlayer.hasPlayedBefore() && !offlinePlayer.isOnline()) {
            sender.sendMessage(this.prefix + Language.COMMANDS_PLAYER_NOT_FOUND.replace("<player>", name));
            return false;
        }
        return true;
    }

    protected boolean checkPlayer(CommandSender sender, Player player, String name) {
        if(player == null) {
            sender.sendMessage(this.prefix + Language.COMMANDS_PLAYER_NOT_ONLINE.replace("<player>", name));
            return false;
        }
        return true;
    }

    protected boolean checkNumber(CommandSender sender, String number) {
        if(!StringUtils.isInteger(number)) {
            sender.sendMessage(this.prefix + Language.COMMANDS_INVALID_NUMBER);
            return false;
        }
        return true;
    }

    public abstract void execute(CommandSender sender, String[] args);
}
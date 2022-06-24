package nl.rubixstudios.gangsturfs.commands.manager;

import lombok.Setter;
import nl.rubixstudios.gangsturfs.GangsTurfs;
import nl.rubixstudios.gangsturfs.data.Config;
import nl.rubixstudios.gangsturfs.data.Language;
import nl.rubixstudios.gangsturfs.utils.Tasks;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.command.defaults.BukkitCommand;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SubCommandExecutor extends BukkitCommand {

    private final String commandName;
    @Setter private String prefix;

    private final List<String> usageMessage;
    private final List<SubCommand> subCommands;

    public SubCommandExecutor(String commandName, List<String> usageMessage) {
        this(commandName, new ArrayList<>(), usageMessage);
    }

    public SubCommandExecutor(String commandName, List<String> aliases, List<String> usageMessage) {
        super(commandName);

        this.commandName = commandName;
        this.setAliases(aliases);

        this.subCommands = new ArrayList<>();
        this.usageMessage = usageMessage;

        GangsTurfs.getInstance().getCommandManager().registerCommand(this);
    }

    public void disable() {
        this.subCommands.clear();
    }

    protected void addSubCommand(SubCommand command) {
        if(this.isGangCommandDisabled(command.getName())) {
            return;
        }

        command.setPrefix(this.prefix);
        this.subCommands.add(command);
    }

    protected List<String> getUsageMessage(CommandSender sender) {
        return this.usageMessage;
    }

    protected SubCommand getSubCommand(String name) {
        for(SubCommand sub : this.subCommands) {
            if(sub.getName().equalsIgnoreCase(name) || sub.getAllies().contains(name.toLowerCase())) {
                return sub;
            }
        }

        return null;
    }

    private boolean isGangCommandDisabled(String subCommandName) {
        return this.commandName.equals("gang") && Config.DISABLED_GANG_SUBCOMMANDS.contains(subCommandName);
    }

    @Override
    public boolean execute(CommandSender sender, String label, String[] args) {
        if(args.length == 0) {
            this.getUsageMessage(sender).forEach(sender::sendMessage);
            return true;
        }

        SubCommand sub = this.getSubCommand(args[0]);

        if(sub == null) {
            sender.sendMessage(this.prefix + Language.COMMANDS_COMMAND_NOT_FOUND
            .replace("<command>", args[0]));
            return true;
        }

        if(sub.forPlayersOnly && sender instanceof ConsoleCommandSender) {
            sender.sendMessage(this.prefix + Language.COMMANDS_FOR_PLAYERS_ONLY);
            return true;
        }

        if(sub.getPermission() != null && !sender.hasPermission(sub.getPermission())) {
            sender.sendMessage(this.prefix + Language.COMMANDS_NO_PERMISSION);
            return true;
        }

        if(sub.isExecuteAsync()) {
            Tasks.async(() -> sub.execute(sender, Arrays.copyOfRange(args, 1, args.length)));
        } else {
            sub.execute(sender, Arrays.copyOfRange(args, 1, args.length));
        }

        return true;
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String alias, String[] args) throws IllegalArgumentException {
        if(args.length != 1) {
            return super.tabComplete(sender, alias, args);
        }

        List<String> completions = new ArrayList<>();

        for(SubCommand subCommand : this.subCommands) {
            if(subCommand.getPermission() != null && !sender.hasPermission(subCommand.getPermission())) continue;

            completions.add(subCommand.getName());
        }

        return completions;
    }
}
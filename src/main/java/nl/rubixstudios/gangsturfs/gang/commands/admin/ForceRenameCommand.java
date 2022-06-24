package nl.rubixstudios.gangsturfs.gang.commands.admin;

import nl.rubixstudios.gangsturfs.commands.manager.SubCommand;
import nl.rubixstudios.gangsturfs.data.Config;
import nl.rubixstudios.gangsturfs.data.Language;
import nl.rubixstudios.gangsturfs.gang.GangManager;
import nl.rubixstudios.gangsturfs.gang.type.PlayerGang;
import nl.rubixstudios.gangsturfs.utils.Messages;
import nl.rubixstudios.gangsturfs.utils.StringUtils;
import org.bukkit.command.CommandSender;

public class ForceRenameCommand extends SubCommand {

    public ForceRenameCommand() {
        super("forcerename", "turtle.GANGS.forcerename");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(args.length < 2) {
            sender.sendMessage(Language.GANG_PREFIX + Language.GANGS_FORCE_RENAME_USAGE);
            return;
        }

        final PlayerGang gang = GangManager.getInstance().searchForGang(args[0]);

        if(gang == null) {
            sender.sendMessage(Language.GANG_PREFIX + Language.GANGS_GANG_DOESNT_EXIST.replace("<argument>", args[0]));
            return;
        }

        if(gang.getName().equalsIgnoreCase(args[1])) {
            sender.sendMessage(Language.GANG_PREFIX + Language.GANGS_FORCE_RENAME_SAME_NAME.replace("<name>", args[1]));
            return;
        }

        if(Config.GANG_NAME_DISALLOWED_NAMES.contains(args[1].toLowerCase())) {
            sender.sendMessage(Language.GANG_PREFIX + Language.GANGS_BLOCKED_GANG_NAME);
            return;
        }

        if(args[1].length() < Config.GANG_NAME_MINIMUM_LENGTH) {
            sender.sendMessage(Language.GANG_PREFIX + Language.GANGS_NAME_TOO_SHORT);
            return;
        }

        if(args[1].length() > Config.GANG_NAME_MAXIMUM_LENGTH) {
            sender.sendMessage(Language.GANG_PREFIX + Language.GANGS_NAME_TOO_BIG);
            return;
        }

        if(!StringUtils.isAlphaNumeric(args[1])) {
            sender.sendMessage(Language.GANG_PREFIX + Language.GANGS_NAME_NOT_ALPHANUMERIC);
            return;
        }

        if(GangManager.getInstance().getGangByName(args[1]) != null) {
            sender.sendMessage(Language.GANG_PREFIX + Language.GANGS_GANG_ALREADY_EXISTS.replace("<name>", args[1]));
            return;
        }

        final String oldName = gang.getName();
        if(!gang.setName(sender, args[1], true)) return;

        Messages.sendMessage(Language.GANGS_FORCE_RENAMED.replace("<name>", oldName).replace("<newName>", args[1]));
    }
}

package nl.rubixstudios.gangsturfs.gang.commands.player;

import nl.rubixstudios.gangsturfs.commands.manager.SubCommand;
import nl.rubixstudios.gangsturfs.data.Config;
import nl.rubixstudios.gangsturfs.data.Language;
import nl.rubixstudios.gangsturfs.gang.GangController;
import nl.rubixstudios.gangsturfs.gang.GangManager;
import nl.rubixstudios.gangsturfs.utils.Messages;
import nl.rubixstudios.gangsturfs.utils.StringUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Collections;

public class CreateCommand extends SubCommand {

    public CreateCommand() {
        super("create", Collections.singletonList("new"), true);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!sender.isOp()) return;

        if(args.length == 0) {
            sender.sendMessage(Language.GANG_PREFIX + Language.GANGS_CREATE_USAGE);
            return;
        }

        if(Config.GANG_NAME_DISALLOWED_NAMES.contains(args[0].toLowerCase())) {
            sender.sendMessage(Language.GANG_PREFIX + Language.GANGS_BLOCKED_GANG_NAME);
            return;
        }

        if(args[0].length() < Config.GANG_NAME_MINIMUM_LENGTH) {
            sender.sendMessage(Language.GANG_PREFIX + Language.GANGS_NAME_TOO_SHORT);
            return;
        }

        if(args[0].length() > 5) {
            sender.sendMessage(Language.GANG_PREFIX + Language.GANGS_NAME_TOO_BIG);
            return;
        }

        if(!StringUtils.isAlphaNumeric(args[0])) {
            sender.sendMessage(Language.GANG_PREFIX + Language.GANGS_NAME_NOT_ALPHANUMERIC);
            return;
        }

        if(GangManager.getInstance().getGangByName(args[0]) != null) {
            sender.sendMessage(Language.GANG_PREFIX + Language.GANGS_GANG_ALREADY_EXISTS.replace("<name>", args[0]));
            return;
        }

        final Player player = (Player) sender;

        if(GangManager.getInstance().getPlayerGang(player) != null) {
            player.sendMessage(Language.GANG_PREFIX + Language.GANGS_ALREADY_IN_GANG_SELF);
            return;
        }

        if(!GangController.getInstance().createPlayerGang(args[0], player)) return;


        Messages.sendMessage(Language.GANGS_CREATED.replace("<player>", player.getName()).replace("<name>", args[0]));
    }
}

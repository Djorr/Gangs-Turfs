package nl.rubixstudios.gangsturfs.gang.commands.player;

import nl.rubixstudios.gangsturfs.commands.manager.SubCommand;
import nl.rubixstudios.gangsturfs.data.Config;
import nl.rubixstudios.gangsturfs.data.Language;
import nl.rubixstudios.gangsturfs.gang.GangController;
import nl.rubixstudios.gangsturfs.gang.GangManager;
import nl.rubixstudios.gangsturfs.gang.type.PlayerGang;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Collections;

public class JoinCommand extends SubCommand {

    public JoinCommand() {
        super("join", Collections.singletonList("accept"), true);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(args.length == 0) {
            sender.sendMessage(Language.GANG_PREFIX + Language.GANGS_JOIN_USAGE);
            return;
        }

        final Player player = (Player) sender;

        if(GangManager.getInstance().getPlayerGang(player) != null) {
            player.sendMessage(Language.GANG_PREFIX + Language.GANGS_ALREADY_IN_GANG_SELF);
            return;
        }

        final PlayerGang gang = GangManager.getInstance().searchForGang(args[0]);

        if(gang == null) {
            sender.sendMessage(Language.GANG_PREFIX + Language.GANGS_GANG_DOESNT_EXIST.replace("<argument>", args[0]));
            return;
        }

        if(!gang.getPlayerInvitations().contains(player.getName())) {
            player.sendMessage(Language.GANG_PREFIX + Language.GANGS_NOT_INVITED.replace("<name>", gang.getName()));
            return;
        }

        if(gang.getMembers().size() >= Config.GANG_PLAYER_LIMIT) {
            player.sendMessage(Language.GANG_PREFIX + Language.GANGS_JOIN_GANG_FULL.replace("<gang>", gang.getName()));
            return;
        }

        if(!Config.GANG_JOIN_WHILE_FROZEN) {
            player.sendMessage(Language.GANG_PREFIX + Language.GANGS_CANNOT_JOIN_WHILE_REGENERATING.replace("<gang>", gang.getName()));
            return;
        }

        if(!GangController.getInstance().joinGang(player, gang)) return;

        gang.sendMessage(Language.GANG_PREFIX + Language.GANGS_JOINED.replace("<player>", player.getName()));
    }
}

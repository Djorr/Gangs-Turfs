package nl.rubixstudios.gangsturfs.gang.commands.player;

import nl.rubixstudios.gangsturfs.commands.manager.SubCommand;
import nl.rubixstudios.gangsturfs.data.Language;
import nl.rubixstudios.gangsturfs.gang.GangManager;
import nl.rubixstudios.gangsturfs.gang.enums.Role;
import nl.rubixstudios.gangsturfs.gang.type.PlayerGang;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Collections;

public class UninviteCommand extends SubCommand {

    public UninviteCommand() {
        super("uninvite", Collections.singletonList("uninv"), true);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(args.length == 0) {
            sender.sendMessage(Language.GANG_PREFIX + Language.GANGS_UNINVITE_USAGE);
            return;
        }

        final Player player = (Player) sender;

        final PlayerGang gang = GangManager.getInstance().getPlayerGang(player);

        if(!gang.getMember(player).getRole().isAtLeast(Role.CAPTAIN)) {
            player.sendMessage(Language.GANG_PREFIX + Language.GANGS_NO_PERMISSION.replace("<role>", Role.getName(Role.CAPTAIN)));
            return;
        }

        if(args[0].equalsIgnoreCase("all")) {
            gang.getPlayerInvitations().clear();
            gang.sendMessage(Language.GANG_PREFIX + Language.GANGS_UNINVITE_ALL.replace("<player>", player.getName()));
            return;
        }

        if(!gang.getPlayerInvitations().contains(args[0])) {
            player.sendMessage(Language.GANG_PREFIX + Language.GANGS_UNINVITE_NOT_INVITED.replace("<player>", args[0]));
            return;
        }

        final Player target = Bukkit.getPlayer(args[0]);

        gang.getPlayerInvitations().remove(target.getName());

        gang.sendMessage(Language.GANG_PREFIX + Language.GANGS_UNINVITE_UNINVITED.replace("<player>", target.getName()));
    }
}

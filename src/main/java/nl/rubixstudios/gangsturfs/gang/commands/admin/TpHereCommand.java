package nl.rubixstudios.gangsturfs.gang.commands.admin;

import nl.rubixstudios.gangsturfs.commands.manager.SubCommand;
import nl.rubixstudios.gangsturfs.data.Language;
import nl.rubixstudios.gangsturfs.gang.GangManager;
import nl.rubixstudios.gangsturfs.gang.type.PlayerGang;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Collections;

public class TpHereCommand extends SubCommand {

    public TpHereCommand() {
        super("tphere", Collections.singletonList("teleporthere"), "gangturfs.gang.tphere", true);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!sender.isOp()) return;

        if(args.length < 1) {
            sender.sendMessage(Language.GANG_PREFIX + Language.GANGS_TP_HERE_USAGE);
            return;
        }

        final PlayerGang gang = GangManager.getInstance().searchForGang(args[0]);

        if(gang == null) {
            sender.sendMessage(Language.GANG_PREFIX + Language.GANGS_GANG_DOESNT_EXIST.replace("<argument>", args[0]));
            return;
        }

        final Player player = (Player) sender;

        gang.getOnlineMembers().forEach(online -> {
            if(!online.teleport(player)) return;

            online.sendMessage(Language.GANG_PREFIX + Language.GANGS_TP_HERE_TELEPORTED_GANG
                .replace("<player>", player.getName()));
        });

        player.sendMessage(Language.GANG_PREFIX + Language.GANGS_TP_HERE_TELEPORTED_SENDER
            .replace("<gang>", gang.getName()));
    }
}

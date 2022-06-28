package nl.rubixstudios.gangsturfs.gang.commands.admin;

import nl.rubixstudios.gangsturfs.commands.manager.SubCommand;
import nl.rubixstudios.gangsturfs.data.Language;
import nl.rubixstudios.gangsturfs.gang.GangController;
import nl.rubixstudios.gangsturfs.gang.GangManager;
import nl.rubixstudios.gangsturfs.gang.GangPlayer;
import nl.rubixstudios.gangsturfs.gang.enums.Role;
import nl.rubixstudios.gangsturfs.gang.type.PlayerGang;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;

public class ForceKickCommand extends SubCommand {

    public ForceKickCommand() {
        super("forcekick", "gangturfs.gang.forcekick");

        this.setExecuteAsync(true);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(args.length == 0) {
            sender.sendMessage(Language.GANG_PREFIX + Language.GANGS_FORCE_KICK_USAGE);
            return;
        }

        final OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
        if(!this.checkOfflinePlayer(sender, target, args[0])) return;

        final PlayerGang gang = GangManager.getInstance().getPlayerGang(target.getUniqueId());

        if(gang == null) {
            sender.sendMessage(Language.GANG_PREFIX + Language.GANGS_NOT_IN_GANG.replace("<player>", target.getName()));
            return;
        }

        final GangPlayer targetPlayer = gang.getMember(target);

        if(targetPlayer.getRole() == Role.LEADER) {
            sender.sendMessage(Language.GANG_PREFIX + Language.GANGS_FORCE_KICK_CANNOT_KICK_LEADER);
            return;
        }

        if(!GangController.getInstance().kickPlayer(target, gang)) return;

        sender.sendMessage(Language.GANG_PREFIX + Language.GANGS_FORCE_KICKED_SENDER
        .replace("<player>", target.getName()).replace("<gang>", gang.getName()));

        if(target.isOnline()) {
            target.getPlayer().sendMessage(Language.GANG_PREFIX +
                    Language.GANGS_FORCE_KICKED_SELF.replace("<name>", gang.getName()));
        }

        gang.sendMessage(Language.GANG_PREFIX + Language.GANGS_FORCE_KICKED_OTHERS.replace("<player>", target.getName()));

    }
}

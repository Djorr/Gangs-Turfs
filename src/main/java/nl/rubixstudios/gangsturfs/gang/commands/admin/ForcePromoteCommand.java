package nl.rubixstudios.gangsturfs.gang.commands.admin;

import nl.rubixstudios.gangsturfs.commands.manager.SubCommand;
import nl.rubixstudios.gangsturfs.data.Language;
import nl.rubixstudios.gangsturfs.gang.GangManager;
import nl.rubixstudios.gangsturfs.gang.GangPlayer;
import nl.rubixstudios.gangsturfs.gang.type.PlayerGang;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;

public class ForcePromoteCommand extends SubCommand {

    public ForcePromoteCommand() {
        super("forcepromote", "gangwars.gangs.forcepromote");

        this.setExecuteAsync(true);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(args.length == 0) {
            sender.sendMessage(Language.GANG_PREFIX + Language.GANGS_FORCE_PROMOTE_USAGE);
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

        if(targetPlayer.getRole().getPromote() == null) {
            sender.sendMessage(Language.GANG_PREFIX + Language.GANGS_FORCE_PROMOTE_MAX_PROMOTE);
            return;
        }

        targetPlayer.setRole(targetPlayer.getRole().getPromote());

        sender.sendMessage(Language.GANG_PREFIX + Language.GANGS_FORCE_PROMOTED_SENDER.replace("<player>",
        target.getName()).replace("<role>", targetPlayer.getRole().getName()));

        gang.sendMessage(Language.GANG_PREFIX + Language.GANGS_FORCE_PROMOTED_GANG.replace("<player>",
        target.getName()).replace("<role>", targetPlayer.getRole().getName()));
    }
}

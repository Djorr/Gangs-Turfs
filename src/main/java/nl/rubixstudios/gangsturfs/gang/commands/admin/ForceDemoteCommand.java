package nl.rubixstudios.gangsturfs.gang.commands.admin;

import nl.rubixstudios.gangsturfs.commands.manager.SubCommand;
import nl.rubixstudios.gangsturfs.data.Language;
import nl.rubixstudios.gangsturfs.gang.GangManager;
import nl.rubixstudios.gangsturfs.gang.GangPlayer;
import nl.rubixstudios.gangsturfs.gang.enums.Role;
import nl.rubixstudios.gangsturfs.gang.type.PlayerGang;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;

public class ForceDemoteCommand extends SubCommand {

    public ForceDemoteCommand() {
        super("forcedemote", "turtle.GANGS.forcedemote");

        this.setExecuteAsync(true);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(args.length == 0) {
            sender.sendMessage(Language.GANG_PREFIX + Language.GANGS_FORCE_DEMOTE_USAGE);
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
            sender.sendMessage(Language.GANG_PREFIX + Language.GANGS_FORCE_DEMOTE_CANNOT_DEMOTE_LEADER);
            return;
        }

        if(targetPlayer.getRole().getDemote() == null) {
            sender.sendMessage(Language.GANG_PREFIX + Language.GANGS_FORCE_DEMOTE_MIN_DEMOTE);
            return;
        }

        targetPlayer.setRole(targetPlayer.getRole().getDemote());

        sender.sendMessage(Language.GANG_PREFIX + Language.GANGS_FORCE_DEMOTED_SENDER.replace("<player>",
        target.getName()).replace("<role>", targetPlayer.getRole().getName()));

        gang.sendMessage(Language.GANG_PREFIX + Language.GANGS_FORCE_DEMOTED_GANG.replace("<player>",
        target.getName()).replace("<role>", targetPlayer.getRole().getName()));
    }
}

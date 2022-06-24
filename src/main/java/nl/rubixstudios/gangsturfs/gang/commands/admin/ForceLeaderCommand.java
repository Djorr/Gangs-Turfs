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

public class ForceLeaderCommand extends SubCommand {

    public ForceLeaderCommand() {
        super("forceleader", "turtle.GANGS.forceleader");

        this.setExecuteAsync(true);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(args.length == 0) {
            sender.sendMessage(Language.GANG_PREFIX + Language.GANGS_FORCE_LEADER_USAGE);
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
            sender.sendMessage(Language.GANG_PREFIX + Language.GANGS_FORCE_LEADER_ALREADY_LEADER.replace("<player>", target.getName()));
            return;
        }

        gang.getLeader().setRole(Role.CAPTAIN);
        targetPlayer.setRole(Role.LEADER);

        sender.sendMessage(Language.GANG_PREFIX + Language.GANGS_FORCE_LEADER_CHANGED_SENDER
        .replace("<player>", target.getName()).replace("<gang>", gang.getName()));

        gang.sendMessage(Language.GANG_PREFIX + Language.GANGS_FORCE_LEADER_CHANGED_GANG
        .replace("<sender>", sender.getName()).replace("<player>", target.getName()));
    }
}

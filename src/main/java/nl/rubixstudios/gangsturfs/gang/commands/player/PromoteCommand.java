package nl.rubixstudios.gangsturfs.gang.commands.player;

import nl.rubixstudios.gangsturfs.commands.manager.SubCommand;
import nl.rubixstudios.gangsturfs.data.Language;
import nl.rubixstudios.gangsturfs.gang.GangManager;
import nl.rubixstudios.gangsturfs.gang.GangPlayer;
import nl.rubixstudios.gangsturfs.gang.enums.Role;
import nl.rubixstudios.gangsturfs.gang.type.PlayerGang;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PromoteCommand extends SubCommand {

    public PromoteCommand() {
        super("promote", true);

        this.setExecuteAsync(true);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(args.length == 0) {
            sender.sendMessage(Language.GANG_PREFIX + Language.GANGS_PROMOTE_USAGE);
            return;
        }

        final Player player = (Player) sender;

        final PlayerGang gang = GangManager.getInstance().getPlayerGang(player);

        if(gang == null) {
            player.sendMessage(Language.GANG_PREFIX + Language.GANGS_NOT_IN_GANG_SELF);
            return;
        }

        if(gang.getMember(player).getRole() != Role.LEADER) {
            player.sendMessage(Language.GANG_PREFIX + Language.GANGS_MUST_BE_LEADER);
            return;
        }

        final OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
        if(!this.checkOfflinePlayer(sender, target, args[0])) return;

        if(player == target) {
            player.sendMessage(Language.GANG_PREFIX + Language.GANGS_CANNOT_PROMOTE_SELF);
            return;
        }

        final GangPlayer targetPlayer = gang.getMember(target);

        if(targetPlayer == null) {
            player.sendMessage(Language.GANG_PREFIX + Language.GANGS_NOT_IN_GANG_OTHERS.replace("<player>", target.getName()));
            return;
        }

        if(targetPlayer.getRole().getPromote() == null) {
            player.sendMessage(Language.GANG_PREFIX + Language.GANGS_PROMOTE_MAX_PROMOTE);
            return;
        }

        targetPlayer.setRole(targetPlayer.getRole().getPromote());

        gang.sendMessage(Language.GANG_PREFIX + Language.GANGS_PROMOTE_PROMOTED.replace("<player>",
        target.getName()).replace("<role>", targetPlayer.getRole().getName()));
    }
}

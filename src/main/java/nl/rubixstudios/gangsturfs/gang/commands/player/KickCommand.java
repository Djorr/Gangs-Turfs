package nl.rubixstudios.gangsturfs.gang.commands.player;

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
import org.bukkit.entity.Player;

public class KickCommand extends SubCommand {

    public KickCommand() {
        super("kick", true);

        this.setExecuteAsync(true);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(args.length == 0) {
            sender.sendMessage(Language.GANG_PREFIX + Language.GANGS_KICK_USAGE);
            return;
        }

        final Player player = (Player) sender;

        final PlayerGang gang = GangManager.getInstance().getPlayerGang(player);

        if(gang == null) {
            player.sendMessage(Language.GANG_PREFIX + Language.GANGS_NOT_IN_GANG_SELF);
            return;
        }

        final Role playerRole = gang.getMember(player).getRole();

        if(!playerRole.isAtLeast(Role.ADVISOR)) {
            player.sendMessage(Language.GANG_PREFIX + Language.GANGS_NO_PERMISSION.replace("<role>", Role.getName(Role.ADVISOR)));
            return;
        }

        final OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
        if(!this.checkOfflinePlayer(sender, target, args[0])) return;

        final GangPlayer targetPlayer = gang.getMember(target);

        if(targetPlayer == null) {
            player.sendMessage(Language.GANG_PREFIX + Language.GANGS_NOT_IN_GANG_OTHERS.replace("<player>", target.getName()));
            return;
        }

        if((playerRole.ordinal() - targetPlayer.getRole().ordinal()) < 1) {
            player.sendMessage(Language.GANG_PREFIX + Language.GANGS_NO_PERMISSION_ROLE.replace("<player>", target.getName()));
            return;
        }

        if(!GangController.getInstance().kickPlayer(target, gang)) return;

        if(target.isOnline()) target.getPlayer().sendMessage(Language.GANG_PREFIX + Language.GANGS_KICKED_SELF.replace("<name>", gang.getName()));
        gang.sendMessage(Language.GANG_PREFIX + Language.GANGS_KICKED_OTHERS.replace("<player>", target.getName()));
    }
}

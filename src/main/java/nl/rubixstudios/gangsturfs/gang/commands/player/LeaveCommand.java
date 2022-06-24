package nl.rubixstudios.gangsturfs.gang.commands.player;

import nl.rubixstudios.gangsturfs.commands.manager.SubCommand;
import nl.rubixstudios.gangsturfs.data.Language;
import nl.rubixstudios.gangsturfs.gang.GangController;
import nl.rubixstudios.gangsturfs.gang.GangManager;
import nl.rubixstudios.gangsturfs.gang.enums.Role;
import nl.rubixstudios.gangsturfs.gang.type.PlayerGang;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class LeaveCommand extends SubCommand {

    public LeaveCommand() {
        super("leave", true);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        final Player player = (Player) sender;

        final PlayerGang playerGang = GangManager.getInstance().getPlayerGang(player);

        if(playerGang == null) {
            player.sendMessage(Language.GANG_PREFIX + Language.GANGS_NOT_IN_GANG_SELF);
            return;
        }

        if(playerGang.getMember(player).getRole() == Role.LEADER) {
            player.sendMessage(Language.GANG_PREFIX + Language.GANGS_LEADER_LEAVE);
            return;
        }

        if(!GangController.getInstance().leaveGang(player, playerGang)) return;

        player.sendMessage(Language.GANG_PREFIX + Language.GANGS_LEFT_SELF.replace("<name>", playerGang.getName()));
        playerGang.sendMessage(Language.GANG_PREFIX + Language.GANGS_LEFT_OTHERS.replace("<player>", player.getName()));
    }
}

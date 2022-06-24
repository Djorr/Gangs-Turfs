package nl.rubixstudios.gangsturfs.gang.commands.player;

import nl.rubixstudios.gangsturfs.commands.manager.SubCommand;
import nl.rubixstudios.gangsturfs.data.Language;
import nl.rubixstudios.gangsturfs.gang.GangController;
import nl.rubixstudios.gangsturfs.gang.GangManager;
import nl.rubixstudios.gangsturfs.gang.enums.Role;
import nl.rubixstudios.gangsturfs.gang.type.PlayerGang;
import nl.rubixstudios.gangsturfs.utils.Messages;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DisbandCommand extends SubCommand {

    public DisbandCommand() {
        super("disband", true);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!sender.isOp()) return;

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

        if(!GangController.getInstance().disbandGang(gang.getId(), player)) return;

        Messages.sendMessage(Language.GANGS_DISBANDED.replace("<player>", player.getName()).replace("<name>", gang.getName()));

        if (player.isOp()) return;
    }
}

package nl.rubixstudios.gangsturfs.gang.commands.player;

import nl.rubixstudios.gangsturfs.commands.manager.SubCommand;
import nl.rubixstudios.gangsturfs.data.Language;
import nl.rubixstudios.gangsturfs.gang.GangController;
import nl.rubixstudios.gangsturfs.gang.object.CreatingGangObject;
import nl.rubixstudios.gangsturfs.utils.Color;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Collections;

public class CancelCommand extends SubCommand {

    public CancelCommand() {
        super("cancel", Collections.singletonList("c"), true);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        final Player player = (Player) sender;

        final CreatingGangObject creatingGangObject = GangController.getInstance().getObject(player);
        if (creatingGangObject == null) return;

        GangController.getInstance().getCreatingGangCache().remove(creatingGangObject);

        player.sendMessage(Language.GANG_PREFIX + Color.translate("&cGang aanmaken is geannuleerd."));
    }
}
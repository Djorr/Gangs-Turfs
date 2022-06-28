package nl.rubixstudios.gangsturfs.turf.command.admin;

import nl.rubixstudios.gangsturfs.commands.manager.SubCommand;
import nl.rubixstudios.gangsturfs.data.Language;
import nl.rubixstudios.gangsturfs.turf.TurfController;
import nl.rubixstudios.gangsturfs.turf.TurfData;
import nl.rubixstudios.gangsturfs.utils.Color;
import org.bukkit.command.CommandSender;

import java.util.Collections;

public class RemoveCommand extends SubCommand {

    public RemoveCommand() {
        super("remove", Collections.singletonList("del"), "gangturfs.turf.remove", true);

        this.setExecuteAsync(true);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (args.length != 1) {
            sender.sendMessage(Language.TURF_PREFIX + "&eGebruik: /turf remove <naam>");
            return;
        }

        final String name = args[0];

        final TurfController turfController = TurfController.getInstance();
        final TurfData turfData = turfController.getTurfManager().getTurf(name);
        if (turfData == null) {
            sender.sendMessage(Language.TURF_PREFIX + Color.translate("&cDe turf <name> bestaat niet.".replace("<name>", name)));
            return;
        }

        turfController.deleteTurf(sender, name);
    }
}

package nl.rubixstudios.gangsturfs.games.turf.command.commands.admin;

import nl.rubixstudios.gangsturfs.commands.manager.SubCommand;
import nl.rubixstudios.gangsturfs.games.turf.TurfController;
import nl.rubixstudios.gangsturfs.games.turf.TurfData;
import org.bukkit.command.CommandSender;

import java.util.Collections;

public class RemoveCommand extends SubCommand {

    public RemoveCommand() {
        super("remove", Collections.singletonList("del"), "gangmt.turf.admin", true);

        this.setExecuteAsync(true);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (args.length != 1) {
            sender.sendMessage("");
            return;
        }

        final String name = args[0];

        final TurfController turfController = TurfController.getInstance();
        final TurfData turfData = turfController.getTurfManager().getTurf(name);
        if (turfData == null) {
            sender.sendMessage("Doesnt exists");
            return;
        }

        turfController.deleteTurf(sender, name);
    }
}

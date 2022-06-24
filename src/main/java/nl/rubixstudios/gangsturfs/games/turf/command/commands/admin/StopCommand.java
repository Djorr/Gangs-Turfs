package nl.rubixstudios.gangsturfs.games.turf.command.commands.admin;

import nl.rubixstudios.gangsturfs.commands.manager.SubCommand;
import nl.rubixstudios.gangsturfs.data.Language;
import nl.rubixstudios.gangsturfs.games.turf.TurfController;
import nl.rubixstudios.gangsturfs.games.turf.TurfData;
import org.bukkit.command.CommandSender;

public class StopCommand extends SubCommand {

    public StopCommand() {
        super("stop", "gangmt.admin",true);

        this.setExecuteAsync(true);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(args.length != 1) {
            sender.sendMessage("Usage: /turf stop <name>");
            return;
        }

        final String turfName = args[0];
        final TurfController turfController = TurfController.getInstance();

        final TurfData turfData = turfController.getTurfManager().getTurf(turfName);
        if (turfData == null) {
            sender.sendMessage("");
            return;
        }

        if (!turfData.isTurfStarted()) {
            sender.sendMessage(Language.TURF_PREFIX + "The turf isn't started yet..");
            return;
        }

        turfController.stopTurf(turfData);
        sender.sendMessage(Language.TURF_PREFIX + "You've succesfully stopped the turf!");
    }
}

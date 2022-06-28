package nl.rubixstudios.gangsturfs.turf.command.admin;

import nl.rubixstudios.gangsturfs.commands.manager.SubCommand;
import nl.rubixstudios.gangsturfs.data.Language;
import nl.rubixstudios.gangsturfs.turf.TurfController;
import nl.rubixstudios.gangsturfs.turf.TurfData;
import nl.rubixstudios.gangsturfs.utils.Color;
import org.bukkit.command.CommandSender;

public class StopCommand extends SubCommand {

    public StopCommand() {
        super("stop", "gangturfs.turf.stop",true);

        this.setExecuteAsync(true);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(args.length != 1) {
            sender.sendMessage("Gebruik: /turf stop <naam>");
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
            sender.sendMessage(Language.TURF_PREFIX + Color.translate("&cDeze turf <name> is niet actief!".replace("<name>", turfName)));
            return;
        }

        turfController.stopTurf(turfData);
        sender.sendMessage(Language.TURF_PREFIX + Color.translate("&aJe hebt succesvol turf <name> gestopt!".replace("<name>", turfName)));
    }
}

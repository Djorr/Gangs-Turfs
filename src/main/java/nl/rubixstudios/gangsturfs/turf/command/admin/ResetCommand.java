package nl.rubixstudios.gangsturfs.turf.command.admin;

import nl.rubixstudios.gangsturfs.commands.manager.SubCommand;
import nl.rubixstudios.gangsturfs.data.Language;
import nl.rubixstudios.gangsturfs.turf.TurfController;
import nl.rubixstudios.gangsturfs.turf.TurfData;
import nl.rubixstudios.gangsturfs.utils.Color;
import org.bukkit.command.CommandSender;

import java.util.Arrays;

public class ResetCommand extends SubCommand {

    public ResetCommand() {
        super("reset", Arrays.asList("rcooldown", "resetcooldown"), "gangturfs.turf.resetcooldown", true);

        this.setExecuteAsync(true);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(args.length != 1) {
            sender.sendMessage("Gebruik: /turf resetcooldown <naam>");
            return;
        }

        final String turfName = args[0];
        final TurfController turfController = TurfController.getInstance();

        final TurfData turfData = turfController.getTurfManager().getTurf(turfName);
        if (turfData == null) {
            sender.sendMessage(Language.TURF_PREFIX + Color.translate("&fDe turf &e<name> &fbestaat niet!".replace("<name>", turfName)));
            return;
        }

        turfData.setTurfEndedTime(0);
        sender.sendMessage(Language.TURF_PREFIX + Color.translate("Je hebt succesvol de cooldown gereset van turf <name>".replace("<name>", turfName)));
    }
}
package nl.rubixstudios.gangsturfs.games.turf.command.commands.admin;

import nl.rubixstudios.gangsturfs.commands.manager.SubCommand;
import nl.rubixstudios.gangsturfs.data.Language;
import nl.rubixstudios.gangsturfs.games.turf.TurfController;
import nl.rubixstudios.gangsturfs.games.turf.TurfData;
import org.bukkit.command.CommandSender;

import java.util.Arrays;

public class ResetCommand extends SubCommand {

    public ResetCommand() {
        super("reset", Arrays.asList("rcooldown", "resetcooldown"), "gangmt.turf.admin", true);

        this.setExecuteAsync(true);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(args.length != 1) {
            sender.sendMessage("Usage: /turf resetcooldown <name>");
            return;
        }

        final String turfName = args[0];
        final TurfController turfController = TurfController.getInstance();

        final TurfData turfData = turfController.getTurfManager().getTurf(turfName);
        if (turfData == null) {
            sender.sendMessage(Language.TURF_PREFIX + "&fDe turf &e<name> &fbestaat niet!".replace("<name>", turfName));
            return;
        }

        turfData.setTurfEndedTime(0);
        sender.sendMessage(Language.TURF_PREFIX + "You've succesfully stopped the turf!");
    }
}
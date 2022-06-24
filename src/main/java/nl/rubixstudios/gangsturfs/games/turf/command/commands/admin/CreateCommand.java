package nl.rubixstudios.gangsturfs.games.turf.command.commands.admin;

import nl.rubixstudios.gangsturfs.commands.manager.SubCommand;
import nl.rubixstudios.gangsturfs.data.Language;
import nl.rubixstudios.gangsturfs.games.turf.TurfController;
import nl.rubixstudios.gangsturfs.utils.worldguard.WorldGuardUtil;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.Objects;

public class CreateCommand extends SubCommand {

    public CreateCommand() {
        super("create", Collections.singletonList("new"), "gangmt.turf.admin", true);

        this.setExecuteAsync(true);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (args.length != 1) {
            sender.sendMessage(Language.TURF_PREFIX + "Usage: /turf create <name>" +
                    "\nExample: /turf create turf-<getal>");
            return;
        }

        final String name = args[0];

        final TurfController turfController = TurfController.getInstance();
        final Player player = (Player) sender;


        if (turfController.getTurfManager().getTurfs().stream().anyMatch(turfData -> turfData.getTurfName().equals(name))) {
            player.sendMessage(Language.TURF_PREFIX + "This name is already an turf.");
        } else if (turfController.getTurfManager().getTurfs().stream().anyMatch(turfData -> Objects.equals(WorldGuardUtil.getTurfRegion(turfData.getTurfRegion()), WorldGuardUtil.getTurfRegion(player.getLocation())))) {
            player.sendMessage(Language.TURF_PREFIX + "This region is already an turf.");
        }

        turfController.createTurf(sender, name);
    }
}

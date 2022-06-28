package nl.rubixstudios.gangsturfs.turf.command.admin;

import nl.rubixstudios.gangsturfs.commands.manager.SubCommand;
import nl.rubixstudios.gangsturfs.data.Config;
import nl.rubixstudios.gangsturfs.data.Language;
import nl.rubixstudios.gangsturfs.turf.TurfController;
import nl.rubixstudios.gangsturfs.utils.Color;
import nl.rubixstudios.gangsturfs.utils.worldguard.WorldGuardUtil;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.Objects;

public class CreateCommand extends SubCommand {

    public CreateCommand() {
        super("create", Collections.singletonList("new"), "gangturfs.turf.create", true);

        this.setExecuteAsync(true);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (args.length != 1) {
            sender.sendMessage(Language.TURF_PREFIX + Color.translate("Gebruik: /turf create <naam>" +
                    "\nVoorbeeld: /turf create <turfTag><getal>".replace("<turfTag>", Config.TURF_REGIONS_START_WITH)));
            return;
        }

        final String name = args[0];

        final TurfController turfController = TurfController.getInstance();
        final Player player = (Player) sender;


        if (turfController.getTurfManager().getTurfs().stream().anyMatch(turfData -> turfData.getTurfName().equals(name))) {
            player.sendMessage(Language.TURF_PREFIX + Color.translate("De naam <name> bestaat al als turf.".replace("<name>", name)));
        } else if (turfController.getTurfManager().getTurfs().stream().anyMatch(turfData -> Objects.equals(WorldGuardUtil.getTurfRegion(turfData.getTurfRegion()), WorldGuardUtil.getTurfRegion(player.getLocation())))) {
            player.sendMessage(Language.TURF_PREFIX + Color.translate("De worldguard region <name> heeft al een turf".replace("<name>", WorldGuardUtil.getTurfRegion(player.getLocation()).getId())));
        }

        turfController.createTurf(sender, name);
    }
}

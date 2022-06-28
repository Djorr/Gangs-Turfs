package nl.rubixstudios.gangsturfs.turf.command.admin;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import nl.rubixstudios.gangsturfs.commands.manager.SubCommand;
import nl.rubixstudios.gangsturfs.data.Language;
import nl.rubixstudios.gangsturfs.turf.TurfController;
import nl.rubixstudios.gangsturfs.utils.Color;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Collections;

public class ListCommand extends SubCommand {

    public ListCommand() {
        super("list", Collections.singletonList("lis"), "gangturfs.turf.list", true);

        this.setExecuteAsync(true);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (args.length != 0) {
            sender.sendMessage(Language.TURF_PREFIX + "Gebruik: /turf list ");
            return;
        }

        final TurfController turfController = TurfController.getInstance();
        final Player player = (Player) sender;

        player.sendMessage(Color.translate("&8&m                  "));
        player.sendMessage(Color.translate("&e&lTurfs Lijst: "));
        player.sendMessage(Color.translate("&e&lNaam, Coords "));
        turfController.getTurfManager().getTurfs().forEach(turfData -> {
            final TextComponent turf = new TextComponent(Color.translate("&8- &f<turfName> &8| "
                    .replace("<turfName>", turfData.getTurfName())
            ));

            final TextComponent coords = new TextComponent(Color.translate("&7Coords: &fx<x> y<y> z<z>"
                    .replace("<x>", "" + Math.round(turfData.getTurfRegion().getX()))
                    .replace("<y>", "" + Math.round(turfData.getTurfRegion().getY()))
                    .replace("<z>", "" + Math.round(turfData.getTurfRegion().getZ()))
            ));

            final String hoverTP = "Klik hier om te teleporteren!";
            coords.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(hoverTP).create()));
            coords.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/tppos " +
                    Math.round(turfData.getTurfRegion().getX()) + " " + Math.round(turfData.getTurfRegion().getY()) + " " + Math.round(turfData.getTurfRegion().getZ()) + " " ));

            player.spigot().sendMessage(turf, coords);
        });
        player.sendMessage(Color.translate("&8&m                  "));
    }
}

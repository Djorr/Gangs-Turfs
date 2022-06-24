package nl.rubixstudios.gangsturfs.commands.player;

import nl.rubixstudios.gangsturfs.commands.manager.BaseCommand;
import org.bukkit.command.CommandSender;

public class CoordsCommand extends BaseCommand {

    public CoordsCommand() {
        super("coords");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        sender.sendMessage("Coords:");
    }
}

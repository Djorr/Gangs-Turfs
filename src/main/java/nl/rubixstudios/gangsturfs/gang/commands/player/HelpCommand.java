package nl.rubixstudios.gangsturfs.gang.commands.player;

import nl.rubixstudios.gangsturfs.commands.manager.SubCommand;
import nl.rubixstudios.gangsturfs.data.Language;
import org.bukkit.command.CommandSender;

import java.util.List;

public class HelpCommand extends SubCommand {

    public HelpCommand() {
        super("help");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(args.length == 0) {
            Language.GANGS_HELP_PAGES.get(1).forEach(sender::sendMessage);
            return;
        }

        if(!this.checkNumber(sender, args[0])) return;

        List<String> help = Language.GANGS_HELP_PAGES.get(Integer.parseInt(args[0]));

        if(help == null) {
            sender.sendMessage(Language.GANG_PREFIX + Language.GANGS_HELP_PAGE_NOT_FOUND.replace("<page>", args[0]));
            return;
        }

        help.forEach(sender::sendMessage);
    }
}

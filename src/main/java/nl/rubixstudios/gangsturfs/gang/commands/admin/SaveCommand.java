package nl.rubixstudios.gangsturfs.gang.commands.admin;

import nl.rubixstudios.gangsturfs.GangsTurfs;
import nl.rubixstudios.gangsturfs.commands.manager.SubCommand;
import nl.rubixstudios.gangsturfs.data.Language;
import nl.rubixstudios.gangsturfs.gang.GangManager;
import org.bukkit.command.CommandSender;

public class SaveCommand extends SubCommand {

    public SaveCommand() {
        super("save", "gangwars.gangs.save");

        this.setExecuteAsync(true);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        GangsTurfs.getInstance().log("&2===&e=============================================&2===");

        GangsTurfs.getInstance().log("&eSaving tasks");
        GangManager.getInstance().saveGangs(true, false);
        GangManager.getInstance().savePlayers(true);

        GangsTurfs.getInstance().log("&2===&e=============================================&2===");

        sender.sendMessage(Language.GANG_PREFIX + Language.GANGS_SAVED);
    }
}

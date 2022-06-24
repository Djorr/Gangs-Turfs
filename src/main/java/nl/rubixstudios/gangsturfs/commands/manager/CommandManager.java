package nl.rubixstudios.gangsturfs.commands.manager;

import nl.rubixstudios.gangsturfs.GangsTurfs;
import nl.rubixstudios.gangsturfs.commands.admin.GangWarsCommand;
import nl.rubixstudios.gangsturfs.commands.player.*;
import nl.rubixstudios.gangsturfs.data.Config;
import nl.rubixstudios.gangsturfs.utils.ManagerEnabler;
import nl.rubixstudios.gangsturfs.utils.nms.NmsUtils;
import org.bukkit.command.CommandMap;
import org.bukkit.command.defaults.BukkitCommand;

import java.util.ArrayList;
import java.util.List;

public class CommandManager implements ManagerEnabler {

    private final CommandMap commandMap;
    private final List<BaseCommand> commands;

    public CommandManager() {
        this.commandMap = NmsUtils.getInstance().getCommandMap();
        this.commands = new ArrayList<>();

        this.commands.add(new CoordsCommand());
        this.commands.add(new GangWarsCommand());
        this.commands.add(new PlaytimeCommand());

        this.commands.forEach(this::registerCommand);

        GangsTurfs.getInstance().log(" ");
        GangsTurfs.getInstance().log("&eCommands ");
        GangsTurfs.getInstance().log("- &7Enabled &e" + this.commands.size() + " &7commands.");
    }

    public void disable() {
        this.commands.clear();
    }

    void registerCommand(BukkitCommand command) {
        if(!Config.DISABLED_LAZARUS_COMMANDS.contains(command.getName())) {
            this.commandMap.register("gang", command);
        }
    }
}

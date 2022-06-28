package nl.rubixstudios.gangsturfs.commands.manager;

import nl.rubixstudios.gangsturfs.GangsTurfs;
import nl.rubixstudios.gangsturfs.commands.admin.GangTurfsCommand;
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

        this.commands.add(new GangTurfsCommand());

        this.commands.forEach(this::registerCommand);

        GangsTurfs.getInstance().log(" ");
        GangsTurfs.getInstance().log("&eCommands ");
        GangsTurfs.getInstance().log("- &7Enabled &e" + this.commands.size() + " &7commands.");
    }

    public void disable() {
        this.commands.clear();
    }

    public void registerCommand(BukkitCommand command) {
        this.commandMap.register("gang", command);
    }
}

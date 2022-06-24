package nl.rubixstudios.gangsturfs.npc.command;

import nl.rubixstudios.gangsturfs.commands.manager.SubCommandExecutor;
import nl.rubixstudios.gangsturfs.data.Language;
import nl.rubixstudios.gangsturfs.npc.command.commands.CreateNPCCommand;
import nl.rubixstudios.gangsturfs.npc.command.commands.RemoveNPCCommand;

import java.util.Arrays;

public class NPCCommandExecutor extends SubCommandExecutor {

    public NPCCommandExecutor() {
        super("gnpc", Arrays.asList("", ""), Language.NPC_HELP_PAGES.get(1));

        this.addSubCommand(new CreateNPCCommand());
        this.addSubCommand(new RemoveNPCCommand());

        this.setPermission("gangmt.gnpc");
    }
}
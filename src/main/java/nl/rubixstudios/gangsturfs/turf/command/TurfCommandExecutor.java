package nl.rubixstudios.gangsturfs.turf.command;

import nl.rubixstudios.gangsturfs.commands.manager.SubCommandExecutor;
import nl.rubixstudios.gangsturfs.data.Language;
import nl.rubixstudios.gangsturfs.turf.command.admin.*;

import java.util.Collections;

public class TurfCommandExecutor extends SubCommandExecutor {

    public TurfCommandExecutor() {
        super("turf", Collections.singletonList("tur"), Language.TURF_HELP_PAGES.get(1));

        this.setPrefix(Language.TURF_PREFIX);

        this.addSubCommand(new CreateCommand());
        this.addSubCommand(new RemoveCommand());
        this.addSubCommand(new ListCommand());
        this.addSubCommand(new ResetCommand());

        this.addSubCommand(new StopCommand());
    }
}
package nl.rubixstudios.gangsturfs.combat.commands;

import nl.rubixstudios.gangsturfs.combat.commands.admin.CancelCommand;
import nl.rubixstudios.gangsturfs.combat.commands.player.CheckCommand;
import nl.rubixstudios.gangsturfs.commands.manager.SubCommandExecutor;
import nl.rubixstudios.gangsturfs.data.Language;

import java.util.Collections;

public class CombatCommandExecutor extends SubCommandExecutor {

    public CombatCommandExecutor() {
        super("combat", Collections.singletonList("ct"), Language.COMBAT_HELP_PAGES.get(1));

        this.setPrefix(Language.GANG_PREFIX);

        this.addSubCommand(new CheckCommand());

        this.addSubCommand(new CancelCommand());
    }
}
package nl.rubixstudios.gangsturfs.gang.commands;

import nl.rubixstudios.gangsturfs.commands.manager.SubCommandExecutor;
import nl.rubixstudios.gangsturfs.data.Language;
import nl.rubixstudios.gangsturfs.gang.commands.admin.*;
import nl.rubixstudios.gangsturfs.gang.commands.player.*;

import java.util.Collections;

public class GangCommandExecutor extends SubCommandExecutor {

    public GangCommandExecutor() {
        super("gang", Collections.singletonList("team"), Language.GANGS_HELP_PAGES.get(1));

        this.setPrefix(Language.GANG_PREFIX);

        this.addSubCommand(new ForceDemoteCommand());
        this.addSubCommand(new ForceJoinCommand());
        this.addSubCommand(new ForceKickCommand());
        this.addSubCommand(new ForceLeaderCommand());
        this.addSubCommand(new ForcePromoteCommand());
        this.addSubCommand(new ForceRenameCommand());
        this.addSubCommand(new SaveCommand());
        this.addSubCommand(new TpHereCommand());
        this.addSubCommand(new ShowCommand());

        this.addSubCommand(new CreateCommand());
        this.addSubCommand(new DemoteCommand());
        this.addSubCommand(new DisbandCommand());
        this.addSubCommand(new HelpCommand());
        this.addSubCommand(new InviteCommand());
        this.addSubCommand(new JoinCommand());
        this.addSubCommand(new KickCommand());
        this.addSubCommand(new LeaveCommand());
        this.addSubCommand(new ListCommand());
        this.addSubCommand(new PromoteCommand());
        this.addSubCommand(new UninviteCommand());
        this.addSubCommand(new CancelCommand());
    }
}

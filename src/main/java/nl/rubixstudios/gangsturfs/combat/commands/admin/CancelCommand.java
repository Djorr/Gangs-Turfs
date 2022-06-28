package nl.rubixstudios.gangsturfs.combat.commands.admin;

import nl.rubixstudios.gangsturfs.GangsTurfs;
import nl.rubixstudios.gangsturfs.combat.CombatTagController;
import nl.rubixstudios.gangsturfs.commands.manager.SubCommand;
import nl.rubixstudios.gangsturfs.data.Language;
import nl.rubixstudios.gangsturfs.utils.Color;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Collections;

public class CancelCommand extends SubCommand {

    public CancelCommand() {
        super("cancel", Collections.singletonList("canc"), "gangturfs.combat.cancelcooldown", true);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (args.length != 1) {
            sender.sendMessage(Language.COMBAT_PREFIX + Language.COMBAT_TAG_CANCEL_COMMAND_USAGE);
            return;
        }

        final String playerName = args[0];
        final Player target = Bukkit.getPlayer(playerName);
        if (target == null || !target.isOnline()) {
            sender.sendMessage(Language.COMBAT_PREFIX + Language.COMBAT_TAG_CANCEL_COMMAND_PLAYER_NOT_ONLINE
                    .replace("<target>", playerName));
            return;
        }

        final CombatTagController combatTagController = GangsTurfs.getInstance().getCombatTagController();
        combatTagController.getCombatTagManager().deleteCombatlogger(target);
        sender.sendMessage(Language.COMBAT_PREFIX + Language.COMBAT_TAG_CANCEL_COMMAND_SUCCESFUL_CANCELED
                .replace("<target>", target.getName()));
    }
}

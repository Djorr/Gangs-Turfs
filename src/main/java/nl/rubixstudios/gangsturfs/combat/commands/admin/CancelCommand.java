package nl.rubixstudios.gangsturfs.combat.commands.admin;

import nl.rubixstudios.gangsturfs.GangsTurfs;
import nl.rubixstudios.gangsturfs.combat.CombatTagController;
import nl.rubixstudios.gangsturfs.commands.manager.SubCommand;
import nl.rubixstudios.gangsturfs.utils.Color;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Collections;

public class CancelCommand extends SubCommand {

    public CancelCommand() {
        super("cancel", Collections.singletonList("canc"), true);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (args.length != 1) {
            sender.sendMessage("Usage: /ct cancel <target>");
            return;
        }

        final String playerName = args[0];
        final Player target = Bukkit.getPlayer(playerName);
        if (target == null || !target.isOnline()) {
            sender.sendMessage(Color.translate("&cCombat &8» &cPlayer &e<target> &cis not online!"
                    .replace("<target>", playerName)));
            return;
        }

        final CombatTagController combatTagController = GangsTurfs.getInstance().getCombatTagController();
        combatTagController.getCombatTagManager().deleteCombatlogger(target);
        sender.sendMessage(Color.translate("&cCombat &8» &cCancelled cooldown for &e<target>&c."
                .replace("<target>", target.getName())));
    }
}

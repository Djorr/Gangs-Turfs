package nl.rubixstudios.gangsturfs.combat.commands.player;

import nl.rubixstudios.gangsturfs.GangsTurfs;
import nl.rubixstudios.gangsturfs.combat.CombatTagController;
import nl.rubixstudios.gangsturfs.combat.CombatTagObject;
import nl.rubixstudios.gangsturfs.commands.manager.SubCommand;
import nl.rubixstudios.gangsturfs.data.Language;
import nl.rubixstudios.gangsturfs.utils.Color;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Collections;

public class CheckCommand extends SubCommand {

    public CheckCommand() {
        super("check", Collections.singletonList("c"), true);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (args.length == 0) {
            final CombatTagController combatTagController = GangsTurfs.getInstance().getCombatTagController();
            final Player player = (Player) sender;
            if (combatTagController.isInCombat(player)) {
                player.sendMessage(Language.COMBAT_PREFIX + Language.COMBAT_TAG_CHECK_COMMAND_SELF_CHECK_STILL_IN_COMBAT
                        .replace("<int>", this.converTime(diff(combatTagController.getCombatTagManager().getCombatLogger(player)))));
            } else {
                player.sendMessage(Language.COMBAT_PREFIX + Language.COMBAT_TAG_CHECK_COMMAND_SELF_CHECK_NOT_IN_COMBAT);
            }
        } else if (args.length == 1) {
            final String playerName = args[0];
            final Player target = Bukkit.getPlayer(playerName);
            if (target == null || !target.isOnline()) {
                sender.sendMessage(Language.COMBAT_PREFIX + Language.COMBAT_TAG_CHECK_COMMAND_TARGET_CHECK_NOT_IN_COMBAT.replace("<target>", target.getName()));
                return;
            }

            final CombatTagController combatTagController = GangsTurfs.getInstance().getCombatTagController();
            final Player player = (Player) sender;
            if (combatTagController.isInCombat(target)) {
                player.sendMessage(Language.COMBAT_PREFIX + Language.COMBAT_TAG_CHECK_COMMAND_TARGET_CHECK_STILL_IN_COMBAT
                        .replace("<int>", this.converTime(diff(combatTagController.getCombatTagManager().getCombatLogger(player))))
                        .replace("<name>", target.getName())
                );
            } else {
                player.sendMessage(Language.COMBAT_PREFIX + Language.COMBAT_TAG_CHECK_COMMAND_TARGET_CHECK_NOT_IN_COMBAT.replace("<name>", target.getName()));
            }
        } else {
            sender.sendMessage(Language.COMBAT_PREFIX + Language.COMBAT_TAG_CHECK_COMMAND_USAGE);
        }
    }

    private long diff(CombatTagObject combatTagObject) {
        final long timeInCombat = combatTagObject.getTimeInCombat();
        final int combatTagCooldown = CombatTagController.getInstance().getTagCooldown();
        return (combatTagCooldown * 1000L) - (System.currentTimeMillis() - timeInCombat);
    }

    public String converTime(final long time) {
        final int seconds = (int) (time / 1000) % 60 ;
        return seconds + "s";
    }
}

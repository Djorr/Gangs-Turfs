package nl.rubixstudios.gangsturfs.combat.commands.player;

import nl.rubixstudios.gangsturfs.GangsTurfs;
import nl.rubixstudios.gangsturfs.combat.CombatTagController;
import nl.rubixstudios.gangsturfs.combat.CombatTagObject;
import nl.rubixstudios.gangsturfs.commands.manager.SubCommand;
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
                player.sendMessage(Color.translate("&cCombat &8» &cYou are still in combat for &e<int> &cseconds."
                        .replace("<int>", this.converTime(diff(combatTagController.getCombatTagManager().getCombatLogger(player))))));
            } else {
                player.sendMessage(Color.translate("&cCombat &8» &cYou are not in combat."));
            }
        } else if (args.length == 1) {
            final String playerName = args[0];
            final Player target = Bukkit.getPlayer(playerName);
            if (target == null || !target.isOnline()) {
                sender.sendMessage(Color.translate("&cCombat &8» &cPlayer &e<target> &cis not online!".replace("<target>", target.getName())));
                return;
            }

            final CombatTagController combatTagController = GangsTurfs.getInstance().getCombatTagController();
            final Player player = (Player) sender;
            if (combatTagController.isInCombat(target)) {
                player.sendMessage(Color.translate("&cCombat &8» &e<name> &cis still in combat for &e<int> &cseconds."
                        .replace("<int>", this.converTime(diff(combatTagController.getCombatTagManager().getCombatLogger(player))))
                        .replace("<name>", target.getName())
                ));
            } else {
                player.sendMessage(Color.translate("&cCombat &8» &c<name> &eis not in combat.".replace("<name>", target.getName())));
            }
        } else {
            sender.sendMessage(Color.translate("&cCombat &8» &cUsage: &e/ct check <target>"));
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

package nl.rubixstudios.gangsturfs.gang.commands.player;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import nl.rubixstudios.gangsturfs.commands.manager.SubCommand;
import nl.rubixstudios.gangsturfs.data.Config;
import nl.rubixstudios.gangsturfs.data.Language;
import nl.rubixstudios.gangsturfs.gang.GangManager;
import nl.rubixstudios.gangsturfs.gang.GangPlayer;
import nl.rubixstudios.gangsturfs.gang.enums.Role;
import nl.rubixstudios.gangsturfs.gang.type.PlayerGang;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Collections;

public class InviteCommand extends SubCommand {

    public InviteCommand() {
        super("invite", Collections.singletonList("inv"), true);

        this.setExecuteAsync(true);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(args.length == 0) {
            sender.sendMessage(Language.GANG_PREFIX + Language.GANGS_INVITE_USAGE);
            return;
        }

        final Player player = (Player) sender;

        final PlayerGang gang = GangManager.getInstance().getPlayerGang(player);

        if(gang == null) {
            player.sendMessage(Language.GANG_PREFIX + Language.GANGS_NOT_IN_GANG_SELF);
            return;
        }

        if(!gang.getMember(player).getRole().isAtLeast(Role.ADVISOR)) {
            player.sendMessage(Language.GANG_PREFIX + Language.GANGS_NO_PERMISSION.replace("<role>", Role.getName(Role.ADVISOR)));
            return;
        }

        OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
        if(!this.checkOfflinePlayer(sender, target, args[0])) return;

        GangPlayer targetPlayer = gang.getMember(target);

        if(targetPlayer != null) {
            player.sendMessage(Language.GANG_PREFIX + Language.GANGS_ALREADY_IN_GANG_OTHERS.replace("<player>", target.getName()));
            return;
        }

        if(gang.getPlayerInvitations().contains(target.getName())) {
            player.sendMessage(Language.GANG_PREFIX + Language.GANGS_INVITE_ALREADY_INVITED.replace("<player>", target.getName()));
            return;
        }

        if(gang.getMembers().size() >= Config.GANG_MAXIMUM_MEMBERS) {
            player.sendMessage(Language.GANG_PREFIX + Language.GANGS_INVITE_GANG_FULL);
            return;
        }

        gang.getPlayerInvitations().add(target.getName());

        String hoverText = Language.GANGS_INVITE_HOVER_TEXT.replace("<gang>", gang.getName());

        ComponentBuilder message = new ComponentBuilder(Language.GANG_PREFIX)
            .append(Language.GANGS_INVITED_SELF.replace("<name>", gang.getName()).replace("<player>", player.getName()))
            .event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(hoverText).create()))
            .event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/g join " + gang.getName()));

        gang.sendMessage(Language.GANG_PREFIX + Language.GANGS_INVITED_OTHERS.replace("<player>", target.getName()));

        target.getPlayer().spigot().sendMessage(message.create());
    }
}

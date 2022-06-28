package nl.rubixstudios.gangsturfs.gang.commands.player;

import nl.rubixstudios.gangsturfs.commands.manager.SubCommand;
import nl.rubixstudios.gangsturfs.data.Config;
import nl.rubixstudios.gangsturfs.data.Language;
import nl.rubixstudios.gangsturfs.gang.Gang;
import nl.rubixstudios.gangsturfs.gang.GangManager;
import nl.rubixstudios.gangsturfs.gang.type.PlayerGang;
import nl.rubixstudios.gangsturfs.utils.Color;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class ShowCommand extends SubCommand {

    public ShowCommand() {
        super("show", Arrays.asList("i", "info", "who"));

        this.setExecuteAsync(true);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(args.length == 0) {
            if(!this.checkConsoleSender(sender)) return;

            final Player player = (Player) sender;
            final PlayerGang gang = GangManager.getInstance().getPlayerGang(player);

            if(gang == null) {
                player.sendMessage(Language.GANG_PREFIX + Language.GANGS_NOT_IN_GANG_SELF);
                return;
            }

            gang.showInformation(player);
            return;
        }

        if (!sender.hasPermission("gangturfs.gang.show")) {
            sender.sendMessage(Language.GANG_PREFIX + Language.COMMANDS_COMMAND_NOT_FOUND);
            return;
        }

        if (!Config.GANG_OPTIONS_SHOW_COMMAND_ENABLE_FOR_STAFF) {
            sender.sendMessage(Language.GANG_PREFIX + Color.translate("&cThis command has been disabled for you."));
            return;
        }

        final Gang gang = GangManager.getInstance().getGangByName(args[0]);
        final PlayerGang playerGang = GangManager.getInstance().getPlayerGang(args[0]);

        if (playerGang == null && gang == null) {
            sender.sendMessage(Language.GANG_PREFIX + Language.GANGS_GANG_DOESNT_EXIST.replace("<argument>", args[0]));
            return;
        }


        if (gang != null) gang.showInformation(sender);
        if (playerGang != null) playerGang.showInformation(sender);
    }
}
package nl.rubixstudios.gangsturfs.commands.admin;

import nl.rubixstudios.gangsturfs.GangsTurfs;
import nl.rubixstudios.gangsturfs.commands.manager.BaseCommand;
import nl.rubixstudios.gangsturfs.data.Config;
import nl.rubixstudios.gangsturfs.data.ConfigFile;
import nl.rubixstudios.gangsturfs.data.Language;
import nl.rubixstudios.gangsturfs.utils.Color;
import org.bukkit.command.CommandSender;

import java.util.Arrays;

public class GangTurfsCommand extends BaseCommand {

    public GangTurfsCommand() {
        super("gangturfs", Arrays.asList("gangs&turfs", "gangs", "turfs", "voorhoofd", "achterhoofd", "djorr"));
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(args.length != 1 || !args[0].equalsIgnoreCase("reload")) {
            this.sendPluginInfo(sender);
            return;
        }

        if(!sender.hasPermission("gangwars.reload")) {
            this.sendPluginInfo(sender);
            return;
        }

        this.reloadConfigs(sender);
    }

    private void sendPluginInfo(CommandSender sender) {
        sender.sendMessage(Color.translate("&6&m---&e&m------------------------------------&6&m---"));
        sender.sendMessage(Color.translate(" &cGangs & Turfs &7made by: &fDjorr"));
        sender.sendMessage(Color.translate(" &7Plugin version: &f" + GangsTurfs.getInstance().getDescription().getVersion()));
        sender.sendMessage(Color.translate(" &7Support discord: &fhttps://www.discord.gg/hZatvFRrDj/"));
        sender.sendMessage(Color.translate(" &7Download: &fhttps://www.spigotmc.org/resources/gangs-turfs-1-12-2-minetopiasdb-addon.102879/"));
        sender.sendMessage(Color.translate("&6&m---&e&m------------------------------------&6&m---"));
    }

    private void reloadConfigs(CommandSender sender) {
        long startTime = System.currentTimeMillis();

        final GangsTurfs gangsTurfs = GangsTurfs.getInstance();

        gangsTurfs.setConfig(new ConfigFile("config.yml"));
        gangsTurfs.setLanguage(new ConfigFile("language.yml"));

        new Config();
        new Language();

        sender.sendMessage(Language.PREFIX + Language.PLUGIN_RELOAD_MESSAGE.replace("<time>",
        String.valueOf((System.currentTimeMillis() - startTime) / 1000)));
    }
}

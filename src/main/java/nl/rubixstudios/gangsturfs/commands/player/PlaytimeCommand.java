package nl.rubixstudios.gangsturfs.commands.player;

import nl.rubixstudios.gangsturfs.commands.manager.BaseCommand;
import nl.rubixstudios.gangsturfs.data.Language;
import org.apache.commons.lang.time.DurationFormatUtils;
import org.bukkit.Bukkit;
import org.bukkit.Statistic;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PlaytimeCommand extends BaseCommand {

    public PlaytimeCommand() {
        super("playtime");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(args.length == 0) {
            sender.sendMessage(Language.PREFIX + Language.PLAYTIME_COMMAND_USAGE);
            return;
        }

        final Player target = Bukkit.getPlayer(args[0]);
        if(!this.checkPlayer(sender, target, args[0])) return;

        long playTime = target.getStatistic(Statistic.PLAY_ONE_TICK);

        sender.sendMessage(Language.PREFIX + Language.PLAYTIME_COMMAND_MESSAGE
            .replace("<time>", DurationFormatUtils.formatDurationWords(playTime * 50, true, true))
            .replace("<player>", target.getName()));
    }
}

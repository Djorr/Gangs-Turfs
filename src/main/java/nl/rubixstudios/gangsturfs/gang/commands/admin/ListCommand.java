package nl.rubixstudios.gangsturfs.gang.commands.admin;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import nl.rubixstudios.gangsturfs.commands.manager.SubCommand;
import nl.rubixstudios.gangsturfs.data.Language;
import nl.rubixstudios.gangsturfs.gang.GangManager;
import nl.rubixstudios.gangsturfs.gang.type.PlayerGang;
import nl.rubixstudios.gangsturfs.utils.StringUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class ListCommand extends SubCommand {

    public ListCommand() {
        super("list", "gangturfs.gang.list");

        this.setExecuteAsync(true);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        final Player player = (Player) sender;
        final int page = args.length == 0 ? 1 : StringUtils.isInteger(args[0]) ? Math.max(1, Integer.parseInt(args[0])) : 1;

        final Map<PlayerGang, Integer> gangs = new HashMap<>();

        GangManager.getInstance().getGangs().values().forEach(faction -> {
            if (!(faction instanceof PlayerGang)) return;

            final PlayerGang playerGang = GangManager.getInstance().getPlayerGangByUuid(faction.getId());
            if (playerGang == null) return;

            gangs.put(playerGang, playerGang.getOnlineMembers().size());
        });

        final int pageTotal = Math.max(1, (int) Math.ceil(gangs.size() / 10d));

        if(page > pageTotal) {
            sender.sendMessage(Language.GANG_PREFIX + Language.GANGS_LIST_PAGE_NOT_FOUND
            .replace("<number>", String.valueOf(pageTotal)));
            return;
        }

        final List<Entry<PlayerGang, Integer>> sortedGangs = new ArrayList<>(gangs.entrySet());
        sortedGangs.sort(Entry.<PlayerGang, Integer>comparingByValue().reversed());

        Language.GANGS_LIST_HEADER.forEach(line -> sender.sendMessage(line.replace("<page>",
        String.valueOf(page)).replace("<pageTotal>", String.valueOf(pageTotal))));

        for(int i = (page - 1) * 10; i < (page - 1) * 10 + 10; i++) {
            if(gangs.size() <= i) break;

            final Entry<PlayerGang, Integer> entry = sortedGangs.get(i);

            final ComponentBuilder message = new ComponentBuilder(Language.GANGS_LIST_GANG_FORMAT
            .replace("<number>", String.valueOf(i + 1))
            .replace("<name>", entry.getKey().getName(sender))

            .replace("<online-count>", String.valueOf(entry.getValue()))
            .replace("<total-count>", String.valueOf(entry.getKey().getMembers().size())));

            final String hoverText = Language.GANGS_SHOW_HOVER_TEXT.replace("<gang>", entry.getKey().getName());

            message.event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(hoverText).create()))
            .event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/gang show " + entry.getKey().getName()));

            player.spigot().sendMessage(message.create());
        }

        Language.GANGS_LIST_FOOTER.forEach(sender::sendMessage);
    }
}

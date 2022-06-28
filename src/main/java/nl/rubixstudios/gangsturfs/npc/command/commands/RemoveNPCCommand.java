package nl.rubixstudios.gangsturfs.npc.command.commands;

import nl.rubixstudios.gangsturfs.GangsTurfs;
import nl.rubixstudios.gangsturfs.commands.manager.SubCommand;
import nl.rubixstudios.gangsturfs.data.Language;
import nl.rubixstudios.gangsturfs.npc.NPCController;
import nl.rubixstudios.gangsturfs.utils.Color;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RemoveNPCCommand extends SubCommand {

    public RemoveNPCCommand() {
        super("remove", "gangturfs.npc.remove");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        final Player player = (Player) sender;

        if (args.length == 1) {
            final String npcName = args[0];

            final NPCController npcController = GangsTurfs.getInstance().getNpcController();
            if (npcController.getNpcManager().getNpc(npcName) == null) {
                sender.sendMessage(Language.NPC_PREFIX + Color.translate("&fDe npc met de naam &c<naam> &fbestaat niet!".replace("<naam>", npcName)));
                return;
            }

            sender.sendMessage(Language.NPC_PREFIX + Language.NPC_REMOVED.replace("<npcName>", npcName));
            npcController.deleteNpc(sender, npcName);
            return;
        }

        player.sendMessage(Color.translate(this.getPrefix() + "&eGebruik: &f/npc remove <name>"));
    }
}

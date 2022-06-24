package nl.rubixstudios.gangsturfs.npc.command.commands;

import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import nl.rubixstudios.gangsturfs.GangsTurfs;
import nl.rubixstudios.gangsturfs.commands.manager.SubCommand;
import nl.rubixstudios.gangsturfs.data.Language;
import nl.rubixstudios.gangsturfs.npc.NPCController;
import nl.rubixstudios.gangsturfs.utils.Color;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CreateNPCCommand extends SubCommand {

    public CreateNPCCommand() {
        super("create", false);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        final Player player = (Player) sender;
        if (!player.isOp()) return;

        if (args.length != 1) {
            player.sendMessage(Color.translate(this.getPrefix() + "&eUsage: &f/npc create <name>"));
            return;
        }

        final String npcName = args[0];

       if (npcAlreadyExists(npcName)) {
           sender.sendMessage(Language.NPCS_PREFIX + Color.translate("&fDe npc met de naam &c<naam> &fbestaat al!".replace("<naam>", npcName)));
           return;
       }

       if (npcName.startsWith("gang-")) {
           final NPCController controller = GangsTurfs.getInstance().getNpcController();
           controller.createNPC(sender, npcName);
           sender.sendMessage(Language.NPCS_PREFIX + Color.translate("&fJe hebt succesvol de npc &a<naam> &faangemaakt!".replace("<naam>", npcName)));
       }
    }

    private boolean npcAlreadyExists(String npcName) {
        for (NPC npc : CitizensAPI.getNPCRegistry()) {
            return npc.getName().equals(npcName);
        }
        return false;
    }
}

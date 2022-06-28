package nl.rubixstudios.gangsturfs.gang.listener;

import nl.rubixstudios.gangsturfs.GangsTurfs;
import nl.rubixstudios.gangsturfs.data.Config;
import nl.rubixstudios.gangsturfs.data.Language;
import nl.rubixstudios.gangsturfs.gang.GangController;
import nl.rubixstudios.gangsturfs.gang.GangManager;
import nl.rubixstudios.gangsturfs.gang.enums.Role;
import nl.rubixstudios.gangsturfs.gang.type.PlayerGang;
import nl.rubixstudios.gangsturfs.utils.Color;
import nl.rubixstudios.gangsturfs.utils.item.ItemUtils;
import nl.rubixstudios.gangsturfs.utils.item.PreBuildItems;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class GangMenuListener implements Listener {

    private final GangController gangController;

    public GangMenuListener() {
        this.gangController = GangController.getInstance();
    }

    @EventHandler
    public void onGangCreationMenu(InventoryClickEvent event) {
        if (!event.getInventory().getName().equals(this.gangController.getGangCreationMenu().getName())) return;
        event.setCancelled(true);

        final ItemStack clickedItem = event.getCurrentItem();
        final Player player = (Player) event.getWhoClicked();

        final PlayerGang playerGang = GangManager.getInstance().getPlayerGang(player);

        if (ItemUtils.isSameItem(clickedItem, PreBuildItems.maakGang())) {
            player.closeInventory();

            if (playerGang != null) {
                player.sendMessage(Language.GANG_PREFIX + Color.translate("&cJe zit al in een gang!"));
                return;
            }

            final OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(player.getUniqueId());
            if (!GangsTurfs.getInstance().getEconomy().has(offlinePlayer, Config.GANG_PRIZE_CREATING_GANG)) {
                player.sendMessage(Language.GANG_PREFIX + Color.translate("&cJe hebt &f€<money> &cnodig om een gang te starten!".replace("<money>", "" + Config.GANG_PRIZE_CREATING_GANG)));
                return;
            }

            GangController.getInstance().startCreation(player);
        } else if (ItemUtils.isSameItem(clickedItem, PreBuildItems.verwijderGang())) {
            player.closeInventory();

            if (playerGang == null) {
                player.sendMessage(Language.GANG_PREFIX + Color.translate("&cJe zit niet in een gang!"));
                return;
            }

            if(playerGang.getMember(player).getRole() != Role.LEADER) {
                player.sendMessage(Language.GANG_PREFIX + Language.GANGS_MUST_BE_LEADER);
                return;
            }

            player.sendMessage(Language.GANG_PREFIX + Color.translate("&fJe hebt succesvol jou gang &e<gang> &fverwijderd!").replace("<gang>", playerGang.getName()));
            GangController.getInstance().disbandGang(playerGang, player);
        }

    }
}

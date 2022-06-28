package nl.rubixstudios.gangsturfs.turf.menu;

import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import nl.rubixstudios.gangsturfs.data.Config;
import nl.rubixstudios.gangsturfs.data.Language;
import nl.rubixstudios.gangsturfs.turf.TurfController;
import nl.rubixstudios.gangsturfs.turf.TurfData;
import nl.rubixstudios.gangsturfs.gang.GangManager;
import nl.rubixstudios.gangsturfs.gang.type.PlayerGang;
import nl.rubixstudios.gangsturfs.utils.Color;
import nl.rubixstudios.gangsturfs.utils.item.ItemUtils;
import nl.rubixstudios.gangsturfs.utils.item.PreBuildItems;
import nl.rubixstudios.gangsturfs.utils.worldguard.WorldGuardUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TurfMenuListener implements Listener {

    private final TurfController turfController;

    public TurfMenuListener() {
        this.turfController = TurfController.getInstance();
    }

    @EventHandler
    public void onStartClick(InventoryClickEvent event) {
        if (!event.getInventory().getName().equals(this.turfController.getNpcInv().getName())) return;
        event.setCancelled(true);

        final ItemStack clickedItem = event.getCurrentItem();
        final Player whoClicked = (Player) event.getWhoClicked();
        if (this.turfController.getTurfManager().getTurfs().isEmpty()) return;

        final ProtectedRegion protectedRegion = WorldGuardUtil.getTurfRegion(whoClicked.getLocation());
        if (protectedRegion == null) return;

        final TurfData turfData = this.turfController.getTurfManager().getTurf(protectedRegion.getId());

        if (ItemUtils.isSameItem(clickedItem, PreBuildItems.alreadyActiveItem())) {
            whoClicked.closeInventory();
            whoClicked.sendMessage(Language.TURF_PREFIX + Color.translate("&cTurf is al actief!"));
        } else if (ItemUtils.isSameItem(clickedItem, PreBuildItems.readyToStartGameItem(turfData))) {
            final List<UUID> playersInAGang = new ArrayList<>();

            Bukkit.getOnlinePlayers().forEach(player -> {
                final PlayerGang playerGang = GangManager.getInstance().getPlayerGang(player.getUniqueId());
                if (playerGang == null) return;
                playersInAGang.add(player.getUniqueId());
            });

            if (this.turfController.anyTurfStartedAlready()) {
                whoClicked.closeInventory();
                whoClicked.sendMessage(Language.TURF_PREFIX + Color.translate("&cThere is already another turf active!"));
                return;
            }

            if (this.turfController.anyTurfStartedAlready()) {
                whoClicked.closeInventory();
                whoClicked.sendMessage(Language.TURF_PREFIX + Color.translate("&cThere is already another turf active!"));
            } else if (this.turfController.isTurfOnCooldown(turfData)) {
                whoClicked.closeInventory();

                final long cooldownTime = (Config.TURF_COOLDOWN_BETWEEN_STARTING_NEW_TURF * 1000L) - (System.currentTimeMillis() - turfData.getTurfEndedTime());
                whoClicked.sendMessage(Language.TURF_PREFIX + Color.translate("&eJe kan een turf starten na <min> minuten!").replace("<min>", "" + PreBuildItems.convertTime(cooldownTime)));
            } else  if (playersInAGang.size() < Config.TURF_MINIMUM_PLAYERS_BEFORE_STARTING) {
                whoClicked.closeInventory();
                whoClicked.sendMessage(Language.TURF_PREFIX + Color.translate("&eEr moeten totaal <size> gangs online zijn om een turf te starten.")
                        .replace("<size>", "" + Config.TURF_MINIMUM_PLAYERS_BEFORE_STARTING)
                );
            } else {
                whoClicked.closeInventory();
                this.turfController.startTurf(whoClicked, Config.TURF_GAME_DURATION, Config.TURF_WINNINGS_FOR_EACH_GANG, turfData);
            }
        }
    }

    @EventHandler
    public void onClick(InventoryDragEvent event) {
        if (!event.getInventory().getName().equals(this.turfController.getNpcInv().getName())) return;
        event.setCancelled(true);
    }

    @EventHandler
    public void onClick(InventoryMoveItemEvent event) {
        if (!event.getSource().getName().equals(this.turfController.getNpcInv().getName())) return;
        event.setCancelled(true);
    }
}

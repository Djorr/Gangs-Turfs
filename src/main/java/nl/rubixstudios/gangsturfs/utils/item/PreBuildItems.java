package nl.rubixstudios.gangsturfs.utils.item;

import nl.rubixstudios.gangsturfs.data.Config;
import nl.rubixstudios.gangsturfs.games.turf.TurfData;
import nl.rubixstudios.gangsturfs.utils.Color;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public class PreBuildItems {

    // Gang

    public static ItemStack maakGang() {
        return new ItemBuilder(Material.STORAGE_MINECART)
                .setName(Color.translate("&aGang aanmaken"))
                .setLore(Arrays.asList(
                        Color.translate("&7 "),
                        Color.translate("&fPrijs&7: &6€<prijs>".replace("<prijs>", "" + Config.GANG_PRIZE)),
                        Color.translate(""),
                        Color.translate("&7Maak een eigen gang aan met jou vrienden"),
                        Color.translate("&7en doe mee aan turf events!"),
                        Color.translate(""),
                        Color.translate("&bKlik hier om een gang aan te maken.")
                ))
                .build();
    }

    public static ItemStack verwijderGang() {
        return new ItemBuilder(Material.MINECART)
                .setName(Color.translate("&cVerwijder gang"))
                .setLore(Arrays.asList(
                        Color.translate("&7 "),
                        Color.translate("&c&lLET OP!"),
                        Color.translate("&7Je raakt alle statistieken van jou gang kwijt en ook"),
                        Color.translate("&7alle buit die je hebt in je kluis!"),
                        Color.translate(""),
                        Color.translate("&bKlik hier om jou gang te verwijderen.")
                ))
                .build();
    }


    // Turf

    public static ItemStack readyToStartGameItem(TurfData turfData) {
        final boolean stillCooldown = System.currentTimeMillis() - turfData.getTurfEndedTime() < Config.TURF_COOLDOWN * 1000L;
        final long cooldownTime = (Config.TURF_COOLDOWN * 1000L) - (System.currentTimeMillis() - turfData.getTurfEndedTime());

        if (stillCooldown) {
            return new ItemBuilder(Material.GOLD_BLOCK, 1)
                    .setName("&e&lTURF")
                    .setLore(Arrays.asList(
                            Color.translate("&7  "),
                            Color.translate("&7Overleef met jou gang in de turf regio"),
                            Color.translate("&7tot dat de tijd over is en ontvang met "),
                            Color.translate("&7jou gang grote geld prijzen!"),
                            Color.translate("&7  "),
                            Color.translate("&7Je moet in een gang zitten om geld prijzen"),
                            Color.translate("&7te kunnen winnen."),
                            Color.translate("&7  "),
                            Color.translate("&fPrize: &6150000.00"),
                            Color.translate("&fSTATUS: &6ON COOLDOWN"),
                            Color.translate("&fCooldown tijd: &e<tijd>".replace("<tijd>", "" + convertTime(cooldownTime))),
                            Color.translate("&7  "),
                            Color.translate("&bKlik hier om de turf te starten!")
                    )).build();
        } else {
            return new ItemBuilder(Material.EMERALD_BLOCK, 1)
                    .setName("&e&lTURF")
                    .setLore(Arrays.asList(
                            Color.translate("&7  "),
                            Color.translate("&7Overleef met jou gang in de turf regio"),
                            Color.translate("&7tot dat de tijd over is en ontvang met "),
                            Color.translate("&7jou gang grote geld prijzen!"),
                            Color.translate("&7  "),
                            Color.translate("&7Je moet in een gang zitten om geld prijzen"),
                            Color.translate("&7te kunnen winnen."),
                            Color.translate("&7  "),
                            Color.translate("&fPrize: &6150000.00"),
                            Color.translate("&fSTATUS: &aGEREED"),
                            Color.translate("&7  "),
                            Color.translate("&bKlik hier om de turf te starten!")
                    )).build();
        }
    }

    public static ItemStack alreadyActiveItem() {
        return new ItemBuilder(Material.REDSTONE_BLOCK, 1)
                .setName("&e&lTURF")
                .setLore(Arrays.asList(
                        Color.translate("&7  "),
                        Color.translate("&7Overleef met jou gang in de turf regio"),
                        Color.translate("&7tot dat de tijd over is en ontvang met "),
                        Color.translate("&7jou gang grote geld prijzen!"),
                        Color.translate("&7  "),
                        Color.translate("&7Je moet in een gang zitten om geld prijzen"),
                        Color.translate("&7te kunnen winnen."),
                        Color.translate("&7  "),
                        Color.translate("&fPrize: &6150000.00"),
                        Color.translate("&fSTATUS: &cACTIEF"),
                        Color.translate("&7  "),
                        Color.translate("&bKlik hier om de turf te starten!")
                )).build();
    }

    public static String convertTime(final long time) {
        final int seconds = (int) (time / 1000) % 60;
        final int minutes = (int) ((time / (1000 * 60)) % 60);
        final int hours = (int) ((time / (1000 * 60 * 60)) % 24);
        final int days = (int) ((time / (1000 * 60 * 60 * 24)) % 30);


        if (time < 3600000L) {
            return minutes + "m, " + seconds + "s";
        } else if (time < 86400000L) {
            return hours + "h, " + minutes + "m, " + seconds + "s";
        } else {
            return days + "d, " + hours + "h, " + minutes + "m, " + seconds + "s";
        }
    }
}

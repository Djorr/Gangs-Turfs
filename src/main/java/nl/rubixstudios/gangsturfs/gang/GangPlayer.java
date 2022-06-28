package nl.rubixstudios.gangsturfs.gang;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nl.rubixstudios.gangsturfs.gang.enums.Role;
import nl.rubixstudios.gangsturfs.gang.type.PlayerGang;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class GangPlayer {

    private UUID uuid;
    private UUID gangId;

    private Role role;

    public GangPlayer(UUID uuid, PlayerGang gang) {
        this.uuid = uuid;

        this.gangId = gang.getId();
        this.role = Role.THUG;
    }

    public PlayerGang getGang() {
        return GangManager.getInstance().getPlayerGangByUuid(this.gangId);
    }

    public Player getPlayer() {
        return Bukkit.getPlayer(this.uuid);
    }

    public String getName() {
        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(this.uuid);
        return offlinePlayer.hasPlayedBefore() || offlinePlayer.isOnline() ? offlinePlayer.getName() : null;
    }

    public void sendMessage(String message) {
        Player player = this.getPlayer();

        if(player != null) {
            player.sendMessage(message);
        }
    }

}

package nl.rubixstudios.gangsturfs.gang;

import lombok.Getter;
import lombok.Setter;
import nl.rubixstudios.gangsturfs.gang.enums.Relation;
import nl.rubixstudios.gangsturfs.gang.event.GangRenameEvent;
import nl.rubixstudios.gangsturfs.gang.type.PlayerGang;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.Timestamp;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
public class Gang {

    protected UUID id;

    private String name;
    private boolean deathban;

    private Timestamp founded;

    protected Gang(String name) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.founded = new Timestamp(System.currentTimeMillis());
    }

    public Gang() {
    }

    public boolean setName(CommandSender sender, String name, boolean forceRename) {
        final GangRenameEvent event = new GangRenameEvent(name, this, sender, forceRename);
        if (event.isCancelled()) return false;

        this.name = name;
        return true;
    }

    private Relation getRelation(CommandSender sender) {
        return sender instanceof Player ? this.getRelation(GangManager.getInstance().getPlayerGang((Player) sender)) : Relation.ENEMY;
    }

    private Relation getRelation(Gang other) {
        if(!(other instanceof PlayerGang) || !(this instanceof PlayerGang)) return Relation.ENEMY;

        PlayerGang faction = (PlayerGang) this;
        PlayerGang playerFaction = (PlayerGang) other;

        return faction == playerFaction ? Relation.MEMBER : Relation.ENEMY;
    }

    private String getRelationColor(CommandSender sender) {
        return this.getRelation(sender).getColor();
    }

    public String getName(CommandSender sender) { return this.getRelationColor(sender) + this.name; }

    public String getDisplayName(CommandSender sender) {
        return this.getName(sender);
    }

    public void showInformation(CommandSender sender) {

    }

    @Override
    public boolean equals(Object other) {
        if(this == other) return true;
        if(!(other instanceof Gang)) return false;

        final Gang gang = (Gang) other;

        return Objects.equals(this.id, gang.getId());
    }

    @Override
    public int hashCode() {
        return this.id.hashCode();
    }
}

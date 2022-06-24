package nl.rubixstudios.gangsturfs.userdata;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class Userdata {

    private long firstTimeJoined;

    private long lastTimeJoined;
    private long lastTimeLeft;

    private UUID uuid;
    private String name;

    private int kills;
    private int deaths;

    private double kdr;

    private long joinedTime;
    private long playTime;

    private int statsResetted;
    private long lastStatReset;

    public Userdata(UUID uuid, String name) {
        this.joinedTime = System.currentTimeMillis();

        this.uuid = uuid;
        this.name = name;

        this.kills = 0;
        this.deaths = 0;

        this.kdr = 0.00;
    }

    public void addStatReset() {
        this.statsResetted++;
        this.setLastStatReset(System.currentTimeMillis());
    }

    public void addKill() { this.kills++; }
    public void addDeath() { this.deaths++; }
    public double getKdr() { return this.kdr = (this.getDeaths() != 0) ? (double) this.getKills() / (double) this.getDeaths() : this.getKills(); }

    public long getOnlinePlayerTime() {
        return this.playTime;
    }

    public long getOfflinePlayerTime() {
        return this.playTime;
    }
}

package nl.rubixstudios.gangsturfs.utils.worldguard;

import com.sk89q.worldguard.protection.flags.DefaultFlag;
import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import nl.rubixstudios.gangsturfs.GangsTurfs;
import nl.rubixstudios.gangsturfs.data.Config;
import org.bukkit.Location;

import java.util.ArrayList;
import java.util.List;

public class WorldGuardUtil {

    public static List<ProtectedRegion> getNearestRegionsCT(Location location) {
        List<ProtectedRegion> regionsList = new ArrayList<>();
        for (ProtectedRegion protectedRegion : GangsTurfs.getInstance().getWorldGuard().getRegionManager(location.getWorld()).getRegions().values()) {
            if (protectedRegion.getFlag(DefaultFlag.PVP) == StateFlag.State.DENY || protectedRegion.getFlag(DefaultFlag.PVP) == StateFlag.State.ALLOW && isInsideTurfRegion(location)) {
                regionsList.add(protectedRegion);
            }
        }
        return regionsList;
    }

    public static boolean isInsideTurfRegion(Location location) {
        for (ProtectedRegion protectedRegion : GangsTurfs.getInstance().getWorldGuard().getRegionManager(location.getWorld()).getApplicableRegions(location)) {
            if (protectedRegion.getId().startsWith(Config.TURF_REGIONS_START_WITH)) {
                return true;
            }
        }
        return false;
    }

    public static ProtectedRegion getTurfRegion(Location location) {
        for (ProtectedRegion protectedRegion : GangsTurfs.getInstance().getWorldGuard().getRegionManager(location.getWorld()).getApplicableRegions(location)) {
            if (protectedRegion.getId().startsWith(Config.TURF_REGIONS_START_WITH)) {
                return protectedRegion;
            }
        }
        return null;
    }
}

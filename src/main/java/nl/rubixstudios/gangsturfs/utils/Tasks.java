package nl.rubixstudios.gangsturfs.utils;

import nl.rubixstudios.gangsturfs.GangsTurfs;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitTask;

import java.util.concurrent.ThreadFactory;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

public class Tasks {

    public static ThreadFactory newThreadFactory(String name) {
        return new ThreadFactoryBuilder().setNameFormat(name).build();
    }

    public static ThreadFactory newThreadFactory(String name, Thread.UncaughtExceptionHandler handler) {
        return new ThreadFactoryBuilder().setNameFormat(name).setUncaughtExceptionHandler(handler).build();
    }

    public static void sync(Callable callable) {
        Bukkit.getScheduler().runTask(GangsTurfs.getInstance(), callable::call);
    }

    public static BukkitTask syncLater(Callable callable, long delay) {
        return Bukkit.getScheduler().runTaskLater(GangsTurfs.getInstance(), callable::call, delay);
    }

    public static BukkitTask syncTimer(Callable callable, long delay, long value) {
        return Bukkit.getScheduler().runTaskTimer(GangsTurfs.getInstance(), callable::call, delay, value);
    }

    public static void async(Callable callable) {
        Bukkit.getScheduler().runTaskAsynchronously(GangsTurfs.getInstance(), callable::call);
    }

    public static BukkitTask asyncLater(Callable callable, long delay) {
        return Bukkit.getScheduler().runTaskLaterAsynchronously(GangsTurfs.getInstance(), callable::call, delay);
    }

    public static BukkitTask asyncTimer(Callable callable, long delay, long value) {
        return Bukkit.getScheduler().runTaskTimerAsynchronously(GangsTurfs.getInstance(), callable::call, delay, value);
    }

    public interface Callable {
        void call();
    }
}
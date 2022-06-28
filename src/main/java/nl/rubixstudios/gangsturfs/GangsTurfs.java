package nl.rubixstudios.gangsturfs;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import lombok.Getter;
import lombok.Setter;
import net.milkbowl.vault.economy.Economy;
import nl.rubixstudios.gangsturfs.combat.CombatTagController;
import nl.rubixstudios.gangsturfs.combat.CombatTagManager;
import nl.rubixstudios.gangsturfs.combat.commands.CombatCommandExecutor;
import nl.rubixstudios.gangsturfs.commands.manager.CommandManager;
import nl.rubixstudios.gangsturfs.commands.manager.SubCommandExecutor;
import nl.rubixstudios.gangsturfs.data.Config;
import nl.rubixstudios.gangsturfs.data.ConfigFile;
import nl.rubixstudios.gangsturfs.data.Language;
import nl.rubixstudios.gangsturfs.turf.TurfController;
import nl.rubixstudios.gangsturfs.turf.TurfManager;
import nl.rubixstudios.gangsturfs.turf.command.TurfCommandExecutor;
import nl.rubixstudios.gangsturfs.gang.GangController;
import nl.rubixstudios.gangsturfs.gang.GangManager;
import nl.rubixstudios.gangsturfs.gang.commands.GangCommandExecutor;
import nl.rubixstudios.gangsturfs.npc.NPCController;
import nl.rubixstudios.gangsturfs.npc.NPCManager;
import nl.rubixstudios.gangsturfs.npc.command.NPCCommandExecutor;
import nl.rubixstudios.gangsturfs.userdata.UserdataController;
import nl.rubixstudios.gangsturfs.utils.*;
import nl.rubixstudios.gangsturfs.utils.gson.GangTypeAdapter;
import nl.rubixstudios.gangsturfs.utils.gson.LocationTypeAdapter;
import nl.rubixstudios.gangsturfs.utils.gson.PlayerTypeAdapter;
import nl.rubixstudios.gangsturfs.utils.nms.NmsUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.*;

@Getter
public class GangsTurfs extends JavaPlugin {

    @Getter private static GangsTurfs instance;
    @Setter public boolean fullyEnabled;

    private Gson gson;

    @Setter private ConfigFile config;
    @Setter private ConfigFile language;

    @Setter private UserdataController userdataController;

    private CombatTagManager combatTagManager;
    private CombatTagController combatTagController;

    private TurfManager turfManager;
    private TurfController turfController;

    private NPCManager npcManager;
    private NPCController npcController;

    private CommandManager commandManager;
    private GangCommandExecutor gangCommandExecutor;
    private TurfCommandExecutor turfCommandExecutor;
    private CombatCommandExecutor combatCommandExecutor;
    private NPCCommandExecutor npcCommandExecutor;

    private @Getter Economy economy = null;

    @Override
    public void onEnable() {
        instance = this;

        final RegisteredServiceProvider<Economy> economyProvider = GangsTurfs.getInstance().getServer().getServicesManager().getRegistration(Economy.class);
        if (economyProvider != null) {
            economy = economyProvider.getProvider();
        }

        NmsUtils.init();
        Config.START_TIME = System.currentTimeMillis();

        Bukkit.getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");

        this.log("&6===&e=============================================&6===");
        this.log("- &eNaam&7: &fGangs & Turfs");
        this.log("- &eVersion&7: &f" + this.getDescription().getVersion());
        this.log("- &eAuthor&7: &fDjorr");

        try {
            this.config = new ConfigFile("config.yml");
            this.language = new ConfigFile("language.yml");
        } catch(RuntimeException e) {
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }

        this.log("");
        this.log("- &7Using &e" + Datastore.DATASTORE + " &7datastore.");
        this.log("");

        this.beforeInit();

        try {
            this.setupDatastore();

            this.setupManagers();
        } catch(Exception e) {
            this.log("&6===&e=============================================&6===");
            this.log("   &eError occurred while enabling Gangs & Turfs. Error:");
            this.log("");

            e.printStackTrace();

            this.log("");
            this.log("&6===&e=============================================&6===");
            Bukkit.getPluginManager().disablePlugin(this);
        }
    }

    @Override
    public void onDisable() {
        if(!this.fullyEnabled) return;

        this.log("&6===&e=============================================&6===");
        this.log("- &7Disabling &fGangWars " + this.getDescription().getVersion());
        this.log("");

        this.userdataController.disable();
        GangManager.getInstance().disable();

        this.disableGameManagers();
        this.disableCommandManagers();

        Bukkit.getServicesManager().unregisterAll(this);

        this.log("&6===&e=============================================&6===");
    }

    private void disableGameManagers() {
        this.turfController.disable();
        this.npcController.disable();

        this.combatTagController.disable();
        this.combatTagManager.disable();

    }

    private void disableCommandManagers() {
        this.commandManager.disable();
        this.gangCommandExecutor.disable();
        this.turfCommandExecutor.disable();
        this.combatCommandExecutor.disable();
        this.npcCommandExecutor.disable();
    }

    private void beforeInit() {
        this.registerGson();

        new Config();
        new Language();
    }

    public WorldGuardPlugin getWorldGuard() {
        final Plugin plugin = Bukkit.getPluginManager().getPlugin("WorldGuard");
        if (!(plugin instanceof WorldGuardPlugin)) return null;
        return (WorldGuardPlugin)plugin;
    }

    private void setupDatastore() {
        this.userdataController = new UserdataController();

        if (Config.GANG_ENABLED) {
            new GangController();
            new GangManager();
        }
    }

    private void setupManagers() throws Exception {
        for(Field field : this.getClass().getDeclaredFields()) {
            if(!ManagerEnabler.class.isAssignableFrom(field.getType()) && field.getType().getSuperclass() != SubCommandExecutor.class) continue;

            field.setAccessible(true);

            Constructor<?> constructor = field.getType().getDeclaredConstructor();
            field.set(this, constructor.newInstance());
        }

        this.log("");
        this.log("- &eEnabled. Took &c" + (System.currentTimeMillis() - Config.START_TIME) + " &ems.");
        this.log("&6===&e=============================================&6===");

        this.fullyEnabled = true;
    }

    public void registerGson() {
        this.gson = new GsonBuilder().setPrettyPrinting().serializeNulls()
                .enableComplexMapKeySerialization().excludeFieldsWithModifiers(Modifier.TRANSIENT, Modifier.STATIC)
                .registerTypeAdapter(GsonUtils.PLAYER_TYPE, new PlayerTypeAdapter())
                .registerTypeAdapter(GsonUtils.GANG_TYPE, new GangTypeAdapter())
                .registerTypeAdapter(GsonUtils.GANG_TYPE, new GangTypeAdapter())
                .registerTypeAdapter(Location.class, new LocationTypeAdapter())
                .create();
    }

    public void log(String message) {
        Bukkit.getConsoleSender().sendMessage(Color.translate(message));
    }

    public ClassLoader getPluginClassLoader() {
        return this.getClassLoader();
    }
}

package ramune314159265.wsconnectionplugin;

import org.bukkit.Bukkit;
import ramune314159265.wsconnectionplugin.WsPluginListener;

import org.bukkit.plugin.java.JavaPlugin;

public final class WsConnectionPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        getServer().getPluginManager().registerEvents(new WsPluginListener(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}

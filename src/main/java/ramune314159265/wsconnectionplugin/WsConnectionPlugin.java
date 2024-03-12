package ramune314159265.wsconnectionplugin;

import org.bukkit.Bukkit;
import ramune314159265.wsconnectionplugin.WsPluginListener;

import org.bukkit.plugin.java.JavaPlugin;
import ramune314159265.wsconnectionplugin.events.ServerStartedEvent;
import ramune314159265.wsconnectionplugin.events.ServerStoppedEvent;

public final class WsConnectionPlugin extends JavaPlugin {

    static WsConnection wsConnection;
    public WsConnectionPlugin(){

    }
    @Override
    public void onEnable() {
        Bukkit.getLogger().info("wsに接続中...");
        WsConnectionPlugin.wsConnection = new WsConnection();
        WsConnectionPlugin.wsConnection.init("ws://localhost:8080");
        // Plugin startup logic
        getServer().getPluginManager().registerEvents(new WsPluginListener(), this);
        WsConnectionPlugin.wsConnection.sendEventData(new ServerStartedEvent())
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        WsConnectionPlugin.wsConnection.sendEventData(new ServerStoppedEvent());
        WsConnectionPlugin.wsConnection.disconnect();
    }
}

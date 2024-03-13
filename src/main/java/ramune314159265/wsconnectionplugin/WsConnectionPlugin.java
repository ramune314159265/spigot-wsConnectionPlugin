package ramune314159265.wsconnectionplugin;

import org.bukkit.Bukkit;
import com.moandjiezana.toml.Toml;

import org.bukkit.plugin.java.JavaPlugin;
import ramune314159265.wsconnectionplugin.events.ServerStartedEvent;
import ramune314159265.wsconnectionplugin.events.ServerStoppedEvent;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

public final class WsConnectionPlugin extends JavaPlugin {

    static WsConnection wsConnection;
    public static String wsUrl;
    public static String serverId;
    public WsConnectionPlugin(){

    }
    @Override
    public void onEnable() {
        Bukkit.getLogger().info("wsに接続中...");
        WsConnectionPlugin.wsConnection = new WsConnection();
        WsConnectionPlugin.wsConnection.init(WsConnectionPlugin.serverId);

        getServer().getPluginManager().registerEvents(new WsPluginListener(), this);
        WsConnectionPlugin.wsConnection.sendEventData(new ServerStartedEvent());
    }

    @Override
    public void onDisable() {
        WsConnectionPlugin.wsConnection.sendEventData(new ServerStoppedEvent());

        Bukkit.getLogger().info("wsを切断中...");
        WsConnectionPlugin.wsConnection.disconnect();
    }

    public void loadConf(){
        File configFile = new File(getDataFolder(),"conf.toml");
        if(!configFile.getParentFile().exists()){
            configFile.getParentFile().mkdirs();
        }

        if(!configFile.exists()){
            try(InputStream input = WsConnectionPlugin.class.getResourceAsStream("/" + configFile.getName())){
                if (input != null) {
                    Files.copy(input, configFile.toPath());
                } else {
                    configFile.createNewFile();
                }
            }catch (IOException e) {
                Bukkit.getLogger().warning(e.toString());
            }
        }

        Toml configToml = new Toml().read(configFile);

        WsConnectionPlugin.wsUrl = configToml.getString("wsUrl");
        WsConnectionPlugin.serverId = configToml.getString("serverId");
    }
}

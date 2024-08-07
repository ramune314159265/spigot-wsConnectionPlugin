package ramune314159265.wsconnectionplugin;

import com.moandjiezana.toml.Toml;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import ramune314159265.wsconnectionplugin.events.EverySecond;
import ramune314159265.wsconnectionplugin.events.ServerStartedEvent;
import ramune314159265.wsconnectionplugin.events.ServerStoppedEvent;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.Timer;
import java.util.TimerTask;

public final class WsConnectionPlugin extends JavaPlugin {
	public static String wsUrl;
	public static String serverId;
	public static boolean isOpeningWs;
	static WsConnection wsConnection;
	Timer timer;

	public WsConnectionPlugin() {
		WsConnectionPlugin.isOpeningWs = false;
	}

	@Override
	public void onEnable() {
		this.loadConf();
		Bukkit.getLogger().info("wsに接続中...");
		WsConnectionPlugin.isOpeningWs = true;
		WsConnectionPlugin.wsConnection = new WsConnection();
		WsConnectionPlugin.wsConnection.init(WsConnectionPlugin.wsUrl);

		getServer().getPluginManager().registerEvents(new WsPluginListener(), this);
		WsConnectionPlugin.wsConnection.sendEventData(new ServerStartedEvent());

		getCommand("reconnectws").setExecutor(new WsCommandExecutor());

		Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this, new TickRunnable(), 0L, 0L);

		this.timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {
			public void run() {
				WsConnectionPlugin.wsConnection.sendEventData(new EverySecond(TickRunnable.tps, TickRunnable.lastTickTimestamp));
			}
		}, 1000, 1000);
	}

	@Override
	public void onDisable() {
		this.timer.cancel();
		WsConnectionPlugin.wsConnection.sendEventData(new ServerStoppedEvent());

		Bukkit.getLogger().info("wsを切断中...");
		WsConnectionPlugin.isOpeningWs = false;
		WsConnectionPlugin.wsConnection.disconnect();
	}

	public void loadConf() {
		File configFile = new File(getDataFolder(), "conf.toml");
		if (!configFile.getParentFile().exists()) {
			configFile.getParentFile().mkdirs();
		}

		if (!configFile.exists()) {
			try (InputStream input = WsConnectionPlugin.class.getResourceAsStream("/" + configFile.getName())) {
				if (input != null) {
					Files.copy(input, configFile.toPath());
				} else {
					configFile.createNewFile();
				}
			} catch (IOException e) {
				Bukkit.getLogger().warning(e.toString());
			}
		}

		Toml configToml = new Toml().read(configFile);

		WsConnectionPlugin.wsUrl = configToml.getString("wsUrl");
		WsConnectionPlugin.serverId = configToml.getString("serverId");
	}

	public static void reconnectWs(){
		WsConnectionPlugin.isOpeningWs = false;
		WsConnectionPlugin.wsConnection.disconnect();
		WsConnectionPlugin.wsConnection = new WsConnection();
		WsConnectionPlugin.wsConnection.init(WsConnectionPlugin.wsUrl);
		WsConnectionPlugin.isOpeningWs = true;
	}
}
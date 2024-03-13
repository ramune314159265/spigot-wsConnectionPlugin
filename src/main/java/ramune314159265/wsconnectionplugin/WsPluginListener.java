package ramune314159265.wsconnectionplugin;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.server.ServerLoadEvent;
import ramune314159265.wsconnectionplugin.events.Event;
import ramune314159265.wsconnectionplugin.events.PlayerDiedEvent;
import ramune314159265.wsconnectionplugin.events.ServerStartedEvent;

public class WsPluginListener implements Listener {
	@EventHandler
	public void onServerLoad(ServerLoadEvent event){
		Event eventDataToSend = new ServerStartedEvent();
		WsConnectionPlugin.wsConnection.sendEventData(eventDataToSend);
	}

	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent event){
		Event eventDataToSend = new PlayerDiedEvent(event.getEntity().getName(),event.getDeathMessage());
		WsConnectionPlugin.wsConnection.sendEventData(eventDataToSend);
	}
}

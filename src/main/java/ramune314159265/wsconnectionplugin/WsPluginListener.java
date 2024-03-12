package ramune314159265.wsconnectionplugin;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerLoadEvent;
import ramune314159265.wsconnectionplugin.events.Event;
import ramune314159265.wsconnectionplugin.events.ServerStartedEvent;

public class WsPluginListener implements Listener {
	@EventHandler
	public void onServerLoad(ServerLoadEvent event){
		Event eventDataToSend = new ServerStartedEvent();
		WsConnectionPlugin.wsConnection.sendEventData(eventDataToSend);
	}
}

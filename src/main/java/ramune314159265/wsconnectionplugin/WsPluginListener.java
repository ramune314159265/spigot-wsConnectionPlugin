package ramune314159265.wsconnectionplugin;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerAdvancementDoneEvent;
import org.bukkit.event.server.ServerLoadEvent;
import ramune314159265.wsconnectionplugin.events.Event;
import ramune314159265.wsconnectionplugin.events.PlayerAdvDoneEvent;
import ramune314159265.wsconnectionplugin.events.PlayerDiedEvent;
import ramune314159265.wsconnectionplugin.events.ServerStartedEvent;

import java.util.Objects;

public class WsPluginListener implements Listener {
	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent event){
		Event eventDataToSend = new PlayerDiedEvent(event.getEntity().getName(),event.getDeathMessage());
		WsConnectionPlugin.wsConnection.sendEventData(eventDataToSend);
	}

	@EventHandler
	public void onPlayerAdvancementDone(PlayerAdvancementDoneEvent event){
		if(Objects.requireNonNull(event.getAdvancement().getDisplay()).isHidden()){
			return;
		}
		AdvancementData advancementData = new AdvancementData(
				event.getAdvancement().getDisplay().getTitle(),
				event.getAdvancement().getDisplay().getType().toString(),
				event.getAdvancement().getDisplay().getDescription()
		);
		Event eventDataToSend = new PlayerAdvDoneEvent(event.getPlayer().getName(),advancementData);
		WsConnectionPlugin.wsConnection.sendEventData(eventDataToSend);
	}

	@EventHandler
	public void onPlayerChat(AsyncPlayerChatEvent event){
		event.setCancelled(true);
	}
}

package ramune314159265.wsconnectionplugin.events;

import ramune314159265.wsconnectionplugin.AdvancementData;
import ramune314159265.wsconnectionplugin.WsConnectionPlugin;

public class PlayerAdvDoneEvent extends Event {
	public String serverId;
	public String playerId;
	public AdvancementData advancement;

	public PlayerAdvDoneEvent(String playerId, AdvancementData advancement) {
		this.type = "player_advancement_done";
		this.serverId = WsConnectionPlugin.serverId;
		this.playerId = playerId;
		this.advancement = advancement;
	}
}

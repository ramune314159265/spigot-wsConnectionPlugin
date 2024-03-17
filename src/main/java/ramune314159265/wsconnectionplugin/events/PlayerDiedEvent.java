package ramune314159265.wsconnectionplugin.events;

import ramune314159265.wsconnectionplugin.WsConnectionPlugin;

public class PlayerDiedEvent extends Event {
	public String serverId;
	public String playerId;
	public String reason;

	public PlayerDiedEvent(String playerId, String reason) {
		this.type = "player_died";
		this.serverId = WsConnectionPlugin.serverId;
		this.playerId = playerId;
		this.reason = reason;
	}
}

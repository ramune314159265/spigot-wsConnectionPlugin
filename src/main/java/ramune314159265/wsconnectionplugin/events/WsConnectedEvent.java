package ramune314159265.wsconnectionplugin.events;

import ramune314159265.wsconnectionplugin.WsConnectionPlugin;

public class WsConnectedEvent extends Event {
	public String serverId;

	public WsConnectedEvent() {
		this.type = "ws_connected";
		this.serverId = WsConnectionPlugin.serverId;
	}
}

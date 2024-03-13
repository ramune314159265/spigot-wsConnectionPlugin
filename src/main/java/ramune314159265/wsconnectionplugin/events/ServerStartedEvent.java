package ramune314159265.wsconnectionplugin.events;

import ramune314159265.wsconnectionplugin.WsConnectionPlugin;

public class ServerStartedEvent extends Event {
	public String serverId;

	public ServerStartedEvent() {
		this.type = "server_started";
		this.serverId = WsConnectionPlugin.serverId;
	}
}
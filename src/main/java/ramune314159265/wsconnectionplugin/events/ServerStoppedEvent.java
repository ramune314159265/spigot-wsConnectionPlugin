package ramune314159265.wsconnectionplugin.events;

import ramune314159265.wsconnectionplugin.WsConnectionPlugin;

public class ServerStoppedEvent extends Event {
	public String serverId;

	public ServerStoppedEvent() {
		this.type = "server_stopped";
		this.serverId = WsConnectionPlugin.serverId;
	}
}
package ramune314159265.wsconnectionplugin.events;

public class ServerStartedEvent extends Event {
	public String serverId;

	public ServerStartedEvent() {
		this.type = "server_started";
		this.serverId = "";
	}
}
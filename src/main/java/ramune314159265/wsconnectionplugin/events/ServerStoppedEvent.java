package ramune314159265.wsconnectionplugin.events;

public class ServerStoppedEvent extends Event {
	public String serverId;

	public ServerStoppedEvent() {
		this.type = "server_stopped";
		this.serverId = "";
	}
}
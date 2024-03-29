package ramune314159265.wsconnectionplugin.events;

import ramune314159265.wsconnectionplugin.WsConnectionPlugin;

public class EverySecond extends Event {
	public String serverId;
	public float tps;
	public long lastTickTimestamp;

	public EverySecond(float tps, long lastTickTimestamp) {
		this.type = "every_second_info_send";
		this.tps = tps;
		this.lastTickTimestamp = lastTickTimestamp;
		this.serverId = WsConnectionPlugin.serverId;
	}
}

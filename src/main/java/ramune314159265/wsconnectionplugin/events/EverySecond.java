package ramune314159265.wsconnectionplugin.events;

import ramune314159265.wsconnectionplugin.WsConnectionPlugin;

public class EverySecond extends Event{
	public String serverId;
	public int tps;
	public long lastTickTimestamp;
	public EverySecond(int tps, long lastTickTimestamp){
		this.tps = tps;
		this.lastTickTimestamp = lastTickTimestamp;
		this.serverId = WsConnectionPlugin.serverId;
	}
}

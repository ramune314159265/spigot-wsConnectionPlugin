package ramune314159265.wsconnectionplugin;

public class PluginMessageReceivedData {
	String type;
	String serverId;
	String proxyId;
	String apiHostName;
	PluginMessageReceivedData(String type,String serverId,String proxyId,String apiHostName){
		this.type = type;
		this.serverId = serverId;
		this.proxyId = proxyId;
		this.apiHostName = apiHostName;
	}
}

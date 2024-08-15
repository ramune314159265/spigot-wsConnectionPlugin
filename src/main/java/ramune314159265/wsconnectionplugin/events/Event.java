package ramune314159265.wsconnectionplugin.events;

import java.util.Date;
import java.util.UUID;

public class Event {
	public String type;
	public Long timestamp;
	public String uuid;

	public Event() {
		this.timestamp = new Date().getTime();
		this.uuid = UUID.randomUUID().toString();
	}
}
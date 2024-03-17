package ramune314159265.wsconnectionplugin;

import com.google.gson.Gson;
import org.bukkit.Bukkit;
import ramune314159265.wsconnectionplugin.events.WsConnectedEvent;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.WebSocket;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutionException;

public class WsConnection {
	public WebSocket ws;

	public void init(String wsUrl) {
		HttpClient client = HttpClient.newHttpClient();
		WebSocket.Builder wsb = client.newWebSocketBuilder();

		WebSocket.Listener listener = new WebSocket.Listener() {
			@Override
			public void onOpen(WebSocket webSocket) {
				Bukkit.getLogger().info("wsに接続しました");
				Gson gson = new Gson();
				String json = gson.toJson(new WsConnectedEvent());
				webSocket.sendText(json, true);
			}

			@Override
			public CompletionStage<?> onClose(WebSocket webSocket, int statusCode, String reason) {
				Bukkit.getLogger().info("wsから切断しました");
				return null;
			}

			@Override
			public CompletionStage<?> onText(WebSocket webSocket, CharSequence data, boolean last) {
				Bukkit.getLogger().info((String) data);
				return null;
			}
		};

		CompletableFuture<WebSocket> comp = wsb.buildAsync(URI.create(wsUrl), listener);
		try {
			this.ws = comp.get();
		} catch (ExecutionException | InterruptedException e) {
			Bukkit.getLogger().warning("wsに接続できませんでした");
		}
	}

	public void sendEventData(Object data) {
		if (Objects.isNull(this.ws)) {
			Bukkit.getLogger().warning("wsが接続されていないため送信できません");
			return;
		}

		Gson gson = new Gson();
		String json = gson.toJson(data);

		this.ws.sendText(json, true);
	}

	public void disconnect() {
		if (Objects.isNull(this.ws)) {
			return;
		}

		this.ws.sendClose(1000, "disconnect() called");
	}
}

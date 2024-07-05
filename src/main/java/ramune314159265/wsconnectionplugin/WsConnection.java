package ramune314159265.wsconnectionplugin;

import com.google.gson.Gson;
import org.bukkit.Bukkit;
import ramune314159265.wsconnectionplugin.events.WsConnectedEvent;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.WebSocket;
import java.util.Objects;
import java.util.concurrent.*;

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
				if (WsConnectionPlugin.isOpeningWs) {
					Bukkit.getLogger().info("1秒後に再接続します");
					ScheduledExecutorService exec = Executors.newSingleThreadScheduledExecutor();
					exec.schedule(() -> {
						WsConnectionPlugin.reconnectWs();
						exec.shutdown();
					}, 1, TimeUnit.SECONDS);
				}
				return null;
			}

			@Override
			public void onError(WebSocket webSocket, Throwable error) {
				Bukkit.getLogger().warning(error.toString());
				Bukkit.getLogger().warning("1秒後に再接続します");
				ScheduledExecutorService exec = Executors.newSingleThreadScheduledExecutor();
				exec.schedule(() -> {
					WsConnectionPlugin.reconnectWs();
					exec.shutdown();
				}, 1, TimeUnit.SECONDS);
			}

			@Override
			public CompletionStage<?> onText(WebSocket webSocket, CharSequence data, boolean last) {
				return null;
			}
		};

		try {
			CompletableFuture<WebSocket> comp = wsb.buildAsync(URI.create(wsUrl), listener);
			this.ws = comp.get();
			this.ws.request(100000);
		} catch (ExecutionException | InterruptedException e) {
			ScheduledExecutorService exec = Executors.newSingleThreadScheduledExecutor();
			exec.schedule(() -> {
				WsConnectionPlugin.reconnectWs();
				exec.shutdown();
			}, 1, TimeUnit.SECONDS);
		}
	}

	public void sendEventData(Object data) {
		if (Objects.isNull(this.ws)) {
			Bukkit.getLogger().warning("wsが接続されていないため送信できません");
			return;
		}

		try {
			Gson gson = new Gson();
			String json = gson.toJson(data);

			this.ws.sendText(json, true);
			ScheduledExecutorService exec = Executors.newSingleThreadScheduledExecutor();
			exec.schedule(() -> {
				this.ws.sendText(json, true);
			}, 3, TimeUnit.SECONDS);
		} catch (Exception e) {
			Bukkit.getLogger().warning("送信処理中にエラーが発生しました");
		}
	}

	public void disconnect() {
		if (Objects.isNull(this.ws)) {
			return;
		}
		if (this.ws.isOutputClosed()) {
			return;
		}

		try {
			CompletableFuture<WebSocket> end = this.ws.sendClose(WebSocket.NORMAL_CLOSURE, "disconnect() called");
			end.get();
		} catch (InterruptedException | ExecutionException e) {
			Bukkit.getLogger().warning("disconnect error");
		}
	}
}

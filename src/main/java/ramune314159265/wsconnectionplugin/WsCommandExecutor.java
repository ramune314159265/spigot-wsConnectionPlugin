package ramune314159265.wsconnectionplugin;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class WsCommandExecutor implements CommandExecutor {
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args){
		WsConnectionPlugin.reconnectWs();
		sender.sendMessage("再接続しました");
		return true;
	}
}

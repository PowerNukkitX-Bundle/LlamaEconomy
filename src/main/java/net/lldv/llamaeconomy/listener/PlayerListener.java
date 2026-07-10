package net.lldv.llamaeconomy.listener;

import org.powernukkitx.event.EventHandler;
import org.powernukkitx.event.Listener;
import org.powernukkitx.event.player.PlayerJoinEvent;
import net.lldv.llamaeconomy.LlamaEconomy;

public class PlayerListener implements Listener {

    private final LlamaEconomy plugin;

    public PlayerListener(LlamaEconomy plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void on(PlayerJoinEvent event) {
        if (!LlamaEconomy.getAPI().hasAccount(event.getPlayer()))
            LlamaEconomy.getAPI().createAccount(event.getPlayer(), this.plugin.getDefaultMoney());
    }

}

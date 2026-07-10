package net.lldv.llamaeconomy.components.event;

import org.powernukkitx.Player;
import org.powernukkitx.event.HandlerList;
import org.powernukkitx.event.player.PlayerEvent;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author LlamaDevelopment
 * @project LlamaEconomy
 * @website http://llamadevelopment.net/
 */
@Getter
public class PlayerAddMoneyEvent extends PlayerEvent {

    private final String target;
    private final double amount;
    private static final HandlerList handlers = new HandlerList();

    public PlayerAddMoneyEvent(Player player, String target, double amount) {
        this.player = player;
        this.target = target;
        this.amount = amount;
    }

    public static HandlerList getHandlers() {
        return handlers;
    }

}

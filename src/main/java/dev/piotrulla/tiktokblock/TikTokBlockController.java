package dev.piotrulla.tiktokblock;

import dev.piotrulla.tiktokblock.hologram.HologramService;
import dev.piotrulla.tiktokblock.util.ColorUtil;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class TikTokBlockController implements Listener {

    private final TikTokBlockRepository repository;
    private final HologramService hologramService;
    private final TikTokMessages messages;
    private final TikTokSettings settings;

    public TikTokBlockController(TikTokSettings settings, TikTokBlockRepository repository, HologramService hologramService, TikTokMessages messages) {
        this.settings = settings;
        this.repository = repository;
        this.hologramService = hologramService;
        this.messages = messages;
    }

    @EventHandler
    void onBreak(BlockBreakEvent event) {
        Block block = event.getBlock();

        this.repository.findBlock(block.getLocation()).ifPresent(tikTokBlock -> {
            if (block.getType() != tikTokBlock.material()) {
                return;
            }

            double toTake = tikTokBlock.multiplier();
            double current = tikTokBlock.health() - toTake;

            tikTokBlock.updateHealth(current);
            this.hologramService.updateHologram(tikTokBlock);

            event.setCancelled(true);

            this.repository.saveBlock(tikTokBlock);

            if (current <= 0) {
                Player player = event.getPlayer();

                this.repository.removeBlock(tikTokBlock);
                tikTokBlock.hologram().deleteHologram();

                block.setType(this.settings.winMaterial());

                player.sendTitle(
                        ColorUtil.color(this.messages.winTitle()),
                        ColorUtil.color(this.messages.winSubTitle()),
                        this.settings.fadeIn(),
                        this.settings.stay(),
                        this.settings.fadeOut()
                );
            }
        });
    }
}

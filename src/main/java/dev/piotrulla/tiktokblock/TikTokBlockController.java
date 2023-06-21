package dev.piotrulla.tiktokblock;

import dev.piotrulla.tiktokblock.util.ColorUtil;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class TikTokBlockController implements Listener {

    private final TikTokBlockRepository repository;
    private final TikTokSettings settings;

    public TikTokBlockController(TikTokSettings settings, TikTokBlockRepository repository) {
        this.settings = settings;
        this.repository = repository;
    }

    @EventHandler
    void onBreak(BlockBreakEvent event) {
        Block block = event.getBlock();

        this.repository.findBlock(block.getLocation()).ifPresent(tikTokBlock -> {
            if (block.getType() != tikTokBlock.material()) {
                return;
            }

            double toTake = 1 * tikTokBlock.multiplier();
            double current = tikTokBlock.health() - toTake;

            tikTokBlock.updateHealth(current);

            event.setCancelled(true);

            this.repository.saveBlock(tikTokBlock);

            if (current <= 0) {
                Player player = event.getPlayer();

                this.repository.removeBlock(tikTokBlock);

                block.setType(this.settings.winMaterial());

                player.sendTitle(
                        ColorUtil.color(this.settings.winTitle()),
                        ColorUtil.color(this.settings.winSubTitle()),
                        this.settings.fadeIn(),
                        this.settings.stay(),
                        this.settings.fadeOut()
                );
            }
        });
    }
}

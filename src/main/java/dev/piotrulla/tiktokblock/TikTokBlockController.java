package dev.piotrulla.tiktokblock;

import dev.piotrulla.tiktokblock.config.implementation.PluginConfiguration;
import dev.piotrulla.tiktokblock.util.ColorUtil;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class TikTokBlockController implements Listener {

    private final PluginConfiguration configuration;
    private final TikTokBlockRepository repository;

    public TikTokBlockController(PluginConfiguration configuration, TikTokBlockRepository repository) {
        this.configuration = configuration;
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

                block.setType(this.configuration.wonMaterial);

                player.sendTitle(
                        ColorUtil.color(this.configuration.winTitleMessage),
                        ColorUtil.color(this.configuration.winSubTitleMessage),
                        this.configuration.fadeIn,
                        this.configuration.stay,
                        this.configuration.fadeOut
                );
            }
        });
    }
}

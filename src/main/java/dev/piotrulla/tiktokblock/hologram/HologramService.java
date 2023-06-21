package dev.piotrulla.tiktokblock.hologram;

import dev.piotrulla.tiktokblock.TikTokBlock;
import dev.piotrulla.tiktokblock.bridge.BridgeService;
import dev.piotrulla.tiktokblock.config.implementation.PluginConfiguration;
import dev.piotrulla.tiktokblock.hologram.wrapper.DecentHologramsWrapper;
import dev.piotrulla.tiktokblock.hologram.wrapper.DefaultHologramWrapper;
import dev.piotrulla.tiktokblock.hologram.wrapper.HologramWrapper;
import dev.piotrulla.tiktokblock.hologram.wrapper.HolographicDisplaysWrapper;
import dev.piotrulla.tiktokblock.position.PositionAdapter;
import dev.piotrulla.tiktokblock.util.ColorUtil;
import org.bukkit.Location;
import org.bukkit.plugin.Plugin;
import panda.utilities.text.Formatter;

public class HologramService {

    private final PluginConfiguration configuration;
    private final BridgeService bridgeService;
    private final Plugin plugin;

    public HologramService(PluginConfiguration configuration, BridgeService bridgeService, Plugin plugin) {
        this.configuration = configuration;
        this.bridgeService = bridgeService;
        this.plugin = plugin;
    }

    public void updateHologram(TikTokBlock tikTokBlock) {
        HologramWrapper wrapper = tikTokBlock.hologram();

        if (wrapper == null) {
            wrapper = this.createHologram();
        }

        Location location = PositionAdapter.convert(tikTokBlock.position())
                .clone()
                .add(0.5, this.configuration.hologramHeight, 0.5);

        Formatter formatter = new Formatter()
                .register("{NAME}", tikTokBlock.name())
                .register("{BASE-HP}", tikTokBlock.baseHealth())
                .register("{CURRENT-HP}", tikTokBlock.health())
                .register("{MULTIPLER}", tikTokBlock.multiplier());

        wrapper.createHologram(location);
        wrapper.setLines(ColorUtil.color(this.configuration.hologramStyle.stream()
                .map(formatter::format)
                .toList())
        );

        tikTokBlock.updateHologram(wrapper);
    }

    HologramWrapper createHologram() {
        if (!this.bridgeService.isDecentHolo() && !this.bridgeService.isHoloDisplays()) {
            return new DefaultHologramWrapper();
        }

        if (this.bridgeService.isDecentHolo()) {
            return new DecentHologramsWrapper();
        }

        return new HolographicDisplaysWrapper(this.plugin);
    }
}

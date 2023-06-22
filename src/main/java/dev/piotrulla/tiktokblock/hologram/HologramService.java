package dev.piotrulla.tiktokblock.hologram;

import dev.piotrulla.tiktokblock.TikTokBlock;
import dev.piotrulla.tiktokblock.bridge.BridgeService;
import dev.piotrulla.tiktokblock.hologram.wrapper.DecentHologramsWrapper;
import dev.piotrulla.tiktokblock.hologram.wrapper.DefaultHologramWrapper;
import dev.piotrulla.tiktokblock.hologram.wrapper.HologramWrapper;
import dev.piotrulla.tiktokblock.hologram.wrapper.HolographicDisplaysWrapper;
import dev.piotrulla.tiktokblock.util.ColorUtil;
import org.bukkit.Location;
import org.bukkit.plugin.Plugin;
import panda.utilities.text.Formatter;

import java.util.stream.Collectors;

public class HologramService {

    private final HologramSettings hologramSettings;
    private final BridgeService bridgeService;
    private final Plugin plugin;

    public HologramService(HologramSettings hologramSettings, BridgeService bridgeService, Plugin plugin) {
        this.hologramSettings = hologramSettings;
        this.bridgeService = bridgeService;
        this.plugin = plugin;
    }

    public void updateHologram(TikTokBlock tikTokBlock) {
        HologramWrapper wrapper = tikTokBlock.hologram();

        Location location = tikTokBlock.location()
                .clone()
                .add(0.5, this.hologramSettings.height(), 0.5);

        if (wrapper == null) {
            wrapper = this.createWrapper();

            wrapper.createHologram(location);
        }

        Formatter formatter = new Formatter()
                .register("{NAME}", tikTokBlock.name())
                .register("{BASE-HP}", tikTokBlock.baseHealth())
                .register("{CURRENT-HP}", tikTokBlock.health())
                .register("{MULTIPLIER}", tikTokBlock.multiplier());

        wrapper.setLines(ColorUtil.color(this.hologramSettings.lines().stream()
                .map(formatter::format)
                .collect(Collectors.toList()))
        );

        tikTokBlock.updateHologram(wrapper);
    }

    HologramWrapper createWrapper() {
        if (!this.bridgeService.isDecentHolo() && !this.bridgeService.isHoloDisplays()) {
            return new DefaultHologramWrapper();
        }

        if (this.bridgeService.isDecentHolo()) {
            return new DecentHologramsWrapper();
        }

        return new HolographicDisplaysWrapper(this.plugin);
    }
}

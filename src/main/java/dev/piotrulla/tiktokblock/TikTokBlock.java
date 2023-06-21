package dev.piotrulla.tiktokblock;

import dev.piotrulla.tiktokblock.hologram.wrapper.HologramWrapper;
import dev.piotrulla.tiktokblock.position.Position;
import org.bukkit.Material;

public interface TikTokBlock {

    String name();

    Position position();

    Material material();

    HologramWrapper hologram();

    void updateHologram(HologramWrapper hologram);

    double multiplier();

    void updateMultiplier(double multiplier);

    double baseHealth();

    double health();

    void updateHealth(double hp);
}

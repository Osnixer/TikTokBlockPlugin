package dev.piotrulla.tiktokblock.hologram;

import org.bukkit.Location;

import java.util.List;

public interface HologramWrapper {

    void createHologram(Location location);

    void deleteHologram();

    void setLines(List<String> lines);
}

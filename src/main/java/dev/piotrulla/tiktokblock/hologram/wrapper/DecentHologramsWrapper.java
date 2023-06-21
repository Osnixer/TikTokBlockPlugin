package dev.piotrulla.tiktokblock.hologram.wrapper;

import dev.piotrulla.tiktokblock.hologram.HologramWrapper;
import eu.decentsoftware.holograms.api.DHAPI;
import eu.decentsoftware.holograms.api.holograms.Hologram;
import org.bukkit.Location;

import java.util.List;
import java.util.UUID;

public class DecentHologramsWrapper implements HologramWrapper {

    private Hologram hologram;

    @Override
    public void createHologram(Location location) {
        UUID uniqueId = UUID.randomUUID();

        this.hologram = DHAPI.createHologram(uniqueId.toString(), location);
    }

    @Override
    public void deleteHologram() {
        if (this.hologram == null) {
            throw new IllegalStateException("Hologram isn't created!");
        }

        this.hologram.delete();
    }

    @Override
    public void setLines(List<String> lines) {
        if (this.hologram == null) {
            throw new IllegalStateException("Hologram isn't created!");
        }

        DHAPI.setHologramLines(this.hologram, lines);
    }
}

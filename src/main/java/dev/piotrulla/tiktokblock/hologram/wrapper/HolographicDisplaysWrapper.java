package dev.piotrulla.tiktokblock.hologram.wrapper;

import dev.piotrulla.tiktokblock.hologram.HologramWrapper;
import me.filoghost.holographicdisplays.api.HolographicDisplaysAPI;
import me.filoghost.holographicdisplays.api.hologram.Hologram;
import org.bukkit.Location;
import org.bukkit.plugin.Plugin;

import java.util.List;

public class HolographicDisplaysWrapper implements HologramWrapper {

    private final HolographicDisplaysAPI hologramAPI;
    private Hologram hologram;

    public HolographicDisplaysWrapper(Plugin plugin) {
        this.hologramAPI = HolographicDisplaysAPI.get(plugin);
    }

    @Override
    public void createHologram(Location location) {
        this.hologram = this.hologramAPI.createHologram(location);
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

        this.hologram.getLines().clear();

        for (int i = 0; i < lines.size(); i++) {
            this.hologram.getLines().insertText(i, lines.get(i));
        }
    }
}

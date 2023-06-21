package dev.piotrulla.tiktokblock.bridge;

import org.bukkit.plugin.PluginManager;

public class BridgeService {

    private final PluginManager pluginManager;
    private boolean decentHolo, holoDisplays;

    public BridgeService(PluginManager pluginManager) {
        this.pluginManager = pluginManager;
    }

    public void setUpBridges() {
        this.init("DecentHolograms", () -> this.decentHolo = true);
        this.init("HolographicDisplays", () -> this.holoDisplays = true);
    }

    private void init(String pluginName, BridgeInitializer initializer) {
        if (this.pluginManager.isPluginEnabled(pluginName)) {
            initializer.initialize();
        }
    }

    public boolean isDecentHolo() {
        return this.decentHolo;
    }

    public boolean isHoloDisplays() {
        return this.holoDisplays;
    }
}

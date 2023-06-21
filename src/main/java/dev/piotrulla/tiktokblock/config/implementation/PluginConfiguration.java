package dev.piotrulla.tiktokblock.config.implementation;

import dev.piotrulla.tiktokblock.TikTokSettings;
import dev.piotrulla.tiktokblock.config.ReloadableConfig;
import dev.piotrulla.tiktokblock.hologram.HologramSettings;
import net.dzikoysk.cdn.source.Resource;
import net.dzikoysk.cdn.source.Source;
import org.bukkit.Material;

import java.io.File;
import java.util.Arrays;
import java.util.List;

public class PluginConfiguration implements ReloadableConfig, HologramSettings, TikTokSettings {

    private String blockNotExistsMessage = "&cBlock with this name not exists!";
    private String noPermission = "&cYou don't have permission to do this! &7({PERMISSIONS})";
    private String invalidUsage = "&cInvalid usage! &7({USAGE})";
    private String invalidUsageHeader = "&cInvalid usage!";
    private String invalidUsageEntry = "&7{ENTRY}";
    private String winTitleMessage = "&aYou won!";
    private String winSubTitleMessage = "&7You have won the block game!";
    private int fadeIn = 10;
    private int stay = 40;
    private int fadeOut = 10;

    private Material winMaterial = Material.AIR;

    private double hologramHeight = 1.5;

    private List<String> hologramLines = Arrays.asList(
            "           &9TikTokBlock v1.0.0",
            "&7https://github.com/Osnixer/TikTokBlockPlugin",
            "            &7by Piotrulla &c<3",
            "",
            "&7Block name: &c{BLOCK-NAME}",
            "&7Current Block health: &c{CURRENT-HP}",
            "&7Block start health: &c{BASE-HP}",
            "&7Block multiplier: &ex&c{MULTIPLIER}"
    );

    @Override
    public Resource resource(File folder) {
        return Source.of(folder, "config.yml");
    }

    @Override
    public List<String> lines() {
        return this.hologramLines;
    }

    @Override
    public double height() {
        return this.hologramHeight;
    }

    @Override
    public String blockNotExists() {
        return this.blockNotExistsMessage;
    }

    @Override
    public String invalidUsage() {
        return this.invalidUsage;
    }

    @Override
    public String invalidUsageHeader() {
        return this.invalidUsageHeader;
    }

    @Override
    public String invalidUsageEntry() {
        return this.invalidUsageEntry;
    }

    @Override
    public String noPermission() {
        return this.noPermission;
    }

    @Override
    public String winTitle() {
        return this.winTitleMessage;
    }

    @Override
    public String winSubTitle() {
        return this.winSubTitleMessage;
    }

    @Override
    public int fadeIn() {
        return this.fadeIn;
    }

    @Override
    public int stay() {
        return this.stay;
    }

    @Override
    public int fadeOut() {
        return this.fadeOut;
    }

    @Override
    public Material winMaterial() {
        return this.winMaterial;
    }
}

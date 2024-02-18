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

    private int fadeIn = 10;
    private int stay = 40;
    private int fadeOut = 10;

    private Material winMaterial = Material.AIR;

    private double hologramHeight = 4.5;

    private List<String> hologramLines = Arrays.asList(
            "           &9TikTokBlock v1.1.0",
            "&7https://github.com/P1otrulla/TikTokBlockPlugin",
            "            &7by Piotrulla &c<3",
            "",
            "&7Block name: &c{NAME}",
            "&7Current Block health: &c{CURRENT-HP}",
            "&7Block start health: &c{BASE-HP}",
            "&7Block multiplier: &ex&c{MULTIPLIER}"
    );

    private int coordXRandomMax = 4;
    private int coordStaticY = 13;

    private int tntRadius = 2;
    private int tntPointOfHp = 2;
    private boolean tntCheckMultiplier = true;
    private int autoExecuteTntAfterHp = 1_000;

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

    @Override
    public int tntRadius() {
        return this.tntRadius;
    }

    @Override
    public int tntPointOfHp() {
        return this.tntPointOfHp;
    }

    @Override
    public boolean tntCheckMultiplier() {
        return this.tntCheckMultiplier;
    }

    @Override
    public int autoExecuteTntAfterHp() {
        return this.autoExecuteTntAfterHp;
    }

    @Override
    public int coordRandomMax() {
        return this.coordXRandomMax;
    }

    @Override
    public int coordStaticY() {
        return this.coordStaticY;
    }
}

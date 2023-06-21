package dev.piotrulla.tiktokblock.config.implementation;

import dev.piotrulla.tiktokblock.config.ReloadableConfig;
import net.dzikoysk.cdn.source.Resource;
import net.dzikoysk.cdn.source.Source;
import org.bukkit.Material;

import java.io.File;
import java.util.Arrays;
import java.util.List;

public class PluginConfiguration implements ReloadableConfig {

    public String winTitleMessage = "&aYou won!";
    public String winSubTitleMessage = "&7You have won the block game!";
    public int fadeIn = 10;
    public int stay = 40;
    public int fadeOut = 10;

    public Material wonMaterial = Material.AIR;

    public double hologramHeight = 1.5;

    public List<String> hologramStyle = Arrays.asList(
            "&9TikTokBlock v1.0.0",
            "&7by Piotrulla &c<3",
            "&7https://github.com/Osnixer/",
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
}

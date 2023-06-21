package dev.piotrulla.tiktokblock.config.implementation.item;

import dev.piotrulla.tiktokblock.TikTokBlock;
import dev.piotrulla.tiktokblock.hologram.wrapper.HologramWrapper;
import dev.piotrulla.tiktokblock.position.Position;
import net.dzikoysk.cdn.entity.Contextual;
import net.dzikoysk.cdn.entity.Exclude;
import org.bukkit.Material;

@Contextual
public class TikTokBlockItem implements TikTokBlock {

    @Exclude
    private HologramWrapper hologram;

    private String name;
    private Position position;
    private Material material;
    private double multiplier;
    private double baseHealth;
    private double health;

    public TikTokBlockItem(String name, Position position, Material material, double multiplier, int baseHealth) {
        this.name = name;
        this.position = position;
        this.material = material;
        this.multiplier = multiplier;
        this.baseHealth = baseHealth;
        this.health = baseHealth;
    }

    public String name() {
        return this.name;
    }

    @Override
    public Position position() {
        return this.position;
    }

    @Override
    public HologramWrapper hologram() {
        return this.hologram;
    }

    @Override
    public void updateHologram(HologramWrapper hologram) {
        this.hologram = hologram;
    }

    public Material material() {
        return this.material;
    }

    public double multiplier() {
        return this.multiplier;
    }

    public void updateMultiplier(double multiplier) {
        this.multiplier = multiplier;
    }

    public double baseHealth() {
        return this.baseHealth;
    }

    public double health() {
        return this.health;
    }

    public void updateHealth(double hp) {
        this.health = hp;
    }
}

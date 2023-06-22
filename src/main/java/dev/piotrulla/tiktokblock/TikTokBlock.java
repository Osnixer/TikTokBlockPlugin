package dev.piotrulla.tiktokblock;

import dev.piotrulla.tiktokblock.hologram.wrapper.HologramWrapper;
import net.dzikoysk.cdn.entity.Contextual;
import net.dzikoysk.cdn.entity.Exclude;
import org.bukkit.Location;
import org.bukkit.Material;

@Contextual
public class TikTokBlock {

    @Exclude
    private HologramWrapper hologram;

    private String name = "none";
    private Location location = new Location(null, 0, 0, 0);
    private Material material = Material.STONE;
    private double multiplier = 1.0;
    private double baseHealth = 1000;
    private double health = 1000;

    public TikTokBlock(String name, Location location, Material material, double multiplier, int baseHealth) {
        this.name = name;
        this.location = location;
        this.material = material;
        this.multiplier = multiplier;
        this.baseHealth = baseHealth;
        this.health = baseHealth;
    }

    public TikTokBlock() {

    }

    public String name() {
        return this.name;
    }

    public Location location() {
        return this.location;
    }

    public HologramWrapper hologram() {
        return this.hologram;
    }

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

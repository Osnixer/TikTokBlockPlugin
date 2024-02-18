package dev.piotrulla.tiktokblock;

import dev.piotrulla.tiktokblock.hologram.wrapper.HologramWrapper;
import net.dzikoysk.cdn.entity.Contextual;
import net.dzikoysk.cdn.entity.Exclude;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.scheduler.BukkitTask;

import java.time.Duration;
import java.time.Instant;

@Contextual
public class TikTokBlock {

    @Exclude
    private HologramWrapper hologram;
    @Exclude
    private TNT tnt;

    private String name = "none";
    private Location location = new Location(null, 0, 0, 0);
    private Material material = Material.STONE;
    private double multiplier = 1_0;
    private double baseHealth = 1_000;
    private double health = 1_000;


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

    public void updateTnt(TNT tnt) {
        this.tnt = tnt;
    }

    public TNT tnt() {
        return this.tnt;
    }

    public static class TNT {

        private boolean enabled = false;

        private int limit;

        private Instant lastUse = Instant.now();

        private Duration cooldown;

        public int limitPerCooldown;

        private BukkitTask bukkitTask;

        public TNT(int limit, Duration cooldown, int limitPerCooldown) {
            this.limit = limit;
            this.cooldown = cooldown;
            this.limitPerCooldown = limitPerCooldown;
        }

        public boolean enabled() {
            return this.enabled;
        }

        public void updateEnabled(boolean tnt) {
            this.enabled = tnt;
        }

        public int limit() {
            return this.limit;
        }

        public void lastUse() {
            this.lastUse = Instant.now();
        }

        public Instant lastUsed() {
            return this.lastUse;
        }

        public Duration cooldown() {
            return this.cooldown;
        }

        public int limitPerCooldown() {
            return this.limitPerCooldown;
        }

        public BukkitTask bukkitTask() {
            return this.bukkitTask;
        }

        public void updateTask(BukkitTask bukkitTask) {
            this.bukkitTask = bukkitTask;
        }

        public void disable() {
            this.enabled = false;

            if (this.bukkitTask != null) {
                this.bukkitTask.cancel();
            }
        }
    }
}

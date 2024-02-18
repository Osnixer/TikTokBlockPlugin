package dev.piotrulla.tiktokblock;

import org.bukkit.Location;
import org.bukkit.entity.TNTPrimed;

import java.time.Instant;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class TikTokBlockTask implements Runnable {

    private static final Random RANDOM = ThreadLocalRandom.current();

    private final TikTokSettings tikTokSettings;
    private final TikTokBlock tikTokBlock;
    private int executedTnts;

    public TikTokBlockTask(TikTokSettings tikTokSettings, TikTokBlock tikTokBlock) {
        this.tikTokSettings = tikTokSettings;
        this.tikTokBlock = tikTokBlock;
    }

    @Override
    public void run() {
        TikTokBlock.TNT tnt = this.tikTokBlock.tnt();

        if (!tnt.enabled()) {
            return;
        }

        Instant lastUsed = tnt.lastUsed().plus(tnt.cooldown());
        Instant now = Instant.now();

        if (lastUsed.isBefore(now)) {
            return;
        }

        tnt.lastUse();

        Location location = this.tikTokBlock.location();

        if (location == null) {
            return;
        }

        for (int i = 0; i < tnt.limitPerCooldown(); i++) {
            if (this.executedTnts >= tnt.limit()) {
                tnt.disable();
                break;
            }

            int coord = this.tikTokSettings.coordRandomMax();
            int coordY = location.getBlockY() + this.tikTokSettings.coordStaticY();

            Location spawnLocation = location.add(RANDOM.nextInt(coord), coordY, RANDOM.nextInt(coord)).clone();

            spawnLocation.getWorld().spawn(this.tikTokBlock.location(), TNTPrimed.class);
            this.executedTnts++;
        }
    }
}

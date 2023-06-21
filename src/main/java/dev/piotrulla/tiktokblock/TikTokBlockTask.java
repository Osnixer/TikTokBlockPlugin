package dev.piotrulla.tiktokblock;

import dev.piotrulla.tiktokblock.hologram.HologramService;

public class TikTokBlockTask implements Runnable {

    private final TikTokBlockRepository repository;
    private final HologramService hologramService;

    public TikTokBlockTask(TikTokBlockRepository repository, HologramService hologramService) {
        this.repository = repository;
        this.hologramService = hologramService;
    }

    @Override
    public void run() {
        for (TikTokBlock tikTokBlock : this.repository.tikTokBlocks()) {
            this.hologramService.updateHologram(tikTokBlock);
        }
    }
}

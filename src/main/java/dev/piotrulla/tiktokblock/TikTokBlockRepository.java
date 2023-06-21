package dev.piotrulla.tiktokblock;

import org.bukkit.Location;

import java.util.Collection;
import java.util.Optional;

public interface TikTokBlockRepository {

    Optional<TikTokBlock> findBlock(String name);

    Optional<TikTokBlock> findBlock(Location location);

    Collection<TikTokBlock> tikTokBlocks();

    Collection<String> tikTokBlocksNames();

    void removeBlock(TikTokBlock block);

    void saveBlock(TikTokBlock block);

}

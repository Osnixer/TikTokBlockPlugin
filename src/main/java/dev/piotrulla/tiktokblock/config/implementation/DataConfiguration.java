package dev.piotrulla.tiktokblock.config.implementation;

import dev.piotrulla.tiktokblock.TikTokBlock;
import dev.piotrulla.tiktokblock.TikTokBlockRepository;
import dev.piotrulla.tiktokblock.config.ConfigService;
import dev.piotrulla.tiktokblock.config.ReloadableConfig;
import net.dzikoysk.cdn.entity.Exclude;
import net.dzikoysk.cdn.source.Resource;
import net.dzikoysk.cdn.source.Source;
import org.bukkit.Location;

import java.io.File;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class DataConfiguration implements ReloadableConfig, TikTokBlockRepository {

    @Exclude
    private final ConfigService configService;

    private Map<String, TikTokBlock> blocks = new HashMap<>();

    public DataConfiguration(ConfigService configService) {
        this.configService = configService;
    }

    @Override
    public Resource resource(File folder) {
        return Source.of(folder, "blocks.yml");
    }

    @Override
    public Optional<TikTokBlock> findBlock(String name) {
        return this.blocks.values()
                .stream()
                .filter(block -> block.name().equals(name))
                .findFirst();
    }

    @Override
    public Optional<TikTokBlock> findBlock(Location location) {
        return this.blocks.values()
                .stream()
                .filter(block -> block.location().equals(location))
                .findFirst();
    }

    @Override
    public Collection<TikTokBlock> tikTokBlocks() {
        return Collections.unmodifiableCollection(this.blocks.values());
    }

    @Override
    public Collection<String> tikTokBlocksNames() {
        return Collections.unmodifiableCollection(this.blocks.keySet());
    }

    @Override
    public void removeBlock(TikTokBlock block) {
        this.blocks.remove(block.name());

        this.configService.save(this);
    }

    @Override
    public void saveBlock(TikTokBlock block) {
        Map<String, TikTokBlock> copy = new HashMap<>(this.blocks);

        copy.put(block.name(), block);

        this.blocks = copy;

        this.configService.save(this);
    }
}

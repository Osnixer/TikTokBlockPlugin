package dev.piotrulla.tiktokblock.config.implementation;

import dev.piotrulla.tiktokblock.TikTokBlock;
import dev.piotrulla.tiktokblock.TikTokBlockRepository;
import dev.piotrulla.tiktokblock.config.ConfigService;
import dev.piotrulla.tiktokblock.config.ReloadableConfig;
import dev.piotrulla.tiktokblock.position.Position;
import dev.piotrulla.tiktokblock.position.PositionAdapter;
import net.dzikoysk.cdn.entity.Exclude;
import net.dzikoysk.cdn.source.Resource;
import net.dzikoysk.cdn.source.Source;
import org.bukkit.Location;

import java.io.File;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class DataConfiguration implements ReloadableConfig, TikTokBlockRepository {

    @Exclude
    private final ConfigService configService;

    private Map<String, TikTokBlock> blocks = new ConcurrentHashMap<>();

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
        Position position = PositionAdapter.convert(location);

        return this.blocks.values()
                .stream()
                .filter(block -> block.position().equals(position))
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
        if (this.blocks.get(block.name()) != null) {
            this.blocks.remove(block.name());
        }

        this.blocks.put(block.name(), block);

        this.configService.save(this);
    }
}

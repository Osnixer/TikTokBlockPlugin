package dev.piotrulla.tiktokblock.config;

import net.dzikoysk.cdn.Cdn;
import net.dzikoysk.cdn.CdnFactory;
import net.dzikoysk.cdn.reflect.Visibility;
import org.bukkit.Location;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

public class ConfigService {

    private final Cdn cdn = CdnFactory
            .createYamlLike()
            .getSettings()
            .withMemberResolver(Visibility.PRIVATE)
            .withComposer(Location.class, new LocationComposer())
            .build();

    private final Set<ReloadableConfig> configs = new HashSet<>();
    private final File dataFolder;

    public ConfigService(File dataFolder) {
        this.dataFolder = dataFolder;
    }

    public <T extends ReloadableConfig> T load(T config) {
        cdn.load(config.resource(this.dataFolder), config)
                .orThrow(RuntimeException::new);

        cdn.render(config, config.resource(this.dataFolder))
                .orThrow(RuntimeException::new);

        this.configs.add(config);

        return config;
    }

    public <T extends ReloadableConfig> T save(T config) {
        cdn.render(config, config.resource(this.dataFolder))
                .orThrow(RuntimeException::new);

        this.configs.add(config);

        return config;
    }

    public void reload() {
        for (ReloadableConfig config : this.configs) {
            load(config);
        }
    }
}
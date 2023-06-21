package dev.piotrulla.tiktokblock;

import dev.piotrulla.tiktokblock.bridge.BridgeService;
import dev.piotrulla.tiktokblock.command.TikTokBlockArgument;
import dev.piotrulla.tiktokblock.command.TikTokBlockCommand;
import dev.piotrulla.tiktokblock.config.ConfigService;
import dev.piotrulla.tiktokblock.config.implementation.DataConfiguration;
import dev.piotrulla.tiktokblock.config.implementation.PluginConfiguration;
import dev.piotrulla.tiktokblock.hologram.HologramService;
import dev.rollczi.litecommands.LiteCommands;
import dev.rollczi.litecommands.bukkit.LiteBukkitFactory;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

public class TikTokBlockPlugin extends JavaPlugin {

    private ConfigService configService;
    private PluginConfiguration pluginConfiguration;

    private BridgeService bridgeService;

    private TikTokBlockRepository repository;

    private HologramService hologramService;

    private volatile BukkitTask updateTask;

    private LiteCommands<CommandSender> liteCommands;

    @Override
    public void onEnable() {
        this.configService = new ConfigService(this.getDataFolder());
        this.pluginConfiguration = this.configService.load(new PluginConfiguration());

        Server server = this.getServer();

        this.bridgeService = new BridgeService(server.getPluginManager());
        this.bridgeService.setUpBridges();

        this.repository = this.configService.load(new DataConfiguration(this.configService));
        this.hologramService = new HologramService(this.pluginConfiguration, this.bridgeService, this);

        this.updateTask = server.getScheduler().runTaskTimer(this, new TikTokBlockTask(this.repository, this.hologramService), 20L, 20L);

        server.getPluginManager().registerEvents(new TikTokBlockController(this.pluginConfiguration, this.repository), this);

        this.liteCommands = LiteBukkitFactory.builder(server, "tiktokblock")
                .argument(TikTokBlock.class, new TikTokBlockArgument(this.repository))

                .commandInstance(new TikTokBlockCommand(this.repository))
                .register();
    }

    @Override
    public void onDisable() {
        this.updateTask.cancel();

        for (TikTokBlock tikTokBlock : this.repository.tikTokBlocks()) {
            tikTokBlock.hologram().deleteHologram();
        }

        this.liteCommands.getPlatform().unregisterAll();
    }

    public ConfigService getConfigService() {
        return this.configService;
    }

    public PluginConfiguration getPluginConfiguration() {
        return this.pluginConfiguration;
    }

    public BridgeService getBridgeService() {
        return this.bridgeService;
    }

    public TikTokBlockRepository getRepository() {
        return this.repository;
    }

    public HologramService getHologramService() {
        return this.hologramService;
    }
}

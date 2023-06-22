package dev.piotrulla.tiktokblock;

import dev.piotrulla.tiktokblock.bridge.BridgeService;
import dev.piotrulla.tiktokblock.command.TikTokBlockArgument;
import dev.piotrulla.tiktokblock.command.TikTokBlockCommand;
import dev.piotrulla.tiktokblock.command.handler.InvalidUsageHandler;
import dev.piotrulla.tiktokblock.command.handler.PermissionHandler;
import dev.piotrulla.tiktokblock.config.ConfigService;
import dev.piotrulla.tiktokblock.config.implementation.DataConfiguration;
import dev.piotrulla.tiktokblock.config.implementation.MessagesConfiguration;
import dev.piotrulla.tiktokblock.config.implementation.PluginConfiguration;
import dev.piotrulla.tiktokblock.hologram.HologramService;
import dev.rollczi.litecommands.LiteCommands;
import dev.rollczi.litecommands.bukkit.LiteBukkitFactory;
import dev.rollczi.litecommands.bukkit.tools.BukkitOnlyPlayerContextual;
import dev.rollczi.litecommands.command.permission.RequiredPermissions;
import dev.rollczi.litecommands.schematic.Schematic;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class TikTokBlockPlugin extends JavaPlugin {

    private ConfigService configService;
    private PluginConfiguration pluginConfiguration;
    private MessagesConfiguration messagesConfiguration;

    private BridgeService bridgeService;

    private TikTokBlockRepository repository;

    private HologramService hologramService;

    private LiteCommands<CommandSender> liteCommands;

    @Override
    public void onEnable() {
        this.configService = new ConfigService(this.getDataFolder());
        this.pluginConfiguration = this.configService.load(new PluginConfiguration());
        this.messagesConfiguration = this.configService.load(new MessagesConfiguration());

        Server server = this.getServer();

        this.bridgeService = new BridgeService(server.getPluginManager());
        this.bridgeService.setUpBridges();

        this.repository = this.configService.load(new DataConfiguration(this.configService));
        this.hologramService = new HologramService(this.pluginConfiguration, this.bridgeService, this);

        server.getPluginManager().registerEvents(new TikTokBlockController(this.pluginConfiguration, this.repository, this.hologramService, this.messagesConfiguration), this);

        this.liteCommands = LiteBukkitFactory.builder(server, "tiktokblock")
                .argument(TikTokBlock.class, new TikTokBlockArgument(this.repository, this.messagesConfiguration))

                .contextualBind(Player.class, new BukkitOnlyPlayerContextual<>("only-player"))

                .resultHandler(Schematic.class, new InvalidUsageHandler(this.messagesConfiguration))
                .resultHandler(RequiredPermissions.class, new PermissionHandler(this.messagesConfiguration))

                .commandInstance(new TikTokBlockCommand(this.repository, hologramService, this.configService, this.messagesConfiguration))
                .register();
    }

    @Override
    public void onDisable() {
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

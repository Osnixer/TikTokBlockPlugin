package dev.piotrulla.tiktokblock.command;

import dev.piotrulla.tiktokblock.TikTokBlock;
import dev.piotrulla.tiktokblock.TikTokBlockRepository;
import dev.piotrulla.tiktokblock.TikTokBlockTask;
import dev.piotrulla.tiktokblock.TikTokMessages;
import dev.piotrulla.tiktokblock.TikTokSettings;
import dev.piotrulla.tiktokblock.config.ConfigService;
import dev.piotrulla.tiktokblock.hologram.HologramService;
import dev.piotrulla.tiktokblock.util.ColorUtil;
import dev.rollczi.litecommands.argument.Arg;
import dev.rollczi.litecommands.command.execute.Execute;
import dev.rollczi.litecommands.command.permission.Permission;
import dev.rollczi.litecommands.command.route.Route;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

import java.time.Duration;

@Route(name = "tiktokblock", aliases = { "ttb" })
@Permission("tiktokblock.manage")
public class TikTokBlockCommand {

    private final TikTokBlockRepository repository;
    private final HologramService hologramService;
    private final ConfigService configService;
    private final TikTokSettings settings;
    private final TikTokMessages messages;
    private final Plugin plugin;
    private final Server server;

    public TikTokBlockCommand(TikTokBlockRepository repository, HologramService hologramService, ConfigService configService, TikTokSettings settings, TikTokMessages messages, Plugin plugin, Server server) {
        this.repository = repository;
        this.hologramService = hologramService;
        this.configService = configService;
        this.settings = settings;
        this.messages = messages;
        this.plugin = plugin;
        this.server = server;
    }

    @Execute(required = 4, route = "create")
    void create(Player player, @Arg String name, @Arg Material material, @Arg double multiplier, @Arg int baseHealth) {
        Location location = player.getLocation();
        Location blockLocation = new Location(player.getWorld(), location.getBlockX(), location.getBlockY(), location.getBlockZ());

        TikTokBlock tikTokBlock = new TikTokBlock(
                name,
                blockLocation,
                material,
                multiplier,
                baseHealth
        );

        blockLocation.getBlock().setType(material);

        this.saveAndUpdate(tikTokBlock);

        player.sendMessage(ColorUtil.color(this.messages.createMessage()));
    }

    @Execute(required = 1, route = "remove")
    void remove(CommandSender sender, @Arg TikTokBlock block) {
        if (block.hologram() != null) {
            block.hologram().deleteHologram();
        }

        this.repository.removeBlock(block);

        sender.sendMessage(ColorUtil.color(this.messages.deleteMessage()));
    }

    @Execute(required = 4, route = "tnt")
    void tnt(CommandSender sender, @Arg TikTokBlock tikTokBlock, @Arg int limit, @Arg Duration cooldown, @Arg int limitPerCooldown) {
        TikTokBlock.TNT tnt = new TikTokBlock.TNT(limit, cooldown, limitPerCooldown);

        tnt.lastUse();

        TikTokBlockTask tikTokBlockTask = new TikTokBlockTask(this.settings, tikTokBlock);
        tnt.updateTask(this.server.getScheduler().runTaskTimer(this.plugin, tikTokBlockTask, 20L, 20L));

        sender.sendMessage(ColorUtil.color(this.messages.boomMessage()));
    }

    @Execute(required = 2, route = "addHealth")
    void addHealth(CommandSender sender, @Arg TikTokBlock tikTokBlock, @Arg double health) {
        tikTokBlock.updateHealth(tikTokBlock.health() + health);

        this.saveAndUpdate(tikTokBlock);

        sender.sendMessage(ColorUtil.color(this.messages.addHealthMessage()));
    }

    @Execute(required = 2, route = "removeHealth")
    void removeHealth(CommandSender sender, @Arg TikTokBlock tikTokBlock, @Arg double health) {
        tikTokBlock.updateHealth(tikTokBlock.health() - health);

        this.saveAndUpdate(tikTokBlock);

        sender.sendMessage(ColorUtil.color(this.messages.removeHealthMessage()));
    }

    @Execute(required = 2, route = "addMultiplier")
    void setMultiplier(CommandSender sender, @Arg TikTokBlock tikTokBlock, @Arg double multiplier) {
        tikTokBlock.updateMultiplier(tikTokBlock.multiplier() + multiplier);

        this.saveAndUpdate(tikTokBlock);

        sender.sendMessage(ColorUtil.color(this.messages.multiplerMessage()));
    }

    @Execute(required = 1, route = "reset")
    void reset(CommandSender sender, @Arg TikTokBlock tikTokBlock) {
        tikTokBlock.updateHealth(tikTokBlock.baseHealth());

        this.saveAndUpdate(tikTokBlock);

        sender.sendMessage(ColorUtil.color(this.messages.resetMessage()));
    }

    @Execute(route = "reload")
    void reload(CommandSender sender) {
        this.configService.reload();

        for (TikTokBlock tikTokBlock : this.repository.tikTokBlocks()) {
            this.hologramService.updateHologram(tikTokBlock);
        }

        sender.sendMessage(ColorUtil.color(this.messages.reloadMessage()));
    }

    @Execute(required = 1, route = "addEfficency")
    void efficency(Player player, @Arg int level) {
        if (level < 0) {
            player.sendMessage(ColorUtil.color(this.messages.greaterThanZero()));

            return;
        }

        ItemStack item = player.getInventory().getItem(EquipmentSlot.HAND);

        if (item == null || !item.getType().toString().contains("PICKAXE")) {
            player.sendMessage(ColorUtil.color(this.messages.onlyPickaxe()));

            return;
        }

        ItemMeta itemMeta = item.getItemMeta();

        int currentLevel = itemMeta.getEnchantLevel(Enchantment.DIG_SPEED);
        itemMeta.addEnchant(Enchantment.DIG_SPEED, currentLevel + level, true);

        item.setItemMeta(itemMeta);

        player.sendMessage(ColorUtil.color(this.messages.efficiencyMessage()));
    }

    void saveAndUpdate(TikTokBlock tikTokBlock) {
        this.hologramService.updateHologram(tikTokBlock);

        this.repository.saveBlock(tikTokBlock);
    }
}

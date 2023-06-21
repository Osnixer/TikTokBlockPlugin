package dev.piotrulla.tiktokblock.command;

import dev.piotrulla.tiktokblock.TikTokBlock;
import dev.piotrulla.tiktokblock.TikTokBlockRepository;
import dev.piotrulla.tiktokblock.config.ConfigService;
import dev.piotrulla.tiktokblock.config.implementation.item.TikTokBlockItem;
import dev.piotrulla.tiktokblock.position.PositionAdapter;
import dev.piotrulla.tiktokblock.util.ColorUtil;
import dev.rollczi.litecommands.command.execute.Execute;
import dev.rollczi.litecommands.command.permission.Permission;
import dev.rollczi.litecommands.command.route.Route;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

@Route(name = "tiktokblock", aliases = { "ttb" })
@Permission("tiktokblock.manage")
public class TikTokBlockCommand {

    private final TikTokBlockRepository repository;
    private final ConfigService configService;

    public TikTokBlockCommand(TikTokBlockRepository repository, ConfigService configService) {
        this.repository = repository;
        this.configService = configService;
    }

    @Execute(required = 4, route = "create")
    void create(Player player, String name, Material material, double multiplier, int baseHealth) {
        Location location = player.getLocation();

        TikTokBlock block = new TikTokBlockItem(
                name,
                PositionAdapter.convert(location),
                material,
                multiplier,
                baseHealth
        );

        this.repository.saveBlock(block);
    }

    @Execute(required = 1, route = "remove")
    void remove(CommandSender sender, TikTokBlock block) {
        this.repository.removeBlock(block);

        sender.sendMessage(ColorUtil.color("&7Removed block: &e" + block.name()));
    }

    @Execute(required = 2, route = "addHealth")
    void addHealth(CommandSender sender, TikTokBlock tikTokBlock, double health) {
        tikTokBlock.updateHealth(tikTokBlock.health() + health);
        this.repository.saveBlock(tikTokBlock);

        sender.sendMessage(ColorUtil.color("&7Updated health for block: &e" + tikTokBlock.name()));
    }

    @Execute(required = 2, route = "removeHealth")
    void removeHealth(CommandSender sender, TikTokBlock tikTokBlock, double health) {
        tikTokBlock.updateHealth(tikTokBlock.health() - health);
        this.repository.saveBlock(tikTokBlock);

        sender.sendMessage(ColorUtil.color("&7Updated health for block: &e" + tikTokBlock.name()));
    }

    @Execute(required = 2, route = "setMultiplier")
    void setMultiplier(CommandSender sender, TikTokBlock tikTokBlock, double multiplier) {
        tikTokBlock.updateMultiplier(multiplier);
        this.repository.saveBlock(tikTokBlock);

        sender.sendMessage(ColorUtil.color("&7Updated multiplier for block: &e" + tikTokBlock.name()));
    }

    @Execute(required = 1, route = "reset")
    void reset(CommandSender sender, TikTokBlock tikTokBlock) {
        tikTokBlock.updateHealth(tikTokBlock.baseHealth());
        this.repository.saveBlock(tikTokBlock);

        sender.sendMessage(ColorUtil.color("&7Reseted block: &e" + tikTokBlock.name()));
    }

    @Execute(route = "reload")
    void reload(CommandSender sender) {
        this.configService.reload();

        sender.sendMessage(ColorUtil.color("&7Reloaded config!"));
    }

    @Execute(required = 1, route = "setEfficency")
    void efficency(Player player, int level) {
        if (level < 0) {
            player.sendMessage(ColorUtil.color("&cLevel must be greater than 0!"));

            return;
        }

        ItemStack item = player.getItemInUse();

        if (item == null) {
            player.sendMessage(ColorUtil.color("&cYou must hold a tool in your hand!"));

            return;
        }

        if (!item.getType().toString().contains("PICKAXE")) {
            player.sendMessage(ColorUtil.color("&cYou must hold a pickaxe in your hand!"));

            return;
        }

        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.addEnchant(Enchantment.DIG_SPEED, level, true);

        item.setItemMeta(itemMeta);
    }
}

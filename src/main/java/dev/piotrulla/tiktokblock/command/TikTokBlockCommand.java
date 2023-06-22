package dev.piotrulla.tiktokblock.command;

import dev.piotrulla.tiktokblock.TikTokBlock;
import dev.piotrulla.tiktokblock.TikTokBlockRepository;
import dev.piotrulla.tiktokblock.TikTokMessages;
import dev.piotrulla.tiktokblock.config.ConfigService;
import dev.piotrulla.tiktokblock.hologram.HologramService;
import dev.piotrulla.tiktokblock.util.ColorUtil;
import dev.rollczi.litecommands.argument.Arg;
import dev.rollczi.litecommands.command.execute.Execute;
import dev.rollczi.litecommands.command.permission.Permission;
import dev.rollczi.litecommands.command.route.Route;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

@Route(name = "tiktokblock", aliases = { "ttb" })
@Permission("tiktokblock.manage")
public class TikTokBlockCommand {

    private final TikTokBlockRepository repository;
    private final HologramService hologramService;
    private final ConfigService configService;
    private final TikTokMessages messages;

    public TikTokBlockCommand(TikTokBlockRepository repository, HologramService hologramService, ConfigService configService, TikTokMessages messages) {
        this.repository = repository;
        this.hologramService = hologramService;
        this.configService = configService;
        this.messages = messages;
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

        this.repository.saveBlock(tikTokBlock);

        this.hologramService.updateHologram(tikTokBlock);

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

    @Execute(required = 2, route = "addHealth")
    void addHealth(CommandSender sender, @Arg TikTokBlock tikTokBlock, @Arg double health) {
        tikTokBlock.updateHealth(tikTokBlock.health() + health);
        this.repository.saveBlock(tikTokBlock);

        sender.sendMessage(ColorUtil.color(this.messages.addHealthMessage()));
    }

    @Execute(required = 2, route = "removeHealth")
    void removeHealth(CommandSender sender, @Arg TikTokBlock tikTokBlock, @Arg double health) {
        tikTokBlock.updateHealth(tikTokBlock.health() - health);
        this.repository.saveBlock(tikTokBlock);

        sender.sendMessage(ColorUtil.color(this.messages.removeHealthMessage()));
    }

    @Execute(required = 2, route = "setMultiplier")
    void setMultiplier(CommandSender sender, @Arg TikTokBlock tikTokBlock, @Arg double multiplier) {
        tikTokBlock.updateMultiplier(multiplier);
        this.repository.saveBlock(tikTokBlock);

        sender.sendMessage(ColorUtil.color(this.messages.multiplerMessage()));
    }

    @Execute(required = 1, route = "reset")
    void reset(CommandSender sender, @Arg TikTokBlock tikTokBlock) {
        tikTokBlock.updateHealth(tikTokBlock.baseHealth());
        this.hologramService.updateHologram(tikTokBlock);

        this.repository.saveBlock(tikTokBlock);

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

    @Execute(required = 1, route = "setEfficency")
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
        itemMeta.addEnchant(Enchantment.DIG_SPEED, level, true);

        item.setItemMeta(itemMeta);

        player.sendMessage(ColorUtil.color(this.messages.efficiencyMessage()));
    }
}

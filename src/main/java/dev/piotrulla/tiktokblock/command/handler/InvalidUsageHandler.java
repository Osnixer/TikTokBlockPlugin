package dev.piotrulla.tiktokblock.command.handler;

import dev.piotrulla.tiktokblock.TikTokSettings;
import dev.piotrulla.tiktokblock.util.ColorUtil;
import dev.rollczi.litecommands.command.LiteInvocation;
import dev.rollczi.litecommands.handle.Handler;
import dev.rollczi.litecommands.schematic.Schematic;
import org.bukkit.command.CommandSender;
import panda.utilities.text.Formatter;

public class InvalidUsageHandler implements Handler<CommandSender, Schematic> {

    private final TikTokSettings settings;

    public InvalidUsageHandler(TikTokSettings settings) {
        this.settings = settings;
    }

    @Override
    public void handle(CommandSender sender, LiteInvocation invocation, Schematic schematic) {
        if (schematic.isOnlyFirst()) {
            Formatter formatter = new Formatter()
                    .register("{USAGE}", schematic.first());

            sender.sendMessage(ColorUtil.color(formatter.format(this.settings.invalidUsage())));

            return;
        }

        sender.sendMessage(ColorUtil.color(this.settings.invalidUsageHeader()));

        for (String schema : schematic.getSchematics()) {
            Formatter formatter = new Formatter()
                    .register("{USAGE}", schema);

            sender.sendMessage(ColorUtil.color(formatter.format(this.settings.invalidUsageEntry())));
        }
    }
}

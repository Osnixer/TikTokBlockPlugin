package dev.piotrulla.tiktokblock.command.handler;

import dev.piotrulla.tiktokblock.TikTokMessages;
import dev.piotrulla.tiktokblock.util.ColorUtil;
import dev.rollczi.litecommands.command.LiteInvocation;
import dev.rollczi.litecommands.handle.Handler;
import dev.rollczi.litecommands.schematic.Schematic;
import org.bukkit.command.CommandSender;
import panda.utilities.text.Formatter;

public class InvalidUsageHandler implements Handler<CommandSender, Schematic> {

    private final TikTokMessages messages;

    public InvalidUsageHandler(TikTokMessages messages) {
        this.messages = messages;
    }

    @Override
    public void handle(CommandSender sender, LiteInvocation invocation, Schematic schematic) {
        if (schematic.isOnlyFirst()) {
            Formatter formatter = new Formatter()
                    .register("{USAGE}", schematic.first());

            sender.sendMessage(ColorUtil.color(formatter.format(this.messages.invalidUsage())));

            return;
        }

        sender.sendMessage(ColorUtil.color(this.messages.invalidUsageHeader()));

        for (String schema : schematic.getSchematics()) {
            Formatter formatter = new Formatter()
                    .register("{ENTRY}", schema);

            sender.sendMessage(ColorUtil.color(formatter.format(this.messages.invalidUsageEntry())));
        }
    }
}

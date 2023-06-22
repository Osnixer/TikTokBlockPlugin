package dev.piotrulla.tiktokblock.command.handler;

import dev.piotrulla.tiktokblock.TikTokMessages;
import dev.piotrulla.tiktokblock.util.ColorUtil;
import dev.rollczi.litecommands.command.LiteInvocation;
import dev.rollczi.litecommands.command.permission.RequiredPermissions;
import dev.rollczi.litecommands.handle.Handler;
import org.bukkit.command.CommandSender;
import panda.utilities.text.Formatter;
import panda.utilities.text.Joiner;

public class PermissionHandler implements Handler<CommandSender, RequiredPermissions> {

    private final TikTokMessages messages;

    public PermissionHandler(TikTokMessages messages) {
        this.messages = messages;
    }

    @Override
    public void handle(CommandSender sender, LiteInvocation invocation, RequiredPermissions permissions) {
        Formatter formatter = new Formatter()
                .register("{PERMISSIONS}", Joiner.on(", ")
                        .join(permissions.getPermissions())
                        .toString());

        sender.sendMessage(ColorUtil.color(formatter.format(this.messages.noPermission())));
    }
}

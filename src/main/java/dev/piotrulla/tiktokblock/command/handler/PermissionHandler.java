package dev.piotrulla.tiktokblock.command.handler;

import dev.piotrulla.tiktokblock.TikTokSettings;
import dev.piotrulla.tiktokblock.util.ColorUtil;
import dev.rollczi.litecommands.command.LiteInvocation;
import dev.rollczi.litecommands.command.permission.RequiredPermissions;
import dev.rollczi.litecommands.handle.Handler;
import org.bukkit.command.CommandSender;
import panda.utilities.text.Formatter;
import panda.utilities.text.Joiner;

public class PermissionHandler implements Handler<CommandSender, RequiredPermissions> {

    private final TikTokSettings settings;

    public PermissionHandler(TikTokSettings settings) {
        this.settings = settings;
    }

    @Override
    public void handle(CommandSender sender, LiteInvocation invocation, RequiredPermissions permissions) {
        Formatter formatter = new Formatter()
                .register("{PERMISSIONS}", Joiner.on(", ")
                        .join(permissions.getPermissions())
                        .toString());

        sender.sendMessage(ColorUtil.color(formatter.format(this.settings.noPermission())));
    }
}

package dev.piotrulla.tiktokblock.util;

import org.bukkit.ChatColor;

import java.util.List;
import java.util.stream.Collectors;

public final class ColorUtil {

    private ColorUtil() {
    }

    public static String color(String toColor) {
        return ChatColor.translateAlternateColorCodes('&', toColor);
    }

    public static List<String> color(List<String> toColor) {
        return toColor.stream()
                .map(ColorUtil::color)
                .collect(Collectors.toList());
    }
}

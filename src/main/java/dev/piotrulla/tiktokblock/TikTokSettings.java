package dev.piotrulla.tiktokblock;

import org.bukkit.Material;

public interface TikTokSettings {

    String blockNotExists();

    String invalidUsage();

    String invalidUsageHeader();

    String invalidUsageEntry();

    String noPermission();

    String winTitle();

    String winSubTitle();

    int fadeIn();

    int stay();

    int fadeOut();

    Material winMaterial();
}

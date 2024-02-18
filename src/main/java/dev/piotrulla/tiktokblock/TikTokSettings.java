package dev.piotrulla.tiktokblock;

import org.bukkit.Material;

public interface TikTokSettings {

    int fadeIn();

    int stay();

    int fadeOut();

    Material winMaterial();

    int tntRadius();

    int tntPointOfHp();

    boolean tntCheckMultiplier();

    int autoExecuteTntAfterHp();

    int coordRandomMax();

    int coordStaticY();

}

package dev.piotrulla.tiktokblock.config.implementation;

import dev.piotrulla.tiktokblock.TikTokMessages;
import dev.piotrulla.tiktokblock.config.ReloadableConfig;
import net.dzikoysk.cdn.source.Resource;
import net.dzikoysk.cdn.source.Source;

import java.io.File;

public class MessagesConfiguration implements ReloadableConfig, TikTokMessages {

    private String blockNotExistsMessage = "&cBlock with this name not exists!";
    private String noPermission = "&cYou don't have permission to do this! &7({PERMISSIONS})";
    private String invalidUsage = "&cInvalid usage! &7({USAGE})";
    private String invalidUsageHeader = "&cInvalid usage!";
    private String invalidUsageEntry = "&7{ENTRY}";
    private String winTitleMessage = "&aYou won!";
    private String winSubTitleMessage = "&7You have won the block game!";

    private String greaterThanZero = "&cLevel must be greater than 0!";
    private String onlyPickaxe = "&cYou can only use pickaxe!";

    private String createMessage = "&aTikTokBlock created!";
    private String deleteMessage = "&aTikTokBlock deleted!";
    private String addHealthMessage = "&aHealth added!";
    private String removeHealthMessage = "&aHealth removed!";
    private String multiplerMessage = "&aMultiplier set!";
    private String resetMessage = "&aTikTokBlock reseted!";
    private String reloadMessage = "&aTikTokBlockPlugin reloaded!";
    private String efficiencyMessage = "&aEfficiency set!";

    private String boomMessage = "&cRICO: &6boom boom? &cSKIPPER: &6Yes Rico, boom boom!";

    @Override
    public Resource resource(File folder) {
        return Source.of(folder, "messages.yml");
    }

    @Override
    public String blockNotExists() {
        return this.blockNotExistsMessage;
    }

    @Override
    public String greaterThanZero() {
        return this.greaterThanZero;
    }

    @Override
    public String onlyPickaxe() {
        return this.onlyPickaxe;
    }

    @Override
    public String createMessage() {
        return this.createMessage;
    }

    @Override
    public String deleteMessage() {
        return this.deleteMessage;
    }

    @Override
    public String addHealthMessage() {
        return this.addHealthMessage;
    }

    @Override
    public String removeHealthMessage() {
        return this.removeHealthMessage;
    }

    @Override
    public String multiplerMessage() {
        return this.multiplerMessage;
    }

    @Override
    public String resetMessage() {
        return this.resetMessage;
    }

    @Override
    public String reloadMessage() {
        return this.reloadMessage;
    }

    @Override
    public String efficiencyMessage() {
        return this.efficiencyMessage;
    }

    @Override
    public String boomMessage() {
        return this.boomMessage;
    }

    @Override
    public String invalidUsage() {
        return this.invalidUsage;
    }

    @Override
    public String invalidUsageHeader() {
        return this.invalidUsageHeader;
    }

    @Override
    public String invalidUsageEntry() {
        return this.invalidUsageEntry;
    }

    @Override
    public String noPermission() {
        return this.noPermission;
    }

    @Override
    public String winTitle() {
        return this.winTitleMessage;
    }

    @Override
    public String winSubTitle() {
        return this.winSubTitleMessage;
    }
}

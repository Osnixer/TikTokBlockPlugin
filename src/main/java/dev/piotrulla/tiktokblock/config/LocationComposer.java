package dev.piotrulla.tiktokblock.config;

import net.dzikoysk.cdn.serdes.Composer;
import net.dzikoysk.cdn.serdes.SimpleDeserializer;
import net.dzikoysk.cdn.serdes.SimpleSerializer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import panda.std.Result;

public class LocationComposer implements Composer<Location>, SimpleSerializer<Location>, SimpleDeserializer<Location> {

    @Override
    public Result<Location, Exception> deserialize(String source) {
        String[] split = source.split(";");

        return Result.ok(new Location(
                Bukkit.getWorld(split[0]),
                Double.parseDouble(split[1]),
                Double.parseDouble(split[2]),
                Double.parseDouble(split[3])
        ));
    }

    @Override
    public Result<String, Exception> serialize(Location location) {
        return Result.ok((location.getWorld().getName() +
                ";" +location.getBlockX()) +
                ";" + location.getBlockY() +
                ";" + location.getBlockZ());
    }
}

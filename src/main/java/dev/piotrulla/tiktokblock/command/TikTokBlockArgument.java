package dev.piotrulla.tiktokblock.command;

import dev.piotrulla.tiktokblock.TikTokBlock;
import dev.piotrulla.tiktokblock.TikTokBlockRepository;
import dev.piotrulla.tiktokblock.TikTokSettings;
import dev.piotrulla.tiktokblock.util.ColorUtil;
import dev.rollczi.litecommands.argument.ArgumentName;
import dev.rollczi.litecommands.argument.simple.OneArgument;
import dev.rollczi.litecommands.command.LiteInvocation;
import dev.rollczi.litecommands.suggestion.Suggestion;
import panda.std.Result;

import java.util.List;

@ArgumentName("name")
public class TikTokBlockArgument implements OneArgument<TikTokBlock> {

    private final TikTokBlockRepository repository;
    private final TikTokSettings settings;

    public TikTokBlockArgument(TikTokBlockRepository repository, TikTokSettings settings) {
        this.repository = repository;
        this.settings = settings;
    }

    @Override
    public Result<TikTokBlock, ?> parse(LiteInvocation invocation, String argument) {
        return this.repository.findBlock(argument)
                .map(Result::ok)
                .orElseGet(() -> Result.error(ColorUtil.color(this.settings.blockNotExists())));
    }

    @Override
    public List<Suggestion> suggest(LiteInvocation invocation) {
        return this.repository.tikTokBlocksNames().stream()
                .map(Suggestion::of)
                .toList();
    }
}

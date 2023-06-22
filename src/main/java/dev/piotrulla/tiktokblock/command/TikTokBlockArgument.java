package dev.piotrulla.tiktokblock.command;

import dev.piotrulla.tiktokblock.TikTokBlock;
import dev.piotrulla.tiktokblock.TikTokBlockRepository;
import dev.piotrulla.tiktokblock.TikTokMessages;
import dev.piotrulla.tiktokblock.util.ColorUtil;
import dev.rollczi.litecommands.argument.ArgumentName;
import dev.rollczi.litecommands.argument.simple.OneArgument;
import dev.rollczi.litecommands.command.LiteInvocation;
import dev.rollczi.litecommands.suggestion.Suggestion;
import panda.std.Result;

import java.util.List;
import java.util.stream.Collectors;

@ArgumentName("name")
public class TikTokBlockArgument implements OneArgument<TikTokBlock> {

    private final TikTokBlockRepository repository;
    private final TikTokMessages messages;

    public TikTokBlockArgument(TikTokBlockRepository repository, TikTokMessages messages) {
        this.repository = repository;
        this.messages = messages;
    }

    @Override
    public Result<TikTokBlock, ?> parse(LiteInvocation invocation, String argument) {
        return this.repository.findBlock(argument)
                .map(Result::ok)
                .orElseGet(() -> Result.error(ColorUtil.color(this.messages.blockNotExists())));
    }

    @Override
    public List<Suggestion> suggest(LiteInvocation invocation) {
        return this.repository.tikTokBlocksNames().stream()
                .map(Suggestion::of)
                .collect(Collectors.toList());
    }
}

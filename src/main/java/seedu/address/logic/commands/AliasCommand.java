package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.alias.AliasMapping;

/**
 * Assigns a short alias to an item or room.
 */
public class AliasCommand extends Command {

    public static final String COMMAND_WORD = "alias";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Assigns a short alias to an item or room.\n"
            + "Parameters: ITEM_OR_ROOM_ID ALIAS_NAME\n"
            + "Example: " + COMMAND_WORD + " Wilson-Evolution-Basketball-1 b1";

    public static final String MESSAGE_SUCCESS = "%1$s is now aliased as '%2$s'";
    public static final String MESSAGE_INVALID_TARGET = "Error: %1$s is not a valid registered item or room.";
    public static final String MESSAGE_DUPLICATE_ALIAS =
            "Error: Alias '%1$s' is already in use. Please choose a different alias.";

    private final AliasMapping aliasMappingToAdd;

    /**
     * Creates an AliasCommand to add the specified {@code AliasMapping}.
     */
    public AliasCommand(AliasMapping aliasMapping) {
        requireNonNull(aliasMapping);
        this.aliasMappingToAdd = aliasMapping;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        String targetId = aliasMappingToAdd.getTargetId();
        String aliasName = aliasMappingToAdd.getAliasName();

        if (!model.hasAliasableTarget(targetId)) {
            throw new CommandException(String.format(MESSAGE_INVALID_TARGET, targetId));
        }

        if (model.hasAliasName(aliasName)) {
            throw new CommandException(String.format(MESSAGE_DUPLICATE_ALIAS, aliasName));
        }

        model.addAliasMapping(aliasMappingToAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, targetId, aliasName));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof AliasCommand)) {
            return false;
        }
        AliasCommand otherAliasCommand = (AliasCommand) other;
        return aliasMappingToAdd.equals(otherAliasCommand.aliasMappingToAdd);
    }
}

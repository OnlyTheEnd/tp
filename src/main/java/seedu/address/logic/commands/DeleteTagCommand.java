package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LOCATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.Taggable;


/**
 * Delete tag from a room/equipment.
 */
public class DeleteTagCommand extends Command {
    public static final String COMMAND_WORD = "untag";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": untags an existing tag from an existing room in the system. "
            + "Parameters: "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_LOCATION + "MPSH-1 "
            + PREFIX_TAG + "Renovation\n"
            + COMMAND_WORD + " "
            + PREFIX_CATEGORY + "Wilson-Basketball "
            + PREFIX_TAG + "Spoilt";

    public static final String MESSAGE_SUCCESS = "Success! %1$s has been untagged from %2$s";
    public static final String MESSAGE_ERROR = "Failure! Untagging was unsuccessful";

    private final Taggable target;
    private final Tag tag;

    /**
     * Creates an DeleteTagCommand to the specified room/equipment and tag
     */
    public DeleteTagCommand(Taggable target, Tag tag) {
        requireNonNull(target);
        requireNonNull(tag);

        this.target = target;
        this.tag = tag;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        //Ensure that target is inside storage
        if (!model.hasTaggable(target)) {
            throw new CommandException(MESSAGE_ERROR);
        }

        model.deleteTag(target, tag);

        return new CommandResult(String.format(MESSAGE_SUCCESS, tag, target.getNameString()));
    }


    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeleteTagCommand)) {
            return false;
        }

        DeleteTagCommand otherDeleteTagCommand = (DeleteTagCommand) other;
        return target.equals(otherDeleteTagCommand.target)
                && tag.equals(otherDeleteTagCommand.tag);
    }
}

package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.Taggable;
import seedu.address.model.tag.exceptions.TagNotFoundException;


/**
 * Delete tag from a room/equipment.
 */
public class DeleteTagCommand extends Command {
    public static final String COMMAND_WORD = "untag-r";
    public static final String COMMAND_WORD2 = "untag-e";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": untags an existing tag from an existing room in the system. "
            + "Parameters: "
            + "Example: " + COMMAND_WORD + " MPSH-1 "
            + "Renovation\n"
            + COMMAND_WORD2 + " "
            + "Wilson-Basketball "
            + "IHG";

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
        try {
            //Ensure that target is inside storage
            if (!model.hasTaggable(target)) {
                throw new CommandException(MESSAGE_ERROR);
            }

            model.deleteTag(target, tag);

            return new CommandResult(String.format(MESSAGE_SUCCESS, tag, target.getNameString()));
        } catch (TagNotFoundException e) {
            throw new CommandException(MESSAGE_ERROR + " Target tag not found and could not be deleted");
        }
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

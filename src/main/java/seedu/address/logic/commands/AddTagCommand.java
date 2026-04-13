package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.Taggable;
import seedu.address.model.tag.exceptions.DuplicateTagException;


/**
 * Adds Tag to a room or equipment.
 */
public class AddTagCommand extends Command {

    public static final String COMMAND_WORD = "tag-r";
    public static final String COMMAND_WORD2 = "tag-e";

    public static final String MESSAGE_USAGE = COMMAND_WORD + "/" + COMMAND_WORD2
            + ": Tags an existing room/equipment to the system.\n"
            + "Parameters: "
            + " ROOM_NAME_OR_EQUIPMENT_NAME TAG\n"
            + "Example: " + COMMAND_WORD + " MPSH-1 "
            + "Renovation\n"
            + COMMAND_WORD2 + " "
            + "Wilson-Basketball "
            + "IHG";



    public static final String MESSAGE_SUCCESS = "Success! %1$s has been tagged to %2$s";
    public static final String MESSAGE_ERROR = "Failure! Tagging was unsuccessful";


    private final Taggable target;
    private final Tag tag;

    /**
     * Creates an AddTagCommand to the specified room/equipment and tag
     */
    public AddTagCommand(Taggable target, Tag tag) {
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
                throw new CommandException(MESSAGE_ERROR + " Target equipment/room not found");
            }

            model.addTag(target, tag);
            return new CommandResult(String.format(MESSAGE_SUCCESS, tag, target.getNameString()));
        } catch (DuplicateTagException e) {
            throw new CommandException(MESSAGE_ERROR + " Cannot add duplicate tag");
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddTagCommand)) {
            return false;
        }

        AddTagCommand otherAddTagCommand = (AddTagCommand) other;
        return target.equals(otherAddTagCommand.target)
                && tag.equals(otherAddTagCommand.tag);
    }
}


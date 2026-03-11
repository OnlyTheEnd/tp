package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.room.Room;
import seedu.address.model.room.RoomName;
import seedu.address.model.tag.Tag;


/**
 * Command class to delete a tag
 */
public class DeleteTagCommand extends Command {
    public static final String COMMAND_WORD = "untag";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": untags an existing tag from an existing room in the system. "
            + "Parameters: "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "MPSH-1 "
            + PREFIX_TAG + "Renovation";

    public static final String MESSAGE_SUCCESS = "Success! %1$s has been untagged";
    public static final String MESSAGE_ERROR = "Failure! Untagging was unsuccessful";


    private final RoomName roomName;
    private final Tag roomTag;

    /**
     * Creates an DeleteTagCommand to the specified room and tag
     */
    public DeleteTagCommand(RoomName roomName, Tag roomTag) {
        requireNonNull(roomName);

        this.roomName = roomName;
        this.roomTag = roomTag;
    }


    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        requireNonNull(roomTag);

        if (!model.hasRoom(new Room(roomName))) {
            throw new CommandException(MESSAGE_ERROR);
        }

        model.deleteTag(roomName, roomTag);
        return new CommandResult(String.format(MESSAGE_SUCCESS, roomTag));
    }
}

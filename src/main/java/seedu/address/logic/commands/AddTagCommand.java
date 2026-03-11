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
 * stuff
 */
public class AddTagCommand extends Command {

    public static final String COMMAND_WORD = "tag";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Tags an existing room to the system. "
            + "Parameters: "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "MPSH-1 "
            + PREFIX_TAG + "Renovation";

    public static final String MESSAGE_SUCCESS = "Success! %1$s has been tagged";
    public static final String MESSAGE_ERROR = "Failure! Tagging was unsuccessful";


    private final RoomName roomName;
    private final Tag roomTag;

    /**
     * Creates an AddTagCommand to the specified room and tag
     */
    public AddTagCommand(RoomName roomName, Tag roomTag) {
        requireNonNull(roomName);

        this.roomName = roomName;
        this.roomTag = roomTag;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (!model.hasRoom(new Room(roomName))) {
            throw new CommandException(MESSAGE_ERROR);
        }

        model.addTag(roomName, roomTag);
        return new CommandResult(String.format(MESSAGE_SUCCESS, roomTag));
    }
}


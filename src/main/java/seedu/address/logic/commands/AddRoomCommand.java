package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LOCATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STATUS;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.room.Room;

/**
 * Adds a room to the address book.
 */
public class AddRoomCommand extends Command {

    public static final String COMMAND_WORD = "add-r";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a room to the system. "
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_LOCATION + "LOCATION "
            + PREFIX_STATUS + "STATUS\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "MPSH-1 "
            + PREFIX_LOCATION + "Sports Centre "
            + PREFIX_STATUS + "Available";

    public static final String MESSAGE_SUCCESS = "New room added: Name: %1$s | Location: %2$s | Status: %3$s";
    public static final String MESSAGE_DUPLICATE_ROOM = "This room already exists in the system!";

    private final Room toAdd;

    /**
     * Creates an AddRoomCommand to add the specified {@code Room}
     */
    public AddRoomCommand(Room room) {
        requireNonNull(room);
        toAdd = room;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasRoom(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_ROOM);
        }

        model.addRoom(toAdd);
        return new CommandResult(
                String.format(MESSAGE_SUCCESS, toAdd.getName(), toAdd.getLocation(), toAdd.getStatus()),
                false, false, false, true, false);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddRoomCommand)) {
            return false;
        }

        AddRoomCommand otherAddRoomCommand = (AddRoomCommand) other;
        return toAdd.equals(otherAddRoomCommand.toAdd);
    }
}

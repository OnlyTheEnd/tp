package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LOCATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STATUS;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.room.Room;

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

    public static final String MESSAGE_SUCCESS = "New room added: %1$s";
    public static final String MESSAGE_DUPLICATE_ROOM = "This room already exists in the system!";

    private final Room toAdd;

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
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }
}
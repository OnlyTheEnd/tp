package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Lists all rooms in the address book to the user.
 */
public class ListRoomCommand extends Command {

    public static final String COMMAND_WORD = "list-r";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Lists all rooms managed by the system.\n"
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_SUCCESS = "Listed all rooms";
    public static final String MESSAGE_FAILURE = "Room List has not been created. Please proceed to add room first.";


    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        model.updateFilteredRoomList(Model.PREDICATE_SHOW_ALL_ROOMS);

        if (model.getFilteredRoomList().isEmpty()) {
            throw new CommandException(MESSAGE_FAILURE);
        }

        return new CommandResult(MESSAGE_SUCCESS,
                false, false, true, true, true);
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof ListRoomCommand);
    }

    @Override
    public int hashCode() {
        return ListRoomCommand.class.hashCode();
    }
}

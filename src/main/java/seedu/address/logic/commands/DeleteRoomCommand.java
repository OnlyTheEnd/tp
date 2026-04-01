package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.room.Room;

/**
 * Deletes a room identified using it's displayed index from the address book.
 */
public class DeleteRoomCommand extends Command {

    public static final String COMMAND_WORD = "delete-r";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the room identified by the index number used in the displayed room list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_ROOM_SUCCESS = "Deleted Room: \n%1$s";
    public static final String MESSAGE_FAILURE = "Fail to delete the room. Please ensure the index is in the list "
            + "and you have input the right format.\n" + MESSAGE_USAGE;
    public static final String MESSAGE_ROOM_BOOKED = "Cannot delete a room that currently has a 'Booked' status.";

    private final Index targetIndex;

    public DeleteRoomCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Room> lastShownList = model.getFilteredRoomList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(MESSAGE_FAILURE);
        }

        Room roomToDelete = lastShownList.get(targetIndex.getZeroBased());

        if (!roomToDelete.getStatus().toString().equalsIgnoreCase("Available")) {
            throw new CommandException(String.format(
                    "Room is currently %1$s. Only allowed to be deleted when it is 'Available'.",
                    roomToDelete.getStatus().toString()));
        }

        model.deleteRoom(roomToDelete);
        return new CommandResult(String.format(MESSAGE_DELETE_ROOM_SUCCESS, roomToDelete),
                false, false, true, true, true);
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof DeleteRoomCommand
                && targetIndex.equals(((DeleteRoomCommand) other).targetIndex));
    }

    @Override
    public int hashCode() {
        return targetIndex.hashCode();
    }
}

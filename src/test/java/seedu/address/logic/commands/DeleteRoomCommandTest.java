package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_ROOM;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_ROOM;
import static seedu.address.testutil.TypicalRooms.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.room.Room;
import seedu.address.testutil.RoomBuilder;

public class DeleteRoomCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndex_success() {
        Room roomToDelete = model.getFilteredRoomList().get(INDEX_FIRST_ROOM.getZeroBased());
        DeleteRoomCommand deleteRoomCommand = new DeleteRoomCommand(INDEX_FIRST_ROOM);

        String expectedMessage = String.format(DeleteRoomCommand.MESSAGE_DELETE_ROOM_SUCCESS, roomToDelete);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deleteRoom(roomToDelete);

        CommandResult expectedCommandResult = new CommandResult(expectedMessage, false, false, true);

        assertCommandSuccess(deleteRoomCommand, model, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_invalidIndex_throwsCommandException() {
        Index outOfBoundsIndex = Index.fromOneBased(model.getFilteredRoomList().size() + 1);
        DeleteRoomCommand deleteRoomCommand = new DeleteRoomCommand(outOfBoundsIndex);

        assertCommandFailure(deleteRoomCommand, model, DeleteRoomCommand.MESSAGE_FAILURE);
    }

    @Test
    public void execute_bookedRoom_throwsCommandException() {
        // Create a room with a UNIQUE name that isn't in TypicalRooms
        Room bookedRoom = new RoomBuilder()
                .withName("Unique-Room-999")
                .withStatus("Booked")
                .build();

        model.addRoom(bookedRoom);

        Index indexLast = Index.fromOneBased(model.getFilteredRoomList().size());

        DeleteRoomCommand deleteRoomCommand = new DeleteRoomCommand(indexLast);

        assertCommandFailure(deleteRoomCommand, model, DeleteRoomCommand.MESSAGE_ROOM_BOOKED);
    }

    @Test
    public void equals() {
        DeleteRoomCommand deleteFirstCommand = new DeleteRoomCommand(INDEX_FIRST_ROOM);
        DeleteRoomCommand deleteSecondCommand = new DeleteRoomCommand(INDEX_SECOND_ROOM);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteRoomCommand deleteFirstCommandCopy = new DeleteRoomCommand(INDEX_FIRST_ROOM);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different room index -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    @Test
    public void hashCode_test() {
        DeleteRoomCommand command = new DeleteRoomCommand(INDEX_FIRST_ROOM);
        assertEquals(command.hashCode(), new DeleteRoomCommand(INDEX_FIRST_ROOM).hashCode());
    }
}

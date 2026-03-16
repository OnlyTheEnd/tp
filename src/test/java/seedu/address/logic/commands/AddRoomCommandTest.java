package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.ModelStub;
import seedu.address.model.room.Room;
import seedu.address.testutil.RoomBuilder;


public class AddRoomCommandTest {

    @Test
    public void execute_roomAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingRoomAdded modelStub = new ModelStubAcceptingRoomAdded();
        Room validRoom = new RoomBuilder().build();

        CommandResult commandResult = new AddRoomCommand(validRoom).execute(modelStub);

        String expectedMessage = String.format(AddRoomCommand.MESSAGE_SUCCESS,
                validRoom.getName().fullName,
                validRoom.getLocation().value,
                validRoom.getStatus().value);

        assertEquals(expectedMessage, commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validRoom), modelStub.roomsAdded);
    }

    @Test
    public void execute_duplicateRoom_throwsCommandException() {
        Room validRoom = new RoomBuilder().build();
        AddRoomCommand addRoomCommand = new AddRoomCommand(validRoom);
        ModelStub modelStub = new ModelStubWithRoom(validRoom);

        assertThrows(CommandException.class, AddRoomCommand.MESSAGE_DUPLICATE_ROOM, () ->
                addRoomCommand.execute(modelStub));
    }

    /**
     * A Model stub that always accepts the room being added.
     */
    private static class ModelStubAcceptingRoomAdded extends ModelStub {
        final ArrayList<Room> roomsAdded = new ArrayList<>();

        @Override
        public boolean hasRoom(Room room) {
            requireNonNull(room);
            return roomsAdded.stream().anyMatch(room::isSameRoom);
        }

        @Override
        public void addRoom(Room room) {
            requireNonNull(room);
            roomsAdded.add(room);
        }
    }

    /**
     * A Model stub that contains a single room.
     */
    private static class ModelStubWithRoom extends ModelStub {
        private final Room room;

        ModelStubWithRoom(Room room) {
            requireNonNull(room);
            this.room = room;
        }

        @Override
        public boolean hasRoom(Room room) {
            requireNonNull(room);
            return this.room.isSameRoom(room);
        }
    }
}

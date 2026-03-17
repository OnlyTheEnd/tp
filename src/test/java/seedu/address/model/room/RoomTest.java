package seedu.address.model.room;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalRooms.ROOM_A;
import static seedu.address.testutil.TypicalRooms.ROOM_B;
//import static seedu.address.testutil.TypicalRooms.ROOM_D;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.RoomBuilder;

public class RoomTest {

    @Test
    public void isSameRoom() {
        // same object -> returns true
        assertTrue(ROOM_A.isSameRoom(ROOM_A));

        // null -> returns false
        assertFalse(ROOM_A.isSameRoom(null));

        // same name, all other attributes different -> returns true
        Room editedRoomA = new Room(ROOM_A.getName(), ROOM_B.getLocation(), ROOM_B.getStatus());
        assertTrue(ROOM_A.isSameRoom(editedRoomA));

        // different name, all other attributes same -> returns false
        editedRoomA = new Room(ROOM_B.getName(), ROOM_A.getLocation(), ROOM_A.getStatus());
        assertFalse(ROOM_A.isSameRoom(editedRoomA));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Room roomACopy = new RoomBuilder(ROOM_A).build();
        assertTrue(ROOM_A.equals(roomACopy));

        // same object -> returns true
        assertTrue(ROOM_A.equals(ROOM_A));

        // null -> returns false
        assertFalse(ROOM_A.equals(null));

        // different types -> returns false
        assertFalse(ROOM_A.equals(5));

        // different room -> returns false
        assertFalse(ROOM_A.equals(ROOM_B));

        // different name -> returns false
        Room editedRoomA = new RoomBuilder(ROOM_A).withName("Different-Name").build();
        assertFalse(ROOM_A.equals(editedRoomA));
    }


}

package seedu.address.model.room;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalRooms.ROOM_A;
import static seedu.address.testutil.TypicalRooms.ROOM_B;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.room.exceptions.DuplicateRoomException;
import seedu.address.model.room.exceptions.RoomNotFoundException;

public class UniqueRoomListTest {

    private final UniqueRoomList uniqueRoomList = new UniqueRoomList();

    @Test
    public void contains_nullRoom_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueRoomList.contains(null));
    }

    @Test
    public void contains_roomNotInList_returnsFalse() {
        assertFalse(uniqueRoomList.contains(ROOM_A));
    }

    @Test
    public void contains_roomInList_returnsTrue() {
        uniqueRoomList.add(ROOM_A);
        assertTrue(uniqueRoomList.contains(ROOM_A));
    }

    @Test
    public void contains_roomWithSameIdentityFieldsInList_returnsTrue() {
        uniqueRoomList.add(ROOM_A);
        // Create a room with same name but different location/status
        Room editedRoomA = new Room(ROOM_A.getName(), ROOM_B.getLocation(), ROOM_B.getStatus());
        assertTrue(uniqueRoomList.contains(editedRoomA));
    }

    @Test
    public void add_nullRoom_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueRoomList.add(null));
    }

    @Test
    public void add_duplicateRoom_throwsDuplicateRoomException() {
        uniqueRoomList.add(ROOM_A);
        assertThrows(DuplicateRoomException.class, () -> uniqueRoomList.add(ROOM_A));
    }

    @Test
    public void setRoom_nullTargetRoom_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueRoomList.setRoom(null, ROOM_A));
    }

    @Test
    public void setRooms_listWithDuplicateRooms_throwsDuplicateRoomException() {
        List<Room> listWithDuplicates = Arrays.asList(ROOM_A, ROOM_A);
        assertThrows(DuplicateRoomException.class, () -> uniqueRoomList.setRooms(listWithDuplicates));
    }

    @Test
    public void setRoom_targetRoomNotInList_throwsRoomNotFoundException() {
        assertThrows(RoomNotFoundException.class, () -> uniqueRoomList.setRoom(ROOM_A, ROOM_A));
    }

    @Test
    public void setRoom_editedRoomHasNonUniqueIdentity_throwsDuplicateRoomException() {
        uniqueRoomList.add(ROOM_A);
        uniqueRoomList.add(ROOM_B);
        assertThrows(DuplicateRoomException.class, () -> uniqueRoomList.setRoom(ROOM_A, ROOM_B));
    }

    @Test
    public void remove_roomDoesNotExist_throwsRoomNotFoundException() {
        assertThrows(RoomNotFoundException.class, () -> uniqueRoomList.remove(ROOM_A));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, ()
                -> uniqueRoomList.asUnmodifiableObservableList().remove(0));
    }
}

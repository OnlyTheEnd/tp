package seedu.address.model.room;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
import seedu.address.model.tag.exceptions.DuplicateTagException;
import seedu.address.model.tag.exceptions.TagNotFoundException;

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

    @Test
    public void addRoomTag_validRoomAndTag_success() {
        Room mpsh = new Room(new RoomName("MPSH"));
        uniqueRoomList.add(mpsh);

        uniqueRoomList.addRoomTag(mpsh, "IFG");

        // Verify tag was added
        assertTrue(mpsh.getTags().stream()
                .anyMatch(tag -> tag.tagName.equals("IFG")));
        assertEquals(1, mpsh.getTags().size());
    }

    @Test
    public void addRoomTag_multipleTagsToSameRoom_success() {
        Room mpsh = new Room(new RoomName("MPSH"));
        uniqueRoomList.add(mpsh);

        uniqueRoomList.addRoomTag(mpsh, "IFG");
        uniqueRoomList.addRoomTag(mpsh, "Talk");

        assertEquals(2, mpsh.getTags().size());
        assertTrue(mpsh.getTags().stream()
                .anyMatch(tag -> tag.tagName.equals("IFG")));
        assertTrue(mpsh.getTags().stream()
                .anyMatch(tag -> tag.tagName.equals("TALK")));
    }

    @Test
    public void addRoomTag_duplicateTag_throwsDuplicateTagException() {
        Room mpsh = new Room(new RoomName("MPSH"));
        uniqueRoomList.add(mpsh);

        uniqueRoomList.addRoomTag(mpsh, "IFG");

        // Adding same tag again should throw exception
        assertThrows(DuplicateTagException.class, () ->
                uniqueRoomList.addRoomTag(mpsh, "IFG"));
    }

    @Test
    public void addRoomTag_duplicateTagCaseInsensitive_throwsDuplicateTagException() {
        Room mpsh = new Room(new RoomName("MPSH"));
        uniqueRoomList.add(mpsh);

        uniqueRoomList.addRoomTag(mpsh, "IFG");

        // Adding same tag with different case should throw exception
        assertThrows(DuplicateTagException.class, () ->
                uniqueRoomList.addRoomTag(mpsh, "ifg"));

        assertThrows(DuplicateTagException.class, () ->
                uniqueRoomList.addRoomTag(mpsh, "Ifg"));
    }

    @Test
    public void addRoomTag_roomNotInList_throwsRoomNotFoundException() {
        Room mpsh = new Room(new RoomName("MPSH"));
        Room lt1 = new Room(new RoomName("LT1"));

        uniqueRoomList.add(mpsh);

        // Adding tag to room not in list should throw exception
        assertThrows(RoomNotFoundException.class, () ->
                uniqueRoomList.addRoomTag(lt1, "IFG"));
    }

    @Test
    public void addRoomTag_nullRoom_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () ->
                uniqueRoomList.addRoomTag(null, "IFG"));
    }

    @Test
    public void addRoomTag_nullTag_throwsNullPointerException() {
        Room mpsh = new Room(new RoomName("MPSH"));;
        uniqueRoomList.add(mpsh);

        assertThrows(NullPointerException.class, () ->
                uniqueRoomList.addRoomTag(mpsh, null));
    }

    @Test
    public void addRoomTag_bothTagsToMpsh_success() {
        Room mpsh = new Room(new RoomName("MPSH"));;
        uniqueRoomList.add(mpsh);

        uniqueRoomList.addRoomTag(mpsh, "IFG");
        uniqueRoomList.addRoomTag(mpsh, "Talk");

        assertEquals(2, mpsh.getTags().size());
    }


    @Test
    public void deleteRoomTag_validRoomAndTag_success() {
        Room mpsh = new Room(new RoomName("MPSH"));;
        uniqueRoomList.add(mpsh);
        uniqueRoomList.addRoomTag(mpsh, "IFG");

        uniqueRoomList.deleteRoomTag(mpsh, "IFG");

        // Verify tag was removed
        assertEquals(0, mpsh.getTags().size());
    }

    @Test
    public void deleteRoomTag_oneOfMultipleTags_success() {
        Room mpsh = new Room(new RoomName("MPSH"));;
        uniqueRoomList.add(mpsh);
        uniqueRoomList.addRoomTag(mpsh, "IFG");
        uniqueRoomList.addRoomTag(mpsh, "Talk");

        uniqueRoomList.deleteRoomTag(mpsh, "IFG");

        assertEquals(1, mpsh.getTags().size());
        assertTrue(mpsh.getTags().stream()
                .anyMatch(tag -> tag.tagName.equals("TALK")));
        assertTrue(mpsh.getTags().stream()
                .noneMatch(tag -> tag.tagName.equals("IFG")));
    }


    @Test
    public void deleteRoomTag_talkTag_success() {
        Room mpsh = new Room(new RoomName("MPSH"));;
        uniqueRoomList.add(mpsh);
        uniqueRoomList.addRoomTag(mpsh, "Talk");

        uniqueRoomList.deleteRoomTag(mpsh, "Talk");

        assertEquals(0, mpsh.getTags().size());
    }

    @Test
    public void deleteRoomTag_tagNotFound_throwsTagNotFoundException() {
        Room mpsh = new Room(new RoomName("MPSH"));;
        uniqueRoomList.add(mpsh);
        uniqueRoomList.addRoomTag(mpsh, "IFG");

        // Deleting non-existent tag should throw exception
        assertThrows(TagNotFoundException.class, () ->
                uniqueRoomList.deleteRoomTag(mpsh, "Talk"));
    }

    @Test
    public void deleteRoomTag_roomHasNoTags_throwsTagNotFoundException() {
        Room mpsh = new Room(new RoomName("MPSH"));;
        uniqueRoomList.add(mpsh);

        // Deleting tag from room with no tags should throw exception
        assertThrows(TagNotFoundException.class, () ->
                uniqueRoomList.deleteRoomTag(mpsh, "IFG"));
    }

    @Test
    public void deleteRoomTag_roomNotInList_throwsRoomNotFoundException() {
        Room mpsh = new Room(new RoomName("MPSH"));;
        Room lt1 = new Room(new RoomName("LT1"));

        uniqueRoomList.add(mpsh);

        // Deleting tag from room not in list should throw exception
        assertThrows(RoomNotFoundException.class, () ->
                uniqueRoomList.deleteRoomTag(lt1, "IFG"));
    }

    @Test
    public void deleteRoomTag_nullRoom_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () ->
                uniqueRoomList.deleteRoomTag(null, "IFG"));
    }

    @Test
    public void deleteRoomTag_nullTag_throwsNullPointerException() {
        Room mpsh = new Room(new RoomName("MPSH"));;
        uniqueRoomList.add(mpsh);

        assertThrows(NullPointerException.class, () ->
                uniqueRoomList.deleteRoomTag(mpsh, null));
    }
}

package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.storage.JsonAdaptedRoom.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalRooms.ROOM_B;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.room.RoomName;




public class JsonAdaptedRoomTest {
    private static final String INVALID_NAME = "R@oom";
    private static final String INVALID_LOCATION = " ";
    private static final String INVALID_STATUS = "Unknown";

    private static final List<JsonAdaptedTag> VALID_TAGS = new ArrayList<>();
    private static final String VALID_NAME = ROOM_B.getName().toString();
    private static final String VALID_LOCATION = ROOM_B.getLocation().toString();
    private static final String VALID_STATUS = ROOM_B.getStatus().toString();

    @Test
    public void toModelType_validRoomDetails_returnsRoom() throws Exception {
        JsonAdaptedRoom room = new JsonAdaptedRoom(ROOM_B);
        assertEquals(ROOM_B, room.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedRoom room = new JsonAdaptedRoom(INVALID_NAME, VALID_LOCATION, VALID_STATUS, VALID_TAGS);
        assertThrows(IllegalValueException.class, room::toModelType);
    }

    @Test
    public void toModelType_invalidStatus_throwsIllegalValueException() {
        JsonAdaptedRoom room = new JsonAdaptedRoom(VALID_NAME, VALID_LOCATION, INVALID_STATUS, VALID_TAGS);
        assertThrows(IllegalValueException.class, room::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedRoom room = new JsonAdaptedRoom(null, VALID_LOCATION, VALID_STATUS, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, RoomName.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, room::toModelType);
    }
}

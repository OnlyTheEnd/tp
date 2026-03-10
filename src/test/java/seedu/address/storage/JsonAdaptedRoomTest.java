package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalRooms.ROOM_B;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;

public class JsonAdaptedRoomTest {
    private static final String INVALID_NAME = "R@oom";
    private static final String INVALID_STATUS = "Unknown";

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
        JsonAdaptedRoom room = new JsonAdaptedRoom(INVALID_NAME, VALID_LOCATION, VALID_STATUS);
        assertThrows(IllegalValueException.class, room::toModelType);
    }

    @Test
    public void toModelType_invalidStatus_throwsIllegalValueException() {
        JsonAdaptedRoom room = new JsonAdaptedRoom(VALID_NAME, VALID_LOCATION, INVALID_STATUS);
        assertThrows(IllegalValueException.class, room::toModelType);
    }
}

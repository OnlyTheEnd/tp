package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.StudentId;
import seedu.address.model.reservation.Reservation;

public class JsonAdaptedReservationTest {

    private static final String VALID_RESOURCE_ID = "Hall-2";
    private static final String VALID_STUDENT_ID = "a1234567a";
    private static final String VALID_START = "2026-03-01 1400";
    private static final String VALID_END = "2026-03-01 1600";
    private static final String INVALID_RESOURCE_ID = "!invalid";
    private static final String INVALID_STUDENT_ID = "12345678";
    private static final String INVALID_DATE_TIME = "2026/03/01 1400";

    private static final Reservation VALID_RESERVATION = new Reservation(VALID_RESOURCE_ID,
            new StudentId(VALID_STUDENT_ID),
            LocalDateTime.of(2026, 3, 1, 14, 0),
            LocalDateTime.of(2026, 3, 1, 16, 0));

    @Test
    public void toModelType_validReservationDetails_returnsReservation() throws Exception {
        JsonAdaptedReservation reservation =
                new JsonAdaptedReservation(VALID_RESOURCE_ID, VALID_STUDENT_ID, VALID_START, VALID_END);

        assertEquals(VALID_RESERVATION, reservation.toModelType());
    }

    @Test
    public void toModelType_invalidResourceId_throwsIllegalValueException() {
        JsonAdaptedReservation reservation =
                new JsonAdaptedReservation(INVALID_RESOURCE_ID, VALID_STUDENT_ID, VALID_START, VALID_END);

        assertThrows(IllegalValueException.class, Reservation.MESSAGE_RESOURCE_ID_CONSTRAINTS,
                reservation::toModelType);
    }

    @Test
    public void toModelType_nullResourceId_throwsIllegalValueException() {
        JsonAdaptedReservation reservation =
                new JsonAdaptedReservation(null, VALID_STUDENT_ID, VALID_START, VALID_END);

        assertThrows(IllegalValueException.class,
                String.format(JsonAdaptedReservation.MISSING_FIELD_MESSAGE_FORMAT, "resourceId"),
                reservation::toModelType);
    }

    @Test
    public void toModelType_invalidStudentId_throwsIllegalValueException() {
        JsonAdaptedReservation reservation =
                new JsonAdaptedReservation(VALID_RESOURCE_ID, INVALID_STUDENT_ID, VALID_START, VALID_END);

        assertThrows(IllegalValueException.class, StudentId.MESSAGE_CONSTRAINTS,
                reservation::toModelType);
    }

    @Test
    public void toModelType_nullStudentId_throwsIllegalValueException() {
        JsonAdaptedReservation reservation =
                new JsonAdaptedReservation(VALID_RESOURCE_ID, null, VALID_START, VALID_END);

        assertThrows(IllegalValueException.class,
                String.format(JsonAdaptedReservation.MISSING_FIELD_MESSAGE_FORMAT,
                        StudentId.class.getSimpleName()),
                reservation::toModelType);
    }

    @Test
    public void toModelType_invalidStartDateTime_throwsIllegalValueException() {
        JsonAdaptedReservation reservation =
                new JsonAdaptedReservation(VALID_RESOURCE_ID, VALID_STUDENT_ID, INVALID_DATE_TIME, VALID_END);

        assertThrows(IllegalValueException.class,
                "Reservation date/time must be in yyyy-MM-dd HHmm format.",
                reservation::toModelType);
    }

    @Test
    public void toModelType_invalidEndDateTime_throwsIllegalValueException() {
        JsonAdaptedReservation reservation =
                new JsonAdaptedReservation(VALID_RESOURCE_ID, VALID_STUDENT_ID, VALID_START, INVALID_DATE_TIME);

        assertThrows(IllegalValueException.class,
                "Reservation date/time must be in yyyy-MM-dd HHmm format.",
                reservation::toModelType);
    }

    @Test
    public void toModelType_endBeforeStart_throwsIllegalValueException() {
        JsonAdaptedReservation reservation =
                new JsonAdaptedReservation(VALID_RESOURCE_ID, VALID_STUDENT_ID,
                        "2026-03-01 1600", "2026-03-01 1400");

        assertThrows(IllegalValueException.class, Reservation.MESSAGE_TIME_RANGE_CONSTRAINTS,
                reservation::toModelType);
    }
}

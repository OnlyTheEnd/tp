package seedu.address.storage;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.StudentId;
import seedu.address.model.reservation.Reservation;

/**
 * Jackson-friendly version of {@link Reservation}.
 */
class JsonAdaptedReservation {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Reservation's %s field is missing!";

    private final String resourceId;
    private final String studentId;
    private final String startDateTime;
    private final String endDateTime;

    @JsonCreator
    public JsonAdaptedReservation(@JsonProperty("resourceId") String resourceId,
                                  @JsonProperty("studentId") String studentId,
                                  @JsonProperty("startDateTime") String startDateTime,
                                  @JsonProperty("endDateTime") String endDateTime) {
        this.resourceId = resourceId;
        this.studentId = studentId;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
    }

    public JsonAdaptedReservation(Reservation source) {
        resourceId = source.getResourceId();
        studentId = source.getStudentId().value;
        startDateTime = source.getFormattedStartDateTime();
        endDateTime = source.getFormattedEndDateTime();
    }

    public Reservation toModelType() throws IllegalValueException {
        if (resourceId == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, "resourceId"));
        }
        if (!Reservation.isValidResourceId(resourceId)) {
            throw new IllegalValueException(Reservation.MESSAGE_RESOURCE_ID_CONSTRAINTS);
        }

        if (studentId == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, StudentId.class.getSimpleName()));
        }
        if (!StudentId.isValidStudentId(studentId)) {
            throw new IllegalValueException(StudentId.MESSAGE_CONSTRAINTS);
        }

        if (startDateTime == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, "startDateTime"));
        }

        if (endDateTime == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, "endDateTime"));
        }

        try {
            StudentId modelStudentId = new StudentId(studentId);
            LocalDateTime modelStart = LocalDateTime.parse(startDateTime, Reservation.DATE_TIME_FORMATTER);
            LocalDateTime modelEnd = LocalDateTime.parse(endDateTime, Reservation.DATE_TIME_FORMATTER);
            return Reservation.fromStorage(resourceId, modelStudentId, modelStart, modelEnd);
        } catch (DateTimeParseException dtpe) {
            throw new IllegalValueException("Reservation date/time must be in yyyy-MM-dd HHmm format.");
        } catch (IllegalArgumentException iae) {
            throw new IllegalValueException(iae.getMessage());
        }
    }
}

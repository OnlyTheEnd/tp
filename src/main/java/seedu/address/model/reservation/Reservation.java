package seedu.address.model.reservation;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.person.StudentId;

/**
 * Represents a reservation of a room/item by a student.
 */
public class Reservation {

    public static final String MESSAGE_RESOURCE_ID_CONSTRAINTS =
            "Resource ID should start with a letter and contain only letters, digits, and hyphens, "
                    + "e.g. Hall-2 or MPSH-1";

    public static final String MESSAGE_START_DATE_TIME_CONSTRAINTS =
            "Start date/time must not be in the past.";

    public static final String MESSAGE_TIME_RANGE_CONSTRAINTS =
            "End date/time must be after start date/time.";

    public static final String VALIDATION_REGEX = "[A-Za-z][A-Za-z0-9-]*";

    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");

    private final String resourceId;
    private final StudentId studentId;
    private final LocalDateTime startDateTime;
    private final LocalDateTime endDateTime;

    /**
     * Creates a {@code Reservation}.
     */
    public Reservation(String resourceId, StudentId studentId,
                       LocalDateTime startDateTime, LocalDateTime endDateTime) {
        requireNonNull(resourceId);
        requireNonNull(studentId);
        requireNonNull(startDateTime);
        requireNonNull(endDateTime);

        checkArgument(isValidResourceId(resourceId), MESSAGE_RESOURCE_ID_CONSTRAINTS);
        checkArgument(!startDateTime.isBefore(LocalDateTime.now()), MESSAGE_START_DATE_TIME_CONSTRAINTS);
        checkArgument(endDateTime.isAfter(startDateTime), MESSAGE_TIME_RANGE_CONSTRAINTS);

        this.resourceId = normalizeResourceId(resourceId);
        this.studentId = studentId;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
    }

    public String getResourceId() {
        return resourceId;
    }

    public StudentId getStudentId() {
        return studentId;
    }

    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    public LocalDateTime getEndDateTime() {
        return endDateTime;
    }

    public String getFormattedStartDateTime() {
        return startDateTime.format(DATE_TIME_FORMATTER);
    }

    public String getFormattedEndDateTime() {
        return endDateTime.format(DATE_TIME_FORMATTER);
    }

    /**
     * Returns true if the reservation time interval overlaps with another reservation.
     */
    public boolean overlapsWith(Reservation other) {
        requireNonNull(other);
        return startDateTime.isBefore(other.endDateTime)
                && endDateTime.isAfter(other.startDateTime);
    }

    /**
     * Returns true if the reservation conflicts with another reservation.
     * Conflict means:
     * - same resource with overlapping time interval, or
     * - same student with overlapping time interval.
     */
    public boolean conflictsWith(Reservation other) {
        requireNonNull(other);

        boolean sameResourceOverlap = resourceId.equals(other.resourceId)
                && overlapsWith(other);

        boolean sameStudentOverlap = studentId.equals(other.studentId)
                && overlapsWith(other);

        return sameResourceOverlap || sameStudentOverlap;
    }

    /**
     * Returns true if the given resource ID is valid.
     */
    public static boolean isValidResourceId(String test) {
        return test != null && normalizeResourceId(test).matches(VALIDATION_REGEX);
    }

    /**
     * Returns the normalized resource ID in uppercase with surrounding whitespace removed.
     */
    public static String normalizeResourceId(String resourceId) {
        requireNonNull(resourceId);
        return resourceId.trim().toUpperCase();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Reservation)) {
            return false;
        }

        Reservation otherReservation = (Reservation) other;
        return resourceId.equals(otherReservation.resourceId)
                && studentId.equals(otherReservation.studentId)
                && startDateTime.equals(otherReservation.startDateTime)
                && endDateTime.equals(otherReservation.endDateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(resourceId, studentId, startDateTime, endDateTime);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("resourceId", resourceId)
                .add("studentId", studentId)
                .add("startDateTime", getFormattedStartDateTime())
                .add("endDateTime", getFormattedEndDateTime())
                .toString();
    }
}

package seedu.address.model.room;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Room's status in the system.
 * Guarantees: immutable; is valid as declared in {@link #isValidStatus(String)}
 */
public enum Status {
    AVAILABLE,
    BOOKED,
    MAINTENANCE;

    public static final String MESSAGE_CONSTRAINTS =
            "Status should only be: Available, Booked, or Maintenance.";

    /**
     * Returns true if a given string is a valid status.
     */
    public static boolean isValidStatus(String test) {
        try {
            valueOf(test.trim().toUpperCase());
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    /**
     * Parses a {@code String status} into a {@code Status}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalArgumentException if the status string is invalid.
     */
    public static Status java_parse(String status) {
        requireNonNull(status);
        String trimmedStatus = status.trim().toUpperCase();
        if (trimmedStatus.isEmpty()) {
            return AVAILABLE;
        }
        checkArgument(isValidStatus(trimmedStatus), MESSAGE_CONSTRAINTS);
        return valueOf(trimmedStatus);
    }

    /**
     * Returns true if the status is Booked.
     */
    public boolean isBooked() {
        return this == BOOKED;
    }

    /**
     * Returns true if the status is Available.
     * Useful for the DeleteRoomCommand safety check.
     */
    public boolean isAvailable() {
        return this == AVAILABLE;
    }

    @Override
    public String toString() {
        String name = name().toLowerCase();
        return name.substring(0, 1).toUpperCase() + name.substring(1);
    }
}

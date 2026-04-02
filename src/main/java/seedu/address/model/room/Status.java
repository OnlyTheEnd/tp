package seedu.address.model.room;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Room's status in the system.
 * Guarantees: immutable; is valid as declared in {@link #isValidStatus(String)}
 */
public class Status {
    public static final String MESSAGE_CONSTRAINTS =
            "Status should be one of the following: Available, Maintenance.";

    public final String value;

    /**
     * Constructs a {@code Status}.
     *
     * @param status A valid status string.
     */
    public Status(String status) {
        requireNonNull(status);
        String formattedStatus = formatStatus(status);
        checkArgument(isValidStatus(formattedStatus), MESSAGE_CONSTRAINTS);
        this.value = formattedStatus;
    }

    /**
     * Formats the status string by trimming, lowering, and capitalizing the first letter.
     * Defaults to "Available" if the input is empty.
     */
    private String formatStatus(String status) {
        String lower = status.trim().toLowerCase();
        if (lower.isEmpty()) {
            return "Available"; // Default as per your spec
        }
        // Capitalize first letter for display consistency
        return lower.substring(0, 1).toUpperCase() + lower.substring(1);
    }

    /**
     * Returns true if a given string is a valid status.
     * Valid statuses are (case-insensitive): available, booked, maintenance.
     */
    public static boolean isValidStatus(String test) {
        String t = test.toLowerCase();
        return t.equals("available") || t.equals("booked") || t.equals("maintenance");
    }

    /**
     * Returns true if the status is Booked.
     */
    public boolean isBooked() {
        return value.equalsIgnoreCase("Booked");
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Status)) {
            return false;
        }

        Status otherStatus = (Status) other;
        return value.equals(otherStatus.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}

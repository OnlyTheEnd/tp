package seedu.address.model.equipment;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents an Equipment's status in the inventory.
 * Guarantees: immutable; is valid as declared in {@link #isValidStatus(String)}
 */
public enum EquipmentStatus {
    AVAILABLE,
    BOOKED,
    MAINTENANCE,
    DAMAGED;

    public static final String MESSAGE_CONSTRAINTS =
            """
                    Status should be one of the following: Available, Maintenance, or Damaged.
                    Example: s/Maintenance
                    """;

    /**
     * Returns true if a given string is a valid status.
     */
    public static boolean isValidStatus(String test) {
        try {
            valueOf(test.toUpperCase());
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    /**
     * Parses a {@code String status} into an {@code EquipmentStatus}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalArgumentException if the status string is invalid.
     */
    public static EquipmentStatus java_parse(String status) {
        requireNonNull(status);
        String trimmedStatus = status.trim().toUpperCase();
        checkArgument(isValidStatus(trimmedStatus), MESSAGE_CONSTRAINTS);
        return valueOf(trimmedStatus);
    }

    @Override
    public String toString() {
        String name = name().toLowerCase();
        return name.substring(0, 1).toUpperCase() + name.substring(1);
    }
}

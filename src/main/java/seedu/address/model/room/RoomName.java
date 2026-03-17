package seedu.address.model.room;

import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Room's name in the system.
 * Guarantees: immutable; is valid as declared in {@link #isValidName(String)}
 */
public class RoomName {
    public static final String MESSAGE_CONSTRAINTS = "Room names should be alphanumeric and not empty.";
    public static final String VALIDATION_REGEX = "[\\p{Alnum} -]+";

    public final String fullName;

    /**
     * Constructs a {@code RoomName}.
     *
     * @param name A valid room name.
     */
    public RoomName(String name) {
        checkArgument(isValidName(name), MESSAGE_CONSTRAINTS);
        this.fullName = name.trim().replaceAll("\\s{2,}", " "); // Collapse multiple spaces
    }

    /**
     * Returns true if a given string is a valid room name.
     *
     * @param test The string to be tested.
     * @return True if the string matches the validation regex.
     */
    public static boolean isValidName(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return fullName;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof RoomName // instanceof handles nulls
                && fullName.equals(((RoomName) other).fullName)); // state check
    }

    @Override
    public int hashCode() {
        return fullName.hashCode();
    }
}

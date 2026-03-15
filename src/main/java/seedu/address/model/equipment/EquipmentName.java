package seedu.address.model.equipment;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Equipment's name in the system.
 * Guarantees: immutable; is valid as declared in {@link #isValidName(String)}
 */
public class EquipmentName {
    public static final String MESSAGE_CONSTRAINTS =
            "Names should only contain alphanumeric characters and spaces, and it should not be blank";

    /*
     * The first character of the name must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} \\-]*";

    public final String fullName;

    /**
     * Constructs a {@code EquipmentName}.
     *
     * @param name A valid equipment name.
     */
    public EquipmentName(String name) {
        requireNonNull(name);
        checkArgument(isValidName(name), MESSAGE_CONSTRAINTS);
        this.fullName = name.trim().replaceAll("\\s{2,}", " ");
    }

    public static boolean isValidName(String test) {
        return test.trim().matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return fullName;
    }

    @Override
    public boolean equals(Object other) {
        return other == this || (other instanceof EquipmentName
                && fullName.equalsIgnoreCase(((EquipmentName) other).fullName));
    }

    @Override
    public int hashCode() {
        return fullName.toLowerCase().hashCode();
    }
}

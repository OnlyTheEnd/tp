package seedu.address.model.equipment;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * Represents a Equipment's name in the system.
 * Guarantees: immutable; is valid as declared in {@link #isValidName(String)}
 */
public class EquipmentName {
    public static final String MESSAGE_CONSTRAINTS =
            """
                    Names should only contain alphanumeric characters and hyphens (-), no spaces allowed, and it should
                    not be blank.
                    Example: n/Wilson-Evolution
                    """;

    /*
     * Alphanumeric characters and hyphens only. No spaces.
     */
    public static final String VALIDATION_REGEX = "[\\p{Alnum}]+(-[\\p{Alnum}]+)*";

    public final String fullName;

    /**
     * Constructs a {@code EquipmentName}.
     *
     * @param name A valid equipment name.
     */
    public EquipmentName(String name) {
        requireNonNull(name);
        checkArgument(isValidName(name), MESSAGE_CONSTRAINTS);
        this.fullName = convertToTitleCase(name.trim());
    }

    private String convertToTitleCase(String name) {
        return Arrays.stream(name.split("-"))
                .map(word -> word.isEmpty() ? ""
                        : Character.toUpperCase(word.charAt(0)) + word.substring(1).toLowerCase())
                .collect(Collectors.joining("-"));
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

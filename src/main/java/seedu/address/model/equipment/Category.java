package seedu.address.model.equipment;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * Represents a Equipment's category in the system.
 * Guarantees: immutable; is valid as declared in {@link #isValidCategory(String)}
 */
public class Category {
    public static final String MESSAGE_CONSTRAINTS =
            """
                    Categories should only contain alphanumeric characters and hyphens, and no spaces.
                    Example: c/Track-&-Field
                    """;
    public static final String VALIDATION_REGEX = "[\\p{Alnum}]+(-[\\p{Alnum}]+)*";
    public final String value;

    /**
     * Constructs a {@code Category}.
     *
     * @param category A valid equipment category.
     */
    public Category(String category) {
        requireNonNull(category);
        checkArgument(isValidCategory(category), MESSAGE_CONSTRAINTS);
        this.value = convertToTitleCase(category.trim());
    }

    private String convertToTitleCase(String name) {
        return Arrays.stream(name.split("-"))
                .map(word -> word.isEmpty() ? ""
                        : Character.toUpperCase(word.charAt(0)) + word.substring(1).toLowerCase())
                .collect(Collectors.joining("-"));
    }

    public static boolean isValidCategory(String test) {
        return test.trim().matches(VALIDATION_REGEX);
    }

    /**
     * Parses a {@code String value} into an {@code Category}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalArgumentException if the category string is invalid.
     */
    public static Category java_parse(String value) {
        requireNonNull(value);
        return new Category(value);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this || (other instanceof Category && value.equals(((Category) other).value));
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}

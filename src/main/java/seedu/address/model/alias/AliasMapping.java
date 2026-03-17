package seedu.address.model.alias;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.Objects;

import seedu.address.commons.util.ToStringBuilder;

/**
 * Represents an alias mapping from a short alias name to a target item/room ID.
 * Guarantees: details are present and not null, and field values are validated.
 */
public class AliasMapping {

    public static final String MESSAGE_ALIAS_NAME_CONSTRAINTS =
            "Alias name should contain only letters, digits, and underscores.";

    public static final String MESSAGE_TARGET_ID_CONSTRAINTS =
            "Target ID should start with a letter and contain only letters, digits, and hyphens.";

    private static final String ALIAS_VALIDATION_REGEX = "[A-Za-z0-9_]+";
    private static final String TARGET_ID_VALIDATION_REGEX = "[A-Za-z][A-Za-z0-9-]*";

    private final String targetId;
    private final String aliasName;

    /**
     * Constructs an {@code AliasMapping}.
     */
    public AliasMapping(String targetId, String aliasName) {
        requireNonNull(targetId);
        requireNonNull(aliasName);

        checkArgument(isValidTargetId(targetId), MESSAGE_TARGET_ID_CONSTRAINTS);
        checkArgument(isValidAliasName(aliasName), MESSAGE_ALIAS_NAME_CONSTRAINTS);

        this.targetId = normalizeTargetId(targetId);
        this.aliasName = normalizeAliasName(aliasName);
    }

    public String getTargetId() {
        return targetId;
    }

    public String getAliasName() {
        return aliasName;
    }

    /**
     * Returns true if {@code test} is a valid alias name.
     */
    public static boolean isValidAliasName(String test) {
        return test != null && normalizeAliasName(test).matches(ALIAS_VALIDATION_REGEX);
    }

    /**
     * Returns true if {@code test} is a valid target ID.
     */
    public static boolean isValidTargetId(String test) {
        return test != null && normalizeTargetId(test).matches(TARGET_ID_VALIDATION_REGEX);
    }

    /**
     * Returns a normalized alias name.
     */
    public static String normalizeAliasName(String aliasName) {
        requireNonNull(aliasName);
        return aliasName.trim().toLowerCase();
    }

    /**
     * Returns a normalized target ID.
     */
    public static String normalizeTargetId(String targetId) {
        requireNonNull(targetId);
        return targetId.trim().toUpperCase();
    }

    /**
     * Returns true if both alias mappings have the same alias name.
     */
    public boolean isSameAlias(AliasMapping other) {
        requireNonNull(other);
        return aliasName.equals(other.aliasName);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof AliasMapping)) {
            return false;
        }
        AliasMapping otherAlias = (AliasMapping) other;
        return targetId.equals(otherAlias.targetId)
                && aliasName.equals(otherAlias.aliasName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(targetId, aliasName);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetId", targetId)
                .add("aliasName", aliasName)
                .toString();
    }
}

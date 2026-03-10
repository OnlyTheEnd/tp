package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.alias.AliasMapping;

/**
 * Jackson-friendly version of {@link AliasMapping}.
 */
class JsonAdaptedAliasMapping {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Alias mapping's %s field is missing!";

    private final String targetId;
    private final String aliasName;

    @JsonCreator
    public JsonAdaptedAliasMapping(@JsonProperty("targetId") String targetId,
                                   @JsonProperty("aliasName") String aliasName) {
        this.targetId = targetId;
        this.aliasName = aliasName;
    }

    public JsonAdaptedAliasMapping(AliasMapping source) {
        targetId = source.getTargetId();
        aliasName = source.getAliasName();
    }

    public AliasMapping toModelType() throws IllegalValueException {
        if (targetId == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "targetId"));
        }
        if (!AliasMapping.isValidTargetId(targetId)) {
            throw new IllegalValueException(AliasMapping.MESSAGE_TARGET_ID_CONSTRAINTS);
        }

        if (aliasName == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "aliasName"));
        }
        if (!AliasMapping.isValidAliasName(aliasName)) {
            throw new IllegalValueException(AliasMapping.MESSAGE_ALIAS_NAME_CONSTRAINTS);
        }

        return new AliasMapping(targetId, aliasName);
    }
}

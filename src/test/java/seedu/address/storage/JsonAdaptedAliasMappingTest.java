package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.alias.AliasMapping;

/**
 * Tests for {@link JsonAdaptedAliasMapping}.
 */
public class JsonAdaptedAliasMappingTest {

    private static final String VALID_TARGET_ID = "Wilson-Evolution-Basketball-1";
    private static final String VALID_ALIAS_NAME = "b1";

    private static final String INVALID_TARGET_ID = "!invalid";
    private static final String INVALID_ALIAS_NAME = "b-1";

    private static final AliasMapping VALID_ALIAS_MAPPING =
            new AliasMapping(VALID_TARGET_ID, VALID_ALIAS_NAME);

    @Test
    public void toModelType_validAliasMappingDetails_returnsAliasMapping() throws Exception {
        JsonAdaptedAliasMapping aliasMapping =
                new JsonAdaptedAliasMapping(VALID_TARGET_ID, VALID_ALIAS_NAME);

        assertEquals(VALID_ALIAS_MAPPING, aliasMapping.toModelType());
    }

    @Test
    public void toModelType_nullTargetId_throwsIllegalValueException() {
        JsonAdaptedAliasMapping aliasMapping =
                new JsonAdaptedAliasMapping(null, VALID_ALIAS_NAME);

        assertThrows(IllegalValueException.class,
                String.format(JsonAdaptedAliasMapping.MISSING_FIELD_MESSAGE_FORMAT, "targetId"),
                aliasMapping::toModelType);
    }

    @Test
    public void toModelType_invalidTargetId_throwsIllegalValueException() {
        JsonAdaptedAliasMapping aliasMapping =
                new JsonAdaptedAliasMapping(INVALID_TARGET_ID, VALID_ALIAS_NAME);

        assertThrows(IllegalValueException.class,
                AliasMapping.MESSAGE_TARGET_ID_CONSTRAINTS,
                aliasMapping::toModelType);
    }

    @Test
    public void toModelType_nullAliasName_throwsIllegalValueException() {
        JsonAdaptedAliasMapping aliasMapping =
                new JsonAdaptedAliasMapping(VALID_TARGET_ID, null);

        assertThrows(IllegalValueException.class,
                String.format(JsonAdaptedAliasMapping.MISSING_FIELD_MESSAGE_FORMAT, "aliasName"),
                aliasMapping::toModelType);
    }

    @Test
    public void toModelType_invalidAliasName_throwsIllegalValueException() {
        JsonAdaptedAliasMapping aliasMapping =
                new JsonAdaptedAliasMapping(VALID_TARGET_ID, INVALID_ALIAS_NAME);

        assertThrows(IllegalValueException.class,
                AliasMapping.MESSAGE_ALIAS_NAME_CONSTRAINTS,
                aliasMapping::toModelType);
    }

    @Test
    public void constructor_fromModelType_validAliasMapping() throws Exception {
        JsonAdaptedAliasMapping adapted = new JsonAdaptedAliasMapping(VALID_ALIAS_MAPPING);

        assertEquals(VALID_ALIAS_MAPPING, adapted.toModelType());
    }
}

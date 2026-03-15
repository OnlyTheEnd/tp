package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.equipment.EquipmentName;

public class JsonAdaptedEquipmentTest {
    private static final String INVALID_NAME = "R@cket";
    private static final String INVALID_STATUS = "broken";

    private static final String VALID_NAME = "Wilson-Basketball";
    private static final String VALID_CATEGORY = "Basketball";
    private static final String VALID_STATUS = "Available";

    @Test
    public void toModelType_validEquipmentDetails_returnsEquipment() throws Exception {
        JsonAdaptedEquipment equipment = new JsonAdaptedEquipment(VALID_NAME, VALID_CATEGORY, VALID_STATUS);
        assertEquals(new EquipmentName(VALID_NAME), equipment.toModelType().getName());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedEquipment equipment = new JsonAdaptedEquipment(INVALID_NAME, VALID_CATEGORY, VALID_STATUS);
        assertThrows(IllegalValueException.class, equipment::toModelType);
    }

    @Test
    public void toModelType_invalidStatus_throwsIllegalValueException() {
        JsonAdaptedEquipment equipment = new JsonAdaptedEquipment(VALID_NAME, VALID_CATEGORY, INVALID_STATUS);
        assertThrows(IllegalValueException.class, equipment::toModelType);
    }
}

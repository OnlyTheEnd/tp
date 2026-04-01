package seedu.address.model.equipment;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class EquipmentNameTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new EquipmentName(null));
    }

    @Test
    public void constructor_invalidName_throwsIllegalArgumentException() {
        String invalidName = "";
        assertThrows(IllegalArgumentException.class, () -> new EquipmentName(invalidName));
    }

    @Test
    public void isValidName() {
        assertThrows(NullPointerException.class, () -> EquipmentName.isValidName(null));

        assertFalse(EquipmentName.isValidName("")); // empty string
        assertFalse(EquipmentName.isValidName(" ")); // spaces only
        assertFalse(EquipmentName.isValidName("^")); // only non-alphanumeric
        assertFalse(EquipmentName.isValidName("wilson basketball")); // with whitespace

        assertTrue(EquipmentName.isValidName("12345")); // numbers only
        assertTrue(EquipmentName.isValidName("wilson-evolution-basketball")); // with hyphens
        assertTrue(EquipmentName.isValidName("Basketball-2")); // alphanumeric characters
    }
}

package seedu.address.model.equipment;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class EquipmentTest {

    @Test
    public void isSameEquipment() {
        Equipment basketball = new Equipment(new EquipmentName("Wilson-Evolution"),
                "Basketball", EquipmentStatus.AVAILABLE);

        assertTrue(basketball.isSameEquipment(basketball));

        assertFalse(basketball.isSameEquipment(null));

        Equipment bookedBasketball = new Equipment(new EquipmentName("Wilson-Evolution"),
                "Basketball", EquipmentStatus.BOOKED);
        assertTrue(basketball.isSameEquipment(bookedBasketball));

        Equipment differentName = new Equipment(new EquipmentName("Molten"), "Basketball", EquipmentStatus.AVAILABLE);
        assertFalse(basketball.isSameEquipment(differentName));
    }

    @Test
    public void equals() {
        Equipment basketball = new Equipment(new EquipmentName("Wilson-Evolution"),
                "Basketball", EquipmentStatus.AVAILABLE);

        assertTrue(basketball.equals(new Equipment(new EquipmentName("Wilson-Evolution"),
                "Basketball", EquipmentStatus.AVAILABLE)));

        assertFalse(basketball.equals(new Equipment(new EquipmentName("Wilson-Evolution"),
                "Basketball", EquipmentStatus.BOOKED)));
    }
}

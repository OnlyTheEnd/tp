package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.equipment.Category;
import seedu.address.model.equipment.Equipment;
import seedu.address.model.equipment.EquipmentName;
import seedu.address.model.equipment.EquipmentStatus;

/**
 * A utility class containing a list of {@code Equipment} objects to be used in tests.
 */
public class TypicalEquipments {

    public static final Equipment BASKETBALL = new Equipment(
            new EquipmentName("Wilson-Evolution"), new Category("Basketball"), EquipmentStatus.AVAILABLE);
    public static final Equipment RACKET = new Equipment(
            new EquipmentName("Yonex-Astrox"), new Category("Badminton"), EquipmentStatus.BOOKED);
    public static final Equipment NET = new Equipment(
            new EquipmentName("Tchoukball-Frame"), new Category("Tchoukball"), EquipmentStatus.AVAILABLE);

    private TypicalEquipments() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical equipments.
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        for (Equipment equipment : getTypicalEquipments()) {
            ab.addEquipment(equipment);
        }
        return ab;
    }

    public static List<Equipment> getTypicalEquipments() {
        return new ArrayList<>(Arrays.asList(BASKETBALL, RACKET, NET));
    }
}

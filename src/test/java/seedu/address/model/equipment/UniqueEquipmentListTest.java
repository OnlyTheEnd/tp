package seedu.address.model.equipment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalEquipments.BASKETBALL;
import static seedu.address.testutil.TypicalEquipments.RACKET;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.equipment.exceptions.DuplicateEquipmentException;
import seedu.address.model.equipment.exceptions.EquipmentNotFoundException;

public class UniqueEquipmentListTest {
    private final UniqueEquipmentList uniqueEquipmentList = new UniqueEquipmentList();
    private final Equipment basketball = new Equipment(new EquipmentName("Wilson-Evolution"),
            new Category("Basketball"), EquipmentStatus.AVAILABLE);

    @Test
    public void add_duplicateEquipment_throwsDuplicateEquipmentException() {
        uniqueEquipmentList.add(basketball);
        assertThrows(DuplicateEquipmentException.class, () -> uniqueEquipmentList.add(basketball));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, ()
                -> uniqueEquipmentList.asUnmodifiableObservableList().remove(0));
    }

    @Test
    public void setEquipments_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueEquipmentList.setEquipments(null));
    }

    @Test
    public void setEquipments_uniqueList_replacesOwnListWithProvidedList() {
        uniqueEquipmentList.add(BASKETBALL);
        List<Equipment> equipmentList = Collections.singletonList(RACKET);
        uniqueEquipmentList.setEquipments(equipmentList);
        UniqueEquipmentList expectedUniqueEquipmentList = new UniqueEquipmentList();
        expectedUniqueEquipmentList.add(RACKET);
        assertEquals(expectedUniqueEquipmentList, uniqueEquipmentList);
    }

    @Test
    public void setEquipments_listWithDuplicateEquipments_throwsDuplicateEquipmentException() {
        List<Equipment> listWithDuplicates = Arrays.asList(BASKETBALL, BASKETBALL);
        assertThrows(DuplicateEquipmentException.class, () -> uniqueEquipmentList.setEquipments(listWithDuplicates));
    }

    @Test
    public void remove_nullEquipment_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueEquipmentList.remove(null));
    }

    @Test
    public void remove_equipmentDoesNotExist_throwsEquipmentNotFoundException() {
        assertThrows(EquipmentNotFoundException.class, () -> uniqueEquipmentList.remove(BASKETBALL));
    }

    @Test
    public void remove_existingEquipment_removesEquipment() {
        uniqueEquipmentList.add(BASKETBALL);
        uniqueEquipmentList.remove(BASKETBALL);
        UniqueEquipmentList expectedUniqueEquipmentList = new UniqueEquipmentList();
        assertEquals(expectedUniqueEquipmentList, uniqueEquipmentList);
    }

    @Test
    public void iterator_containsAllEquipments_success() {
        uniqueEquipmentList.add(BASKETBALL);
        uniqueEquipmentList.add(RACKET);

        List<Equipment> expectedList = Arrays.asList(BASKETBALL, RACKET);
        List<Equipment> actualList = new ArrayList<>();

        uniqueEquipmentList.iterator().forEachRemaining(actualList::add);

        assertEquals(expectedList, actualList);
    }

    @Test
    public void equals() {
        uniqueEquipmentList.add(BASKETBALL);

        assertTrue(uniqueEquipmentList.equals(uniqueEquipmentList));

        UniqueEquipmentList copy = new UniqueEquipmentList();
        copy.add(BASKETBALL);
        assertTrue(uniqueEquipmentList.equals(copy));

        assertFalse(uniqueEquipmentList.equals(null));

        assertFalse(uniqueEquipmentList.equals(new ArrayList<>()));

        UniqueEquipmentList different = new UniqueEquipmentList();
        different.add(RACKET);
        assertFalse(uniqueEquipmentList.equals(different));
    }

    @Test
    public void hashCode_test() {
        uniqueEquipmentList.add(BASKETBALL);
        UniqueEquipmentList copy = new UniqueEquipmentList();
        copy.add(BASKETBALL);

        assertEquals(uniqueEquipmentList.hashCode(), copy.hashCode());

        UniqueEquipmentList different = new UniqueEquipmentList();
        different.add(RACKET);
        assertNotEquals(uniqueEquipmentList.hashCode(), different.hashCode());
    }
}

package seedu.address.model.equipment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalEquipments.BASKETBALL;
import static seedu.address.testutil.TypicalEquipments.RACKET;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.equipment.exceptions.DuplicateEquipmentException;

public class UniqueEquipmentListTest {
    private final UniqueEquipmentList uniqueEquipmentList = new UniqueEquipmentList();
    private final Equipment basketball = new Equipment(new EquipmentName("Wilson-Evolution"),
            "Basketball", EquipmentStatus.AVAILABLE);

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
}

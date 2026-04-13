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
import seedu.address.model.tag.exceptions.DuplicateTagException;

public class UniqueEquipmentListTest {
    private final UniqueEquipmentList uniqueEquipmentList = new UniqueEquipmentList();
    private final Equipment basketball = new Equipment(new EquipmentName("Wilson-Evolution"),
            new Category("Basketball"), EquipmentStatus.AVAILABLE);
    private final String validTag = "VALID";
    private final String validTag2 = "VALID2";
    private final String invalidTag = "";

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

    @Test
    public void addEquipmentTagTest_success() {
        Equipment laptop = new Equipment(new EquipmentName("Laptop"));
        uniqueEquipmentList.add(laptop);

        uniqueEquipmentList.addEquipmentTag(laptop, "working");

        // Verify tag was added
        assertTrue(laptop.getTags().stream()
                .anyMatch(tag -> tag.tagName.equals("WORKING")));
        assertEquals(1, laptop.getTags().size());


    }

    @Test
    public void addEquipmentTag_failure() {
        Equipment laptop = new Equipment(new EquipmentName("Laptop"));
        uniqueEquipmentList.add(laptop);

        uniqueEquipmentList.addEquipmentTag(laptop, "working");
        uniqueEquipmentList.addEquipmentTag(laptop, "new");
        uniqueEquipmentList.addEquipmentTag(laptop, "available");

        assertEquals(3, laptop.getTags().size());
    }

    @Test
    public void addEquipmentTag_duplicateTag_throwsDuplicateTagException() {
        Equipment laptop = new Equipment(new EquipmentName("Laptop"));
        uniqueEquipmentList.add(laptop);

        uniqueEquipmentList.addEquipmentTag(laptop, "working");

        // Adding same tag again should throw exception
        assertThrows(DuplicateTagException.class, () ->
                uniqueEquipmentList.addEquipmentTag(laptop, "working"));
    }

    @Test
    public void addEquipmentTag_duplicateTagCaseInsensitive_throwsDuplicateTagException() {
        Equipment laptop = new Equipment(new EquipmentName("Laptop"));
        uniqueEquipmentList.add(laptop);

        uniqueEquipmentList.addEquipmentTag(laptop, "working");

        // Adding same tag with different case should throw exception
        assertThrows(DuplicateTagException.class, () ->
                uniqueEquipmentList.addEquipmentTag(laptop, "WORKING"));

        assertThrows(DuplicateTagException.class, () ->
                uniqueEquipmentList.addEquipmentTag(laptop, "Working"));
    }

    @Test
    public void addEquipmentTag_equipmentNotInList_throwsEquipmentNotFoundException() {
        Equipment laptop = new Equipment(new EquipmentName("Laptop"));
        Equipment projector = new Equipment(new EquipmentName("Projector"));

        uniqueEquipmentList.add(laptop);

        // Adding tag to equipment not in list should throw exception
        assertThrows(EquipmentNotFoundException.class, () ->
                uniqueEquipmentList.addEquipmentTag(projector, "working"));
    }

    @Test
    public void deleteEquipmentTag_validEquipmentAndTag_success() {
        Equipment laptop = new Equipment(new EquipmentName("Laptop"));
        uniqueEquipmentList.add(laptop);
        uniqueEquipmentList.addEquipmentTag(laptop, "working");

        uniqueEquipmentList.deleteEquipmentTag(laptop, "working");

        // Verify tag was removed
        assertEquals(0, laptop.getTags().size());
    }

    @Test
    public void deleteEquipmentTag_oneOfMultipleTags_success() {
        Equipment laptop = new Equipment(new EquipmentName("Laptop"));
        uniqueEquipmentList.add(laptop);
        uniqueEquipmentList.addEquipmentTag(laptop, "working");
        uniqueEquipmentList.addEquipmentTag(laptop, "new");
        uniqueEquipmentList.addEquipmentTag(laptop, "available");

        uniqueEquipmentList.deleteEquipmentTag(laptop, "new");

        assertEquals(2, laptop.getTags().size());
        assertTrue(laptop.getTags().stream()
                .anyMatch(tag -> tag.tagName.equals("WORKING")));
        assertTrue(laptop.getTags().stream()
                .anyMatch(tag -> tag.tagName.equals("AVAILABLE")));
        assertTrue(laptop.getTags().stream()
                .noneMatch(tag -> tag.tagName.equals("NEW")));
    }

}

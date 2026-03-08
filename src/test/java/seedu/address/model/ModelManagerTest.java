package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.GuiSettings;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.StudentId;
import seedu.address.model.reservation.Reservation;
import seedu.address.testutil.AddressBookBuilder;

public class ModelManagerTest {

    private static final Reservation HALL_TWO_SLOT_ONE = new Reservation("Hall-2", new StudentId("a1234567a"),
            LocalDateTime.of(2026, 3, 1, 14, 0),
            LocalDateTime.of(2026, 3, 1, 16, 0));

    private static final Reservation HALL_TWO_CONFLICTING = new Reservation("Hall-2", new StudentId("a2345678b"),
            LocalDateTime.of(2026, 3, 1, 15, 0),
            LocalDateTime.of(2026, 3, 1, 17, 0));

    private static final Reservation HALL_TWO_ADJACENT = new Reservation("Hall-2", new StudentId("a2345678b"),
            LocalDateTime.of(2026, 3, 1, 16, 0),
            LocalDateTime.of(2026, 3, 1, 18, 0));

    private static final Reservation MPSH_ONE_SAME_TIME = new Reservation("MPSH-1", new StudentId("a2345678b"),
            LocalDateTime.of(2026, 3, 1, 14, 0),
            LocalDateTime.of(2026, 3, 1, 16, 0));

    private ModelManager modelManager = new ModelManager();

    @Test
    public void constructor() {
        assertEquals(new UserPrefs(), modelManager.getUserPrefs());
        assertEquals(new GuiSettings(), modelManager.getGuiSettings());
        assertEquals(new AddressBook(), new AddressBook(modelManager.getAddressBook()));
        assertEquals(0, modelManager.getReservationList().size());
    }

    @Test
    public void setUserPrefs_nullUserPrefs_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setUserPrefs(null));
    }

    @Test
    public void setUserPrefs_validUserPrefs_copiesUserPrefs() {
        UserPrefs userPrefs = new UserPrefs();
        userPrefs.setAddressBookFilePath(Paths.get("address/book/file/path"));
        userPrefs.setGuiSettings(new GuiSettings(1, 2, 3, 4));
        modelManager.setUserPrefs(userPrefs);
        assertEquals(userPrefs, modelManager.getUserPrefs());

        UserPrefs oldUserPrefs = new UserPrefs(userPrefs);
        userPrefs.setAddressBookFilePath(Paths.get("new/address/book/file/path"));
        assertEquals(oldUserPrefs, modelManager.getUserPrefs());
    }

    @Test
    public void setGuiSettings_nullGuiSettings_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setGuiSettings(null));
    }

    @Test
    public void setGuiSettings_validGuiSettings_setsGuiSettings() {
        GuiSettings guiSettings = new GuiSettings(1, 2, 3, 4);
        modelManager.setGuiSettings(guiSettings);
        assertEquals(guiSettings, modelManager.getGuiSettings());
    }

    @Test
    public void setAddressBookFilePath_nullPath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setAddressBookFilePath(null));
    }

    @Test
    public void setAddressBookFilePath_validPath_setsAddressBookFilePath() {
        Path path = Paths.get("address/book/file/path");
        modelManager.setAddressBookFilePath(path);
        assertEquals(path, modelManager.getAddressBookFilePath());
    }

    @Test
    public void hasPerson_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.hasPerson(null));
    }

    @Test
    public void hasPerson_personNotInAddressBook_returnsFalse() {
        assertFalse(modelManager.hasPerson(ALICE));
    }

    @Test
    public void hasPerson_personInAddressBook_returnsTrue() {
        modelManager.addPerson(ALICE);
        assertTrue(modelManager.hasPerson(ALICE));
    }

    @Test
    public void hasStudentId_nullStudentId_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.hasStudentId(null));
    }

    @Test
    public void hasStudentId_studentIdNotInAddressBook_returnsFalse() {
        assertFalse(modelManager.hasStudentId(ALICE.getStudentId()));
    }

    @Test
    public void hasStudentId_studentIdInAddressBook_returnsTrue() {
        modelManager.addPerson(ALICE);
        assertTrue(modelManager.hasStudentId(ALICE.getStudentId()));
    }

    @Test
    public void hasReservableItem_nullResourceId_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.hasReservableItem(null));
    }

    @Test
    public void hasReservableItem_validResource_returnsTrue() {
        assertTrue(modelManager.hasReservableItem("Hall-2"));
        assertTrue(modelManager.hasReservableItem("hall-2"));
    }

    @Test
    public void hasReservableItem_invalidResource_returnsFalse() {
        assertFalse(modelManager.hasReservableItem("Invalid-Room"));
    }

    @Test
    public void hasConflictingReservation_nullReservation_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.hasConflictingReservation(null));
    }

    @Test
    public void hasConflictingReservation_conflictingReservation_returnsTrue() {
        modelManager.addReservation(HALL_TWO_SLOT_ONE);
        assertTrue(modelManager.hasConflictingReservation(HALL_TWO_CONFLICTING));
    }

    @Test
    public void hasConflictingReservation_nonConflictingReservation_returnsFalse() {
        modelManager.addReservation(HALL_TWO_SLOT_ONE);
        assertFalse(modelManager.hasConflictingReservation(HALL_TWO_ADJACENT));
        assertFalse(modelManager.hasConflictingReservation(MPSH_ONE_SAME_TIME));
    }

    @Test
    public void getConflictingReservation_conflictingReservation_returnsReservation() {
        modelManager.addReservation(HALL_TWO_SLOT_ONE);
        assertEquals(Optional.of(HALL_TWO_SLOT_ONE),
                modelManager.getConflictingReservation(HALL_TWO_CONFLICTING));
    }

    @Test
    public void getConflictingReservation_nonConflictingReservation_returnsEmpty() {
        modelManager.addReservation(HALL_TWO_SLOT_ONE);
        assertEquals(Optional.empty(), modelManager.getConflictingReservation(HALL_TWO_ADJACENT));
    }

    @Test
    public void addReservation_validReservation_success() {
        modelManager.addReservation(HALL_TWO_SLOT_ONE);
        assertTrue(modelManager.getReservationList().contains(HALL_TWO_SLOT_ONE));
    }

    @Test
    public void getFilteredPersonList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> modelManager.getFilteredPersonList().remove(0));
    }

    @Test
    public void getReservationList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> modelManager.getReservationList().remove(0));
    }

    @Test
    public void equals() {
        AddressBook addressBook = new AddressBookBuilder().withPerson(ALICE).withPerson(BENSON).build();
        AddressBook differentAddressBook = new AddressBook();
        AddressBook addressBookWithReservation = new AddressBook(addressBook);
        addressBookWithReservation.addReservation(HALL_TWO_SLOT_ONE);
        UserPrefs userPrefs = new UserPrefs();

        modelManager = new ModelManager(addressBook, userPrefs);
        ModelManager modelManagerCopy = new ModelManager(addressBook, userPrefs);
        assertTrue(modelManager.equals(modelManagerCopy));

        assertTrue(modelManager.equals(modelManager));
        assertFalse(modelManager.equals(null));
        assertFalse(modelManager.equals(5));

        assertFalse(modelManager.equals(new ModelManager(differentAddressBook, userPrefs)));
        assertFalse(modelManager.equals(new ModelManager(addressBookWithReservation, userPrefs)));

        String[] keywords = ALICE.getName().fullName.split("\\s+");
        modelManager.updateFilteredPersonList(new NameContainsKeywordsPredicate(Arrays.asList(keywords)));
        assertFalse(modelManager.equals(new ModelManager(addressBook, userPrefs)));

        modelManager.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setAddressBookFilePath(Paths.get("differentFilePath"));
        assertFalse(modelManager.equals(new ModelManager(addressBook, differentUserPrefs)));
    }
}

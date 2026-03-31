package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalRooms.ROOM_A;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.GuiSettings;
import seedu.address.model.alias.AliasMapping;
import seedu.address.model.equipment.Equipment;
import seedu.address.model.equipment.EquipmentStatus;
import seedu.address.model.issue.IssueRecord;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.StudentId;
import seedu.address.model.reservation.Reservation;
import seedu.address.model.room.Room;
import seedu.address.model.room.Status;
import seedu.address.testutil.AddressBookBuilder;
import seedu.address.testutil.EquipmentBuilder;
import seedu.address.testutil.RoomBuilder;

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

    private static final IssueRecord HALL_TWO_ISSUE = new IssueRecord("Wilson-Evolution-Basketball-1",
            new StudentId("a1234567a"),
            LocalDateTime.of(2099, 3, 15, 17, 0));
    private static final AliasMapping BASKETBALL_ALIAS =
            new AliasMapping("Wilson-Evolution-Basketball-1", "b1");
    private static final Reservation HALL_TWO_SLOT_TWO = new Reservation("Hall-2", new StudentId("a2345678b"),
            LocalDateTime.of(2099, 3, 15, 16, 0),
            LocalDateTime.of(2099, 3, 15, 18, 0));

    private static final Reservation BASKETBALL_RESERVATION = new Reservation("Wilson-Evolution-Basketball-1",
            new StudentId("a2345678b"),
            LocalDateTime.of(2099, 3, 16, 9, 0),
            LocalDateTime.of(2099, 3, 16, 11, 0));

    private static final IssueRecord BASKETBALL_ISSUE = new IssueRecord("Wilson-Evolution-Basketball-1",
            new StudentId("a1234567a"),
            LocalDateTime.of(2099, 3, 17, 17, 0));
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
    public void hasRoom_nullRoom_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.hasRoom(null));
    }

    @Test
    public void hasRoom_roomNotInAddressBook_returnsFalse() {
        assertFalse(modelManager.hasRoom(ROOM_A));
    }

    @Test
    public void hasRoom_roomInAddressBook_returnsTrue() {
        modelManager.addRoom(ROOM_A);
        assertTrue(modelManager.hasRoom(ROOM_A));
    }

    @Test
    public void getFilteredRoomList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> modelManager.getFilteredRoomList().remove(0));
    }

    @Test
    public void getReservationList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> modelManager.getReservationList().remove(0));
    }

    @Test
    public void hasIssuedItem_nullItemId_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.hasIssuedItem(null));
    }

    @Test
    public void hasIssuedItem_itemNotIssued_returnsFalse() {
        assertFalse(modelManager.hasIssuedItem("Wilson-Evolution-Basketball-1"));
    }

    @Test
    public void hasIssuedItem_itemIssued_returnsTrue() {
        modelManager.addIssueRecord(HALL_TWO_ISSUE);
        assertTrue(modelManager.hasIssuedItem(HALL_TWO_ISSUE.getItemId()));
    }

    @Test
    public void getIssueRecordByItemId_nullItemId_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.getIssueRecordByItemId(null));
    }

    @Test
    public void getIssueRecordByItemId_itemNotIssued_returnsEmpty() {
        assertEquals(Optional.empty(), modelManager.getIssueRecordByItemId("Wilson-Evolution-Basketball-1"));
    }

    @Test
    public void addIssueRecord_nullIssueRecord_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.addIssueRecord(null));
    }

    @Test
    public void hasIssuableItem_nullItemId_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.hasIssuableItem(null));
    }

    @Test
    public void hasIssuableItem_invalidItem_returnsFalse() {
        assertFalse(modelManager.hasIssuableItem("Invalid-Item"));
    }

    @Test
    public void hasAliasableTarget_nullTargetId_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.hasAliasableTarget(null));
    }

    @Test
    public void hasAliasableTarget_validRoom_returnsTrue() {
        assertTrue(modelManager.hasAliasableTarget("Hall-2"));
    }

    @Test
    public void hasAliasableTarget_validItem_returnsTrue() {
        assertTrue(modelManager.hasAliasableTarget("Wilson-Evolution-Basketball-1"));
    }

    @Test
    public void hasAliasableTarget_invalidTarget_returnsFalse() {
        assertFalse(modelManager.hasAliasableTarget("Invalid-Target"));
    }

    @Test
    public void hasAliasName_nullAliasName_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.hasAliasName(null));
    }

    @Test
    public void hasAliasName_aliasNotPresent_returnsFalse() {
        assertFalse(modelManager.hasAliasName("b1"));
    }

    @Test
    public void hasAliasName_aliasPresent_returnsTrue() {
        modelManager.addAliasMapping(BASKETBALL_ALIAS);
        assertTrue(modelManager.hasAliasName("b1"));
    }

    @Test
    public void getAliasMappingByName_nullAliasName_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.getAliasMappingByName(null));
    }

    @Test
    public void getAliasMappingByName_aliasNotPresent_returnsEmpty() {
        assertEquals(Optional.empty(), modelManager.getAliasMappingByName("b1"));
    }

    @Test
    public void getAliasMappingByName_aliasPresent_returnsAliasMapping() {
        modelManager.addAliasMapping(BASKETBALL_ALIAS);
        assertEquals(Optional.of(BASKETBALL_ALIAS), modelManager.getAliasMappingByName("b1"));
    }

    @Test
    public void addAliasMapping_nullAliasMapping_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.addAliasMapping(null));
    }

    @Test
    public void addAliasMapping_validAliasMapping_success() {
        modelManager.addAliasMapping(BASKETBALL_ALIAS);
        assertTrue(modelManager.getAliasMappingList().contains(BASKETBALL_ALIAS));
    }

    @Test
    public void getAliasMappingList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> modelManager.getAliasMappingList().remove(0));
    }

    @Test
    public void resolveAlias_nullInput_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.resolveAlias(null));
    }

    @Test
    public void resolveAlias_aliasExists_returnsTargetId() {
        modelManager.addAliasMapping(BASKETBALL_ALIAS);
        assertEquals("WILSON-EVOLUTION-BASKETBALL-1", modelManager.resolveAlias("b1"));
    }

    @Test
    public void resolveAlias_aliasDoesNotExist_returnsOriginalInput() {
        assertEquals("Hall-2", modelManager.resolveAlias("Hall-2"));
    }

    @Test
    public void addReservation_roomResource_updatesRoomStatusToBooked() {
        Room hallTwo = new RoomBuilder()
                .withName("Hall-2")
                .withLocation("Sports-Centre")
                .withStatus("Available")
                .build();
        modelManager.addRoom(hallTwo);

        modelManager.addReservation(HALL_TWO_SLOT_ONE);

        Room updatedRoom = findRoomByName("Hall-2");
        assertEquals(new Status("Booked"), updatedRoom.getStatus());
    }

    @Test
    public void removeReservation_onlyReservation_updatesRoomStatusToAvailable() {
        Room hallTwo = new RoomBuilder()
                .withName("Hall-2")
                .withLocation("Sports-Centre")
                .withStatus("Available")
                .build();
        modelManager.addRoom(hallTwo);
        modelManager.addReservation(HALL_TWO_SLOT_ONE);

        modelManager.removeReservation(HALL_TWO_SLOT_ONE);

        Room updatedRoom = findRoomByName("Hall-2");
        assertEquals(new Status("Available"), updatedRoom.getStatus());
    }

    @Test
    public void removeReservation_otherReservationStillExists_roomRemainsBooked() {
        Room hallTwo = new RoomBuilder()
                .withName("Hall-2")
                .withLocation("Sports-Centre")
                .withStatus("Available")
                .build();
        modelManager.addRoom(hallTwo);
        modelManager.addReservation(HALL_TWO_SLOT_ONE);
        modelManager.addReservation(HALL_TWO_SLOT_TWO);

        modelManager.removeReservation(HALL_TWO_SLOT_ONE);

        Room updatedRoom = findRoomByName("Hall-2");
        assertEquals(new Status("Booked"), updatedRoom.getStatus());
    }

    @Test
    public void addReservation_equipmentResource_updatesEquipmentStatusToBooked() {
        Equipment basketball = new EquipmentBuilder()
                .withName("Wilson-Evolution-Basketball-1")
                .withCategory("Basketball")
                .withStatus(EquipmentStatus.AVAILABLE)
                .build();
        modelManager.addEquipment(basketball);

        modelManager.addReservation(BASKETBALL_RESERVATION);

        Equipment updatedEquipment = findEquipmentByName("Wilson-Evolution-Basketball-1");
        assertEquals(EquipmentStatus.BOOKED, updatedEquipment.getStatus());
    }

    @Test
    public void addIssueRecord_equipmentResource_updatesEquipmentStatusToBooked() {
        Equipment basketball = new EquipmentBuilder()
                .withName("Wilson-Evolution-Basketball-1")
                .withCategory("Basketball")
                .withStatus(EquipmentStatus.AVAILABLE)
                .build();
        modelManager.addEquipment(basketball);

        modelManager.addIssueRecord(BASKETBALL_ISSUE);

        Equipment updatedEquipment = findEquipmentByName("Wilson-Evolution-Basketball-1");
        assertEquals(EquipmentStatus.BOOKED, updatedEquipment.getStatus());
    }

    @Test
    public void removeIssueRecord_noOtherBooking_updatesEquipmentStatusToAvailable() {
        Equipment basketball = new EquipmentBuilder()
                .withName("Wilson-Evolution-Basketball-1")
                .withCategory("Basketball")
                .withStatus(EquipmentStatus.AVAILABLE)
                .build();
        modelManager.addEquipment(basketball);
        modelManager.addIssueRecord(BASKETBALL_ISSUE);

        modelManager.removeIssueRecord(BASKETBALL_ISSUE);

        Equipment updatedEquipment = findEquipmentByName("Wilson-Evolution-Basketball-1");
        assertEquals(EquipmentStatus.AVAILABLE, updatedEquipment.getStatus());
    }

    @Test
    public void removeIssueRecord_reservationStillExists_equipmentRemainsBooked() {
        Equipment basketball = new EquipmentBuilder()
                .withName("Wilson-Evolution-Basketball-1")
                .withCategory("Basketball")
                .withStatus(EquipmentStatus.AVAILABLE)
                .build();
        modelManager.addEquipment(basketball);
        modelManager.addReservation(BASKETBALL_RESERVATION);
        modelManager.addIssueRecord(BASKETBALL_ISSUE);

        modelManager.removeIssueRecord(BASKETBALL_ISSUE);

        Equipment updatedEquipment = findEquipmentByName("Wilson-Evolution-Basketball-1");
        assertEquals(EquipmentStatus.BOOKED, updatedEquipment.getStatus());
    }

    @Test
    public void removeReservation_issueStillExists_equipmentRemainsBooked() {
        Equipment basketball = new EquipmentBuilder()
                .withName("Wilson-Evolution-Basketball-1")
                .withCategory("Basketball")
                .withStatus(EquipmentStatus.AVAILABLE)
                .build();
        modelManager.addEquipment(basketball);
        modelManager.addReservation(BASKETBALL_RESERVATION);
        modelManager.addIssueRecord(BASKETBALL_ISSUE);

        modelManager.removeReservation(BASKETBALL_RESERVATION);

        Equipment updatedEquipment = findEquipmentByName("Wilson-Evolution-Basketball-1");
        assertEquals(EquipmentStatus.BOOKED, updatedEquipment.getStatus());
    }

    private Room findRoomByName(String roomName) {
        return modelManager.getFilteredRoomList().stream()
                .filter(room -> room.getName().fullName.equals(roomName))
                .findFirst()
                .orElseThrow(() -> new AssertionError("Room not found: " + roomName));
    }

    private Equipment findEquipmentByName(String equipmentName) {
        return modelManager.getFilteredEquipmentList().stream()
                .filter(equipment -> equipment.getName().fullName.equals(equipmentName))
                .findFirst()
                .orElseThrow(() -> new AssertionError("Equipment not found: " + equipmentName));
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

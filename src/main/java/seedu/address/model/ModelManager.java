package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.alias.AliasMapping;
import seedu.address.model.equipment.Equipment;
import seedu.address.model.issue.IssueRecord;
import seedu.address.model.person.Person;
import seedu.address.model.person.StudentId;
import seedu.address.model.reservation.Reservation;
import seedu.address.model.room.Room;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.Taggable;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    /**
     * Temporary resource registry for the MVP.
     */
    private static final Set<String> VALID_RESOURCES = Collections.unmodifiableSet(
            new HashSet<>(Arrays.asList(
                    "HALL-1", "HALL-2", "HALL-3",
                    "MPSH-1", "MPSH-2",
                    "COURT-1", "COURT-2",
                    "MPR-1", "MPR-2")));

    /**
     * Temporary item registry for the MVP.
     */
    private static final Set<String> VALID_ITEMS = Collections.unmodifiableSet(
            new HashSet<>(Arrays.asList(
                    "WILSON-EVOLUTION-BASKETBALL-1",
                    "WILSON-EVOLUTION-BASKETBALL-2",
                    "MOLTEN-VOLLEYBALL",
                    "MOLTEN-VOLLEYBALL-1",
                    "MOLTEN-VOLLEYBALL-2",
                    "YONEX-BADMINTON-RACKET-1",
                    "YONEX-BADMINTON-RACKET-2",
                    "YONEX-BADMINTON-RACKET-3")));

    private final AddressBook addressBook;
    private final UserPrefs userPrefs;
    private final FilteredList<Person> filteredPersons;
    private final FilteredList<Room> filteredRooms;
    private final FilteredList<Equipment> filteredEquipments;


    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, ReadOnlyUserPrefs userPrefs) {
        requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        this.addressBook = new AddressBook(addressBook);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredPersons = new FilteredList<>(this.addressBook.getPersonList());
        filteredRooms = new FilteredList<>(this.addressBook.getRoomList());
        filteredEquipments = new FilteredList<>(this.addressBook.getEquipmentList());
    }

    public ModelManager() {
        this(new AddressBook(), new UserPrefs());
    }

    // =========== UserPrefs
    // ==================================================================================

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getAddressBookFilePath() {
        return userPrefs.getAddressBookFilePath();
    }

    @Override
    public void setAddressBookFilePath(Path addressBookFilePath) {
        requireNonNull(addressBookFilePath);
        userPrefs.setAddressBookFilePath(addressBookFilePath);
    }

    // =========== AddressBook
    // ================================================================================

    @Override
    public void setAddressBook(ReadOnlyAddressBook addressBook) {
        this.addressBook.resetData(addressBook);
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return addressBook;
    }

    @Override
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return addressBook.hasPerson(person);
    }

    @Override
    public void deletePerson(Person target) {
        addressBook.removePerson(target);
    }

    @Override
    public void addPerson(Person person) {
        addressBook.addPerson(person);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
    }

    @Override
    public void setPerson(Person target, Person editedPerson) {
        requireAllNonNull(target, editedPerson);
        addressBook.setPerson(target, editedPerson);
    }

    //=========== Room ================================================================================

    @Override
    public boolean hasRoom(Room room) {
        requireNonNull(room);
        return addressBook.hasRoom(room);
    }

    @Override
    public void addRoom(Room room) {
        addressBook.addRoom(room);
        updateFilteredRoomList(PREDICATE_SHOW_ALL_ROOMS);
    }

    @Override
    public void deleteRoom(Room target) {
        target.onDelete();
        addressBook.removeRoom(target);
    }

    //=========== Reservation ================================================================================

    @Override
    public boolean hasStudentId(StudentId studentId) {
        requireNonNull(studentId);
        return addressBook.getPersonList().stream()
                .anyMatch(person -> person.getStudentId().equals(studentId));
    }

    @Override
    public boolean hasReservableItem(String resourceId) {
        requireNonNull(resourceId);
        String resolvedResourceId = resolveAlias(resourceId);

        boolean roomExists = addressBook.getRoomList().stream()
                .anyMatch(room -> room.getName().fullName.equalsIgnoreCase(resolvedResourceId));

        boolean equipmentExists = addressBook.getEquipmentList().stream()
                .anyMatch(equipment -> equipment.getName().fullName.equalsIgnoreCase(resolvedResourceId));

        return roomExists || equipmentExists;
    }

    @Override
    public boolean hasConflictingReservation(Reservation reservation) {
        requireNonNull(reservation);
        return addressBook.hasConflictingReservation(reservation);
    }

    @Override
    public Optional<Reservation> getConflictingReservation(Reservation reservation) {
        requireNonNull(reservation);
        return addressBook.getConflictingReservation(reservation);
    }

    @Override
    public void addReservation(Reservation reservation) {
        requireNonNull(reservation);
        addressBook.addReservation(reservation);
    }

    @Override
    public ObservableList<Reservation> getReservationList() {
        return addressBook.getReservationList();
    }


    @Override
    public boolean hasIssuableItem(String itemId) {
        requireNonNull(itemId);
        String resolvedItemId = resolveAlias(itemId);

        return addressBook.getEquipmentList().stream()
                .anyMatch(equipment -> equipment.getName().fullName.equalsIgnoreCase(resolvedItemId));
    }

    @Override
    public boolean hasIssuedItem(String itemId) {
        requireNonNull(itemId);
        return addressBook.hasIssuedItem(itemId);
    }

    @Override
    public Optional<IssueRecord> getIssueRecordByItemId(String itemId) {
        requireNonNull(itemId);
        return addressBook.getIssueRecordByItemId(itemId);
    }

    @Override
    public void addIssueRecord(IssueRecord issueRecord) {
        requireNonNull(issueRecord);
        addressBook.addIssueRecord(issueRecord);
    }

    @Override
    public ObservableList<IssueRecord> getIssueRecordList() {
        return addressBook.getIssueRecordList();
    }

    //=========== Equipment ================================================================================

    @Override
    public boolean hasEquipment(Equipment equipment) {
        requireNonNull(equipment);
        return addressBook.hasEquipment(equipment);
    }

    @Override
    public boolean hasEquipmentName(Equipment equipment) {
        requireNonNull(equipment);
        return addressBook.hasEquipmentName(equipment);
    }

    @Override
    public void addEquipment(Equipment equipment) {
        addressBook.addEquipment(equipment);
        updateFilteredEquipmentList(PREDICATE_SHOW_ALL_EQUIPMENTS);
    }

    @Override
    public void setEquipment(Equipment target, Equipment editedEquipment) {
        requireNonNull(target);
        addressBook.setEquipment(target, editedEquipment);
        updateFilteredEquipmentList(PREDICATE_SHOW_ALL_EQUIPMENTS);
    }

    @Override
    public void deleteEquipment(Equipment target) {
        target.onDelete();
        addressBook.removeEquipment(target);
        updateFilteredEquipmentList(PREDICATE_SHOW_ALL_EQUIPMENTS);
    }

    @Override
    public ObservableList<Equipment> getFilteredEquipmentList() {
        return filteredEquipments;
    }

    @Override
    public void updateFilteredEquipmentList(Predicate<Equipment> predicate) {
        requireNonNull(predicate);
        filteredEquipments.setPredicate(predicate);
    }

    // =========== Filtered Person List Accessors
    // =============================================================

    @Override
    public ObservableList<Person> getFilteredPersonList() {
        return filteredPersons;
    }

    @Override
    public void updateFilteredPersonList(Predicate<Person> predicate) {
        requireNonNull(predicate);
        filteredPersons.setPredicate(predicate);
    }

    @Override
    public boolean hasAliasableTarget(String targetId) {
        requireNonNull(targetId);
        String normalizedTargetId = AliasMapping.normalizeTargetId(targetId);
        return VALID_RESOURCES.contains(normalizedTargetId) || VALID_ITEMS.contains(normalizedTargetId);
    }

    @Override
    public boolean hasAliasName(String aliasName) {
        requireNonNull(aliasName);
        return addressBook.hasAliasName(aliasName);
    }

    @Override
    public Optional<AliasMapping> getAliasMappingByName(String aliasName) {
        requireNonNull(aliasName);
        return addressBook.getAliasMappingByName(aliasName);
    }

    @Override
    public void addAliasMapping(AliasMapping aliasMapping) {
        requireNonNull(aliasMapping);
        addressBook.addAliasMapping(aliasMapping);
    }

    @Override
    public ObservableList<AliasMapping> getAliasMappingList() {
        return addressBook.getAliasMappingList();
    }

    @Override
    public String resolveAlias(String input) {
        requireNonNull(input);
        return getAliasMappingByName(input)
                .map(AliasMapping::getTargetId)
                .orElse(input);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof ModelManager)) {
            return false;
        }

        ModelManager otherModelManager = (ModelManager) other;
        return addressBook.equals(otherModelManager.addressBook)
                && userPrefs.equals(otherModelManager.userPrefs)
                && filteredPersons.equals(otherModelManager.filteredPersons);
    }

    //=========== Filtered Room List Accessors =============================================================

    @Override
    public ObservableList<Room> getFilteredRoomList() {
        return filteredRooms;
    }

    @Override
    public void updateFilteredRoomList(Predicate<Room> predicate) {
        requireNonNull(predicate);
        filteredRooms.setPredicate(predicate);
    }

    //============ Add tags ================================================================================
    @Override
    public boolean hasTaggable(Taggable target) {
        if (target instanceof Room targetRoom) {
            return hasRoom(targetRoom);
        } else if (target instanceof Equipment targetEquipment) {
            return hasEquipmentName(targetEquipment);
        } else {
            return false;
        }
    }

    @Override
    public void addTag(Taggable target, Tag tag) {
        requireAllNonNull(target, tag);
        if (target instanceof Room room) {
            Room targetRoom = addressBook.getRoomList().stream()
                    .filter(r -> r.getName().equals(room.getName()))
                    .findFirst()
                    .orElseThrow(() -> new AssertionError("Room not found: " + room.getName()));
            targetRoom.addTag(tag);
            updateFilteredRoomList(PREDICATE_SHOW_ALL_ROOMS);
        } else if (target instanceof Equipment equipment) {
            Equipment targetEquipment = addressBook.getEquipmentList().stream()
                    .filter(e -> e.getName().equals(equipment.getName()))
                    .findFirst()
                    .orElseThrow(() -> new AssertionError("Equipment not found: " + equipment.getName()));
            targetEquipment.addTag(tag);
            updateFilteredEquipmentList(PREDICATE_SHOW_ALL_EQUIPMENTS);
        } else {
            throw new AssertionError("Unknown Taggable type: " + target.getClass());
        }
    }

    @Override
    public void deleteTag(Taggable target, Tag tag) {
        requireAllNonNull(target, tag);
        if (target instanceof Room room) {
            Room targetRoom = addressBook.getRoomList().stream()
                    .filter(r -> r.getName().equals(room.getName()))
                    .findFirst()
                    .orElseThrow(() -> new AssertionError("Room not found: " + room.getName()));
            targetRoom.deleteTag(tag);
            updateFilteredRoomList(PREDICATE_SHOW_ALL_ROOMS);
        } else if (target instanceof Equipment equipment) {
            Equipment targetEquipment = addressBook.getEquipmentList().stream()
                    .filter(e -> e.getName().equals(equipment.getName()))
                    .findFirst()
                    .orElseThrow(() -> new AssertionError("Equipment not found: " + equipment.getName()));
            targetEquipment.deleteTag(tag);
            updateFilteredEquipmentList(PREDICATE_SHOW_ALL_EQUIPMENTS);
        } else {
            throw new AssertionError("Unknown Taggable type: " + target.getClass());
        }
    }
}

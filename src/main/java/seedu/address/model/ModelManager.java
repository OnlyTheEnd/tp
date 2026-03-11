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
import seedu.address.model.issue.IssueRecord;
import seedu.address.model.person.Person;
import seedu.address.model.person.StudentId;
import seedu.address.model.reservation.Reservation;
import seedu.address.model.room.Room;
import seedu.address.model.room.RoomName;
import seedu.address.model.tag.Tag;

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
        return VALID_RESOURCES.contains(Reservation.normalizeResourceId(resourceId));
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
        return VALID_ITEMS.contains(IssueRecord.normalizeItemId(itemId));
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
    public void addTag(RoomName roomName, Tag tag) {
        requireAllNonNull(roomName, tag);
        Room targetRoom = addressBook.getRoomList().stream()
                .filter(room -> room.getName().equals(roomName))
                .findFirst()
                .orElse(null);
        assert targetRoom != null;
        targetRoom.addTag(tag);
    }

    @Override
    public void deleteTag(RoomName roomName, Tag tag) {
        requireAllNonNull(roomName, tag);
        Room targetRoom = addressBook.getRoomList().stream()
                .filter(room -> room.getName().equals(roomName))
                .findFirst()
                .orElse(null);
        targetRoom.deleteTag(tag);
    }
}

package seedu.address.model;

import java.nio.file.Path;
import java.util.Optional;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
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
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Person> PREDICATE_SHOW_ALL_PERSONS = unused -> true;

    /** {@code Predicate} that always evaluate to true for rooms */
    Predicate<Room> PREDICATE_SHOW_ALL_ROOMS = unused -> true;

    /** {@code Predicate} that always evaluate to true */
    Predicate<Equipment> PREDICATE_SHOW_ALL_EQUIPMENTS = unused -> true;

    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    ReadOnlyUserPrefs getUserPrefs();

    GuiSettings getGuiSettings();

    void setGuiSettings(GuiSettings guiSettings);

    Path getAddressBookFilePath();

    void setAddressBookFilePath(Path addressBookFilePath);

    void setAddressBook(ReadOnlyAddressBook addressBook);

    ReadOnlyAddressBook getAddressBook();

    boolean hasPerson(Person person);

    void deletePerson(Person target);

    void addPerson(Person person);

    void setPerson(Person target, Person editedPerson);

    ObservableList<Person> getFilteredPersonList();

    void updateFilteredPersonList(Predicate<Person> predicate);

    void addRoom(Room room);

    boolean hasRoom(Room room);

    void deleteRoom(Room target);

    ObservableList<Room> getFilteredRoomList();

    void updateFilteredRoomList(Predicate<Room> predicate);

    boolean hasStudentId(StudentId studentId);

    boolean hasReservableItem(String resourceId);

    boolean hasConflictingReservation(Reservation reservation);

    Optional<Reservation> getConflictingReservation(Reservation reservation);

    void addReservation(Reservation reservation);

    ObservableList<Reservation> getReservationList();
    boolean hasIssuableItem(String itemId);
    boolean hasIssuedItem(String itemId);
    Optional<IssueRecord> getIssueRecordByItemId(String itemId);
    void addIssueRecord(IssueRecord issueRecord);
    ObservableList<IssueRecord> getIssueRecordList();

    boolean hasAliasableTarget(String targetId);

    boolean hasAliasName(String aliasName);

    Optional<AliasMapping> getAliasMappingByName(String aliasName);

    void addAliasMapping(AliasMapping aliasMapping);

    ObservableList<AliasMapping> getAliasMappingList();

    String resolveAlias(String input);

    boolean hasEquipment(Equipment equipment);

    boolean hasEquipmentName(Equipment equipment);

    void addEquipment(Equipment equipment);

    void setEquipment(Equipment target, Equipment editedEquipment);

    void deleteEquipment(Equipment target);

    ObservableList<Equipment> getFilteredEquipmentList();

    void updateFilteredEquipmentList(Predicate<Equipment> predicate);

    boolean hasTaggable(Taggable target);

    void addTag(Taggable target, Tag tag);

    void deleteTag(Taggable target, Tag tag);
}

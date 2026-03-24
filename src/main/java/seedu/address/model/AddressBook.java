package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javafx.collections.ObservableList;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.alias.AliasMapping;
import seedu.address.model.alias.UniqueAliasMappingList;
import seedu.address.model.equipment.Equipment;
import seedu.address.model.equipment.UniqueEquipmentList;
import seedu.address.model.issue.IssueRecord;
import seedu.address.model.issue.UniqueIssueRecordList;
import seedu.address.model.person.Person;
import seedu.address.model.person.UniquePersonList;
import seedu.address.model.reservation.Reservation;
import seedu.address.model.reservation.UniqueReservationList;
import seedu.address.model.room.Room;
import seedu.address.model.room.UniqueRoomList;

/**
 * Wraps all data at the address-book level.
 */
public class AddressBook implements ReadOnlyAddressBook {

    private final UniquePersonList persons;
    private final UniqueRoomList rooms;
    private final UniqueReservationList reservations;
    private final UniqueIssueRecordList issueRecords;
    private final UniqueAliasMappingList aliasMappings;
    private final UniqueEquipmentList equipments;

    {
        persons = new UniquePersonList();
        rooms = new UniqueRoomList();
        reservations = new UniqueReservationList();
        issueRecords = new UniqueIssueRecordList();
        aliasMappings = new UniqueAliasMappingList();
        equipments = new UniqueEquipmentList();
    }

    public AddressBook() {}

    /**
     * Creates an AddressBook using the data in the {@code toBeCopied}.
     */
    public AddressBook(ReadOnlyAddressBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    /**
     * Replaces the contents of the person list with {@code persons}.
     */
    public void setPersons(List<Person> persons) {
        this.persons.setPersons(persons);
    }

    /**
     * Replaces the contents of the reservation list with {@code reservations}.
     */
    public void setReservations(List<Reservation> reservations) {
        this.reservations.setReservations(reservations);
    }

    /**
     * Replaces the contents of the issue record list with {@code issueRecords}.
     */
    public void setIssueRecords(List<IssueRecord> issueRecords) {
        this.issueRecords.setIssueRecords(issueRecords);
    }

    /**
     * Resets the existing data of this {@code AddressBook} with {@code newData}.
     */
    public void resetData(ReadOnlyAddressBook newData) {
        requireNonNull(newData);

        setPersons(newData.getPersonList());
        setRooms(newData.getRoomList());
        setReservations(newData.getReservationList());
        setIssueRecords(newData.getIssueRecordList());
        setEquipments(newData.getEquipmentList());
    }

    /**
     * Returns true if the address book contains a person with the same identity as {@code person}.
     */
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return persons.contains(person);
    }

    /**
     * Adds a person to the address book.
     */
    public void addPerson(Person p) {
        persons.add(p);
    }

    /**
     * Replaces the given person {@code target} with {@code editedPerson}.
     */
    public void setPerson(Person target, Person editedPerson) {
        requireNonNull(editedPerson);
        persons.setPerson(target, editedPerson);
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     */
    public void removePerson(Person key) {
        persons.remove(key);
    }

    //=========== Room-level Operations =====================================================================

    /**
     * Replaces the contents of the room list with {@code room}.
     */
    public void setRooms(List<Room> rooms) {
        this.rooms.setRooms(rooms);
    }

    /**
     * Returns true if the address book contains a room with the same identity as {@code room}.
     */
    public boolean hasRoom(Room room) {
        requireNonNull(room);
        return rooms.contains(room);
    }

    /**
     * Adds a room to the address book.
     */
    public void addRoom(Room r) {
        rooms.add(r);
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     */
    public void removeRoom(Room key) {
        rooms.remove(key);
    }

    /**
     * Returns true if the given reservation conflicts with an existing reservation.
     */
    public boolean hasConflictingReservation(Reservation reservation) {
        requireNonNull(reservation);
        return reservations.hasConflict(reservation);
    }

    /**
     * Returns the first conflicting reservation for the given reservation, if any.
     */
    public Optional<Reservation> getConflictingReservation(Reservation reservation) {
        requireNonNull(reservation);
        return reservations.findConflictingReservation(reservation);
    }

    /**
     * Adds a reservation to the address book.
     */
    public void addReservation(Reservation reservation) {
        reservations.add(reservation);
    }

    /**
     * Returns true if the given item is currently issued.
     */
    public boolean hasIssuedItem(String itemId) {
        requireNonNull(itemId);
        return issueRecords.hasIssuedItem(itemId);
    }

    /**
     * Returns the issue record for the given item, if any.
     */
    public Optional<IssueRecord> getIssueRecordByItemId(String itemId) {
        requireNonNull(itemId);
        return issueRecords.getIssueRecordByItemId(itemId);
    }

    /**
     * Adds an issue record to the address book.
     */
    public void addIssueRecord(IssueRecord issueRecord) {
        issueRecords.add(issueRecord);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("persons", persons)
                .add("reservations", reservations)
                .add("issueRecords", issueRecords)
                .add("aliasMappings", aliasMappings)
                .toString();
    }

    /// Equipment-level operations

    /**
     * Returns true if an equipment with the same identity as {@code equipment} exists in the address book.
     */
    public boolean hasEquipment(Equipment equipment) {
        requireNonNull(equipment);
        return equipments.contains(equipment);
    }

    /**
     * Returns true if an equipment with the same name as {@code equipment} exists in the address book.
     */
    public boolean hasEquipmentName(Equipment equipment) {
        requireNonNull(equipment);
        return equipments.containsName(equipment);
    }

    /**
     * Adds an equipment to the address book.
     * The equipment must not already exist in the address book.
     */
    public void addEquipment(Equipment e) {
        equipments.add(e);
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     */
    public void removeEquipment(Equipment key) {
        equipments.remove(key);
    }

    /**
     * Replaces the contents of the equipment list with {@code equipments}.
     */
    public void setEquipments(List<Equipment> equipments) {
        this.equipments.setEquipments(equipments);
    }

    /**
     * Replaces the given equipment {@code target} in the list with {@code editedEquipment}.
     */
    public void setEquipment(Equipment target, Equipment editedEquipment) {
        requireNonNull(editedEquipment);
        equipments.setEquipment(target, editedEquipment);
    }

    @Override
    public ObservableList<Equipment> getEquipmentList() {
        return equipments.asUnmodifiableObservableList();
    }

    /**
     * Returns an unmodifiable view of the person list.
     */
    @Override
    public ObservableList<Person> getPersonList() {
        return persons.asUnmodifiableObservableList();
    }

    /**
     * Returns an unmodifiable view of the rooms list.
     */
    @Override
    public ObservableList<Room> getRoomList() {
        return rooms.asUnmodifiableObservableList();
    }

    /**
     * Returns an unmodifiable view of the reservations list.
     */
    @Override
    public ObservableList<Reservation> getReservationList() {
        return reservations.asUnmodifiableObservableList();
    }

    /**
     * Returns an unmodifiable view of the issue record list.
     */
    @Override
    public ObservableList<IssueRecord> getIssueRecordList() {
        return issueRecords.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof AddressBook)) {
            return false;
        }

        AddressBook otherAddressBook = (AddressBook) other;
        return persons.equals(otherAddressBook.persons)
                && reservations.equals(otherAddressBook.reservations)
                && issueRecords.equals(otherAddressBook.issueRecords);
    }

    @Override
    public int hashCode() {
        return Objects.hash(persons, reservations, issueRecords);
    }
    public void setAliasMappings(List<AliasMapping> aliasMappings) {
        this.aliasMappings.setAliasMappings(aliasMappings);
    }

    /**
     * Returns true if alias name exists
     */
    public boolean hasAliasName(String aliasName) {
        requireNonNull(aliasName);
        return aliasMappings.hasAliasName(aliasName);
    }

    /**
     * Returns the alias mapping by name.
     */
    public Optional<AliasMapping> getAliasMappingByName(String aliasName) {
        requireNonNull(aliasName);
        return aliasMappings.getAliasMappingByName(aliasName);
    }

    public void addAliasMapping(AliasMapping aliasMapping) {
        aliasMappings.add(aliasMapping);
    }

    @Override
    public ObservableList<AliasMapping> getAliasMappingList() {
        return aliasMappings.asUnmodifiableObservableList();
    }
}

package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javafx.collections.ObservableList;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.person.Person;
import seedu.address.model.person.UniquePersonList;
import seedu.address.model.reservation.Reservation;
import seedu.address.model.reservation.UniqueReservationList;

/**
 * Wraps all data at the address-book level.
 */
public class AddressBook implements ReadOnlyAddressBook {

    private final UniquePersonList persons;
    private final UniqueReservationList reservations;

    {
        persons = new UniquePersonList();
        reservations = new UniqueReservationList();
    }

    public AddressBook() {}

    /**
     * Creates an AddressBook using the data in the {@code toBeCopied}
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
     * Resets the existing data of this {@code AddressBook} with {@code newData}.
     */
    public void resetData(ReadOnlyAddressBook newData) {
        requireNonNull(newData);

        setPersons(newData.getPersonList());
        setReservations(newData.getReservationList());
    }



    /**
     * Returns true if have person
     */
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return persons.contains(person);
    }

    /**
     * Adds a person
     */
    public void addPerson(Person p) {
        persons.add(p);
    }

    /**
     * Edit a person's detail
     */
    public void setPerson(Person target, Person editedPerson) {
        requireNonNull(editedPerson);
        persons.setPerson(target, editedPerson);
    }

    /**
     * Remove a person
     */
    public void removePerson(Person key) {
        persons.remove(key);
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



    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("persons", persons)
                .add("reservations", reservations)
                .toString();
    }

    /**
     * Returns an unmodifiable view of the person list.
     */
    @Override
    public ObservableList<Person> getPersonList() {
        return persons.asUnmodifiableObservableList();
    }

    /**
     * Returns an unmodifiable view of the reservations list.
     */
    @Override
    public ObservableList<Reservation> getReservationList() {
        return reservations.asUnmodifiableObservableList();
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
                && reservations.equals(otherAddressBook.reservations);
    }

    @Override
    public int hashCode() {
        return Objects.hash(persons, reservations);
    }
}

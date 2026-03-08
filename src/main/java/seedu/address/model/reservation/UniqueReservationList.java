package seedu.address.model.reservation;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.reservation.exceptions.DuplicateReservationException;
import seedu.address.model.reservation.exceptions.ReservationConflictException;

/**
 * A list of reservations that enforces no exact duplicates and no conflicting bookings.
 */
public class UniqueReservationList implements Iterable<Reservation> {

    private final ObservableList<Reservation> internalList = FXCollections.observableArrayList();
    private final ObservableList<Reservation> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains the exact reservation.
     */
    public boolean contains(Reservation toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::equals);
    }

    /**
     * Returns true if the list contains a conflicting reservation.
     */
    public boolean hasConflict(Reservation toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(existing -> existing.conflictsWith(toCheck));
    }

    /**
     * Returns the first conflicting reservation, if any.
     */
    public Optional<Reservation> findConflictingReservation(Reservation toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().filter(existing -> existing.conflictsWith(toCheck)).findFirst();
    }

    /**
     * Adds a reservation to the list.
     */
    public void add(Reservation toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateReservationException();
        }
        if (hasConflict(toAdd)) {
            throw new ReservationConflictException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the contents of this list with {@code reservations}.
     */
    public void setReservations(List<Reservation> reservations) {
        requireAllNonNull(reservations);
        if (!reservationsAreUniqueAndNonConflicting(reservations)) {
            throw new DuplicateReservationException();
        }
        internalList.setAll(reservations);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Reservation> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    private boolean reservationsAreUniqueAndNonConflicting(List<Reservation> reservations) {
        for (int i = 0; i < reservations.size(); i++) {
            for (int j = i + 1; j < reservations.size(); j++) {
                Reservation first = reservations.get(i);
                Reservation second = reservations.get(j);

                if (first.equals(second) || first.conflictsWith(second)) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public Iterator<Reservation> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof UniqueReservationList)) {
            return false;
        }

        UniqueReservationList otherUniqueReservationList = (UniqueReservationList) other;
        return internalList.equals(otherUniqueReservationList.internalList);
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    @Override
    public String toString() {
        return internalList.toString();
    }
}

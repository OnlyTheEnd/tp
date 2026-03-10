package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.alias.AliasMapping;
import seedu.address.model.issue.IssueRecord;
import seedu.address.model.person.Person;
import seedu.address.model.reservation.Reservation;

/**
 * Unmodifiable view of an address book
 */
public interface ReadOnlyAddressBook {

    /**
     * Returns an unmodifiable view of the persons list.
     */
    ObservableList<Person> getPersonList();

    /**
     * Returns an unmodifiable view of the reservations list.
     */
    ObservableList<Reservation> getReservationList();
    /**
     * Returns an unmodifiable view of the issue record list.
     */
    ObservableList<IssueRecord> getIssueRecordList();
    /**
     * Returns an unmodifiable view of the AliasMappingList.
     */
    ObservableList<AliasMapping> getAliasMappingList();
}

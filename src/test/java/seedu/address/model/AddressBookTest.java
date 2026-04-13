package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.alias.AliasMapping;
import seedu.address.model.equipment.Category;
import seedu.address.model.equipment.Equipment;
import seedu.address.model.equipment.EquipmentName;
import seedu.address.model.equipment.EquipmentStatus;
import seedu.address.model.issue.IssueRecord;
import seedu.address.model.person.Person;
import seedu.address.model.person.StudentId;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.reservation.Reservation;
import seedu.address.model.room.Location;
import seedu.address.model.room.Room;
import seedu.address.model.room.RoomName;
import seedu.address.model.room.Status;
import seedu.address.testutil.PersonBuilder;

public class AddressBookTest {

    private final AddressBook addressBook = new AddressBook();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), addressBook.getPersonList());
        assertEquals(Collections.emptyList(), addressBook.getReservationList());
        assertEquals(Collections.emptyList(), addressBook.getIssueRecordList());
        assertEquals(Collections.emptyList(), addressBook.getAliasMappingList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> addressBook.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyAddressBook_replacesData() {
        AddressBook newData = getTypicalAddressBook();
        addressBook.resetData(newData);
        assertEquals(newData, addressBook);
    }

    @Test
    public void resetData_withDuplicatePersons_throwsDuplicatePersonException() {
        Person editedAlice = new PersonBuilder(ALICE).build();
        List<Person> newPersons = Arrays.asList(ALICE, editedAlice);
        AddressBookStub newData = new AddressBookStub(newPersons);

        assertThrows(DuplicatePersonException.class, () -> addressBook.resetData(newData));
    }

    @Test
    public void hasPerson_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> addressBook.hasPerson(null));
    }

    @Test
    public void hasPerson_personNotInAddressBook_returnsFalse() {
        assertFalse(addressBook.hasPerson(ALICE));
    }

    @Test
    public void hasPerson_personInAddressBook_returnsTrue() {
        addressBook.addPerson(ALICE);
        assertTrue(addressBook.hasPerson(ALICE));
    }

    @Test
    public void hasPerson_personWithSameIdentityFieldsInAddressBook_returnsTrue() {
        addressBook.addPerson(ALICE);
        Person editedAlice = new PersonBuilder(ALICE).build();
        assertTrue(addressBook.hasPerson(editedAlice));
    }

    @Test
    public void getPersonList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> addressBook.getPersonList().remove(0));
    }

    @Test
    public void getReservationList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> addressBook.getReservationList().remove(0));
    }

    @Test
    public void getIssueRecordList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> addressBook.getIssueRecordList().remove(0));
    }

    @Test
    public void getAliasMappingList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> addressBook.getAliasMappingList().remove(0));
    }

    @Test
    public void removeEquipment_removesAssociatedAliases() {
        Equipment equipment = new Equipment(
                new EquipmentName("Wilson-Evolution-3"),
                new Category("Soccer"),
                EquipmentStatus.AVAILABLE);

        AliasMapping aliasMapping = new AliasMapping("Wilson-Evolution-3", "w3");

        addressBook.addEquipment(equipment);
        addressBook.addAliasMapping(aliasMapping);

        addressBook.removeEquipment(equipment);

        assertFalse(addressBook.hasAliasName("w3"));
    }

    @Test
    public void removeRoom_removesAssociatedAliases() {
        Room room = new Room(
                new RoomName("TR-16"),
                new Location("Level-1"),
                Status.AVAILABLE);

        AliasMapping aliasMapping = new AliasMapping("TR-16", "tr16");

        addressBook.addRoom(room);
        addressBook.addAliasMapping(aliasMapping);

        addressBook.removeRoom(room);

        assertFalse(addressBook.hasAliasName("tr16"));
    }

    @Test
    public void toStringMethod() {
        String expected = AddressBook.class.getCanonicalName()
                + "{persons=" + addressBook.getPersonList()
                + ", reservations=" + addressBook.getReservationList()
                + ", issueRecords=" + addressBook.getIssueRecordList()
                + ", aliasMappings=" + addressBook.getAliasMappingList() + "}";
        assertEquals(expected, addressBook.toString());
    }

    @Test
    public void hasIssuedItem_nullItemId_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> addressBook.hasIssuedItem(null));
    }

    @Test
    public void hasIssuedItem_itemNotIssued_returnsFalse() {
        assertFalse(addressBook.hasIssuedItem("Wilson-Evolution-Basketball-1"));
    }

    @Test
    public void addIssueRecord_andGetIssueRecordByItemId_success() {
        IssueRecord issueRecord = new IssueRecord("Wilson-Evolution-Basketball-1",
                new StudentId("a1234567a"),
                java.time.LocalDateTime.of(2099, 3, 15, 17, 0));

        addressBook.addIssueRecord(issueRecord);

        assertTrue(addressBook.hasIssuedItem("Wilson-Evolution-Basketball-1"));
        assertEquals(Optional.of(issueRecord),
                addressBook.getIssueRecordByItemId("Wilson-Evolution-Basketball-1"));
    }

    /**
     * A stub ReadOnlyAddressBook whose persons list can violate interface constraints.
     */
    private static class AddressBookStub implements ReadOnlyAddressBook {
        private final ObservableList<Person> persons = FXCollections.observableArrayList();

        AddressBookStub(Collection<Person> persons) {
            this.persons.setAll(persons);
        }

        @Override
        public ObservableList<Person> getPersonList() {
            return persons;
        }

        @Override
        public ObservableList<Reservation> getReservationList() {
            return FXCollections.observableArrayList();
        }

        @Override
        public ObservableList<IssueRecord> getIssueRecordList() {
            return FXCollections.observableArrayList();
        }

        @Override
        public ObservableList<AliasMapping> getAliasMappingList() {
            return FXCollections.observableArrayList();
        }

        @Override
        public ObservableList<Equipment> getEquipmentList() {
            return FXCollections.observableArrayList();
        }

    }
    @Test
    public void hasAliasName_nullAliasName_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> addressBook.hasAliasName(null));
    }

    @Test
    public void hasAliasName_aliasNotPresent_returnsFalse() {
        assertFalse(addressBook.hasAliasName("b1"));
    }

    @Test
    public void hasAliasName_aliasPresent_returnsTrue() {
        AliasMapping aliasMapping = new AliasMapping("Wilson-Evolution-Basketball-1", "b1");
        addressBook.addAliasMapping(aliasMapping);
        assertTrue(addressBook.hasAliasName("b1"));
    }

    @Test
    public void getAliasMappingByName_nullAliasName_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> addressBook.getAliasMappingByName(null));
    }

    @Test
    public void addAliasMapping_andGetAliasMappingByName_success() {
        AliasMapping aliasMapping = new AliasMapping("Wilson-Evolution-Basketball-1", "b1");

        addressBook.addAliasMapping(aliasMapping);

        assertTrue(addressBook.hasAliasName("b1"));
        assertEquals(Optional.of(aliasMapping), addressBook.getAliasMappingByName("b1"));
    }
}

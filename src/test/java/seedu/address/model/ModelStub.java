package seedu.address.model;

import java.nio.file.Path;
import java.util.Optional;
import java.util.function.Predicate;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.alias.AliasMapping;
import seedu.address.model.issue.IssueRecord;
import seedu.address.model.person.Person;
import seedu.address.model.person.StudentId;
import seedu.address.model.reservation.Reservation;
import seedu.address.model.room.RoomName;
import seedu.address.model.tag.Tag;




/**
 * A default model stub that throws on all unexpected calls.
 */
public abstract class ModelStub implements Model {

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public GuiSettings getGuiSettings() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public Path getAddressBookFilePath() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setAddressBookFilePath(Path addressBookFilePath) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setAddressBook(ReadOnlyAddressBook addressBook) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean hasPerson(Person person) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void deletePerson(Person target) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void addPerson(Person person) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setPerson(Person target, Person editedPerson) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ObservableList<Person> getFilteredPersonList() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void updateFilteredPersonList(Predicate<Person> predicate) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean hasStudentId(StudentId studentId) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean hasReservableItem(String resourceId) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean hasConflictingReservation(Reservation reservation) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public Optional<Reservation> getConflictingReservation(Reservation reservation) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void addReservation(Reservation reservation) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ObservableList<Reservation> getReservationList() {
        return FXCollections.observableArrayList();
    }

    @Override
    public boolean hasIssuableItem(String itemId) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean hasIssuedItem(String itemId) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public Optional<IssueRecord> getIssueRecordByItemId(String itemId) {
        return Optional.empty();
    }

    @Override
    public void addIssueRecord(IssueRecord issueRecord) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ObservableList<IssueRecord> getIssueRecordList() {
        return FXCollections.observableArrayList();
    }

    @Override
    public boolean hasAliasableTarget(String targetId) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean hasAliasName(String aliasName) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public Optional<AliasMapping> getAliasMappingByName(String aliasName) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void addAliasMapping(AliasMapping aliasMapping) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ObservableList<AliasMapping> getAliasMappingList() {
        return FXCollections.observableArrayList();
    }

    @Override
    public String resolveAlias(String input) {
        return input;
    }

    //============ Add tags =================================================================================
    @Override
    public void addTag(RoomName roomName, Tag tag) {
        throw new AssertionError("This method should not be called.");
    };

    @Override
    public void deleteTag(RoomName roomName, Tag tag) {
        throw new AssertionError("This method should not be called.");
    };
}




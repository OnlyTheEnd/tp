package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.alias.AliasMapping;
import seedu.address.model.issue.IssueRecord;
import seedu.address.model.person.Person;
import seedu.address.model.person.StudentId;
import seedu.address.model.reservation.Reservation;

public class IssueCommandTest {

    private static final String VALID_ITEM_ID = "Wilson-Evolution-Basketball-1";
    private static final StudentId VALID_STUDENT_ID = new StudentId("a1234567a");
    private static final LocalDateTime VALID_DUE_DATE_TIME = LocalDateTime.of(2099, 3, 15, 17, 0);
    private static final IssueRecord VALID_ISSUE_RECORD =
            new IssueRecord(VALID_ITEM_ID, VALID_STUDENT_ID, VALID_DUE_DATE_TIME);

    @Test
    public void execute_issueAccepted_addSuccessful() throws Exception {
        ModelStubAcceptingIssueRecordAdded modelStub = new ModelStubAcceptingIssueRecordAdded();
        IssueCommand issueCommand = new IssueCommand(VALID_ISSUE_RECORD);

        CommandResult commandResult = issueCommand.execute(modelStub);

        assertEquals(VALID_ISSUE_RECORD, modelStub.issueRecordAdded);
        assertEquals(String.format(IssueCommand.MESSAGE_SUCCESS,
                        VALID_ISSUE_RECORD.getItemId(),
                        VALID_ISSUE_RECORD.getStudentId(),
                        VALID_ISSUE_RECORD.getFormattedDueDateTime()),
                commandResult.getFeedbackToUser());
    }

    @Test
    public void execute_invalidItem_throwsCommandException() {
        ModelStub modelStub = new ModelStub() {
            @Override
            public boolean hasIssuableItem(String itemId) {
                return false;
            }

            @Override
            public boolean hasStudentId(StudentId studentId) {
                return true;
            }
        };

        IssueCommand issueCommand = new IssueCommand(VALID_ISSUE_RECORD);

        assertThrows(CommandException.class,
                String.format(IssueCommand.MESSAGE_INVALID_ITEM,
                        VALID_ISSUE_RECORD.getItemId()), () -> issueCommand.execute(modelStub));
    }

    @Test
    public void execute_invalidStudent_throwsCommandException() {
        ModelStub modelStub = new ModelStub() {
            @Override
            public boolean hasIssuableItem(String itemId) {
                return true;
            }

            @Override
            public boolean hasStudentId(StudentId studentId) {
                return false;
            }
        };

        IssueCommand issueCommand = new IssueCommand(VALID_ISSUE_RECORD);

        assertThrows(CommandException.class,
                String.format(IssueCommand.MESSAGE_INVALID_STUDENT,
                        VALID_ISSUE_RECORD.getStudentId()), () -> issueCommand.execute(modelStub));
    }

    @Test
    public void execute_itemAlreadyIssued_throwsCommandException() {
        IssueRecord existingIssueRecord = new IssueRecord("Wilson-Evolution-Basketball-1",
                new StudentId("a2345678b"),
                LocalDateTime.of(2099, 3, 12, 12, 0));

        ModelStub modelStub = new ModelStub() {
            @Override
            public boolean hasIssuableItem(String itemId) {
                return true;
            }

            @Override
            public boolean hasStudentId(StudentId studentId) {
                return true;
            }

            @Override
            public Optional<IssueRecord> getIssueRecordByItemId(String itemId) {
                return Optional.of(existingIssueRecord);
            }
        };

        IssueCommand issueCommand = new IssueCommand(VALID_ISSUE_RECORD);

        assertThrows(CommandException.class,
                String.format(IssueCommand.MESSAGE_ALREADY_ISSUED,
                        existingIssueRecord.getItemId(),
                        existingIssueRecord.getStudentId(),
                        existingIssueRecord
                                .getFormattedDueDateTime()), () -> issueCommand.execute(modelStub));
    }

    /**
     * A default model stub that throws on all unexpected calls.
     */
    private abstract static class ModelStub implements Model {

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
    }

    /**
     * A model stub that always accepts an issue record.
     */
    private static class ModelStubAcceptingIssueRecordAdded extends ModelStub {
        private IssueRecord issueRecordAdded;

        @Override
        public boolean hasIssuableItem(String itemId) {
            return true;
        }

        @Override
        public boolean hasStudentId(StudentId studentId) {
            return true;
        }

        @Override
        public void addIssueRecord(IssueRecord issueRecord) {
            issueRecordAdded = issueRecord;
        }
    }
}

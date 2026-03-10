package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.nio.file.Path;
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

/**
 * Tests for {@link AliasCommand}.
 */
public class AliasCommandTest {

    private static final AliasMapping VALID_ALIAS_MAPPING =
            new AliasMapping("Wilson-Evolution-Basketball-1", "b1");

    @Test
    public void constructor_nullAliasMapping_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AliasCommand(null));
    }

    @Test
    public void execute_aliasAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingAliasAdded modelStub = new ModelStubAcceptingAliasAdded();
        AliasCommand aliasCommand = new AliasCommand(VALID_ALIAS_MAPPING);

        CommandResult commandResult = aliasCommand.execute(modelStub);

        assertEquals(VALID_ALIAS_MAPPING, modelStub.aliasAdded);
        assertEquals(String.format(AliasCommand.MESSAGE_SUCCESS,
                        VALID_ALIAS_MAPPING.getTargetId(),
                        VALID_ALIAS_MAPPING.getAliasName()),
                commandResult.getFeedbackToUser());
    }

    @Test
    public void execute_invalidTarget_throwsCommandException() {
        ModelStub modelStub = new ModelStub() {
            @Override
            public boolean hasAliasableTarget(String targetId) {
                return false;
            }

            @Override
            public boolean hasAliasName(String aliasName) {
                return false;
            }
        };

        AliasCommand aliasCommand = new AliasCommand(VALID_ALIAS_MAPPING);

        assertThrows(CommandException.class,
                String.format(AliasCommand.MESSAGE_INVALID_TARGET,
                        VALID_ALIAS_MAPPING.getTargetId()), () -> aliasCommand.execute(modelStub));
    }

    @Test
    public void execute_duplicateAlias_throwsCommandException() {
        ModelStub modelStub = new ModelStub() {
            @Override
            public boolean hasAliasableTarget(String targetId) {
                return true;
            }

            @Override
            public boolean hasAliasName(String aliasName) {
                return true;
            }
        };

        AliasCommand aliasCommand = new AliasCommand(VALID_ALIAS_MAPPING);

        assertThrows(CommandException.class,
                String.format(AliasCommand.MESSAGE_DUPLICATE_ALIAS,
                        VALID_ALIAS_MAPPING.getAliasName()), () -> aliasCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        AliasCommand aliasFirstCommand = new AliasCommand(VALID_ALIAS_MAPPING);
        AliasCommand aliasSecondCommand = new AliasCommand(new AliasMapping("Hall-2", "h2"));
        AliasCommand aliasFirstCommandCopy = new AliasCommand(VALID_ALIAS_MAPPING);

        assertTrue(aliasFirstCommand.equals(aliasFirstCommand));
        assertTrue(aliasFirstCommand.equals(aliasFirstCommandCopy));

        assertFalse(aliasFirstCommand.equals(1));
        assertFalse(aliasFirstCommand.equals(null));
        assertFalse(aliasFirstCommand.equals(aliasSecondCommand));
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
            throw new AssertionError("This method should not be called.");
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
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A model stub that always accepts the alias being added.
     */
    private static class ModelStubAcceptingAliasAdded extends ModelStub {
        private AliasMapping aliasAdded;

        @Override
        public boolean hasAliasableTarget(String targetId) {
            requireNonNull(targetId);
            return true;
        }

        @Override
        public boolean hasAliasName(String aliasName) {
            requireNonNull(aliasName);
            return false;
        }

        @Override
        public void addAliasMapping(AliasMapping aliasMapping) {
            requireNonNull(aliasMapping);
            aliasAdded = aliasMapping;
        }
    }
}

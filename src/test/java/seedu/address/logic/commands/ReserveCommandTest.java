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

public class ReserveCommandTest {

    private static final String VALID_RESOURCE_ID = "Hall-2";
    private static final StudentId VALID_STUDENT_ID = new StudentId("a1234567a");
    private static final LocalDateTime VALID_START = LocalDateTime.of(2026, 3, 1, 14, 0);
    private static final LocalDateTime VALID_END = LocalDateTime.of(2026, 3, 1, 16, 0);
    private static final Reservation VALID_RESERVATION = new Reservation(VALID_RESOURCE_ID, VALID_STUDENT_ID,
            VALID_START, VALID_END);

    @Test
    public void execute_reservationAccepted_addSuccessful() throws Exception {
        ModelStubAcceptingReservationAdded modelStub = new ModelStubAcceptingReservationAdded();
        ReserveCommand reserveCommand = new ReserveCommand(VALID_RESERVATION);

        CommandResult commandResult = reserveCommand.execute(modelStub);

        assertEquals(VALID_RESERVATION, modelStub.reservationAdded);
        assertEquals(String.format(ReserveCommand.MESSAGE_SUCCESS,
                        VALID_RESERVATION.getResourceId(),
                        VALID_RESERVATION.getStudentId(),
                        VALID_RESERVATION.getFormattedStartDateTime(),
                        VALID_RESERVATION.getFormattedEndDateTime()),
                commandResult.getFeedbackToUser());
    }

    @Test
    public void execute_invalidResource_throwsCommandException() {
        ModelStub modelStub = new ModelStub() {
            @Override
            public boolean hasReservableItem(String resourceId) {
                return false;
            }

            @Override
            public boolean hasStudentId(StudentId studentId) {
                return true;
            }
        };

        ReserveCommand reserveCommand = new ReserveCommand(VALID_RESERVATION);

        assertThrows(CommandException.class,
                String.format(ReserveCommand.MESSAGE_INVALID_RESOURCE,
                        VALID_RESERVATION.getResourceId()), () -> reserveCommand.execute(modelStub));
    }

    @Test
    public void execute_invalidStudent_throwsCommandException() {
        ModelStub modelStub = new ModelStub() {
            @Override
            public boolean hasReservableItem(String resourceId) {
                return true;
            }

            @Override
            public boolean hasStudentId(StudentId studentId) {
                return false;
            }
        };

        ReserveCommand reserveCommand = new ReserveCommand(VALID_RESERVATION);

        assertThrows(CommandException.class,
                String.format(ReserveCommand.MESSAGE_INVALID_STUDENT,
                        VALID_RESERVATION.getStudentId()), () -> reserveCommand.execute(modelStub));
    }

    @Test
    public void execute_conflictingReservation_throwsCommandException() {
        Reservation conflictingReservation = new Reservation("Hall-2", new StudentId("a2345678b"),
                LocalDateTime.of(2026, 3, 1, 13, 0),
                LocalDateTime.of(2026, 3, 1, 15, 0));

        ModelStub modelStub = new ModelStub() {
            @Override
            public boolean hasReservableItem(String resourceId) {
                return true;
            }

            @Override
            public boolean hasStudentId(StudentId studentId) {
                return true;
            }

            @Override
            public Optional<Reservation> getConflictingReservation(Reservation reservation) {
                return Optional.of(conflictingReservation);
            }
        };

        ReserveCommand reserveCommand = new ReserveCommand(VALID_RESERVATION);

        assertThrows(CommandException.class,
                String.format(ReserveCommand.MESSAGE_CONFLICT,
                        conflictingReservation.getResourceId(),
                        conflictingReservation.getFormattedStartDateTime(),
                        conflictingReservation.getFormattedEndDateTime()), () -> reserveCommand.execute(modelStub));
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
            return Optional.empty();
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
            return input;
        }
    }

    /**
     * A model stub that always accepts a reservation.
     */
    private static class ModelStubAcceptingReservationAdded extends ModelStub {
        private Reservation reservationAdded;

        @Override
        public boolean hasReservableItem(String resourceId) {
            return true;
        }

        @Override
        public boolean hasStudentId(StudentId studentId) {
            return true;
        }

        @Override
        public void addReservation(Reservation reservation) {
            reservationAdded = reservation;
        }
    }
}

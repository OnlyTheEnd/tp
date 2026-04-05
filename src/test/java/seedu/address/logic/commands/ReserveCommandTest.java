package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.ModelStub;
import seedu.address.model.person.StudentId;
import seedu.address.model.reservation.Reservation;

public class ReserveCommandTest {

    private static final String VALID_RESOURCE_ID = "Hall-2";
    private static final StudentId VALID_STUDENT_ID = new StudentId("a1234567a");
    private static final LocalDateTime VALID_START = LocalDateTime.of(2099, 3, 1, 14, 0);
    private static final LocalDateTime VALID_END = LocalDateTime.of(2099, 3, 1, 16, 0);
    private static final Reservation VALID_RESERVATION = new Reservation(
            VALID_RESOURCE_ID, VALID_STUDENT_ID, VALID_START, VALID_END);

    @Test
    public void execute_reservationAccepted_addSuccessful() throws Exception {
        ModelStubAcceptingReservationAdded modelStub = new ModelStubAcceptingReservationAdded();
        ReserveCommand reserveCommand = new ReserveCommand(VALID_RESERVATION);

        CommandResult commandResult = reserveCommand.execute(modelStub);

        assertEquals(new Reservation("HALL-2", VALID_STUDENT_ID, VALID_START, VALID_END),
                modelStub.reservationAdded);
        assertEquals(String.format(ReserveCommand.MESSAGE_SUCCESS,
                        "HALL-2",
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
                        "HALL-2"), () -> reserveCommand.execute(modelStub));
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
    public void execute_conflictingResourceReservation_throwsCommandException() {
        Reservation conflictingReservation = new Reservation("Hall-2", new StudentId("a2345678b"),
                LocalDateTime.of(2099, 3, 1, 13, 0),
                LocalDateTime.of(2099, 3, 1, 15, 0));

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
                String.format(ReserveCommand.MESSAGE_RESOURCE_CONFLICT,
                        conflictingReservation.getResourceId(),
                        conflictingReservation.getFormattedStartDateTime(),
                        conflictingReservation.getFormattedEndDateTime()), () -> reserveCommand.execute(modelStub));
    }

    @Test
    public void execute_conflictingStudentReservation_throwsCommandException() {
        Reservation conflictingReservation = new Reservation("MPSH-1", VALID_STUDENT_ID,
                LocalDateTime.of(2099, 3, 1, 13, 30),
                LocalDateTime.of(2099, 3, 1, 15, 30));

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
                String.format(ReserveCommand.MESSAGE_STUDENT_CONFLICT,
                        conflictingReservation.getStudentId(),
                        conflictingReservation.getFormattedStartDateTime(),
                        conflictingReservation.getFormattedEndDateTime()), () -> reserveCommand.execute(modelStub));
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

        @Override
        public Optional<Reservation> getConflictingReservation(Reservation reservation) {
            return Optional.empty();
        }
    }
}

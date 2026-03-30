package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.ModelStub;
import seedu.address.model.person.StudentId;
import seedu.address.model.reservation.Reservation;

/**
 * Tests for {@link CancelReservationCommand}.
 */
public class CancelReservationCommandTest {

    private static final String VALID_RESOURCE_ID = "Hall-2";
    private static final StudentId VALID_STUDENT_ID = new StudentId("a1234567a");
    private static final LocalDateTime VALID_START = LocalDateTime.of(2099, 3, 15, 9, 0);

    private static final Reservation VALID_RESERVATION = new Reservation(
            "Hall-2",
            VALID_STUDENT_ID,
            VALID_START,
            LocalDateTime.of(2099, 3, 15, 11, 0));

    @Test
    public void constructor_nullResourceId_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new CancelReservationCommand(null,
                VALID_STUDENT_ID, VALID_START));
    }

    @Test
    public void constructor_nullStudentId_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new CancelReservationCommand(VALID_RESOURCE_ID,
                null, VALID_START));
    }

    @Test
    public void constructor_nullStartDateTime_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new CancelReservationCommand(VALID_RESOURCE_ID,
                VALID_STUDENT_ID, null));
    }

    @Test
    public void execute_cancelReservation_success() throws Exception {
        ModelStubAcceptingCancel modelStub = new ModelStubAcceptingCancel();
        CancelReservationCommand command =
                new CancelReservationCommand(VALID_RESOURCE_ID, VALID_STUDENT_ID, VALID_START);

        CommandResult result = command.execute(modelStub);

        assertEquals(VALID_RESERVATION, modelStub.removedReservation);
        assertEquals(
                "Reservation cancelled:\nReserved HALL-2 by Student a1234567a from 2099-03-15 0900 to 2099-03-15 1100",
                result.getFeedbackToUser());
    }

    @Test
    public void execute_reservationNotFound_throwsCommandException() {
        ModelStub modelStub = new ModelStub() {
            @Override
            public ObservableList<Reservation> getReservationList() {
                return FXCollections.observableArrayList();
            }

            @Override
            public String resolveAlias(String input) {
                return input.toUpperCase();
            }
        };

        CancelReservationCommand command =
                new CancelReservationCommand(VALID_RESOURCE_ID, VALID_STUDENT_ID, VALID_START);

        assertThrows(CommandException.class,
                "Error:\nNo matching reservation found "
                        + "for HALL-2 by a1234567a starting at 2099-03-15 0900", () -> command.execute(modelStub));
    }

    @Test
    public void equals() {
        CancelReservationCommand command =
                new CancelReservationCommand(VALID_RESOURCE_ID, VALID_STUDENT_ID, VALID_START);
        CancelReservationCommand sameCommand =
                new CancelReservationCommand(VALID_RESOURCE_ID, VALID_STUDENT_ID, VALID_START);
        CancelReservationCommand differentResource =
                new CancelReservationCommand("Hall-3", VALID_STUDENT_ID, VALID_START);
        CancelReservationCommand differentStudent =
                new CancelReservationCommand(VALID_RESOURCE_ID, new StudentId("a7654321a"), VALID_START);
        CancelReservationCommand differentStart =
                new CancelReservationCommand(VALID_RESOURCE_ID, VALID_STUDENT_ID,
                        LocalDateTime.of(2099, 3, 15, 10, 0));

        assertTrue(command.equals(command));
        assertTrue(command.equals(sameCommand));

        assertFalse(command.equals(1));
        assertFalse(command.equals(null));
        assertFalse(command.equals(differentResource));
        assertFalse(command.equals(differentStudent));
        assertFalse(command.equals(differentStart));
    }

    @Test
    public void toStringMethod() {
        CancelReservationCommand command =
                new CancelReservationCommand(VALID_RESOURCE_ID, VALID_STUDENT_ID, VALID_START);

        String expected = CancelReservationCommand.class.getCanonicalName()
                + "{resourceId=HALL-2, studentId=a1234567a, startDateTime=2099-03-15 0900}";

        assertEquals(expected, command.toString());
    }

    /**
     * A model stub that always accepts a reservation cancellation.
     */
    private static class ModelStubAcceptingCancel extends ModelStub {
        private final ObservableList<Reservation> reservations =
                FXCollections.observableArrayList(VALID_RESERVATION);
        private Reservation removedReservation;

        @Override
        public ObservableList<Reservation> getReservationList() {
            return reservations;
        }

        @Override
        public void removeReservation(Reservation reservation) {
            removedReservation = reservation;
            reservations.remove(reservation);
        }

        @Override
        public String resolveAlias(String input) {
            return input.toUpperCase();
        }
    }
}

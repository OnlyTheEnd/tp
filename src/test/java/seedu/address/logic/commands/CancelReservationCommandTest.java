package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
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

    private static final StudentId VALID_STUDENT_ID = new StudentId("a1234567a");
    private static final LocalDateTime VALID_START = LocalDateTime.of(2099, 3, 15, 9, 0);

    private static final Reservation VALID_RESERVATION = new Reservation(
            "Hall-2",
            VALID_STUDENT_ID,
            VALID_START,
            LocalDateTime.of(2099, 3, 15, 11, 0));

    @Test
    public void execute_cancelReservation_success() throws Exception {
        ModelStubAcceptingCancel modelStub = new ModelStubAcceptingCancel();
        CancelReservationCommand command = new CancelReservationCommand("Hall-2", VALID_STUDENT_ID, VALID_START);

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

        CancelReservationCommand command = new CancelReservationCommand("Hall-2", VALID_STUDENT_ID, VALID_START);

        assertThrows(CommandException.class,
                "Error:\nNo matching reservation "
                        + "found for HALL-2 by a1234567a "
                        + "starting at 2099-03-15 0900", () -> command.execute(modelStub));
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

package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FROM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TO;

import java.util.Optional;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.reservation.Reservation;

/**
 * Reserves a room/item for a given student within a date/time range.
 */
public class ReserveCommand extends Command {

    public static final String COMMAND_WORD = "reserve";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Reserves an item/room for a student.\n"
            + "Parameters: ITEM_OR_ROOM_ID STUDENT_ID "
            + PREFIX_FROM + "START_DATE_TIME "
            + PREFIX_TO + "END_DATE_TIME\n"
            + "Date/time format: yyyy-MM-dd HHmm\n"
            + "Example: " + COMMAND_WORD + " Hall-2 a1234567a "
            + PREFIX_FROM + "2026-03-01 1400 "
            + PREFIX_TO + "2026-03-01 1600";

    public static final String MESSAGE_SUCCESS = "Reservation confirmed:\n"
            + "Reserved %1$s by Student %2$s from %3$s to %4$s";

    public static final String MESSAGE_INVALID_RESOURCE = "Error:\n%1$s is not a valid registered room/item.";
    public static final String MESSAGE_INVALID_STUDENT = "Error:\n%1$s is not a valid student ID in the system.";
    public static final String MESSAGE_CONFLICT = "Error:\n%1$s is already reserved from %2$s to %3$s";

    private final Reservation reservationToAdd;

    /**
     * Creates a ReserveCommand to add the specified {@code Reservation}.
     */
    public ReserveCommand(Reservation reservation) {
        requireNonNull(reservation);
        this.reservationToAdd = reservation;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (!model.hasReservableItem(reservationToAdd.getResourceId())) {
            throw new CommandException(String.format(
                    MESSAGE_INVALID_RESOURCE, reservationToAdd.getResourceId()));
        }

        if (!model.hasStudentId(reservationToAdd.getStudentId())) {
            throw new CommandException(String.format(
                    MESSAGE_INVALID_STUDENT, reservationToAdd.getStudentId()));
        }

        Optional<Reservation> conflictingReservation = model.getConflictingReservation(reservationToAdd);
        if (conflictingReservation.isPresent()) {
            Reservation existing = conflictingReservation.get();
            throw new CommandException(String.format(
                    MESSAGE_CONFLICT,
                    existing.getResourceId(),
                    existing.getFormattedStartDateTime(),
                    existing.getFormattedEndDateTime()));
        }

        String resolvedResourceId = model.resolveAlias(reservationToAdd.getResourceId());
        Reservation resolvedReservation = new Reservation(
                resolvedResourceId,
                reservationToAdd.getStudentId(),
                reservationToAdd.getStartDateTime(),
                reservationToAdd.getEndDateTime());

        model.addReservation(reservationToAdd);
        return new CommandResult(String.format(
                MESSAGE_SUCCESS,
                reservationToAdd.getResourceId(),
                reservationToAdd.getStudentId(),
                reservationToAdd.getFormattedStartDateTime(),
                reservationToAdd.getFormattedEndDateTime()));
    }

    /**
     * Returns true if both reserve commands have the same reservation to add.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof ReserveCommand)) {
            return false;
        }

        ReserveCommand otherReserveCommand = (ReserveCommand) other;
        return reservationToAdd.equals(otherReserveCommand.reservationToAdd);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("reservationToAdd", reservationToAdd)
                .toString();
    }
}

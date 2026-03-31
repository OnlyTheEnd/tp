package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.time.LocalDateTime;
import java.util.Optional;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.StudentId;
import seedu.address.model.reservation.Reservation;

/**
 * Cancels an existing reservation.
 */
public class CancelReservationCommand extends Command {

    public static final String COMMAND_WORD = "cancel";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Cancels an existing reservation.\n"
            + "Parameters: ITEM_OR_ROOM_ID STUDENT_ID f/START_DATE_TIME\n"
            + "Date/time format: yyyy-MM-dd HHmm\n"
            + "Example: " + COMMAND_WORD + " Hall-2 a1234567a f/2099-03-15 0900";

    public static final String MESSAGE_SUCCESS =
            "Reservation cancelled:\nReserved %1$s by Student %2$s from %3$s to %4$s";
    public static final String MESSAGE_NOT_FOUND =
            "Error:\nNo matching reservation found for %1$s by %2$s starting at %3$s";

    private final String resourceId;
    private final StudentId studentId;
    private final LocalDateTime startDateTime;

    /**
     * Creates a CancelReservationCommand to cancel the specified reservation.
     *
     * @param resourceId The reserved item or room id.
     * @param studentId The student id of the reserver.
     * @param startDateTime The start date/time of the reservation.
     */
    public CancelReservationCommand(String resourceId, StudentId studentId, LocalDateTime startDateTime) {
        requireNonNull(resourceId);
        requireNonNull(studentId);
        requireNonNull(startDateTime);

        this.resourceId = Reservation.normalizeResourceId(resourceId);
        this.studentId = studentId;
        this.startDateTime = startDateTime;
    }

    /**
     * Executes the cancel reservation command.
     *
     * @param model {@code Model} which the command should operate on.
     * @return A command result containing the success message.
     * @throws CommandException If no matching reservation exists.
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        String resolvedResourceId = Reservation.normalizeResourceId(model.resolveAlias(resourceId));

        Optional<Reservation> existingReservation = model.getReservationList().stream()
                .filter(reservation -> reservation.getResourceId().equals(resolvedResourceId)
                        && reservation.getStudentId().equals(studentId)
                        && reservation.getStartDateTime().equals(startDateTime))
                .findFirst();

        if (existingReservation.isEmpty()) {
            throw new CommandException(String.format(
                    MESSAGE_NOT_FOUND,
                    resolvedResourceId,
                    studentId,
                    formatStartDateTime()));
        }

        Reservation reservation = existingReservation.get();
        model.removeReservation(reservation);

        model.updatePersonDisplay(reservation.getStudentId());

        return new CommandResult(String.format(
                MESSAGE_SUCCESS,
                reservation.getResourceId(),
                reservation.getStudentId(),
                reservation.getFormattedStartDateTime(),
                reservation.getFormattedEndDateTime()));
    }

    private String formatStartDateTime() {
        return startDateTime.format(Reservation.DATE_TIME_FORMATTER);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof CancelReservationCommand)) {
            return false;
        }

        CancelReservationCommand otherCommand = (CancelReservationCommand) other;
        return resourceId.equals(otherCommand.resourceId)
                && studentId.equals(otherCommand.studentId)
                && startDateTime.equals(otherCommand.startDateTime);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("resourceId", resourceId)
                .add("studentId", studentId)
                .add("startDateTime", formatStartDateTime())
                .toString();
    }
}

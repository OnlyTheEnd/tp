package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.issue.IssueRecord;
import seedu.address.model.person.Person;
import seedu.address.model.person.StudentId;
import seedu.address.model.reservation.Reservation;

/**
 * Lists all loans and reservations for a specific student.
 */
public class CheckStudentLoansCommand extends Command {

    public static final String COMMAND_WORD = "check-s";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Checks loans and reservations for a student. "
            + "Parameters: MATRIC_NUMBER";

    public static final String MESSAGE_SUCCESS_HEADER = "Displaying loans and reservations for: %1$s (%2$s)";
    public static final String MESSAGE_NO_LOANS = "No existing loans.";
    public static final String MESSAGE_NO_RESERVATIONS = "No existing reservations.";
    public static final String MESSAGE_STUDENT_NOT_FOUND = "Unsuccessful: Cannot find user.";

    private final StudentId targetId;

    /**
     * Constructs a {@code CheckStudentLoansCommand} to view the records of a specific student.
     *
     * @param targetStudentId The student ID used to filter records.
     */
    public CheckStudentLoansCommand(StudentId targetId) {
        this.targetId = targetId;
    }

    /**
     * Executes the command by fetching associated records and generating a formatted output.
     *
     * @param model {@code Model} which the command should operate on.
     * @return A {@code CommandResult} containing the student's status report.
     * @throws CommandException If the target student does not exist in the system.
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        // Find the student in the system to get their name
        Person student = model.getFilteredPersonList().stream()
                .filter(p -> p.getStudentId().equals(targetId))
                .findFirst()
                .orElseThrow(() -> new CommandException(MESSAGE_STUDENT_NOT_FOUND));

        // Get all issue records matching this StudentId
        List<IssueRecord> loans = model.getIssueRecordList().stream()
                .filter(record -> record.getStudentId().equals(targetId))
                .collect(Collectors.toList());

        // Get all reservations matching this StudentId
        List<Reservation> reservations = model.getReservationList().stream()
                .filter(res -> res.getStudentId().equals(targetId))
                .collect(Collectors.toList());

        StringBuilder sb = new StringBuilder();
        sb.append(String.format(MESSAGE_SUCCESS_HEADER, student.getName(), targetId)).append("\n");

        // Active Loans
        sb.append("============\n");
        sb.append("LOANS\n");
        if (loans.isEmpty()) {
            sb.append(MESSAGE_NO_LOANS).append("\n");
        } else {
            loans.stream().map(this::formatLoan).forEach(s -> sb.append(s).append("\n"));
        }

        // Upcoming Reservations
        sb.append("\nRESERVATIONS\n");
        if (reservations.isEmpty()) {
            sb.append(MESSAGE_NO_RESERVATIONS).append("\n");
        } else {
            reservations.stream().map(this::formatReservation).forEach(s -> sb.append(s).append("\n"));
        }

        return new CommandResult(sb.toString().trim());
    }

    /**
     * Formats an individual loan record with an overdue check.
     */
    private String formatLoan(IssueRecord record) {
        String status = record.getDueDateTime().isBefore(LocalDateTime.now()) ? "[OVERDUE]" : "[BORROWED]";
        return String.format("%s %s | Due: %s",
                status, record.getItemId(), record.getFormattedDueDateTime());
    }

    /**
     * Formats an individual reservation record.
     */
    private String formatReservation(Reservation res) {
        return String.format("[RESERVED] %s | From: %s to %s",
                res.getResourceId(),
                res.getFormattedStartDateTime(),
                res.getFormattedEndDateTime());
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof CheckStudentLoansCommand
                && targetId.equals(((CheckStudentLoansCommand) other).targetId));
    }
}

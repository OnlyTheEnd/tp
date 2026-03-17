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

/**
 * Lists all loans for a specific student.
 */
public class CheckStudentLoansCommand extends Command {

    public static final String COMMAND_WORD = "check-s";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Checks loans for a student. "
            + "Parameters: MATRIC_NUMBER";

    public static final String MESSAGE_SUCCESS_HEADER = "Displaying Loans for: %1$s (%2$s)";
    public static final String MESSAGE_NO_LOANS = "No existing loans.";
    public static final String MESSAGE_STUDENT_NOT_FOUND = "Unsuccessful: Cannot find user.";

    private final StudentId targetId;

    public CheckStudentLoansCommand(StudentId targetId) {
        this.targetId = targetId;
    }

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

        if (loans.isEmpty()) {
            return new CommandResult(String.format(MESSAGE_SUCCESS_HEADER, student.getName(), targetId)
                    + "\n" + MESSAGE_NO_LOANS);
        }

        String result = loans.stream()
                .map(this::formatLoan)
                .collect(Collectors.joining("\n"));

        return new CommandResult(String.format(MESSAGE_SUCCESS_HEADER, student.getName(), targetId)
                + "\n" + result);
    }

    private String formatLoan(IssueRecord record) {
        String status = record.getDueDateTime().isBefore(LocalDateTime.now()) ? "[OVERDUE]" : "[BORROWED]";
        return String.format("%s %s | Due: %s",
                status, record.getItemId(), record.getFormattedDueDateTime());
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof CheckStudentLoansCommand // instanceof handles nulls
                && targetId.equals(((CheckStudentLoansCommand) other).targetId));
    }
}

package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Optional;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.person.StudentId;

/**
 * Deletes a student identified by their matric number from the address book.
 */
public class DeleteStudentCommand extends Command {
    public static final String COMMAND_WORD = "delete-s";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the student identified by the matric number.\n"
            + "Parameters: MATRIC_NUMBER\n"
            + "Example: " + COMMAND_WORD + " A0123456X";
    public static final String MESSAGE_DELETE_STUDENT_SUCCESS = "Student [%1$s - %2$s] "
            + "has been successfully removed from the system.";
    public static final String MESSAGE_STUDENT_NOT_FOUND = "Deletion unsuccessful. "
            + "No student found with matric number: %1$s";
    public static final String MESSAGE_HAS_ACTIVE_LOANS = "Deletion blocked. "
            + "Student has active loans or reservation.";

    private final StudentId targetId;

    /**
     * Deletes the student identified by the matric number.
     *
     * @param targetId The matric number of the student to be deleted.
     */
    public DeleteStudentCommand(StudentId targetId) {
        this.targetId = targetId;
    }

    /**
     * Executes the deletion logic.
     * Validates student existence and ensures no active loans or reservations exist before deletion.
     *
     * @param model {@code Model} which the command should operate on.
     * @return A {@code CommandResult} indicating the successful removal.
     * @throws CommandException If the student is not found or has active obligations.
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getAddressBook().getPersonList();

        // Find the student by Matric Number
        Optional<Person> studentToDelete = lastShownList.stream()
                .filter(person -> person.getStudentId().equals(targetId))
                .findFirst();

        if (studentToDelete.isEmpty()) {
            throw new CommandException(String.format(MESSAGE_STUDENT_NOT_FOUND, targetId));
        }

        Person personToDelete = studentToDelete.get();

        // Block deletion for active loans
        boolean hasActiveLoans = model.getAddressBook().getIssueRecordList().stream()
                .anyMatch(loan -> loan.getStudentId().equals(personToDelete.getStudentId()));


        if (hasActiveLoans) {
            throw new CommandException(MESSAGE_HAS_ACTIVE_LOANS);
        }

        boolean hasReservations = model.getAddressBook().getReservationList().stream()
                .anyMatch(res -> res.getStudentId().equals(personToDelete.getStudentId()));

        if (hasReservations) {
            throw new CommandException(MESSAGE_HAS_ACTIVE_LOANS);
        }

        model.deletePerson(personToDelete);
        return new CommandResult(String.format(MESSAGE_DELETE_STUDENT_SUCCESS,
                personToDelete.getName(), personToDelete.getStudentId()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof DeleteStudentCommand)) {
            return false;
        }

        DeleteStudentCommand otherDeleteCommand = (DeleteStudentCommand) other;
        return targetId.equals(otherDeleteCommand.targetId);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetId", targetId)
                .toString();
    }
}

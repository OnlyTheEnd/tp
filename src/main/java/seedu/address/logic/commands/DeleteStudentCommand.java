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
            + "Student has active loans that must be returned first.";

    private final StudentId targetId;

    /**
     * Deletes the student identified by the matric number.
     *
     * @param targetId The matric number of the student to be deleted.
     */
    public DeleteStudentCommand(StudentId targetId) {
        this.targetId = targetId;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getAddressBook().getPersonList();

        // 1. Find the student by Matric Number
        Optional<Person> studentToDelete = lastShownList.stream()
                .filter(person -> person.getStudentId().equals(targetId))
                .findFirst();

        if (studentToDelete.isEmpty()) {
            throw new CommandException(String.format(MESSAGE_STUDENT_NOT_FOUND, targetId));
        }

        Person personToDelete = studentToDelete.get();

        // Check for active loans to be implemented later

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

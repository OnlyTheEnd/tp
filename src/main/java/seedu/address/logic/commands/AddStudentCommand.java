package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STUDENT_ID;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Adds a student profile to the database.
 */
public class AddStudentCommand extends Command {

    public static final String COMMAND_WORD = "add-s";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a student profile. "
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_STUDENT_ID + "MATRIC_NUMBER "
            + PREFIX_PHONE + "PHONE_NUMBER "
            + PREFIX_EMAIL + "EMAIL\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "John "
            + PREFIX_STUDENT_ID + "A0100000A "
            + PREFIX_PHONE + "91234567 "
            + PREFIX_EMAIL + "e1234567@u.nus.edu";

    public static final String MESSAGE_SUCCESS = "New student added: \n"
            + "Name: %1$s | Matric number: %2$s \n"
            + "Phone number: %3$s | Email: %4$s";

    public static final String MESSAGE_DUPLICATE_STUDENT = "Unsuccessful. "
            + "Matric number, phone number or email already exist in the system.";

    private final Person toAdd;

    public AddStudentCommand(Person student) {
        requireNonNull(student);
        toAdd = student;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasPerson(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_STUDENT);
        }

        model.addPerson(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS,
                toAdd.getName(), toAdd.getStudentId(), toAdd.getPhone(), toAdd.getEmail()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddStudentCommand)) {
            return false;
        }

        AddStudentCommand otherAddStudentCommand = (AddStudentCommand) other;
        return toAdd.equals(otherAddStudentCommand.toAdd);
    }
}

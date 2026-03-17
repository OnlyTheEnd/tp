package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.DeleteStudentCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.StudentId;

/**
 * Parses input arguments and creates a new DeleteStudentCommand object
 */
public class DeleteStudentCommandParser implements Parser<DeleteStudentCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteStudentCommand
     * @param args
     * @return DeleteStudentCommand
     * @throws ParseException
     */
    public DeleteStudentCommand parse(String args) throws ParseException {
        try {
            StudentId studentId = ParserUtil.parseStudentId(args.trim());
            return new DeleteStudentCommand(studentId);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteStudentCommand.MESSAGE_USAGE), pe);
        }
    }
}

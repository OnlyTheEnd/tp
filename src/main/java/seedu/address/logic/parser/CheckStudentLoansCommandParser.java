package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.CheckStudentLoansCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.StudentId;

/**
 * Parses input arguments and creates a new CheckStudentLoansCommand object
 */
public class CheckStudentLoansCommandParser implements Parser<CheckStudentLoansCommand> {

    /**
     * Parses arguments
     * @param args
     * @return
     * @throws ParseException
     */
    public CheckStudentLoansCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();

        if (trimmedArgs.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    CheckStudentLoansCommand.MESSAGE_USAGE));
        }

        StudentId studentId = ParserUtil.parseStudentId(trimmedArgs);

        return new CheckStudentLoansCommand(studentId);
    }
}

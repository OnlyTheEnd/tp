package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.ListStudentCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
* Parses input arguments and creates a new {@code ListStudentCommand} object.
*/
public class ListStudentCommandParser implements Parser<ListStudentCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the {@code ListStudentCommand}
     * @param args
     * @return ListStudentCommand
     * @throws ParseException
     */
    public ListStudentCommand parse(String args) throws ParseException {
        if (!args.trim().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                                     ListStudentCommand.MESSAGE_USAGE));
        }
        return new ListStudentCommand();
    }
}

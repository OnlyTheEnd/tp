package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.time.LocalDateTime;

import seedu.address.logic.commands.IssueCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.issue.IssueRecord;
import seedu.address.model.person.StudentId;

/**
 * Parses input arguments and creates a new {@code IssueCommand} object.
 */
public class IssueCommandParser implements Parser<IssueCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the {@code IssueCommand}
     * and returns an {@code IssueCommand} object for execution.
     *
     * @param args Full user input arguments.
     * @return An {@code IssueCommand} containing the parsed issue record.
     * @throws ParseException If the user input does not conform to the expected format,
     *         or if any parsed value violates the constraints of the model.
     */
    @Override
    public IssueCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        String[] parts = trimmedArgs.split("\\s+");

        if (parts.length != 4) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, IssueCommand.MESSAGE_USAGE));
        }

        String itemId = parts[0];
        StudentId studentId = ParserUtil.parseStudentId(parts[1]);
        LocalDateTime dueDateTime = ParserUtil.parseDateTime(parts[2] + " " + parts[3]);

        try {
            IssueRecord issueRecord = new IssueRecord(itemId, studentId, dueDateTime);
            return new IssueCommand(issueRecord);
        } catch (IllegalArgumentException iae) {
            throw new ParseException(iae.getMessage(), iae);
        }
    }
}

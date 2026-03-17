package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteRoomCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeleteRoomCommand object
 */
public class DeleteRoomCommandParser implements Parser<DeleteRoomCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteRoomCommand
     * and returns a DeleteRoomCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteRoomCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new DeleteRoomCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteRoomCommand.MESSAGE_USAGE), pe);
        }
    }
}

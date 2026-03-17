package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.ListRoomCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ListRoomCommand object
 */
public class ListRoomCommandParser implements Parser<ListRoomCommand> {

    /**
     * Parses the given {@code String} of arguments and returns a ListRoomCommand object.
     */
    public ListRoomCommand parse(String args) throws ParseException {
        if (!args.trim().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListRoomCommand.MESSAGE_USAGE));
        }
        return new ListRoomCommand();
    }
}

package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.FilterTagCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.tag.Tag;


/**
 * Parses input arguments and creates a new FilterTagCommand object
 */
public class FilterTagCommandParser implements Parser<FilterTagCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the FilterTagCommand
     * and returns an FilterTagCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public FilterTagCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        String[] parts = trimmedArgs.split("\\s+");

        if (parts.length != 2) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterTagCommand.MESSAGE_USAGE));
        }

        String flag = parts[0];
        String tagName = parts[1];
        try {
            return new FilterTagCommand(flag, new Tag(tagName));
        } catch (IllegalArgumentException iae) {
            throw new ParseException(iae.getMessage(), iae);
        }

    }



}

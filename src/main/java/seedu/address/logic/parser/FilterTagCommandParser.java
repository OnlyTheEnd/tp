package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LOCATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.stream.Stream;

import seedu.address.logic.commands.AddTagCommand;
import seedu.address.logic.commands.FilterTagCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.tag.Tag;


/**
 * Parses input arguments and creates a new FilterTagCommand object
 */
public class FilterTagCommandParser implements Parser<FilterTagCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the AddTagCommand
     * and returns an FilterTagCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public FilterTagCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args,
                        PREFIX_CATEGORY, PREFIX_TAG, PREFIX_LOCATION);

        if (!argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTagCommand.MESSAGE_USAGE));
        }

        boolean hasLocation = arePrefixesPresent(argMultimap, PREFIX_LOCATION, PREFIX_TAG);
        boolean hasEquipment = arePrefixesPresent(argMultimap, PREFIX_CATEGORY, PREFIX_TAG);

        argMultimap.verifyNoDuplicatePrefixesFor(
                PREFIX_LOCATION, PREFIX_TAG, PREFIX_CATEGORY);

        if ((!hasLocation && !hasEquipment) || (hasLocation && hasEquipment)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTagCommand.MESSAGE_USAGE));
        }
        if (hasLocation) {
            Tag tag = ParserUtil.parseTag(argMultimap.getValue(PREFIX_TAG).get());
            return new FilterTagCommand("Room", tag);
        } else {
            Tag tag = ParserUtil.parseTag(argMultimap.getValue(PREFIX_TAG).get());
            return new FilterTagCommand("Equipment", tag);
        }
    }

    /**
     * Returns true if all prefixes contain non-empty values.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }



}

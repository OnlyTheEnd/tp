package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LOCATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STATUS;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditRoomCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new EditRoomCommand object
 */
public class EditRoomCommandParser implements Parser<EditRoomCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditRoomCommand
     * and returns an EditRoomCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditRoomCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_LOCATION, PREFIX_STATUS);

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_LOCATION, PREFIX_STATUS);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    EditRoomCommand.MESSAGE_USAGE), pe);
        }

        EditRoomCommand.EditRoomDescriptor editRoomDescriptor = new EditRoomCommand.EditRoomDescriptor();

        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            editRoomDescriptor.setName(ParserUtil.parseRoomName(argMultimap.getValue(PREFIX_NAME).get()));
        }
        if (argMultimap.getValue(PREFIX_LOCATION).isPresent()) {
            editRoomDescriptor.setLocation(ParserUtil.parseLocation(argMultimap.getValue(PREFIX_LOCATION).get()));
        }
        if (argMultimap.getValue(PREFIX_STATUS).isPresent()) {
            editRoomDescriptor.setStatus(ParserUtil.parseStatus(argMultimap.getValue(PREFIX_STATUS).get()));
        }

        if (!editRoomDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditRoomCommand.MESSAGE_NOT_EDITED);
        }

        return new EditRoomCommand(index, editRoomDescriptor);
    }
}

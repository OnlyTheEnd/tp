package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LOCATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STATUS;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditEquipmentCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new EditEquipmentCommand object
 */
public class EditEquipmentCommandParser implements Parser<EditEquipmentCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditEquipmentCommand
     * and returns an EditEquipmentCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditEquipmentCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_CATEGORY, PREFIX_STATUS);

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_CATEGORY, PREFIX_STATUS);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    EditEquipmentCommand.MESSAGE_USAGE), pe);
        }

        EditEquipmentCommand.EditEquipmentDescriptor editEquipmentDescriptor =
                new EditEquipmentCommand.EditEquipmentDescriptor();

        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            editEquipmentDescriptor.setName(ParserUtil.parseEquipmentName(
                    argMultimap.getValue(PREFIX_NAME).get().trim()));
        }
        if (argMultimap.getValue(PREFIX_CATEGORY).isPresent()) {
            editEquipmentDescriptor.setCategory(ParserUtil.parseCategory(
                    argMultimap.getValue(PREFIX_CATEGORY).get().trim()));
        }
        if (argMultimap.getValue(PREFIX_STATUS).isPresent()) {
            editEquipmentDescriptor.setStatus(ParserUtil.parseEquipmentStatus(
                    argMultimap.getValue(PREFIX_STATUS).get().trim()));
        }

        if (!editEquipmentDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditEquipmentCommand.MESSAGE_NOT_EDITED);
        }

        return new EditEquipmentCommand(index, editEquipmentDescriptor);
    }
}

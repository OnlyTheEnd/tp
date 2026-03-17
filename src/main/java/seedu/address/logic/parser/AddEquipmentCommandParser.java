package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STATUS;

import seedu.address.logic.commands.AddEquipmentCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.equipment.Equipment;
import seedu.address.model.equipment.EquipmentName;
import seedu.address.model.equipment.EquipmentStatus;

/**
 * Parses input arguments and creates a new AddEquipmentCommand object
 */
public class AddEquipmentCommandParser implements Parser<AddEquipmentCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the AddEquipmentCommand
     * and returns an AddEquipmentCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddEquipmentCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_CATEGORY, PREFIX_STATUS);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_CATEGORY, PREFIX_STATUS)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    AddEquipmentCommand.MESSAGE_USAGE));
        }

        EquipmentName name = ParserUtil.parseEquipmentName(argMultimap.getValue(PREFIX_NAME).get());
        String category = argMultimap.getValue(PREFIX_CATEGORY).get();
        EquipmentStatus status = ParserUtil.parseEquipmentStatus(argMultimap.getValue(PREFIX_STATUS).get());

        Equipment equipment = new Equipment(name, category, status);
        return new AddEquipmentCommand(equipment);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return java.util.stream.Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}

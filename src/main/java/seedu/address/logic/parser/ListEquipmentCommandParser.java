package seedu.address.logic.parser;

import seedu.address.logic.commands.ListEquipmentCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ListEquipmentCommand object
 */
public class ListEquipmentCommandParser implements Parser<ListEquipmentCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ListEquipmentCommand
     * and returns a ListEquipmentCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ListEquipmentCommand parse(String args) throws ParseException {
        // Even if there are arguments, we ignore them and just return the command.
        // Or you can throw a ParseException if args.trim() is not empty.
        return new ListEquipmentCommand();
    }
}

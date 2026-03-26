package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LOCATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.stream.Stream;

import seedu.address.logic.commands.DeleteTagCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.equipment.Equipment;
import seedu.address.model.equipment.EquipmentName;
import seedu.address.model.room.Room;
import seedu.address.model.room.RoomName;
import seedu.address.model.tag.Tag;


/**
 * Parses input arguments and creates a new AddCommand object
 */
public class DeleteTagCommandParser implements Parser<DeleteTagCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteTagCommand
     * and returns an AddCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteTagCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args,
                        PREFIX_LOCATION, PREFIX_TAG, PREFIX_CATEGORY);

        if (!argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteTagCommand.MESSAGE_USAGE));
        }

        boolean hasLocation = arePrefixesPresent(argMultimap, PREFIX_LOCATION, PREFIX_TAG);
        boolean hasEquipment = arePrefixesPresent(argMultimap, PREFIX_CATEGORY, PREFIX_TAG);

        argMultimap.verifyNoDuplicatePrefixesFor(
                PREFIX_LOCATION, PREFIX_TAG, PREFIX_CATEGORY);

        //verify either location or equipment with tag
        if ((!hasLocation && !hasEquipment) || (hasLocation && hasEquipment)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteTagCommand.MESSAGE_USAGE));
        }
        if (hasLocation) {
            RoomName roomName = ParserUtil.parseRoomName(argMultimap.getValue(PREFIX_LOCATION).get());
            Tag roomTag = ParserUtil.parseTag(argMultimap.getValue(PREFIX_TAG).get());
            return new DeleteTagCommand(new Room(roomName), roomTag);
        } else {
            EquipmentName equipmentName = ParserUtil.parseEquipmentName(argMultimap.getValue(PREFIX_CATEGORY).get());
            Tag equipmentTag = ParserUtil.parseTag(argMultimap.getValue(PREFIX_TAG).get());
            return new DeleteTagCommand(new Equipment(equipmentName), equipmentTag);
        }
    }

    /**
     * Returns true if all prefixes contain non-empty values.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}

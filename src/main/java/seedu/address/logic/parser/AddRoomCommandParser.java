package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LOCATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STATUS;

import seedu.address.logic.commands.AddRoomCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.room.Location;
import seedu.address.model.room.Room;
import seedu.address.model.room.RoomName;
import seedu.address.model.room.Status;

/**
 * Parses input arguments and creates a new AddRoomCommand object
 */
public class AddRoomCommandParser implements Parser<AddRoomCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddRoomCommand
     * and returns an AddRoomCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddRoomCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_LOCATION, PREFIX_STATUS);

        // Check if mandatory prefixes (n/ and l/) are present and preamble is empty
        if (!argMultimap.getValue(PREFIX_NAME).isPresent()
                || !argMultimap.getValue(PREFIX_LOCATION).isPresent()
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddRoomCommand.MESSAGE_USAGE));
        }

        RoomName name = ParserUtil.parseRoomName(argMultimap.getValue(PREFIX_NAME).get());
        Location location = ParserUtil.parseLocation(argMultimap.getValue(PREFIX_LOCATION).get());
        Status status = ParserUtil.parseStatus(argMultimap.getValue(PREFIX_STATUS).get());

        Room room = new Room(name, location, status);

        return new AddRoomCommand(room);
    }
}

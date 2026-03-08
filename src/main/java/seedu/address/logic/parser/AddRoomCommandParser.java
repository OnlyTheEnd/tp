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

public class AddRoomCommandParser implements Parser<AddRoomCommand> {

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

        // Status is optional, defaults to "Available" if not provided
        Status status = new Status(argMultimap.getValue(PREFIX_STATUS).orElse("Available"));

        Room room = new Room(name, location, status);

        return new AddRoomCommand(room);
    }
}
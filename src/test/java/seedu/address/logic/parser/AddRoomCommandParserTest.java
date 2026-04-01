package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LOCATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STATUS;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AddRoomCommand;
import seedu.address.model.room.Location;
import seedu.address.model.room.Room;
import seedu.address.model.room.RoomName;
import seedu.address.testutil.RoomBuilder;

public class AddRoomCommandParserTest {
    private AddRoomCommandParser parser = new AddRoomCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Room expectedRoom = new RoomBuilder()
                .withName("MPSH-1")
                .withLocation("Kent-Ridge")
                .withStatus("Available")
                .build();

        assertParseSuccess(parser, " " + PREFIX_NAME + "MPSH-1 " + PREFIX_LOCATION + "Kent-Ridge",
                new AddRoomCommand(expectedRoom));

        assertParseSuccess(parser, " " + PREFIX_NAME + "mpsh-1 " + PREFIX_LOCATION + "kent-ridge",
                new AddRoomCommand(expectedRoom));
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, " " + PREFIX_NAME + "MPSH#1 " + PREFIX_LOCATION + "Kent-Ridge",
                RoomName.MESSAGE_CONSTRAINTS);

        assertParseFailure(parser, " " + PREFIX_NAME + "MPSH-1 " + PREFIX_LOCATION + "Kent@Ridge",
                Location.MESSAGE_CONSTRAINTS);

        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddRoomCommand.MESSAGE_USAGE);
        assertParseFailure(parser, " " + PREFIX_NAME + "MPSH-1" + PREFIX_LOCATION + "Kent-Ridge"
                + PREFIX_STATUS + "Available", expectedMessage);
    }
}

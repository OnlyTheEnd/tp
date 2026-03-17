package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LOCATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STATUS;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalRooms.ROOM_B;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AddRoomCommand;
import seedu.address.model.room.Room;
import seedu.address.testutil.RoomBuilder;

public class AddRoomCommandParserTest {
    private AddRoomCommandParser parser = new AddRoomCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Room expectedRoom = new RoomBuilder(ROOM_B).build();

        // Testing with ROOM_B which contains hyphens and numbers
        assertParseSuccess(parser, " " + PREFIX_NAME + "Sports-Hall-2 "
                        + PREFIX_LOCATION + "University-Town "
                        + PREFIX_STATUS + "Maintenance",
                new AddRoomCommand(expectedRoom));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddRoomCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, " Sports-Hall-2 l/University-Town s/Maintenance", expectedMessage);

        // missing location prefix
        assertParseFailure(parser, " n/Sports-Hall-2 University-Town s/Maintenance", expectedMessage);
    }
}

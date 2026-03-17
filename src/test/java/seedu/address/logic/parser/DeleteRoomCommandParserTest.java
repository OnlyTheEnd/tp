package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_ROOM;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.DeleteRoomCommand;

public class DeleteRoomCommandParserTest {
    private DeleteRoomCommandParser parser = new DeleteRoomCommandParser();

    @Test
    public void parse_validArgs_returnsDeleteRoomCommand() {
        assertParseSuccess(parser, "1", new DeleteRoomCommand(INDEX_FIRST_ROOM));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        // Test non-numeric input
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DeleteRoomCommand.MESSAGE_USAGE));

        // Test empty input
        assertParseFailure(parser, "", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DeleteRoomCommand.MESSAGE_USAGE));
    }
}

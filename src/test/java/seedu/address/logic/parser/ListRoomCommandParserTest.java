package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.ListRoomCommand;

public class ListRoomCommandParserTest {

    private ListRoomCommandParser parser = new ListRoomCommandParser();

    @Test
    public void parse_emptyArgs_returnsListRoomCommand() {
        assertParseSuccess(parser, "", new ListRoomCommand());
    }

    @Test
    public void parse_invalidArgs_returnsListRoomCommand() {
        assertParseSuccess(parser, "  ", new ListRoomCommand());
    }

    @Test
    public void parse_validArgs_returnsListRoomCommand() {
        assertParseSuccess(parser, "", new ListRoomCommand());
    }

    @Test
    public void parse_extraWhitespace_returnsListRoomCommand() {
        assertParseSuccess(parser, "    \n \t", new ListRoomCommand());
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, " extra_arguments",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListRoomCommand.MESSAGE_USAGE));
    }
}

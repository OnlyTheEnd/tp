package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_EQUIPMENT;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.DeleteEquipmentCommand;

public class DeleteEquipmentCommandParserTest {

    private DeleteEquipmentCommandParser parser = new DeleteEquipmentCommandParser();

    @Test
    public void parse_validArgs_returnsDeleteCommand() {
        assertParseSuccess(parser, "1", new DeleteEquipmentCommand(INDEX_FIRST_EQUIPMENT));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        // Non-numeric input
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DeleteEquipmentCommand.MESSAGE_USAGE));

        // Empty input
        assertParseFailure(parser, "", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DeleteEquipmentCommand.MESSAGE_USAGE));
    }
}

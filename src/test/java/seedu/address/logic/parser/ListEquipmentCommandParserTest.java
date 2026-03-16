package seedu.address.logic.parser;

import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.ListEquipmentCommand;

public class ListEquipmentCommandParserTest {

    private ListEquipmentCommandParser parser = new ListEquipmentCommandParser();

    @Test
    public void parse_emptyArgs_returnsListEquipmentCommand() {
        assertParseSuccess(parser, "", new ListEquipmentCommand());
    }

    @Test
    public void parse_whitespaceArgs_returnsListEquipmentCommand() {
        assertParseSuccess(parser, "  ", new ListEquipmentCommand());
    }
}

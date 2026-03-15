package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AddEquipmentCommand;
import seedu.address.model.equipment.Equipment;
import seedu.address.model.equipment.EquipmentName;
import seedu.address.model.equipment.EquipmentStatus;

public class AddEquipmentCommandParserTest {
    private AddEquipmentCommandParser parser = new AddEquipmentCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Equipment expectedEquipment = new Equipment(new EquipmentName("Wilson"),
                "Basketball", EquipmentStatus.AVAILABLE);

        assertParseSuccess(parser, " n/Wilson c/Basketball s/Available",
                new AddEquipmentCommand(expectedEquipment));

        assertParseSuccess(parser, " n/Wilson c/Basketball s/aVaIlAbLe",
                new AddEquipmentCommand(expectedEquipment));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                AddEquipmentCommand.MESSAGE_USAGE);

        assertParseFailure(parser, " c/Basketball", expectedMessage);

        assertParseFailure(parser, " n/Wilson", expectedMessage);
    }
}


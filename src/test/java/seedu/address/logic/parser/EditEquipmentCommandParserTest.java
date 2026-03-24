package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_EQUIPMENT;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_EQUIPMENT;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditEquipmentCommand;
import seedu.address.model.equipment.EquipmentStatus;
import seedu.address.testutil.EditEquipmentDescriptorBuilder;

public class EditEquipmentCommandParserTest {
    private EditEquipmentCommandParser parser = new EditEquipmentCommandParser();

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND_EQUIPMENT;
        String userInput = targetIndex.getOneBased() + " n/Wilson-Evolution c/Basketball s/Available";

        EditEquipmentCommand.EditEquipmentDescriptor descriptor = new EditEquipmentDescriptorBuilder()
                .withName("Wilson-Evolution").withCategory("Basketball")

                .withStatus(EquipmentStatus.AVAILABLE).build();
        EditEquipmentCommand expectedCommand = new EditEquipmentCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        Index targetIndex = INDEX_FIRST_EQUIPMENT;
        String userInput = targetIndex.getOneBased() + " s/Booked";

        EditEquipmentCommand.EditEquipmentDescriptor descriptor = new EditEquipmentDescriptorBuilder()
                .withStatus(EquipmentStatus.BOOKED).build();
        EditEquipmentCommand expectedCommand = new EditEquipmentCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_missingParts_failure() {
        assertParseFailure(parser, "n/Basketball",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditEquipmentCommand.MESSAGE_USAGE));

        assertParseFailure(parser, "1", EditEquipmentCommand.MESSAGE_NOT_EDITED);
    }
}

package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_ROOM;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_ROOM;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditRoomCommand;
import seedu.address.logic.commands.EditRoomCommand.EditRoomDescriptor;
import seedu.address.testutil.EditRoomDescriptorBuilder;

public class EditRoomCommandParserTest {
    private EditRoomCommandParser parser = new EditRoomCommandParser();

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND_ROOM;
        String userInput = targetIndex.getOneBased() + " n/MPSH-2 l/Sports-Centre s/Booked";

        EditRoomDescriptor descriptor = new EditRoomDescriptorBuilder()
                .withName("MPSH-2")
                .withLocation("Sports-Centre")
                .withStatus("Booked")
                .build();
        EditRoomCommand expectedCommand = new EditRoomCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        Index targetIndex = INDEX_FIRST_ROOM;
        String userInput = targetIndex.getOneBased() + " s/Maintenance";

        EditRoomDescriptor descriptor = new EditRoomDescriptorBuilder()
                .withStatus("Maintenance")
                .build();
        EditRoomCommand expectedCommand = new EditRoomCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_missingParts_failure() {
        assertParseFailure(parser, "n/MPSH-1",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditRoomCommand.MESSAGE_USAGE));

        assertParseFailure(parser, "1", EditRoomCommand.MESSAGE_NOT_EDITED);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        assertParseFailure(parser, "-5 n/MPSH-1",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditRoomCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "0 n/MPSH-1",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditRoomCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "1 some random string",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditRoomCommand.MESSAGE_USAGE));
    }
}

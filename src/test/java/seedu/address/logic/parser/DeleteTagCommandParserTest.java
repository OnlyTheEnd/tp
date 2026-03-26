package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LOCATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.DeleteTagCommand;
import seedu.address.model.equipment.Equipment;
import seedu.address.model.equipment.EquipmentName;
import seedu.address.model.room.Room;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.RoomBuilder;

public class DeleteTagCommandParserTest {
    private static final String VALID_EQUIPMENT_ID = "Wilson-Evolution-Basketball-1";
    private static final String VALID_ROOM_ID = "Sports-Hall-2";
    private static final String VALID_TAG = "Valid";

    private static final Equipment expectedEquipment = new Equipment(new EquipmentName(VALID_EQUIPMENT_ID));
    private static final Room expectedRoom = new RoomBuilder().withName(VALID_ROOM_ID)
            .withLocation("placeholder").build();

    private static final String INVALID_ARGUMENTS = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
            DeleteTagCommand.MESSAGE_USAGE);

    private final DeleteTagCommandParser parser = new DeleteTagCommandParser();

    @Test
    public void parse_allFields_present() {
        //Tag Equipment
        assertParseSuccess(parser, " " + PREFIX_CATEGORY + VALID_EQUIPMENT_ID + " " + PREFIX_TAG + VALID_TAG,
                new DeleteTagCommand(expectedEquipment, new Tag(VALID_TAG)));
        //Tag Room
        assertParseSuccess(parser, " " + PREFIX_LOCATION + VALID_ROOM_ID + " " + PREFIX_TAG + VALID_TAG,
                new DeleteTagCommand(expectedRoom, new Tag(VALID_TAG)));
    }

    @Test
    public void parse_compulsoryMissing_failure() {
        //No Prefixes
        assertParseFailure(parser, " " + VALID_ROOM_ID + VALID_TAG, INVALID_ARGUMENTS);
        //No fields
        assertParseFailure(parser, " " + PREFIX_LOCATION + PREFIX_TAG, INVALID_ARGUMENTS);
    }

    @Test
    public void parse_duplicateFields_failure() {
        //Too many prefixes
        assertParseFailure(parser, " " + PREFIX_CATEGORY + VALID_EQUIPMENT_ID + " " + PREFIX_LOCATION
                + VALID_ROOM_ID + " " + PREFIX_TAG + VALID_TAG, INVALID_ARGUMENTS);
    }
}

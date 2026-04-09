package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AddTagCommand;
import seedu.address.model.equipment.Equipment;
import seedu.address.model.equipment.EquipmentName;
import seedu.address.model.room.Room;
import seedu.address.model.room.RoomName;
import seedu.address.model.tag.Tag;

public class AddTagCommandParserTest {
    private static final String VALID_EQUIPMENT_ID = "Wilson-Evolution-Basketball-1";
    private static final String VALID_ROOM_ID = "Sports-Hall-2";
    private static final String VALID_TAG = "Valid";

    private static final Equipment expectedEquipment = new Equipment(new EquipmentName(VALID_EQUIPMENT_ID));
    private static final Room expectedRoom = new Room(new RoomName(VALID_ROOM_ID));

    private static final String INVALID_ARGUMENTS = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
            AddTagCommand.MESSAGE_USAGE);

    private final AddTagCommandParser parser = new AddTagCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        // Tag Equipment
        assertParseSuccess(parser, " equipment " + VALID_EQUIPMENT_ID + " " + VALID_TAG,
                new AddTagCommand(expectedEquipment, new Tag(VALID_TAG)));

        // Tag Room
        assertParseSuccess(parser, " room " + VALID_ROOM_ID + " " + VALID_TAG,
                new AddTagCommand(expectedRoom, new Tag(VALID_TAG)));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        // Missing flag
        assertParseFailure(parser, " " + VALID_ROOM_ID + " " + VALID_TAG, INVALID_ARGUMENTS);

        // Missing name
        assertParseFailure(parser, " room " + VALID_TAG, INVALID_ARGUMENTS);

        // Missing tag
        assertParseFailure(parser, " room " + VALID_ROOM_ID, INVALID_ARGUMENTS);

        // No fields at all
        assertParseFailure(parser, "", INVALID_ARGUMENTS);

        // Only flag
        assertParseFailure(parser, " room", INVALID_ARGUMENTS);
    }

    @Test
    public void parse_tooManyArguments_failure() {
        // Too many arguments
        assertParseFailure(parser, " room " + VALID_ROOM_ID + " " + VALID_TAG + " extra", INVALID_ARGUMENTS);
    }

    @Test
    public void parse_whitespaceHandling_success() {
        // Multiple spaces between arguments
        assertParseSuccess(parser, "  room   " + VALID_ROOM_ID + "   " + VALID_TAG,
                new AddTagCommand(expectedRoom, new Tag(VALID_TAG)));

        // Tab characters
        assertParseSuccess(parser, "\tequipment\t" + VALID_EQUIPMENT_ID + "\t" + VALID_TAG,
                new AddTagCommand(expectedEquipment, new Tag(VALID_TAG)));
    }

    @Test
    public void parse_invalidRoomName_failure() {
        //  empty room name is invalid
        assertParseFailure(parser, " room  " + VALID_TAG, INVALID_ARGUMENTS);
    }

    @Test
    public void parse_invalidEquipmentName_failure() {
        //  empty equipment name is invalid
        assertParseFailure(parser, " equipment  " + VALID_TAG, INVALID_ARGUMENTS);
    }
}

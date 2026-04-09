package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FilterTagCommand;
import seedu.address.model.tag.Tag;

public class FilterTagCommandParserTest {
    private static final String VALID_TAG = "maintenance";
    private static final String ANOTHER_VALID_TAG = "urgent";

    private static final String INVALID_ARGUMENTS = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
            FilterTagCommand.MESSAGE_USAGE);

    private final FilterTagCommandParser parser = new FilterTagCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        // Filter rooms by tag
        assertParseSuccess(parser, " room " + VALID_TAG,
                new FilterTagCommand("room", new Tag(VALID_TAG)));

        // Filter equipment by tag
        assertParseSuccess(parser, " equipment " + ANOTHER_VALID_TAG,
                new FilterTagCommand("equipment", new Tag(ANOTHER_VALID_TAG)));
    }

    @Test
    public void parse_tooManyArguments_failure() {
        // Too many arguments
        assertParseFailure(parser, " room  " + VALID_TAG + " extra", INVALID_ARGUMENTS);

        // Multiple tags
        assertParseFailure(parser, " equipment  " + VALID_TAG + " " + ANOTHER_VALID_TAG, INVALID_ARGUMENTS);
    }

    @Test
    public void parse_whitespaceHandling_success() {
        // Multiple spaces between arguments
        assertParseSuccess(parser, "   room    " + VALID_TAG,
                new FilterTagCommand("room", new Tag(VALID_TAG)));

        // Tab characters
        assertParseSuccess(parser, "\t equipment \t" + ANOTHER_VALID_TAG,
                new FilterTagCommand("equipment", new Tag(ANOTHER_VALID_TAG)));
    }

    @Test
    public void parse_invalidTagName_failure() {
        // Tag with space (not alphanumeric) - will be caught as too many arguments
        assertParseFailure(parser, " room  Invalid Tag", INVALID_ARGUMENTS);

        // Tag with special characters
        assertParseFailure(parser, " room  Invalid@Tag", Tag.MESSAGE_CONSTRAINTS);

        // Empty tag
        assertParseFailure(parser, " room  ", INVALID_ARGUMENTS);
    }
}

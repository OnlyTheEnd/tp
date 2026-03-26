package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LOCATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FilterTagCommand;
import seedu.address.model.tag.Tag;

public class FilterTagCommandParserTest {
    private static final String VALID_TAG = "Valid";

    private static final String INVALID_ARGUMENTS = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
            FilterTagCommand.MESSAGE_USAGE);
    private final FilterTagCommandParser parser = new FilterTagCommandParser();

    @Test
    public void parse_allFields_present() {
        assertParseSuccess(parser, " " + PREFIX_CATEGORY + " " + PREFIX_TAG + VALID_TAG,
                new FilterTagCommand("Equipment", new Tag(VALID_TAG)));
        assertParseSuccess(parser, " " + PREFIX_LOCATION + " " + PREFIX_TAG + VALID_TAG,
                new FilterTagCommand("Room", new Tag(VALID_TAG)));
    }

    @Test
    public void parse_compulsoryMissing_failure() {
        assertParseFailure(parser, " ", INVALID_ARGUMENTS);
        assertParseFailure(parser, " " + PREFIX_TAG + " " + VALID_TAG, INVALID_ARGUMENTS);
    }

    @Test
    public void parse_duplicateFields_failure() {
        //Too many prefixes
        assertParseFailure(parser, " " + PREFIX_CATEGORY + PREFIX_LOCATION + PREFIX_TAG + VALID_TAG,
                INVALID_ARGUMENTS);
    }


}

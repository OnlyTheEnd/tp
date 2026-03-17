package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AliasCommand;
import seedu.address.model.alias.AliasMapping;

/**
 * Tests for {@link AliasCommandParser}.
 */
public class AliasCommandParserTest {

    private static final String VALID_TARGET_ID = "Wilson-Evolution-Basketball-1";
    private static final String VALID_ALIAS_NAME = "b1";

    private final AliasCommandParser parser = new AliasCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        AliasMapping aliasMapping = new AliasMapping(VALID_TARGET_ID, VALID_ALIAS_NAME);

        assertParseSuccess(parser, " " + VALID_TARGET_ID + " " + VALID_ALIAS_NAME,
                new AliasCommand(aliasMapping));
    }

    @Test
    public void parse_missingFields_failure() {
        assertParseFailure(parser, " " + VALID_TARGET_ID,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AliasCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_tooManyFields_failure() {
        assertParseFailure(parser, " " + VALID_TARGET_ID + " " + VALID_ALIAS_NAME + " extra",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AliasCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidTargetId_failure() {
        assertParseFailure(parser, " !invalid " + VALID_ALIAS_NAME,
                AliasMapping.MESSAGE_TARGET_ID_CONSTRAINTS);
    }

    @Test
    public void parse_invalidAliasName_failure() {
        assertParseFailure(parser, " " + VALID_TARGET_ID + " b-1",
                AliasMapping.MESSAGE_ALIAS_NAME_CONSTRAINTS);
    }
}

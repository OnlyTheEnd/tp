package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;

/**
 * Tests for {@link HelpCommand}.
 */
public class HelpCommandTest {

    private final Model model = new ModelManager();

    @Test
    public void execute_generalHelp_success() throws Exception {
        HelpCommand helpCommand = new HelpCommand();

        CommandResult commandResult = helpCommand.execute(model);

        assertEquals(true, commandResult.getFeedbackToUser().contains("Available commands:"));
        assertEquals(true, commandResult.getFeedbackToUser().contains("help COMMAND"));
    }

    @Test
    public void execute_specificHelp_success() throws Exception {
        HelpCommand helpCommand = new HelpCommand("tag");

        CommandResult commandResult = helpCommand.execute(model);

        assertEquals(true, commandResult.getFeedbackToUser().contains("SUCCESS! TAG COMMAND FOUND"));
        assertEquals(true, commandResult.getFeedbackToUser().contains("Format: tag"));
    }

    @Test
    public void execute_unknownCommand_throwsCommandException() {
        HelpCommand helpCommand = new HelpCommand("undo");

        assertThrows(CommandException.class,
                "FAILURE! UNDO COMMAND NOT FOUND", () -> helpCommand.execute(model));
    }

    @Test
    public void equals() {
        assertEquals(new HelpCommand(), new HelpCommand());
        assertEquals(new HelpCommand("tag"), new HelpCommand("tag"));
    }
}

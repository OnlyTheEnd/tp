package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
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

        assertTrue(commandResult.getFeedbackToUser().contains("Available commands:"));
        assertTrue(commandResult.getFeedbackToUser().contains("help COMMAND"));
        assertTrue(commandResult.getFeedbackToUser().contains("cancel"));
        assertTrue(commandResult.getFeedbackToUser().contains("return"));
    }

    @Test
    public void execute_specificHelp_success() throws Exception {
        HelpCommand helpCommand = new HelpCommand("cancel");

        CommandResult commandResult = helpCommand.execute(model);

        assertTrue(commandResult.getFeedbackToUser().contains("SUCCESS! CANCEL COMMAND FOUND"));
        assertTrue(commandResult.getFeedbackToUser().contains(CancelReservationCommand.MESSAGE_USAGE));
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
        assertEquals(new HelpCommand("cancel"), new HelpCommand("cancel"));
    }
}

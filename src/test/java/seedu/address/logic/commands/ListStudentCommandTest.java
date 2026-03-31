package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListCommand.
 */
public class ListStudentCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        CommandResult expectedCommandResult = new CommandResult(
                ListStudentCommand.MESSAGE_SUCCESS, false, false, true, true, true);

        assertCommandSuccess(new ListStudentCommand(), model, expectedCommandResult, expectedModel);
    }

    @Test
    public void equals() {
        ListStudentCommand listStudentCommand = new ListStudentCommand();

        // same object -> returns true
        assertTrue(listStudentCommand.equals(listStudentCommand));

        // same values -> returns true
        assertTrue(listStudentCommand.equals(new ListStudentCommand()));

        // different types -> returns false
        assertFalse(listStudentCommand.equals(1));

        // null -> returns false
        assertFalse(listStudentCommand.equals(null));
    }
}

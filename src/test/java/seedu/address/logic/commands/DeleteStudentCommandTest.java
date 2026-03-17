package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BOB;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.StudentId;

/**
 * Contains integration tests (interaction with the Model) and unit tests for {@code DeleteStudentCommand}.
 */
public class DeleteStudentCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validStudentIdUnfilteredList_success() {
        StudentId idToDelete = ALICE.getStudentId();
        DeleteStudentCommand deleteCommand = new DeleteStudentCommand(idToDelete);

        String expectedMessage = String.format(DeleteStudentCommand.MESSAGE_DELETE_STUDENT_SUCCESS,
                ALICE.getName(), ALICE.getStudentId());

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deletePerson(ALICE);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_nonExistentStudentIdUnfilteredList_throwsCommandException() {
        StudentId nonExistentId = new StudentId("A9999999Z");
        DeleteStudentCommand deleteCommand = new DeleteStudentCommand(nonExistentId);

        assertCommandFailure(deleteCommand, model,
                String.format(DeleteStudentCommand.MESSAGE_STUDENT_NOT_FOUND, nonExistentId));
    }

    @Test
    public void execute_validStudentIdFilteredList_success() {
        // Filter list so Alice is NOT visible, but she is still in the AddressBook
        model.updateFilteredPersonList(p -> p.equals(BOB));
        assertFalse(model.getFilteredPersonList().contains(ALICE));

        StudentId idToDelete = ALICE.getStudentId();
        DeleteStudentCommand deleteCommand = new DeleteStudentCommand(idToDelete);

        String expectedMessage = String.format(DeleteStudentCommand.MESSAGE_DELETE_STUDENT_SUCCESS,
                ALICE.getName(), ALICE.getStudentId());

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deletePerson(ALICE);

        expectedModel.updateFilteredPersonList(p -> p.equals(BOB));
        // Success because deletion is by ID, not by visible index
        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void equals() {
        StudentId firstId = new StudentId("A1111111A");
        StudentId secondId = new StudentId("B2222222B");
        DeleteStudentCommand deleteFirstCommand = new DeleteStudentCommand(firstId);
        DeleteStudentCommand deleteSecondCommand = new DeleteStudentCommand(secondId);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteStudentCommand deleteFirstCommandCopy = new DeleteStudentCommand(firstId);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different student ID -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    @Test
    public void toStringMethod() {
        StudentId targetId = new StudentId("A0123456X");
        DeleteStudentCommand deleteCommand = new DeleteStudentCommand(targetId);
        String expected = DeleteStudentCommand.class.getCanonicalName() + "{targetId=" + targetId + "}";
        assertEquals(expected, deleteCommand.toString());
    }
}

package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalRooms.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class ListRoomCommandTest {

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
                ListRoomCommand.MESSAGE_SUCCESS, false, false, true, true, true);

        assertCommandSuccess(new ListRoomCommand(), model, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_emptyRoomList_throwsCommandException() {
        model.setAddressBook(new AddressBook());
        assertCommandFailure(new ListRoomCommand(), model, ListRoomCommand.MESSAGE_FAILURE);
    }

    @Test
    public void equals() {
        ListRoomCommand listRoomCommand = new ListRoomCommand();
        assertTrue(listRoomCommand.equals(listRoomCommand));
        assertTrue(listRoomCommand.equals(new ListRoomCommand()));
        assertFalse(listRoomCommand.equals(1));
        assertFalse(listRoomCommand.equals(null));
    }

    @Test
    public void hashCode_test() {
        ListRoomCommand listRoomCommand = new ListRoomCommand();
        assertEquals(listRoomCommand.hashCode(), listRoomCommand.hashCode());
        assertEquals(listRoomCommand.hashCode(), new ListRoomCommand().hashCode());
    }
}

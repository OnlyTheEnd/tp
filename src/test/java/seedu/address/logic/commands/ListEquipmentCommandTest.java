package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.testutil.TypicalEquipments;

public class ListEquipmentCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(TypicalEquipments.getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        CommandResult expectedCommandResult = new CommandResult(
                ListEquipmentCommand.MESSAGE_SUCCESS, false, false, true, true, true);

        assertCommandSuccess(new ListEquipmentCommand(), model, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsEverything() {
        expectedModel.updateFilteredEquipmentList(Model.PREDICATE_SHOW_ALL_EQUIPMENTS);

        CommandResult expectedCommandResult = new CommandResult(
                ListEquipmentCommand.MESSAGE_SUCCESS, false, false, true, true, true);

        assertCommandSuccess(new ListEquipmentCommand(), model, expectedCommandResult, expectedModel);
    }

    @Test
    public void equals() {
        ListEquipmentCommand listEquipmentCommand = new ListEquipmentCommand();

        assertTrue(listEquipmentCommand.equals(listEquipmentCommand));

        assertTrue(listEquipmentCommand.equals(new ListEquipmentCommand()));

        assertFalse(listEquipmentCommand.equals(1));

        assertFalse(listEquipmentCommand.equals(null));
    }

    @Test
    public void hashCode_test() {
        assertEquals(new ListEquipmentCommand().hashCode(), new ListEquipmentCommand().hashCode());
    }
}

package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.DeleteEquipmentCommand.MESSAGE_INVALID_EQUIPMENT_DISPLAYED_INDEX;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_EQUIPMENT;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_EQUIPMENT;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.equipment.Equipment;
import seedu.address.testutil.TypicalEquipments;

public class DeleteEquipmentCommandTest {

    private Model model = new ModelManager(TypicalEquipments.getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Equipment equipmentToDelete = model.getFilteredEquipmentList().get(INDEX_FIRST_EQUIPMENT.getZeroBased());
        DeleteEquipmentCommand deleteCommand = new DeleteEquipmentCommand(INDEX_FIRST_EQUIPMENT);

        String expectedMessage = String.format(DeleteEquipmentCommand.MESSAGE_DELETE_EQUIPMENT_SUCCESS,
                equipmentToDelete);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deleteEquipment(equipmentToDelete);

        CommandResult expectedCommandResult = new CommandResult(expectedMessage, false, false, true, true, true);

        assertCommandSuccess(deleteCommand, model, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredEquipmentList().size() + 1);
        DeleteEquipmentCommand deleteCommand = new DeleteEquipmentCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, MESSAGE_INVALID_EQUIPMENT_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        DeleteEquipmentCommand deleteFirstCommand = new DeleteEquipmentCommand(INDEX_FIRST_EQUIPMENT);
        DeleteEquipmentCommand deleteSecondCommand = new DeleteEquipmentCommand(INDEX_SECOND_EQUIPMENT);

        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        DeleteEquipmentCommand deleteFirstCommandCopy = new DeleteEquipmentCommand(INDEX_FIRST_EQUIPMENT);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        assertFalse(deleteFirstCommand.equals(1));

        assertFalse(deleteFirstCommand.equals(null));

        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }
}

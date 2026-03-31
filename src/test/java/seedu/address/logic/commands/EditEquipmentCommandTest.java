package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.model.util.SampleDataUtil.getSampleAddressBook;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_EQUIPMENT;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_EQUIPMENT;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.equipment.Equipment;
import seedu.address.testutil.EditEquipmentDescriptorBuilder;
import seedu.address.testutil.EquipmentBuilder;

public class EditEquipmentCommandTest {

    private Model model = new ModelManager(getSampleAddressBook(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Equipment editedEquipment = new EquipmentBuilder().build();
        EditEquipmentCommand.EditEquipmentDescriptor descriptor =
                new EditEquipmentDescriptorBuilder(editedEquipment).build();
        EditEquipmentCommand editCommand = new EditEquipmentCommand(INDEX_FIRST_EQUIPMENT, descriptor);

        String expectedMessage = String.format(EditEquipmentCommand.MESSAGE_EDIT_EQUIPMENT_SUCCESS, editedEquipment);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setEquipment(model.getFilteredEquipmentList().get(0), editedEquipment);

        CommandResult expectedCommandResult = new CommandResult(expectedMessage,
                false, false, true, true, true);

        assertCommandSuccess(editCommand, model, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_duplicateEquipmentUnfilteredList_failure() {
        Equipment firstEquipment = model.getFilteredEquipmentList().get(INDEX_FIRST_EQUIPMENT.getZeroBased());
        EditEquipmentCommand.EditEquipmentDescriptor descriptor =
                new EditEquipmentDescriptorBuilder(firstEquipment).build();
        EditEquipmentCommand editCommand = new EditEquipmentCommand(INDEX_SECOND_EQUIPMENT, descriptor);

        assertCommandFailure(editCommand, model, EditEquipmentCommand.MESSAGE_DUPLICATE_EQUIPMENT);
    }

    @Test
    public void execute_invalidEquipmentIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredEquipmentList().size() + 1);
        EditEquipmentCommand.EditEquipmentDescriptor descriptor =
                new EditEquipmentDescriptorBuilder().withName("Valid-Name").build();
        EditEquipmentCommand editCommand = new EditEquipmentCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_EQUIPMENT_DISPLAYED_INDEX);
    }
}

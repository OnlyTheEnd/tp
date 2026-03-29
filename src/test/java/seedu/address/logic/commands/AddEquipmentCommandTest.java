package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.ModelStub;
import seedu.address.model.equipment.Category;
import seedu.address.model.equipment.Equipment;
import seedu.address.model.equipment.EquipmentName;
import seedu.address.model.equipment.EquipmentStatus;

public class AddEquipmentCommandTest {

    @Test
    public void constructor_nullEquipment_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddEquipmentCommand(null));
    }

    @Test
    public void execute_equipmentAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingEquipmentAdded modelStub = new ModelStubAcceptingEquipmentAdded();
        Equipment validEquipment = new Equipment(new EquipmentName("Wilson-Basketball"),
                 new Category("Basketball"), EquipmentStatus.AVAILABLE);

        CommandResult commandResult = new AddEquipmentCommand(validEquipment).execute(modelStub);

        assertEquals(String.format(AddEquipmentCommand.MESSAGE_SUCCESS,
                        validEquipment.getName(), validEquipment.getCategory(), validEquipment.getStatus()),
                commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validEquipment), modelStub.equipmentsAdded);
    }

    @Test
    public void execute_duplicateEquipment_throwsCommandException() {
        Equipment validEquipment = new Equipment(new EquipmentName("Wilson-Basketball"),
                new Category("Basketball"), EquipmentStatus.AVAILABLE);
        AddEquipmentCommand addEquipmentCommand = new AddEquipmentCommand(validEquipment);
        ModelStubWithEquipment modelStub = new ModelStubWithEquipment(validEquipment);

        assertThrows(CommandException.class,
                AddEquipmentCommand.MESSAGE_DUPLICATE_EQUIPMENT, () -> addEquipmentCommand.execute(modelStub));
    }

    /**
     * A Model stub that always accept the equipment being added.
     */
    private class ModelStubAcceptingEquipmentAdded extends ModelStub {
        final ArrayList<Equipment> equipmentsAdded = new ArrayList<>();

        @Override
        public boolean hasEquipment(Equipment equipment) {
            requireNonNull(equipment);
            return equipmentsAdded.stream().anyMatch(equipment::isSameEquipment);
        }

        @Override
        public void addEquipment(Equipment equipment) {
            requireNonNull(equipment);
            equipmentsAdded.add(equipment);
        }
    }

    /**
     * A Model stub that contains a single equipment.
     */
    private class ModelStubWithEquipment extends ModelStub {
        private final Equipment equipment;

        ModelStubWithEquipment(Equipment equipment) {
            requireNonNull(equipment);
            this.equipment = equipment;
        }

        @Override
        public boolean hasEquipment(Equipment equipment) {
            requireNonNull(equipment);
            return this.equipment.isSameEquipment(equipment);
        }
    }
}


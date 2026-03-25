package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.ModelStub;
import seedu.address.model.equipment.Equipment;
import seedu.address.model.room.Room;
import seedu.address.model.tag.Tag;

public class FilterTagCommandTest {
    private static final Tag VALID_TAG = new Tag("Valid");
    private static final String ROOM_TYPE = "Room";
    private static final String EQUIPMENT_TYPE = "Equipment";
    private static final String INVALID_TYPE = "InvalidType";

    @Test
    public void execute_nullModel_throwsNullPointerException() {
        FilterTagCommand command = new FilterTagCommand(ROOM_TYPE, VALID_TAG);

        assertThrows(NullPointerException.class, () -> command.execute(null));
    }

    @Test
    public void execute_filterByRoomTag_success() throws Exception {
        ModelStubCapturingRoomFilter modelStub = new ModelStubCapturingRoomFilter();

        CommandResult result = new FilterTagCommand(ROOM_TYPE, VALID_TAG).execute(modelStub);

        String expectedMessage = String.format(FilterTagCommand.MESSAGE_SUCCESS, ROOM_TYPE, VALID_TAG);
        assertEquals(expectedMessage, result.getFeedbackToUser());
    }

    @Test
    public void execute_filterByRoomTag_updatesRoomList() throws Exception {
        ModelStubCapturingRoomFilter modelStub = new ModelStubCapturingRoomFilter();

        new FilterTagCommand(ROOM_TYPE, VALID_TAG).execute(modelStub);

        // Verify that updateFilteredRoomList was called
        assertEquals(true, modelStub.wasRoomListUpdated);
    }

    @Test
    public void execute_filterByEquipmentTag_success() throws Exception {
        ModelStubCapturingEquipmentFilter modelStub = new ModelStubCapturingEquipmentFilter();

        CommandResult result = new FilterTagCommand(EQUIPMENT_TYPE, VALID_TAG).execute(modelStub);

        String expectedMessage = String.format(FilterTagCommand.MESSAGE_SUCCESS, EQUIPMENT_TYPE, VALID_TAG);
        assertEquals(expectedMessage, result.getFeedbackToUser());
    }

    @Test
    public void execute_filterByEquipmentTag_updatesEquipmentList() throws Exception {
        ModelStubCapturingEquipmentFilter modelStub = new ModelStubCapturingEquipmentFilter();

        new FilterTagCommand(EQUIPMENT_TYPE, VALID_TAG).execute(modelStub);

        // Verify that updateFilteredEquipmentList was called
        assertEquals(true, modelStub.wasEquipmentListUpdated);
    }


    @Test
    public void execute_invalidTargetType_throwsCommandException() {
        ModelStubCapturingRoomFilter modelStub = new ModelStubCapturingRoomFilter();
        FilterTagCommand command = new FilterTagCommand(INVALID_TYPE, VALID_TAG);

        assertThrows(CommandException.class, FilterTagCommand.MESSAGE_ERROR, () ->
                command.execute(modelStub));
    }


    /**
     * A Model stub that captures filter updates.
     */
    private static class ModelStubCapturingRoomFilter extends ModelStub {
        private boolean wasRoomListUpdated = false;

        @Override
        public void updateFilteredRoomList(Predicate<Room> predicate) {
            requireNonNull(predicate);
            wasRoomListUpdated = true;
        }
    }

    /**
     * A Model stub that captures equipment filter updates.
     */
    private static class ModelStubCapturingEquipmentFilter extends ModelStub {
        private boolean wasEquipmentListUpdated = false;

        @Override
        public void updateFilteredEquipmentList(Predicate<Equipment> predicate) {
            requireNonNull(predicate);
            wasEquipmentListUpdated = true;
        }
    }
}


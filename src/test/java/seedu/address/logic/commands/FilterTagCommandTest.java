package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalEquipments.BASKETBALL;
import static seedu.address.testutil.TypicalRooms.ROOM_A;

import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.ModelStub;
import seedu.address.model.equipment.Equipment;
import seedu.address.model.room.Room;
import seedu.address.model.tag.Tag;



public class FilterTagCommandTest {
    private static final Tag VALID_TAG = new Tag("Valid");
    private static final Tag IFG_TAG = new Tag("IFG");
    private static final Tag TALK_TAG = new Tag("Talk");
    private static final String ROOM_TYPE = "room";
    private static final String EQUIPMENT_TYPE = "equipment";
    private static final String INVALID_TYPE = "InvalidType";

    // ==================== Room Filter Tests - Positive ====================

    @Test
    public void execute_filterByRoomTag_success() throws Exception {
        ModelStubWithNonEmptyRoomList modelStub = new ModelStubWithNonEmptyRoomList();

        CommandResult result = new FilterTagCommand(ROOM_TYPE, IFG_TAG).execute(modelStub);

        String expectedMessage = String.format(FilterTagCommand.MESSAGE_SUCCESS,
                ROOM_TYPE, IFG_TAG);
        assertEquals(expectedMessage, result.getFeedbackToUser());
    }

    @Test
    public void execute_filterByRoomTag_updatesRoomList() throws Exception {
        ModelStubWithNonEmptyRoomList modelStub = new ModelStubWithNonEmptyRoomList();

        new FilterTagCommand(ROOM_TYPE, IFG_TAG).execute(modelStub);

        assertTrue(modelStub.wasRoomListUpdated);
    }

    @Test
    public void execute_filterByRoomTag_correctPredicateUsed() throws Exception {
        ModelStubCapturingRoomPredicate modelStub = new ModelStubCapturingRoomPredicate();

        new FilterTagCommand(ROOM_TYPE, IFG_TAG).execute(modelStub);

        // Verify predicate was captured
        assertTrue(modelStub.predicateCaptured);
    }

    @Test
    public void execute_filterByTalkTag_success() throws Exception {
        ModelStubWithNonEmptyRoomList modelStub = new ModelStubWithNonEmptyRoomList();

        CommandResult result = new FilterTagCommand(ROOM_TYPE, TALK_TAG).execute(modelStub);

        String expectedMessage = String.format(FilterTagCommand.MESSAGE_SUCCESS,
                ROOM_TYPE, TALK_TAG);
        assertEquals(expectedMessage, result.getFeedbackToUser());
    }

    // ==================== Room Filter Tests - Negative ====================

    @Test
    public void execute_filterByRoomTagNoRoom_throwsCommandException() {
        //empty room list
        ModelStubWithEmptyRoomList modelStub = new ModelStubWithEmptyRoomList();

        FilterTagCommand command = new FilterTagCommand(ROOM_TYPE, IFG_TAG);

        assertThrows(CommandException.class, () -> command.execute(modelStub));
    }

    @Test
    public void execute_filterByRoomTagNoMatch_throwsCommandException() {
        //noMatchingRooms
        ModelStubWithEmptyRoomList modelStub = new ModelStubWithEmptyRoomList();

        FilterTagCommand command = new FilterTagCommand(ROOM_TYPE, VALID_TAG);

        assertThrows(CommandException.class,
                String.format(FilterTagCommand.MESSAGE_TAG_NOT_FOUND_ROOM, VALID_TAG), ()
                        -> command.execute(modelStub));
    }

    // ==================== Equipment Filter Tests - Positive ====================

    @Test
    public void execute_filterByEquipmentTag_success() throws Exception {
        ModelStubWithNonEmptyEquipmentList modelStub = new ModelStubWithNonEmptyEquipmentList();

        CommandResult result = new FilterTagCommand(EQUIPMENT_TYPE, VALID_TAG).execute(modelStub);

        String expectedMessage = String.format(FilterTagCommand.MESSAGE_SUCCESS,
                EQUIPMENT_TYPE, VALID_TAG);
        assertEquals(expectedMessage, result.getFeedbackToUser());
    }

    @Test
    public void execute_filterByEquipmentTag_updatesEquipmentList() throws Exception {
        ModelStubWithNonEmptyEquipmentList modelStub = new ModelStubWithNonEmptyEquipmentList();

        new FilterTagCommand(EQUIPMENT_TYPE, VALID_TAG).execute(modelStub);

        assertTrue(modelStub.wasEquipmentListUpdated);
    }

    @Test
    public void execute_filterByEquipmentTag_correctPredicateUsed() throws Exception {
        ModelStubCapturingEquipmentPredicate modelStub = new ModelStubCapturingEquipmentPredicate();

        new FilterTagCommand(EQUIPMENT_TYPE, VALID_TAG).execute(modelStub);

        // Verify predicate was captured
        assertTrue(modelStub.predicateCaptured);
    }

    // ==================== Equipment Filter Tests - Negative ====================

    @Test
    public void execute_filterByEquipmentTagNoEquipment_throwsCommandException() {
        //No equipment
        ModelStubWithEmptyEquipmentList modelStub = new ModelStubWithEmptyEquipmentList();

        FilterTagCommand command = new FilterTagCommand(EQUIPMENT_TYPE, VALID_TAG);

        assertThrows(CommandException.class, () -> command.execute(modelStub));
    }

    @Test
    public void execute_filterByEquipmentTagNoMatch_throwsCommandException() {
        //No matched tag
        ModelStubWithEmptyEquipmentList modelStub = new ModelStubWithEmptyEquipmentList();

        FilterTagCommand command = new FilterTagCommand(EQUIPMENT_TYPE, VALID_TAG);

        assertThrows(CommandException.class,
                String.format(FilterTagCommand.MESSAGE_TAG_NOT_FOUND_EQUIPMENT, VALID_TAG), ()
                        -> command.execute(modelStub));
    }

    // ==================== Invalid Type Tests ====================

    @Test
    public void execute_invalidTargetType_throwsCommandException() {
        ModelStubWithNonEmptyRoomList modelStub = new ModelStubWithNonEmptyRoomList();
        FilterTagCommand command = new FilterTagCommand(INVALID_TYPE, VALID_TAG);

        assertThrows(CommandException.class,
                FilterTagCommand.MESSAGE_ERROR, () -> command.execute(modelStub));
    }

    // ==================== Equals Tests ====================

    @Test
    public void equals() {
        FilterTagCommand command1 = new FilterTagCommand(EQUIPMENT_TYPE, VALID_TAG);
        FilterTagCommand command2 = new FilterTagCommand(ROOM_TYPE, VALID_TAG);
        FilterTagCommand command3 = new FilterTagCommand(EQUIPMENT_TYPE, IFG_TAG);
        FilterTagCommand command4 = new FilterTagCommand(EQUIPMENT_TYPE, VALID_TAG);

        // same object -> returns true
        assertTrue(command1.equals(command1));

        // same values -> returns true
        assertTrue(command1.equals(command4));

        // different types -> returns false
        assertFalse(command1.equals(1));

        // null -> returns false
        assertFalse(command1.equals(null));

        // different target type -> returns false
        assertFalse(command1.equals(command2));

        // different tag -> returns false
        assertFalse(command1.equals(command3));
    }

    @Test
    public void equals_differentCase_returnsFalse() {
        FilterTagCommand command1 = new FilterTagCommand(EQUIPMENT_TYPE, IFG_TAG);
        FilterTagCommand command2 = new FilterTagCommand(EQUIPMENT_TYPE, TALK_TAG);

        assertFalse(command1.equals(command2));
    }

    // ==================== Model Stubs ====================

    /**
     * A Model stub that returns a non-empty room list.
     */
    private static class ModelStubWithNonEmptyRoomList extends ModelStub {
        private boolean wasRoomListUpdated = false;

        @Override
        public void updateFilteredRoomList(Predicate<Room> predicate) {
            requireNonNull(predicate);
            wasRoomListUpdated = true;
        }

        @Override
        public ObservableList<Room> getFilteredRoomList() {
            // Return non-empty list
            return FXCollections.observableArrayList(ROOM_A);
        }
    }

    /**
     * A Model stub that returns an empty room list.
     */
    private static class ModelStubWithEmptyRoomList extends ModelStub {
        @Override
        public void updateFilteredRoomList(Predicate<Room> predicate) {
            requireNonNull(predicate);
        }

        @Override
        public ObservableList<Room> getFilteredRoomList() {
            // Return empty list
            return FXCollections.observableArrayList();
        }
    }

    /**
     * A Model stub that captures the room predicate.
     */
    private static class ModelStubCapturingRoomPredicate extends ModelStub {
        private boolean predicateCaptured = false;

        @Override
        public void updateFilteredRoomList(Predicate<Room> predicate) {
            requireNonNull(predicate);
            predicateCaptured = true;
        }

        @Override
        public ObservableList<Room> getFilteredRoomList() {
            return FXCollections.observableArrayList(ROOM_A);
        }
    }

    /**
     * A Model stub that returns a non-empty equipment list.
     */
    private static class ModelStubWithNonEmptyEquipmentList extends ModelStub {
        private boolean wasEquipmentListUpdated = false;

        @Override
        public void updateFilteredEquipmentList(Predicate<Equipment> predicate) {
            requireNonNull(predicate);
            wasEquipmentListUpdated = true;
        }

        @Override
        public ObservableList<Equipment> getFilteredEquipmentList() {
            // Return non-empty list
            return FXCollections.observableArrayList(BASKETBALL);
        }
    }

    /**
     * A Model stub that returns an empty equipment list.
     */
    private static class ModelStubWithEmptyEquipmentList extends ModelStub {
        @Override
        public void updateFilteredEquipmentList(Predicate<Equipment> predicate) {
            requireNonNull(predicate);
        }

        @Override
        public ObservableList<Equipment> getFilteredEquipmentList() {
            // Return empty list
            return FXCollections.observableArrayList();
        }
    }

    /**
     * A Model stub that captures the equipment predicate.
     */
    private static class ModelStubCapturingEquipmentPredicate extends ModelStub {
        private boolean predicateCaptured = false;

        @Override
        public void updateFilteredEquipmentList(Predicate<Equipment> predicate) {
            requireNonNull(predicate);
            predicateCaptured = true;
        }

        @Override
        public ObservableList<Equipment> getFilteredEquipmentList() {
            return FXCollections.observableArrayList(BASKETBALL);
        }
    }
}

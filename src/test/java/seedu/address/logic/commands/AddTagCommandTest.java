package seedu.address.logic.commands;
import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.ModelStub;
import seedu.address.model.equipment.Equipment;
import seedu.address.model.equipment.EquipmentName;
import seedu.address.model.room.Room;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.Taggable;
import seedu.address.testutil.RoomBuilder;



public class AddTagCommandTest {
    private static final Tag VALID_TAG = new Tag("Valid");
    private static final String VALID_EQUIPMENT_ID = "Wilson-Evolution-Basketball-1";
    private static final String VALID_ROOM_ID = "MPSH-1";
    private static final Equipment expectedEquipment = new Equipment(new EquipmentName(VALID_EQUIPMENT_ID));
    private static final Room expectedRoom = new RoomBuilder().build();

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddTagCommand(null, null));
    }

    //Room
    @Test
    public void execute_roomTagAccepted_addSuccessful() throws CommandException {
        ModelStubAcceptingTag modelStub = new ModelStubAcceptingTag(expectedRoom);
        AddTagCommand addTagCommand = new AddTagCommand(expectedRoom, VALID_TAG);

        CommandResult commandResult = addTagCommand.execute(modelStub);

        String expectedMessage = String.format(AddTagCommand.MESSAGE_SUCCESS,
                VALID_TAG, VALID_ROOM_ID);
        assertEquals(expectedMessage, commandResult.getFeedbackToUser());
    }

    @Test
    public void execute_roomTagAddedToModel() throws Exception {
        ModelStubAcceptingTag modelStub = new ModelStubAcceptingTag(expectedRoom);

        new AddTagCommand(expectedRoom, VALID_TAG).execute(modelStub);

        assertEquals(VALID_TAG, modelStub.tagAdded);
        assertEquals(expectedRoom, modelStub.taggedTarget);
    }


    //Equipment
    @Test
    public void execute_equipmentTagAccepted_addSuccessful() throws CommandException {
        ModelStubAcceptingTag modelStub = new ModelStubAcceptingTag(
                new Equipment(new EquipmentName(VALID_EQUIPMENT_ID)));
        AddTagCommand addTagCommand = new AddTagCommand(new Equipment(new EquipmentName(VALID_EQUIPMENT_ID)),
                VALID_TAG);

        CommandResult commandResult = addTagCommand.execute(modelStub);

        String expectedMessage = String.format(AddTagCommand.MESSAGE_SUCCESS,
                VALID_TAG, VALID_EQUIPMENT_ID);
        assertEquals(expectedMessage, commandResult.getFeedbackToUser());
    }

    @Test
    public void execute_equipmentTtagAddedToModel() throws Exception {
        ModelStubAcceptingTag modelStub = new ModelStubAcceptingTag(expectedEquipment);

        new AddTagCommand(new Equipment(new EquipmentName(VALID_EQUIPMENT_ID)), VALID_TAG).execute(modelStub);

        assertEquals(VALID_TAG, modelStub.tagAdded);
        assertEquals(expectedEquipment, modelStub.taggedTarget);
    }

    @Test
    public void execute_taggableNotFound_throwsCommandException() {
        Room validRoom = new RoomBuilder().build();
        // Stub reports hasTaggable = false
        ModelStubTaggableNotFound modelStub = new ModelStubTaggableNotFound();

        AddTagCommand command = new AddTagCommand(validRoom, VALID_TAG);

        assertThrows(CommandException.class, () -> command.execute(modelStub));
    }

    /**
     * A Model stub that contains a single Taggable and accepts a tag being added.
     */
    private static class ModelStubAcceptingTag extends ModelStub {
        private final Taggable taggable;
        Taggable taggedTarget = null;
        Tag tagAdded = null;

        ModelStubAcceptingTag(Taggable taggable) {
            requireNonNull(taggable);
            this.taggable = taggable;
        }

        @Override
        public boolean hasTaggable(Taggable target) {
            requireNonNull(target);
            return taggable.equals(target);
        }

        @Override
        public void addTag(Taggable target, Tag tag) {
            requireNonNull(target);
            requireNonNull(tag);
            this.taggedTarget = target;
            this.tagAdded = tag;
        }
    }

    /**
     * A Model stub where hasTaggable always returns false.
     */
    private static class ModelStubTaggableNotFound extends ModelStub {

        @Override
        public boolean hasTaggable(Taggable target) {
            return false;
        }
    }

}

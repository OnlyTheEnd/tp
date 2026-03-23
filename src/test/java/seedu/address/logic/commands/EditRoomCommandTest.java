package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.model.util.SampleDataUtil.getSampleAddressBook;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_ROOM;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_ROOM;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.room.Room;
import seedu.address.testutil.EditRoomDescriptorBuilder;
import seedu.address.testutil.RoomBuilder;

public class EditRoomCommandTest {

    private Model model = new ModelManager(getSampleAddressBook(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Room editedRoom = new RoomBuilder().build();
        EditRoomCommand.EditRoomDescriptor descriptor =
                new EditRoomDescriptorBuilder(editedRoom).build();
        EditRoomCommand editCommand = new EditRoomCommand(INDEX_FIRST_ROOM, descriptor);

        String expectedMessage = String.format(EditRoomCommand.MESSAGE_EDIT_ROOM_SUCCESS, editedRoom);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setRoom(model.getFilteredRoomList().get(0), editedRoom);

        CommandResult expectedCommandResult = new CommandResult(expectedMessage,
                false, false, false, true, false);

        assertCommandSuccess(editCommand, model, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_duplicateRoomUnfilteredList_failure() {
        Room firstRoom = model.getFilteredRoomList().get(INDEX_FIRST_ROOM.getZeroBased());
        EditRoomCommand.EditRoomDescriptor descriptor =
                new EditRoomDescriptorBuilder(firstRoom).build();
        EditRoomCommand editCommand = new EditRoomCommand(INDEX_SECOND_ROOM, descriptor);

        assertCommandFailure(editCommand, model, EditRoomCommand.MESSAGE_DUPLICATE_ROOM);
    }

    @Test
    public void execute_invalidRoomIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredRoomList().size() + 1);
        EditRoomCommand.EditRoomDescriptor descriptor =
                new EditRoomDescriptorBuilder().withName("YIH").build();
        EditRoomCommand editCommand = new EditRoomCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_ROOM_DISPLAYED_INDEX);
    }
}

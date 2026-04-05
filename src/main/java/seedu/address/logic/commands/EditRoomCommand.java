package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Optional;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.room.Location;
import seedu.address.model.room.Room;
import seedu.address.model.room.RoomName;
import seedu.address.model.room.Status;

/**
 * Edits the details of an existing room.
 */
public class EditRoomCommand extends Command {

    public static final String COMMAND_WORD = "edit-r";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the room identified "
            + "by the index number used in the displayed room list. "
            + "At least one field to edit must be provided.\n"
            + "Parameters: INDEX(must be a positive integer) "
            + "[n/NAME] [l/LOCATION] [s/STATUS (Available, Maintenance only)]\n"
            + "Example: " + COMMAND_WORD + " 1 s/Maintenance";

    public static final String MESSAGE_EDIT_ROOM_SUCCESS = "Updated Room:\n%1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_ROOM = "This room already exists in the address book.";

    private final Index index;
    private final EditRoomDescriptor editRoomDescriptor;

    /**
     * @param index of the room in the filtered room list to edit
     * @param editRoomDescriptor details to edit the room with
     */
    public EditRoomCommand(Index index, EditRoomDescriptor editRoomDescriptor) {
        requireNonNull(index);
        requireNonNull(editRoomDescriptor);
        this.index = index;
        this.editRoomDescriptor = new EditRoomDescriptor(editRoomDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        List<Room> lastShownList = model.getFilteredRoomList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_ROOM_DISPLAYED_INDEX);
        }

        Room roomToEdit = lastShownList.get(index.getZeroBased());

        if (roomToEdit.getStatus().toString().equalsIgnoreCase("BOOKED")) {
            throw new CommandException("This room is currently 'Booked' and cannot be edited. "
                    + "Please wait until it is cancelled.");
        }

        if (editRoomDescriptor.getStatus().isPresent()) {
            Status currentStatus = roomToEdit.getStatus();
            Status requestedStatus = editRoomDescriptor.getStatus().get();

            if (currentStatus.equals(requestedStatus)) {
                editRoomDescriptor.setStatus(null);
            } else {
                validateStatusTransition(currentStatus, requestedStatus);
            }
        }

        Room editedRoom = createEditedRoom(roomToEdit, editRoomDescriptor);

        if (roomToEdit.equals(editedRoom)) {
            throw new CommandException("No changes detected (the information provided matches current records).");
        }

        if (!roomToEdit.isSameRoom(editedRoom) && model.hasRoom(editedRoom)) {
            throw new CommandException(MESSAGE_DUPLICATE_ROOM);
        }

        model.setRoom(roomToEdit, editedRoom);
        model.updateFilteredRoomList(Model.PREDICATE_SHOW_ALL_ROOMS);

        return new CommandResult(String.format(MESSAGE_EDIT_ROOM_SUCCESS, editedRoom),
                false, false, true, true, true);
    }

    private static Room createEditedRoom(Room roomToEdit, EditRoomDescriptor editRoomDescriptor) {
        RoomName updatedName = editRoomDescriptor.getName().orElse(roomToEdit.getName());
        Location updatedLocation = editRoomDescriptor.getLocation().orElse(roomToEdit.getLocation());
        Status updatedStatus = editRoomDescriptor.getStatus().orElse(roomToEdit.getStatus());

        return new Room(updatedName, updatedLocation, updatedStatus);
    }

    /**
     * Validates the room status transition.
     */
    private void validateStatusTransition(Status current, Status requested)
            throws CommandException {
        String currentVal = current.toString().toUpperCase();
        String requestedVal = requested.toString().toUpperCase();

        if (currentVal.equals(requestedVal)) {
            throw new CommandException("Equipment is already in " + current.toString() + ", no status change.");
        }

        if (currentVal.equals("BOOKED")) {
            throw new CommandException("Room is currently 'Booked' and its status cannot be edited as it is "
                    + "currently issued or reserved");
        }

        if (currentVal.equals("AVAILABLE")) {
            if (!requestedVal.equals("MAINTENANCE")) {
                throw new CommandException("Room in 'Available' status can only be edited to 'Maintenance'");
            }
        } else if (currentVal.equals("MAINTENANCE")) {
            if (!requestedVal.equals("AVAILABLE")) {
                throw new CommandException("Room in 'Maintenance' status can only be edited to "
                        + "'Available'.");
            }
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof EditRoomCommand)) {
            return false;
        }
        EditRoomCommand e = (EditRoomCommand) other;
        return index.equals(e.index)
                && editRoomDescriptor.equals(e.editRoomDescriptor);
    }

    /**
     * Stores the details to edit the room with.
     */
    public static class EditRoomDescriptor {
        private RoomName name;
        private Location location;
        private Status status;

        public EditRoomDescriptor() {}

        /**
         * Copy constructor.
         */
        public EditRoomDescriptor(EditRoomDescriptor toCopy) {
            setName(toCopy.name);
            setLocation(toCopy.location);
            setStatus(toCopy.status);
        }

        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, location, status);
        }

        public void setName(RoomName name) {
            this.name = name;
        }

        public Optional<RoomName> getName() {
            return Optional.ofNullable(name);
        }

        public void setLocation(Location location) {
            this.location = location;
        }

        public Optional<Location> getLocation() {
            return Optional.ofNullable(location);
        }

        public void setStatus(Status status) {
            this.status = status;
        }

        public Optional<Status> getStatus() {
            return Optional.ofNullable(status);
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }
            if (!(other instanceof EditRoomDescriptor)) {
                return false;
            }
            EditRoomDescriptor e = (EditRoomDescriptor) other;
            return getName().equals(e.getName())
                    && getLocation().equals(e.getLocation())
                    && getStatus().equals(e.getStatus());
        }
    }
}

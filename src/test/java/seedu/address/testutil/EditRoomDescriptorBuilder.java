package seedu.address.testutil;

import seedu.address.logic.commands.EditRoomCommand.EditRoomDescriptor;
import seedu.address.model.room.Location;
import seedu.address.model.room.Room;
import seedu.address.model.room.RoomName;
import seedu.address.model.room.Status;

/**
 * A utility class to help with building EditRoomDescriptor objects.
 */
public class EditRoomDescriptorBuilder {

    private EditRoomDescriptor descriptor;

    public EditRoomDescriptorBuilder() {
        descriptor = new EditRoomDescriptor();
    }

    public EditRoomDescriptorBuilder(EditRoomDescriptor descriptor) {
        this.descriptor = new EditRoomDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditRoomDescriptor} with fields containing {@code room}'s details
     */
    public EditRoomDescriptorBuilder(Room room) {
        descriptor = new EditRoomDescriptor();
        descriptor.setName(room.getName());
        descriptor.setLocation(room.getLocation());
        descriptor.setStatus(room.getStatus());
    }

    /**
     * Sets the {@code RoomName} of the {@code EditRoomDescriptor} that we are building.
     */
    public EditRoomDescriptorBuilder withName(String name) {
        descriptor.setName(new RoomName(name));
        return this;
    }

    /**
     * Sets the {@code Location} of the {@code EditRoomDescriptor} that we are building.
     */
    public EditRoomDescriptorBuilder withLocation(String location) {
        descriptor.setLocation(new Location(location));
        return this;
    }

    /**
     * Sets the {@code Status} of the {@code EditRoomDescriptor} that we are building.
     */
    public EditRoomDescriptorBuilder withStatus(String status) {
        descriptor.setStatus(Status.java_parse(status));
        return this;
    }

    public EditRoomDescriptor build() {
        return descriptor;
    }
}

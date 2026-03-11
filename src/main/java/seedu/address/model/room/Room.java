package seedu.address.model.room;

import seedu.address.model.tag.Tag;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Represents a Room in the system.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Room {

    // Identity fields
    private final RoomName name;

    // Data fields
    private final Location location;
    private final Status status;
    private final Set<Tag> tags = new HashSet<>();
    /**
     * Every field must be present and not null.
     * @param name A valid room name.
     * @param location A valid room location.
     * @param status A valid room status.
     */
    public Room(RoomName name, Location location, Status status) {
        requireAllNonNull(name, location, status);
        this.name = name;
        this.location = location;
        this.status = status;
    }

    /**
     * For checking a roomName is inside the roomList
     * @param name A valid room name.
     */
    public Room(RoomName name) {
        this.name = name;
        this.location = new Location("");
        this.status = new Status("Available");
    }

    public RoomName getName() {
        return name;
    }

    public Location getLocation() {
        return location;
    }

    public Status getStatus() {
        return status;
    }

    /**
     * Returns true if both rooms have the same name.
     * This defines a weaker notion of equality between two rooms (used for duplicate checking).
     */
    public boolean isSameRoom(Room otherRoom) {
        if (otherRoom == this) {
            return true;
        }
        return otherRoom != null && otherRoom.getName().equals(getName());
    }

    @Override
    public String toString() {
        return String.format("Name: %s | Location: %s | Status: %s", name, location, status);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Room)) {
            return false;
        }

        Room otherRoom = (Room) other;
        return name.equals(otherRoom.name)
                && location.equals(otherRoom.location)
                && status.equals(otherRoom.status);
    }

    @Override
    public int hashCode() {
        // use this to ensure consistent hashing for collections
        return Objects.hash(name, location, status);
    }

    public void addTag(Tag tag) {
        tags.add(tag);
    }

    public void deleteTag(Tag otherTag) {
        tags.removeIf(tag -> tag.equals(otherTag));
    }
}

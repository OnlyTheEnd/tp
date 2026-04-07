package seedu.address.model.room;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;
import java.util.Set;

import seedu.address.model.tag.Tag;
import seedu.address.model.tag.Taggable;
import seedu.address.model.tag.exceptions.DuplicateTagException;
import seedu.address.model.tag.exceptions.TagNotFoundException;


/**
 * Represents a Room in the system.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Room extends Taggable {
    // Identity fields
    private final RoomName name;

    // Data fields
    private final Location location;
    private final Status status;

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
        this.location = new Location("placeholder");
        this.status = Status.AVAILABLE;
    }

    /**
     * For modelEquipment creation
     * @param name A modelName
     * @param location A modelLocation
     * @param status A modelStatus
     * @param tags A modelTags
     */
    public Room(RoomName name, Location location, Status status, Set<Tag> tags) {
        requireAllNonNull(name, location, status);
        this.name = name;
        this.location = location;
        this.status = status;
        this.tags.addAll(tags);
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

    /**
     * Adds tag from tagList on Room
     * @param tagName A tagName
     */
    public void addTag(String tagName) {
        Tag tag = new Tag(tagName);
        if (tags.contains(tag)) {
            throw new DuplicateTagException();
        }
        tags.add(tag);
    }

    /**
     * Removes tag from tagList on Room
     * @param otherTagName A tagName
     */
    public void deleteTag(String otherTagName) {
        Tag otherTag = new Tag(otherTagName);
        if (!tags.contains(otherTag)) {
            throw new TagNotFoundException();
        }
        tags.removeIf(tag -> tag.equals(otherTag));
    }

    @Override
    public String getNameString() {
        return this.name.toString();
    }
}

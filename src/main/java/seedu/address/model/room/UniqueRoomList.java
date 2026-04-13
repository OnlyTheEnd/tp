package seedu.address.model.room;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.room.exceptions.DuplicateRoomException;
import seedu.address.model.room.exceptions.RoomNotFoundException;

/**
 * A list of rooms that enforces uniqueness between its elements and does not allow nulls.
 */
public class UniqueRoomList implements Iterable<Room> {
    private final ObservableList<Room> internalList = FXCollections.observableArrayList();
    private final ObservableList<Room> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent room as the given argument.
     */
    public boolean contains(Room toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameRoom);
    }

    /**
     * Adds a room to the list.
     * The room must not already exist in the list.
     */
    public void add(Room toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateRoomException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the contents of this list with {@code rooms}.
     */
    public void setRooms(List<Room> rooms) {
        requireAllNonNull(rooms);
        if (!roomsAreUnique(rooms)) {
            throw new DuplicateRoomException();
        }

        internalList.setAll(rooms);
    }

    /**
     * Replaces the room {@code target} in the list with {@code editedRoom}.
     * {@code target} must exist in the list.
     * The room identity of {@code editedRoom} must not be the same as another existing room in the list.
     */
    public void setRoom(Room target, Room editedRoom) {
        requireNonNull(target);
        requireNonNull(editedRoom);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new RoomNotFoundException();
        }

        if (!target.isSameRoom(editedRoom) && contains(editedRoom)) {
            throw new DuplicateRoomException();
        }

        internalList.set(index, editedRoom);
    }

    /**
     * Returns true if {@code rooms} contains only unique rooms.
     */
    private boolean roomsAreUnique(List<Room> rooms) {
        for (int i = 0; i < rooms.size() - 1; i++) {
            for (int j = i + 1; j < rooms.size(); j++) {
                if (rooms.get(i).isSameRoom(rooms.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Removes the equivalent room from the list.
     * The room must exist in the list.
     */
    public void remove(Room toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new RoomNotFoundException();
        }
    }

    /**
     * Adds a tag to a room with a matching name
     */
    public void addRoomTag(Room toCheck, String tag) {
        Room roomToUpdate = internalList.stream()
                .filter(toCheck::isSameRoom)
                .findFirst()
                .orElseThrow(() -> new RoomNotFoundException());

        int index = internalList.indexOf(roomToUpdate);
        roomToUpdate.addTag(tag);
        internalList.set(index, roomToUpdate);
    }

    /**
     * Delete tag from a Room in the roomList
     * @param toCheck must exist in roomList
     * @param tag A string
     */
    public void deleteRoomTag(Room toCheck, String tag) {
        Room roomToUpdate = internalList.stream()
                .filter(toCheck::isSameRoom)
                .findFirst()
                .orElseThrow(() -> new RoomNotFoundException());

        int index = internalList.indexOf(roomToUpdate);
        roomToUpdate.deleteTag(tag);
        internalList.set(index, roomToUpdate);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Room> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Room> iterator() {
        return internalList.iterator();
    }
}

package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.room.Room;

/**
 * A utility class containing a list of {@code Room} objects to be used in tests.
 */
public class TypicalRooms {

    public static final Room ROOM_A = new RoomBuilder().withName("MPSH-1")
            .withLocation("Sports-Centre").withStatus("Available").build();
    public static final Room ROOM_B = new RoomBuilder().withName("Sports-Hall-2")
            .withLocation("University-Town").withStatus("Maintenance").build();
    public static final Room ROOM_C = new RoomBuilder().withName("Outdoor-Tennis-Court")
            .withLocation("Kent-Ridge").withStatus("Booked").build();
    public static final Room ROOM_D = new RoomBuilder().withName("Outdoor-Tennis-Court")
            .withLocation("Kent-Ridge").withStatus("Booked").withTags("Sports", "Tennis").build();

    private TypicalRooms() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical rooms.
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        for (Room room : getTypicalRooms()) {
            ab.addRoom(room);
        }
        return ab;
    }

    public static List<Room> getTypicalRooms() {
        return new ArrayList<>(Arrays.asList(ROOM_A, ROOM_B, ROOM_C));
    }
}

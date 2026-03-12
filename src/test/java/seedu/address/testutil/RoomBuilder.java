package seedu.address.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.room.Location;
import seedu.address.model.room.Room;
import seedu.address.model.room.RoomName;
import seedu.address.model.room.Status;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;



/**
 * A utility class to help with building Room objects.
 */
public class RoomBuilder {

    public static final String DEFAULT_NAME = "MPSH-1";
    public static final String DEFAULT_LOCATION = "Sports-Centre";
    public static final String DEFAULT_STATUS = "Available";


    private RoomName name;
    private Location location;
    private Status status;
    private Set<Tag> tags;

    /**
     * Creates a {@code RoomBuilder} with the default details.
     */
    public RoomBuilder() {
        name = new RoomName(DEFAULT_NAME);
        location = new Location(DEFAULT_LOCATION);
        status = new Status(DEFAULT_STATUS);
        tags = new HashSet<>();
    }

    /**
     * Initializes the RoomBuilder with the data of {@code roomToCopy}.
     */
    public RoomBuilder(Room roomToCopy) {
        name = roomToCopy.getName();
        location = roomToCopy.getLocation();
        status = roomToCopy.getStatus();
    }

    /**
     * Sets the {@code RoomName} of the {@code Room} that we are building.
     */
    public RoomBuilder withName(String name) {
        this.name = new RoomName(name);
        return this;
    }

    /**
     * Sets the {@code Location} of the {@code Room} that we are building.
     */
    public RoomBuilder withLocation(String location) {
        this.location = new Location(location);
        return this;
    }

    /**
     * Sets the {@code Status} of the {@code Room} that we are building.
     */
    public RoomBuilder withStatus(String status) {
        this.status = new Status(status);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Room} that we are building.
     */
    public RoomBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    public Room build() {
        return new Room(name, location, status);
    }
}

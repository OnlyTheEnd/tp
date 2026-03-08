package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.room.Location;
import seedu.address.model.room.Room;
import seedu.address.model.room.RoomName;
import seedu.address.model.room.Status;

/**
 * Jackson-friendly version of {@code Room}.
 */
class JsonAdaptedRoom {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Room's %s field is missing!";

    private final String name;
    private final String location;
    private final String status;

    /**
     * Constructs a {@code JsonAdaptedRoom} with the given room details.
     */
    @JsonCreator
    public JsonAdaptedRoom(@JsonProperty("name") String name, @JsonProperty("location") String location,
                           @JsonProperty("status") String status) {
        this.name = name;
        this.location = location;
        this.status = status;
    }

    /**
     * Converts a given {@code Room} into this class for Jackson use.
     */
    public JsonAdaptedRoom(Room source) {
        name = source.getName().fullName;
        location = source.getLocation().value;
        status = source.getStatus().value;
    }

    /**
     * Converts this Jackson-friendly adapted room object into the model's {@code Room} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted room.
     */
    public Room toModelType() throws IllegalValueException {
        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, RoomName.class.getSimpleName()));
        }
        if (!RoomName.isValidName(name)) {
            throw new IllegalValueException(RoomName.MESSAGE_CONSTRAINTS);
        }
        final RoomName modelName = new RoomName(name);

        if (location == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Location.class.getSimpleName()));
        }
        final Location modelLocation = new Location(location);

        if (status == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Status.class.getSimpleName()));
        }
        if (!Status.isValidStatus(status)) {
            throw new IllegalValueException(Status.MESSAGE_CONSTRAINTS);
        }
        final Status modelStatus = new Status(status);

        return new Room(modelName, modelLocation, modelStatus);
    }
}
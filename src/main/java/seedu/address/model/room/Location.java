package seedu.address.model.room;

import static java.util.Objects.requireNonNull;

public class Location {
    public final String value;

    public Location(String location) {
        requireNonNull(location);
        this.value = location.trim();
    }

    @Override
    public String toString() {
        return value;
    }
}
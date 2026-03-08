package seedu.address.model.room;

import static seedu.address.commons.util.AppUtil.checkArgument;

public class RoomName {
    public static final String MESSAGE_CONSTRAINTS = "Room names should be alphanumeric and not empty.";
    public final String fullName;

    public RoomName(String name) {
        checkArgument(isValidName(name), MESSAGE_CONSTRAINTS);
        this.fullName = name.trim().replaceAll("\\s{2,}", " "); // Collapse multiple spaces
    }

    public static boolean isValidName(String test) {
        return test.matches("[\\p{Alnum}][\\p{Alnum} /-]*");
    }

    @Override
    public String toString() {
        return fullName;
    }
}
package seedu.address.model.room;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

public class Status {
    public static final String MESSAGE_CONSTRAINTS =
            "Status should only be: Available, Booked, or Maintenance.";

    public final String value;

    public Status(String status) {
        requireNonNull(status);
        String formattedStatus = formatStatus(status);
        checkArgument(isValidStatus(formattedStatus), MESSAGE_CONSTRAINTS);
        this.value = formattedStatus;
    }

    private String formatStatus(String status) {
        String lower = status.trim().toLowerCase();
        if (lower.isEmpty()) return "Available"; // Default as per your spec
        // Capitalize first letter for display consistency
        return lower.substring(0, 1).toUpperCase() + lower.substring(1);
    }

    public static boolean isValidStatus(String test) {
        String t = test.toLowerCase();
        return t.equals("available") || t.equals("booked") || t.equals("maintenance");
    }

    @Override
    public String toString() {
        return value;
    }
}
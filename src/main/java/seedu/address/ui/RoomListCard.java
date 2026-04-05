package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.room.Room;

/**
 * An UI component that displays information of a {@code Room}.
 */
public class RoomListCard extends UiPart<Region> {
    private static final String FXML = "RoomListCard.fxml";

    public final Room room;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label roomLocation;
    @FXML
    private Label status;

    /**
     * Creates a {@code RoomListCard} with the given {@code Room} and index to display.
     */
    public RoomListCard(Room room, int displayedIndex) {
        super(FXML);
        this.room = room;
        id.setText(displayedIndex + ". ");
        String nameText = room.getName().toString();
        name.setText(nameText);

        String locationText = room.getLocation().toString();
        roomLocation.setText("Location: " + locationText);

        String statusText = room.getStatus().toString();
        status.setText(statusText);
        configureStatusStyle(statusText);
    }

    private void configureStatusStyle(String statusText) {
        if (statusText.equalsIgnoreCase("Available")) {
            status.setStyle("-fx-background-color: #2e7d32; -fx-text-fill: white;"
                    + " -fx-padding: 2 5 2 5; -fx-background-radius: 5;");
        } else if (statusText.equalsIgnoreCase("Booked")) {
            status.setStyle("-fx-background-color: #c62828; -fx-text-fill: white;"
                    + " -fx-padding: 2 5 2 5; -fx-background-radius: 5;");
        } else if (statusText.equalsIgnoreCase("Maintenance")) {
            status.setStyle("-fx-background-color: #ffa000; -fx-text-fill: white;"
                    + " -fx-padding: 2 5 2 5; -fx-background-radius: 5;");
        }
    }
}

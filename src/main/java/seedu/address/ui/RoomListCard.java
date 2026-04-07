package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
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
    @FXML
    private FlowPane tags;

    /**
     * Creates a {@code RoomListCard} with the given {@code Room} and index to display.
     */
    public RoomListCard(Room room, int displayedIndex) {
        super(FXML);
        this.room = room;
        id.setText(displayedIndex + ". ");
        name.setText(room.getName().toString());
        roomLocation.setText(room.getLocation().toString());
        status.setText("Status: " + room.getStatus().toString());
        room.getTags().stream()
                .sorted()
                .forEach(tag -> {
                    Label tagLabel = new Label(tag.tagName);
                    tagLabel.getStyleClass().add("tag");
                    tags.getChildren().add(tagLabel);
                });
    }
}

package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.equipment.Equipment;

/**
 * An UI component that displays information of a {@code Equipment}.
 */
public class EquipmentListCard extends UiPart<Region> {
    private static final String FXML = "EquipmentListCard.fxml";

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label category;
    @FXML
    private Label status;

    /**
     * Creates a {@code EquipmentListCard} with the given {@code Equipment} and index to display.
     */
    public EquipmentListCard(Equipment equipment, int displayedIndex) {
        super(FXML);
        id.setText(displayedIndex + ". ");
        String nameText = equipment.getName().fullName;
        name.setText(nameText);

        String categoryText = equipment.getCategory().toString();
        category.setText("Category: " + categoryText);

        String statusText = equipment.getStatus().toString();
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
        } else if (statusText.equalsIgnoreCase("Damaged")) {
            status.setStyle("-fx-background-color: #546e7a; -fx-text-fill: white;"
                    + " -fx-padding: 2 5 2 5; -fx-background-radius: 5;");
        }
    }
}

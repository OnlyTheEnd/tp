package seedu.address.ui;

import java.time.LocalDateTime;
import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import seedu.address.model.issue.IssueRecord;
import seedu.address.model.person.Person;
import seedu.address.model.reservation.Reservation;

/**
 * An UI component that displays information of a {@code Person}.
 */
public class PersonCard extends UiPart<Region> {

    private static final String FXML = "PersonListCard.fxml";

    public final Person person;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label phone;
    @FXML
    private Label address;
    @FXML
    private Label email;
    @FXML
    private VBox loansContainer;
    @FXML
    private VBox reservationsContainer;

    /**
     * Creates a {@code PersonCard} with the given {@code Person} and index to
     * display.
     */
    public PersonCard(Person person, int displayedIndex, List<IssueRecord> loans, List<Reservation> reservations) {
        super(FXML);
        this.person = person;
        id.setText(displayedIndex + ". ");
        name.setText(person.getName().fullName + " [" + person.getStudentId().value + "]");
        phone.setText(person.getPhone().value);
        email.setText(person.getEmail().value);

        // Populate the loans container
        if (loans.isEmpty()) {
            Label noLoans = new Label("No active loans");
            noLoans.getStyleClass().add("cell_small_label");
            loansContainer.getChildren().add(noLoans);
        } else {
            loans.forEach(loan -> {
                boolean isOverdue = loan.getDueDateTime().isBefore(LocalDateTime.now());

                Label status = new Label(isOverdue ? "OVERDUE" : "BORROWED");
                // Red for overdue. Orange for borrowed.
                configureStatusStyle(status, isOverdue ? "#c62828" : "#ffa000");

                Label loanInfo = new Label(" " + loan.getItemId() + " (Due: " + loan.getFormattedDueDateTime() + ")");
                loanInfo.setStyle("-fx-font-weight: bold; -fx-text-fill: #E0E0E0; -fx-font-size: 11px;");

                // Put them in a row and add to container
                HBox loanRow = new HBox(status, loanInfo);
                loansContainer.getChildren().add(loanRow);
            });
        }

        if (reservations.isEmpty()) {
            Label noReservation = new Label("No reservations");
            noReservation.getStyleClass().add("cell_small_label");
            reservationsContainer.getChildren().add(noReservation);
        } else {
            reservations.forEach(res -> {

                Label resLabel = new Label(
                        "[RESERVED] " + res.getResourceId() + " (" + res.getFormattedStartDateTime()
                                + " to " + res.getFormattedEndDateTime() + ")");
                resLabel.getStyleClass().add("cell_small_label");
                resLabel.setStyle("-fx-text-fill: #FFD700;"); // Gold
                loansContainer.getChildren().add(resLabel);
                Label status = new Label("RESERVED");
                // Orange for reserved.
                configureStatusStyle(status, "#ffa000");

                String timeRange = String.format(" %s [%s — %s]",
                            res.getResourceId(),
                            res.getFormattedStartDateTime(),
                            res.getFormattedEndDateTime());

                Label resInfo = new Label(timeRange);
                resInfo.setStyle("-fx-font-weight: bold; -fx-text-fill: #FFF59D; -fx-font-size: 11px;");

                // Put them in a row and add to container
                HBox resRow = new HBox(status, resInfo);
                reservationsContainer.getChildren().add(resRow);

            });
        }
    }

    private void configureStatusStyle(Label label, String color) {
        label.setStyle("-fx-background-color: " + color + "; "
                + "-fx-text-fill: white; "
                + "-fx-padding: 2 5 2 5; "
                + "-fx-background-radius: 5; "
                + "-fx-font-size: 10px; "
                + "-fx-font-weight: bold;");
    }
}

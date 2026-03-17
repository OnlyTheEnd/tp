package seedu.address.ui;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import seedu.address.model.issue.IssueRecord;
import seedu.address.model.person.Person;

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
    private FlowPane tags;
    @FXML
    private VBox loansContainer;

    /**
     * Creates a {@code PersonCard} with the given {@code Person} and index to
     * display.
     */
    public PersonCard(Person person, int displayedIndex, List<IssueRecord> loans) {
        super(FXML);
        this.person = person;
        id.setText(displayedIndex + ". ");
        name.setText(person.getName().fullName + " [" + person.getStudentId().value + "]");
        phone.setText(person.getPhone().value);
        // address.setText(person.getAddress().value);
        email.setText(person.getEmail().value);
        person.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));

        // Populate the loans container
        if (loans.isEmpty()) {
            Label noLoans = new Label("No active loans");
            noLoans.getStyleClass().add("cell_small_label");
            loansContainer.getChildren().add(noLoans);
        } else {
            loans.forEach(loan -> {
                String status = loan.getDueDateTime().isBefore(LocalDateTime.now())
                        ? "[OVERDUE] "
                        : "[BORROWED] ";
                Label loanLabel = new Label(
                        status + loan.getItemId() + " (Due: " + loan.getFormattedDueDateTime() + ")");
                loanLabel.getStyleClass().add("cell_small_label");
                // Set text color to red if overdue
                if (status.contains("OVERDUE")) {
                    loanLabel.setStyle("-fx-text-fill: #FF4B4B;");
                }
                loansContainer.getChildren().add(loanLabel);
            });
        }
    }
}

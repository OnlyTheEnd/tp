package seedu.address.ui;

import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.issue.IssueRecord;
import seedu.address.model.person.Person;
import seedu.address.model.reservation.Reservation;

/**
 * Panel containing the list of persons.
 */
public class PersonListPanel extends UiPart<Region> {
    private static final String FXML = "PersonListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(PersonListPanel.class);

    @FXML
    private ListView<Person> personListView;

    /**
     * Creates a {@code PersonListPanel} with the given {@code ObservableList}.
     */
    public PersonListPanel(ObservableList<Person> personList, ObservableList<IssueRecord> issueRecordList,
                                ObservableList<Reservation> reservationList) {
        super(FXML);
        personListView.setItems(personList);
        personListView.setCellFactory(listView -> new PersonListViewCell(issueRecordList, reservationList));
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Person} using
     * a {@code PersonCard}.
     */
    class PersonListViewCell extends ListCell<Person> {

        private final ObservableList<IssueRecord> allLoans;
        private final ObservableList<Reservation> allReservations;

        public PersonListViewCell(ObservableList<IssueRecord> allLoans, ObservableList<Reservation> allReservations) {
            this.allLoans = allLoans;
            this.allReservations = allReservations;
        }

        @Override
        protected void updateItem(Person person, boolean empty) {
            super.updateItem(person, empty);

            if (empty || person == null) {
                setGraphic(null);
                setText(null);
            } else {
                // Filter loans for this specific person's student ID
                List<IssueRecord> studentLoans = allLoans.stream()
                        .filter(loan -> loan.getStudentId().equals(person.getStudentId()))
                        .collect(Collectors.toList());

                List<Reservation> studentReservations = allReservations.stream()
                        .filter(res -> res.getStudentId().equals(person.getStudentId()))
                        .collect(Collectors.toList());

                setGraphic(new PersonCard(person, getIndex() + 1, studentLoans, studentReservations).getRoot());
            }
        }
    }
}

package seedu.address.ui;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.model.equipment.Equipment;

/**
 * Panel containing the list of equipments.
 */
public class EquipmentListPanel extends UiPart<Region> {
    private static final String FXML = "EquipmentListPanel.fxml";

    @FXML
    private ListView<Equipment> equipmentListView;

    /**
     * Creates a {@code EquipmentListPanel} with the given {@code ObservableList}.
     */
    public EquipmentListPanel(ObservableList<Equipment> equipmentList) {
        super(FXML);
        equipmentListView.setItems(equipmentList);
        equipmentListView.setCellFactory(listView -> new EquipmentListViewCell());
    }

    class EquipmentListViewCell extends ListCell<Equipment> {
        @Override
        protected void updateItem(Equipment equipment, boolean empty) {
            super.updateItem(equipment, empty);

            if (empty || equipment == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new EquipmentListCard(equipment, getIndex() + 1).getRoot());
            }
        }
    }
}

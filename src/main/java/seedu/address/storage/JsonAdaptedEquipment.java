package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.equipment.Equipment;
import seedu.address.model.equipment.EquipmentName;
import seedu.address.model.equipment.EquipmentStatus;

/**
 * Jackson-friendly version of {@link Equipment}.
 */
class JsonAdaptedEquipment {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Equipment's %s field is missing!";

    private final String name;
    private final String category;
    private final String status;

    /**
     * Constructs a {@code JsonAdaptedEquipment} with the given equipment details.
     */
    @JsonCreator
    public JsonAdaptedEquipment(@JsonProperty("name") String name, @JsonProperty("category") String category,
                                @JsonProperty("status") String status) {
        this.name = name;
        this.category = category;
        this.status = status;
    }

    /**
     * Converts a given {@code Equipment} into this class for Jackson use.
     */
    public JsonAdaptedEquipment(Equipment source) {
        name = source.getName().fullName;
        category = source.getCategory();
        status = source.getStatus().toString();
    }

    /**
     * Converts this Jackson-friendly adapted equipment object into the model's {@code Equipment} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted equipment.
     */
    public Equipment toModelType() throws IllegalValueException {
        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    EquipmentName.class.getSimpleName()));
        }
        if (!EquipmentName.isValidName(name)) {
            throw new IllegalValueException(EquipmentName.MESSAGE_CONSTRAINTS);
        }
        final EquipmentName modelName = new EquipmentName(name);

        if (category == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "category"));
        }

        if (status == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    EquipmentStatus.class.getSimpleName()));
        }
        if (!EquipmentStatus.isValidStatus(status)) {
            throw new IllegalValueException(EquipmentStatus.MESSAGE_CONSTRAINTS);
        }
        final EquipmentStatus modelStatus = EquipmentStatus.valueOf(status.toUpperCase());

        return new Equipment(modelName, category, modelStatus);
    }
}

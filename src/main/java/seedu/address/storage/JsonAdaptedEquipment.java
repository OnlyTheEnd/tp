package seedu.address.storage;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.equipment.Equipment;
import seedu.address.model.equipment.EquipmentName;
import seedu.address.model.equipment.EquipmentStatus;
import seedu.address.model.tag.Tag;



/**
 * Jackson-friendly version of {@link Equipment}.
 */
class JsonAdaptedEquipment {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Equipment's %s field is missing!";

    private final String name;
    private final String category;
    private final String status;
    private final List<JsonAdaptedTag> tags = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedEquipment} with the given equipment details.
     */
    @JsonCreator
    public JsonAdaptedEquipment(@JsonProperty("name") String name, @JsonProperty("category") String category,
                                @JsonProperty("status") String status,
                                @JsonProperty("tags") List<JsonAdaptedTag> tags) {
        this.name = name;
        this.category = category;
        this.status = status;
        if (tags != null) {
            this.tags.addAll(tags);
        }
    }

    /**
     * Converts a given {@code Equipment} into this class for Jackson use.
     */
    public JsonAdaptedEquipment(Equipment source) {
        name = source.getName().fullName;
        category = source.getCategory();
        status = source.getStatus().toString();
        tags.addAll(source.getTags().stream()
                .map(JsonAdaptedTag::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted equipment object into the model's {@code Equipment} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted equipment.
     */
    public Equipment toModelType() throws IllegalValueException {
        final List<Tag> equipmentTags = new ArrayList<>();
        for (JsonAdaptedTag tag : tags) {
            equipmentTags.add(tag.toModelType());
        }
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

        final Set<Tag> modelTags = new HashSet<>(equipmentTags);
        return new Equipment(modelName, category, modelStatus, modelTags);
    }
}

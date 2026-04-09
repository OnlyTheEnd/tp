package seedu.address.logic.commands;



import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.tag.Tag;

/**
 * Filter and displays list based on tag to user
 */
public class FilterTagCommand extends Command {
    public static final String COMMAND_WORD = "filter-r";
    public static final String COMMAND_WORD2 = "filter-e";


    public static final String MESSAGE_USAGE = COMMAND_WORD + "/" + COMMAND_WORD2
            + ": Filters out all room/equipment by tag\n"
            + "Parameters: " + " TAG\n"
            + "Example: " + COMMAND_WORD + " IHG\n"
            + COMMAND_WORD2 + " IHG";

    public static final String MESSAGE_SUCCESS = "Success! List of %1$s tagged with %2$s shown";
    public static final String MESSAGE_ERROR = "Failure! Target type or tag not specified";
    public static final String MESSAGE_TAG_NOT_FOUND_ROOM = "Failure! No rooms found with tag: %s";
    public static final String MESSAGE_TAG_NOT_FOUND_EQUIPMENT = "Failure! No equipments found with tag: %s";


    private final String targetType;
    private final Tag targetTag;
    /**
     * Creates an FilterTagCommand to the specified tag
     */
    public FilterTagCommand(String targetType, Tag targetTag) {
        requireNonNull(targetType);
        requireNonNull(targetTag);
        this.targetType = targetType;
        this.targetTag = targetTag;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        if (targetType.equals("room")) {
            model.updateFilteredRoomList(unused -> true);
            model.updateFilteredRoomList(room -> room.getTags().contains(targetTag));
            // Check if any rooms match
            if (model.getFilteredRoomList().isEmpty()) {
                throw new CommandException(
                        String.format(MESSAGE_TAG_NOT_FOUND_ROOM, targetTag));
            }
            return new CommandResult(String.format(MESSAGE_SUCCESS, targetType, targetTag),
                    false, false, true, true, true);
        } else if (targetType.equals("equipment")) {
            model.updateFilteredEquipmentList(equipment -> equipment.getTags().contains(targetTag));
            // Check if any rooms match
            if (model.getFilteredEquipmentList().isEmpty()) {
                model.updateFilteredEquipmentList(unused -> true);
                throw new CommandException(
                        String.format(MESSAGE_TAG_NOT_FOUND_EQUIPMENT, targetTag));
            }
            return new CommandResult(String.format(MESSAGE_SUCCESS, targetType, targetTag),
                    false, false, true, true, true);
        } else {
            throw new CommandException(MESSAGE_ERROR);
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof FilterTagCommand)) {
            return false;
        }

        FilterTagCommand otherFilterTagCommand = (FilterTagCommand) other;
        return targetType.equals(otherFilterTagCommand.targetType)
                && targetTag.equals(otherFilterTagCommand.targetTag);
    }
}

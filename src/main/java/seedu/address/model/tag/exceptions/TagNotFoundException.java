package seedu.address.model.tag.exceptions;

/**
 * Signals that the operation will result in removing missing Tags
 * (Tags are considered missing if they are not in tagSet of room/equipment).
 */
public class TagNotFoundException extends RuntimeException {
    public TagNotFoundException() {
        super("Operation did not find tag to remove");
    }
}

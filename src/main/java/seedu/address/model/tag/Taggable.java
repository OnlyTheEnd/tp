package seedu.address.model.tag;
import java.util.HashSet;
import java.util.Set;

/**
 * An abstract class for taggable objects
 */
public abstract class Taggable {
    protected static final Set<String> TAGGED_ENTRIES = new HashSet<>();

    protected final Set<Tag> tags = new HashSet<>();

    static String buildKey(String typeName, String name, String tag) {
        return typeName + ":" + name + ":" + tag;
    }

    static boolean hasBeenTagged(String typeName, String name, String tag) {
        return TAGGED_ENTRIES.contains(buildKey(typeName, name, tag));
    }

    protected void registerTag(String typeName, String name, String tag) {
        TAGGED_ENTRIES.add(buildKey(typeName, name, tag));
    }

    // Remove a single tag entry
    protected static void removeTag(String typeName, String name, String tag) {
        TAGGED_ENTRIES.remove(buildKey(typeName, name, tag));
    }

    public Set<Tag> getTags() {
        return this.tags;
    }
    /**
     * Delete all related tags on deletion of Taggable Obejct
     */
    public void onDelete() {
        for (Tag tag : tags) {
            Taggable.removeTag(this.getClass().getSimpleName(), this.getNameString(), tag.toString());
        }
    }


    public abstract String getNameString();

    public abstract void addTag(Tag tag);

    public abstract void deleteTag(Tag otherTag);

}

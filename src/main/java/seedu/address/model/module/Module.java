package seedu.address.model.module;

import java.util.Objects;

import javafx.collections.ObservableList;
import seedu.address.model.group.Group;
import seedu.address.model.group.UniqueGroupList;

/**
 * Represents a module in the TAT.
 */
public class Module {
    private final String identifier;
    private final UniqueGroupList groups;

    /**
     * Constructs a group object.
     *
     * @param identifier identifies the module. Usually equal
     *                   to the module code.
     */
    public Module(String identifier) {
        this.identifier = identifier;
        groups = new UniqueGroupList();
    }

    //TODO: the following methods
    /**
     * Adds a group to the list of groups.
     */
    public void addGroup(Group group) {
        groups.add(group);
    }

    /**
     * Gets group with given group code (could be tutorial or
     * lab code). Returns null if no such group exists.
     */
    public Group getGroup(String identifier) {
        Group group = null;
        for (int i = 0; i < groups.size(); ++i) {
            group = groups.get(i);
            if (group.getIdentifier().equals(identifier)) {
                break;
            }
        }
        return group;
    }

    /**
     * Returns the group list.
     */
    public ObservableList<Group> getGroupList() {
        return groups.asUnmodifiableObservableList();
    }

    /**
     * Returns the module identifier.
     */
    public String getIdentifier() {
        return identifier;
    }

    /**
     * Returns true if both modules have the same identifiers.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Module)) {
            return false;
        }

        Module otherModule = (Module) other;
        return otherModule.getIdentifier().equals(this.getIdentifier());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(identifier);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getIdentifier())
                .append(" has ")
                .append(groups.size())
                .append(" groups");
        return builder.toString();
    }
}

package tatracker.model.module;

import java.util.Objects;

import javafx.collections.ObservableList;

import tatracker.model.group.Group;
import tatracker.model.group.UniqueGroupList;
import tatracker.model.session.Session;
import tatracker.model.session.UniqueSessionList;

/**
 * Represents a module in the TAT.
 */
public class Module {
    private static final String DEFAULT_NAME = "";

    private final String identifier;
    private final String name;
    private final UniqueGroupList groups;
    private final UniqueSessionList doneSessions;

    /**
     * Constructs a module object with no name.
     */
    public Module(String identifier) {
        this(identifier, DEFAULT_NAME);
    }

    /**
     * Constructs a module object.
     *
     * @param identifier identifies the module.
     *                   Usually equal to the module code.
     * @param name the name of the module.
     */
    public Module(String identifier, String name) {
        this.identifier = identifier;
        this.name = name;
        this.groups = new UniqueGroupList();
        this.doneSessions = new UniqueSessionList();
    }

    /**
     * Returns the module identifier.
     */
    public String getIdentifier() {
        return identifier;
    }

    /**
     * Returns module name.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the group list.
     */
    public ObservableList<Group> getGroupList() {
        return groups.asUnmodifiableObservableList();
    }

    /**
     * Returns the session list.
     */
    public ObservableList<Session> getSessionList() {
        return doneSessions.asUnmodifiableObservableList();
    }

    public boolean hasGroup(Group group) {
        return groups.contains(group);
    }

    /**
     * Adds a group to the list of module groups.
     */
    public void addGroup(Group group) {
        groups.add(group);
    }

    /**
     * Returns the group in this module with the given group id.
     * Returns null if no such group exists.
     */
    public Group getGroup(String groupId) {
        return groups.get(groupId);
    }

    /**
     * Deletes the given group from the list of module groups,
     * if it exists.
     */
    public void deleteGroup(Group group) {
        groups.remove(group);
    }

    public boolean hasDoneSession(Session session) {
        return doneSessions.contains(session);
    }

    /**
     * Adds a done session to the list of done sessions for this module.
     */
    public void addDoneSession(Session session) {
        doneSessions.add(session);
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
        return this.identifier.equals(otherModule.identifier);
    }

    @Override
    public int hashCode() {
        return Objects.hash(identifier);
    }

    @Override
    public String toString() {
        return String.format("%s (%s) %s", name, identifier, groupsString());
    }

    /**
     * Returns a string that shows the value inside the groups list.
     */
    private String groupsString() {
        // TODO: test groups are printed correctly
        StringBuilder str = new StringBuilder();
        str.append("[");
        for (int i = 0; i < groups.size(); ++i) {
            if (i > 0) {
                str.append(", ");
            }
            groups.get(i);
        }
        str.append("]");
        return str.toString();
    }
}

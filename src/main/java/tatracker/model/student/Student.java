package tatracker.model.student;

import static tatracker.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import tatracker.model.tag.Tag;

/**
 * Represents a Student in the Ta-Tracker.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Student {

    // Identity fields
    private final Name name;
    private final Phone phone;
    private final Email email;
    private final Matric matric;

    // Data fields
    private final Set<Tag> tags;

    /**
     * Every field must be present and not null.
     */
    public Student(Name name, Phone phone, Email email, Matric matric, Set<Tag> tags) {
        requireAllNonNull(name, phone, email, matric, tags);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.matric = matric;

        this.tags = new HashSet<>();
        this.tags.addAll(tags);
    }

    /**
     * Completes the details of a {@code Student} using the builder pattern.
     */
    private Student(StudentBuilder sb) {
        this.name = sb.name;
        this.phone = sb.phone;
        this.email = sb.email;
        this.matric = sb.matric;

        this.tags = new HashSet<>();
        this.tags.addAll(sb.tags);
    }

    public Name getName() {
        return name;
    }

    public Phone getPhone() {
        return phone;
    }

    public Email getEmail() {
        return email;
    }

    public Matric getMatric() {
        return matric;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns true if both students of the same name have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two students.
     */
    public boolean isSameStudent(Student otherStudent) {
        if (otherStudent == this) {
            return true;
        }

        return otherStudent != null
                && otherStudent.getName().equals(getName())
                && (otherStudent.getPhone().equals(getPhone()) || otherStudent.getEmail().equals(getEmail()))
                && otherStudent.getMatric().equals(getMatric());
    }

    /**
     * Returns true if both students have the same identity and data fields.
     * This defines a stronger notion of equality between two students.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Student)) {
            return false;
        }

        Student otherStudent = (Student) other;
        return otherStudent.getName().equals(getName())
                && otherStudent.getPhone().equals(getPhone())
                && otherStudent.getEmail().equals(getEmail())
                && otherStudent.getMatric().equals(getMatric())
                && otherStudent.getTags().equals(getTags());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, matric, tags);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" Phone: ")
                .append(getPhone())
                .append(" Email: ")
                .append(getEmail())
                .append(" Matric: ")
                .append(getMatric())
                .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }

    /**
     * A utility class to help build a {@code Student} with optional fields.
     */
    public static class StudentBuilder {

        // Identity fields
        private Name name;
        private Phone phone;
        private Email email;
        private Matric matric;

        // Data fields
        private Set<Tag> tags;

        /**
         * All fields must be present and not null.
         */
        public StudentBuilder(Name name, Phone phone, Email email, Matric matric) {
            requireAllNonNull(name, phone, email, matric);
            this.name = name;
            this.phone = phone;
            this.email = email;
            this.matric = matric;
            this.tags = new HashSet<>();
        }

        /**
         * Initializes the StudentBuilder with the data of {@code studentToCopy}.
         */
        public StudentBuilder(Student studentToCopy) {
            this.name = studentToCopy.getName();
            this.phone = studentToCopy.getPhone();
            this.email = studentToCopy.getEmail();
            this.matric = studentToCopy.getMatric();
            this.tags = new HashSet<>(studentToCopy.getTags());
        }

        /**
         * Adds a list of {@code Tags} for the {@code Student} that is being built.
         */
        public StudentBuilder withTags(Tag ... tags) {
            this.tags.addAll(Arrays.asList(tags));
            return this;
        }

        /**
         * Adds a set of {@code Tags} for the {@code Student} that is being built.
         */
        public StudentBuilder withTags(Set<Tag> tags) {
            this.tags.addAll(tags);
            return this;
        }

        public Student build() {
            return new Student(this);
        }
    }
}

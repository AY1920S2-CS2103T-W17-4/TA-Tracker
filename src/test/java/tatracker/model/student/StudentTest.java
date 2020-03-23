package tatracker.model.student;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import tatracker.logic.commands.CommandTestUtil;
import tatracker.testutil.Assert;
import tatracker.testutil.StudentBuilder;
import tatracker.testutil.TypicalStudents;

public class StudentTest {

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Student student = new StudentBuilder().build();
        Assert.assertThrows(UnsupportedOperationException.class, () -> student.getTags().remove(0));
    }

    @Test
    public void isSameStudent() {
        // same object -> returns true
        Assertions.assertTrue(TypicalStudents.ALICE.isSameStudent(TypicalStudents.ALICE));

        // null -> returns false
        Assertions.assertFalse(TypicalStudents.ALICE.isSameStudent(null));

        // different phone and email -> returns false
        Student editedAlice = new StudentBuilder(TypicalStudents.ALICE)
                .withPhone(CommandTestUtil.VALID_PHONE_BOB)
                .withEmail(CommandTestUtil.VALID_EMAIL_BOB).build();
        Assertions.assertFalse(TypicalStudents.ALICE.isSameStudent(editedAlice));

        // different name -> returns false
        editedAlice = new StudentBuilder(TypicalStudents.ALICE)
                .withName(CommandTestUtil.VALID_NAME_BOB).build();
        Assertions.assertFalse(TypicalStudents.ALICE.isSameStudent(editedAlice));

        // same name, same phone, same matric, different attributes -> returns true
        editedAlice = new StudentBuilder(TypicalStudents.ALICE)
                .withEmail(CommandTestUtil.VALID_EMAIL_BOB)
                .withTags(CommandTestUtil.VALID_TAG_HUSBAND).build();
        Assertions.assertTrue(TypicalStudents.ALICE.isSameStudent(editedAlice));

        // same name, same email, same matric, different attributes -> returns true
        editedAlice = new StudentBuilder(TypicalStudents.ALICE)
                .withPhone(CommandTestUtil.VALID_PHONE_BOB)
                .withTags(CommandTestUtil.VALID_TAG_HUSBAND).build();
        Assertions.assertTrue(TypicalStudents.ALICE.isSameStudent(editedAlice));

        // same name, same phone, same email, same matric different attributes -> returns true
        editedAlice = new StudentBuilder(TypicalStudents.ALICE)
                .withTags(CommandTestUtil.VALID_TAG_HUSBAND).build();
        Assertions.assertTrue(TypicalStudents.ALICE.isSameStudent(editedAlice));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Student aliceCopy = new StudentBuilder(TypicalStudents.ALICE).build();
        Assertions.assertTrue(TypicalStudents.ALICE.equals(aliceCopy));

        // same object -> returns true
        Assertions.assertTrue(TypicalStudents.ALICE.equals(TypicalStudents.ALICE));

        // null -> returns false
        Assertions.assertFalse(TypicalStudents.ALICE.equals(null));

        // different type -> returns false
        Assertions.assertFalse(TypicalStudents.ALICE.equals(5));

        // different student -> returns false
        Assertions.assertFalse(TypicalStudents.ALICE.equals(TypicalStudents.BOB));

        // different name -> returns false
        Student editedAlice = new StudentBuilder(TypicalStudents.ALICE)
                .withName(CommandTestUtil.VALID_NAME_BOB).build();
        Assertions.assertFalse(TypicalStudents.ALICE.equals(editedAlice));

        // different phone -> returns false
        editedAlice = new StudentBuilder(TypicalStudents.ALICE).withPhone(CommandTestUtil.VALID_PHONE_BOB).build();
        Assertions.assertFalse(TypicalStudents.ALICE.equals(editedAlice));

        // different email -> returns false
        editedAlice = new StudentBuilder(TypicalStudents.ALICE).withEmail(CommandTestUtil.VALID_EMAIL_BOB).build();
        Assertions.assertFalse(TypicalStudents.ALICE.equals(editedAlice));

        // different matric -> returns false
        editedAlice = new StudentBuilder(TypicalStudents.ALICE).withMatric(CommandTestUtil.VALID_MATRIC_BOB).build();
        Assertions.assertFalse(TypicalStudents.ALICE.equals(editedAlice));

        // different tags -> returns false
        editedAlice = new StudentBuilder(TypicalStudents.ALICE).withTags(CommandTestUtil.VALID_TAG_HUSBAND).build();
        Assertions.assertFalse(TypicalStudents.ALICE.equals(editedAlice));
    }
}

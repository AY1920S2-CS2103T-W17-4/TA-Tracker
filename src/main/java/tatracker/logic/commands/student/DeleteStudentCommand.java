package tatracker.logic.commands.student;

import static java.util.Objects.requireNonNull;
import static tatracker.logic.parser.Prefixes.GROUP;
import static tatracker.logic.parser.Prefixes.MATRIC;
import static tatracker.logic.parser.Prefixes.MODULE;

import java.util.List;

import tatracker.logic.commands.Command;
import tatracker.logic.commands.CommandDetails;
import tatracker.logic.commands.CommandResult;
import tatracker.logic.commands.CommandWords;
import tatracker.logic.commands.exceptions.CommandException;
import tatracker.model.Model;
import tatracker.model.group.Group;
import tatracker.model.module.Module;
import tatracker.model.student.Matric;
import tatracker.model.student.Student;

/**
 * Deletes a student identified using it's displayed index from the TA-Tracker.
 */
public class DeleteStudentCommand extends Command {

    public static final CommandDetails DETAILS = new CommandDetails(
            CommandWords.STUDENT,
            CommandWords.DELETE_MODEL,
            "Deletes the student with the given matric number from the given module group.",
            List.of(MATRIC, MODULE, GROUP),
            List.of(),
            MATRIC, MODULE, GROUP
    );

    public static final String MESSAGE_DELETE_STUDENT_SUCCESS = "Deleted Student: %1$s";
    public static final String MESSAGE_INVALID_MODULE_FORMAT =
            "There is no module with the given module code: %s";
    public static final String MESSAGE_INVALID_GROUP_FORMAT =
            "There is no group in the module (%s) with the given group code: %s";
    public static final String MESSAGE_INVALID_STUDENT_FORMAT =
            "There is no student in the (%s) group (%s) with the given matric number: %s";

    public static final int FIRST_GROUP_INDEX = 0;
    public static final int FIRST_MODULE_INDEX = 0;

    private final Matric toDelete;
    private final Group targetGroup;
    private final Module targetModule;

    public DeleteStudentCommand(Matric matric, Group group, Module module) {
        requireNonNull(matric);
        requireNonNull(group);
        requireNonNull(module);
        this.toDelete = matric;
        this.targetGroup = group;
        this.targetModule = module;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (!model.hasModule(targetModule)) {
            throw new CommandException(String.format(MESSAGE_INVALID_MODULE_FORMAT, targetModule.getIdentifier()));
        }

        if (!model.hasGroup(targetGroup, targetModule)) {
            throw new CommandException(String.format(MESSAGE_INVALID_GROUP_FORMAT,
                    targetModule.getIdentifier(),
                    targetGroup.getIdentifier()));
        }

        Module actualModule = model.getModule(targetModule.getIdentifier());
        Group actualGroup = actualModule.getGroup(targetGroup.getIdentifier());

        Student studentToDelete = actualGroup.getStudent(toDelete);

        // TODO: consider replacing has methods with id instead of actual objects
        if (studentToDelete == null) {
            throw new CommandException(String.format(MESSAGE_INVALID_STUDENT_FORMAT,
                    targetModule.getIdentifier(),
                    targetGroup.getIdentifier(),
                    toDelete));
        }

        model.deleteStudent(studentToDelete, targetGroup, targetModule);

        if (model.getFilteredModuleList().isEmpty()) {
            model.setFilteredGroupList();
            model.setFilteredStudentList();
        } else {
            model.updateGroupList(FIRST_MODULE_INDEX);
            if (model.getFilteredGroupList().isEmpty()) {
                model.setFilteredStudentList();
            } else {
                model.updateStudentList(FIRST_GROUP_INDEX, FIRST_MODULE_INDEX);
            }
        }

        return new CommandResult(String.format(MESSAGE_DELETE_STUDENT_SUCCESS, studentToDelete));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true; // short circuit if same object
        }

        if (!(other instanceof DeleteStudentCommand)) {
            return false; // instanceof handles nulls
        }

        DeleteStudentCommand otherCommand = (DeleteStudentCommand) other;
        return toDelete.equals(otherCommand.toDelete)
                && targetGroup.equals(otherCommand.targetGroup)
                && targetModule.equals(otherCommand.targetModule);
    }
}

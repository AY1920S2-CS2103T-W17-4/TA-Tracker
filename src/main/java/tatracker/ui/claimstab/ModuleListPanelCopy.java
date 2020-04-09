package tatracker.ui.claimstab;

import static tatracker.model.TaTracker.getCurrentlyShownModuleClaim;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;

import tatracker.commons.core.LogsCenter;
import tatracker.model.module.Module;
import tatracker.ui.Focusable;
import tatracker.ui.UiPart;
import tatracker.ui.studenttab.ModuleCard;

/**
 * Panel containing the list of modules.
 */
public class ModuleListPanelCopy extends UiPart<Region> implements Focusable {
    private static final String FXML = "ModuleListPanelCopy.fxml";
    private static final String BACKGROUND_COLOUR = "#5f4d42";
    private static final String BORDER_COLOUR = "#917b3e";
    private static final String BORDER_WIDTH = "1";

    private final Logger logger = LogsCenter.getLogger(ModuleListPanelCopy.class);

    @FXML
    private ListView<Module> moduleListViewCopy;

    public ModuleListPanelCopy(ObservableList<Module> moduleListCopy) {
        super(FXML);
        moduleListViewCopy.setItems(moduleListCopy);
        moduleListViewCopy.setCellFactory(listView -> new ModuleListViewCellCopy());
    }

    @Override
    public void requestFocus() {
        moduleListViewCopy.requestFocus();
    }

    @Override
    public boolean isFocused() {
        return moduleListViewCopy.isFocused();
    }

    /**
     * Update ListCells in order to facilitate highlighting when a filter command is entered
     * @param moduleList the updated moduleList
     */
    public void updateCells(ObservableList<Module> moduleList) {
        logger.fine("reached updateCells");
        moduleListViewCopy.setItems(moduleList);
        moduleListViewCopy.setCellFactory(listView -> new ModuleListViewCellCopy());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Module} using a {@code ModuleCardCopy}.
     */
    class ModuleListViewCellCopy extends ListCell<Module> {
        @Override
        protected void updateItem(Module module, boolean empty) {
            super.updateItem(module, empty);

            if (empty || module == null) {
                setGraphic(null);
                setText(null);
                setStyle("");
            } else {
                setGraphic(new ModuleCard(module, getIndex() + 1).getRoot());
                if (module.equals(getCurrentlyShownModuleClaim())) {
                    setStyle("-fx-background-color: " + BACKGROUND_COLOUR + "; "
                            + "-fx-border-color: " + BORDER_COLOUR + "; "
                            + "-fx-border-width: " + BORDER_WIDTH + ";");
                } else {
                    setStyle("");
                }
            }
        }
    }

}

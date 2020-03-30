package tatracker.ui;

import static tatracker.model.TaTracker.getCurrentlyShownGroup;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import tatracker.commons.core.LogsCenter;
import tatracker.model.group.Group;

/**
 * Panel containing the list of groups.
 */
public class GroupListPanel extends UiPart<Region> {
    private static final String FXML = "GroupListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(GroupListPanel.class);
    private Group currentlyShownGroup = getCurrentlyShownGroup();

    @FXML
    private ListView<Group> groupListView;

    private static final String BACKGROUND_COLOUR = "#424d5f";
    private static final String BORDER_COLOUR = "#3e7b91";
    private static final String BORDER_WIDTH = "1";

    public GroupListPanel(ObservableList<Group> groupList) {
        super(FXML);
        groupListView.setItems(groupList);
        groupListView.setCellFactory(listView -> new GroupListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Group} using a {@code GroupCard}.
     */
    class GroupListViewCell extends ListCell<Group> {
        @Override
        protected void updateItem(Group group, boolean empty) {
            super.updateItem(group, empty);

            if (empty || group == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new GroupCard(group, getIndex() + 1).getRoot());
                if (group.equals(currentlyShownGroup)) {
                    setStyle("-fx-background-color: " + BACKGROUND_COLOUR + "; " +
                            "-fx-border-color: " + BORDER_COLOUR + "; " +
                            "-fx-border-width: " + BORDER_WIDTH + ";");
                }
            }
        }
    }
}

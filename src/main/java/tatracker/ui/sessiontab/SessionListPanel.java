package tatracker.ui.sessiontab;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;

import tatracker.commons.core.LogsCenter;
import tatracker.model.session.Session;
import tatracker.ui.Focusable;
import tatracker.ui.UiPart;

/**
 * Panel containing the list of sessions.
 */
public class SessionListPanel extends UiPart<Region> implements Focusable {
    private static final String FXML = "SessionListPanel.fxml";
    private static final String BACKGROUND_COLOUR = "#5f4d42";
    private static final String BORDER_COLOUR = "#917b3e";
    private static final String BORDER_WIDTH = "1";

    private final Logger logger = LogsCenter.getLogger(SessionListPanel.class);

    @FXML
    private ListView<Session> sessionListView;

    @FXML
    private GridPane currentFiltersGrid;

    @FXML
    private Label currentDateFilters;

    @FXML
    private Label currentTypeFilters;

    @FXML
    private Label currentModuleFilters;

    public SessionListPanel(ObservableList<Session> sessionList) {
        super(FXML);
        sessionListView.setItems(sessionList);
        sessionListView.setCellFactory(listView -> new SessionListViewCell());
        currentDateFilters.setText("Date: No Filters");
        currentModuleFilters.setText("Module: No Filters");
        currentTypeFilters.setText("Type: No Filters");
        currentFiltersGrid.setStyle("-fx-background-color: " + BACKGROUND_COLOUR + "; "
                + "-fx-border-color: " + BORDER_COLOUR + "; "
                + "-fx-border-width: " + BORDER_WIDTH + ";");
    }

    /**
     * Update Label in order to facilitate changing current filters
     */
    public void updateLabel(String dateFilter, String moduleFilter, String typeFilter) {
        currentDateFilters.setText("Date: " + dateFilter);
        currentModuleFilters.setText("Module: " + moduleFilter);
        currentTypeFilters.setText("Type: " + typeFilter);
    }

    @Override
    public void requestFocus() {
        sessionListView.requestFocus();
    }

    @Override
    public boolean isFocused() {
        return sessionListView.isFocused();
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Session} using a {@code SessionCard}.
     */
    class SessionListViewCell extends ListCell<Session> {
        @Override
        protected void updateItem(Session session, boolean empty) {
            super.updateItem(session, empty);

            if (empty || session == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new SessionCard(session, getIndex() + 1).getRoot());
            }
        }
    }
}
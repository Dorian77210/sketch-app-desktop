package com.terbah.sketch.natif.component.ui.common;

import com.terbah.sketch.api.data.util.SketchDataWrapper;
import com.terbah.sketch.api.ui.SketchConfigurationPopup;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.util.List;

/**
 * @author Dorian TERBAH
 *
 * Popup to display data in list
 *
 * @version 1.0
 */
public class SketchDisplayListPopup extends SketchConfigurationPopup {

    /**
     * Create a new popup with the owner window and a specific title.
     *
     * @param title
     */
    public SketchDisplayListPopup(String title, List<?> data) {
        super(title);
        BorderPane pane = new BorderPane();
        VBox box = new VBox();
        box.getChildren().add(new Label("Data proceeded"));
        ObservableList<?> list = (data != null) ? FXCollections.observableList(data) : FXCollections.observableArrayList();
        ListView<?> listView = new ListView<>(list);
        box.getChildren().add(listView);
        pane.setCenter(box);
        this.setScene(new Scene(pane));
    }
}

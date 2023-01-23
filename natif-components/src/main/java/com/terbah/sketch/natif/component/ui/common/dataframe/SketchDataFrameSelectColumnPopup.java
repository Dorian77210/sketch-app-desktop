package com.terbah.sketch.natif.component.ui.common.dataframe;

import com.terbah.sketch.api.data.dataframe.DataFrame;
import com.terbah.sketch.api.data.util.SketchDataWrapper;
import com.terbah.sketch.api.data.util.SketchDataWrapperFactory;
import com.terbah.sketch.api.ui.SketchConfigurationPopup;
import com.terbah.sketch.natif.component.common.dataframe.SketchDataFrameSelectColumnComponent;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Window;
import org.controlsfx.control.ListSelectionView;

import java.util.ArrayList;
import java.util.List;

import com.jfoenix.controls.JFXButton;

/**
 * @author Dorian TERBAH
 *
 * Popup used to select some headers of a dataframe.
 *
 * @version 1.0
 */
public class SketchDataFrameSelectColumnPopup extends SketchConfigurationPopup
{
    /**
     * Data wrapper used by this popup.
     */
    private final SketchDataFrameSelectColumnModel wrapper;

    /**
     * Create a new popup with the owner window and a specific title.
     *
     * @param wrapper The associated data wrapper for this popup.
     */
    public SketchDataFrameSelectColumnPopup(SketchDataFrameSelectColumnModel wrapper)
    {
        super("Select column");
        this.wrapper = wrapper;
        BorderPane pane = new BorderPane();
        ListSelectionView<String> selectionView = new ListSelectionView<>();
        selectionView.setSourceHeader(new Label("Available columns"));
        selectionView.setTargetHeader(new Label("Selected columns"));
        Scene scene = new Scene(pane);

        JFXButton button = new JFXButton("Finish");
        button.getStyleClass().add("button-raised");

        pane.setBottom(button);

        DataFrame dataframe = this.wrapper.getDataFrame();
        if (dataframe != null)
        {
            List<String> headers = dataframe.getHeaders();
            List<String> selectedHeaders = this.wrapper.getSelectedHeaders();
            List<String> filteredHeaders = new ArrayList<>(headers);
            filteredHeaders.removeAll(selectedHeaders);
            selectionView.setSourceItems(FXCollections.observableList(filteredHeaders));
            selectionView.setTargetItems(FXCollections.observableList(this.wrapper.getSelectedHeaders()));
        }

        button.setOnAction(event -> {
            ObservableList<String> selectedHeaders = selectionView.getTargetItems();
            this.wrapper.setSelectedHeaders(selectedHeaders);
            this.close();
        });

        pane.setCenter(selectionView);
        scene.getStylesheets().add(SketchDataFrameSelectColumnComponent.class.getResource("/css/style.css").toExternalForm());
        this.setScene(scene);
    }

    public static class SketchDataFrameSelectColumnModel
    {
        private final SketchDataWrapper<DataFrame> data;

        private final SketchDataWrapper<List<String>> selectedHeaders;

        public SketchDataFrameSelectColumnModel()
        {
            this.data = SketchDataWrapperFactory.getWrapper(DataFrame.class);
            this.selectedHeaders = SketchDataWrapperFactory.getWrapper(new ArrayList<>());
        }

        public void setDataFrame(final DataFrame dataFrame)
        {
            this.data.setData(dataFrame);
        }

        public DataFrame getDataFrame()
        {
            return this.data.getData();
        }

        public void setSelectedHeaders(final List<String> selectedHeaders)
        {
            this.selectedHeaders.setData(selectedHeaders);
        }

        public List<String> getSelectedHeaders()
        {
            return this.selectedHeaders.getData();
        }

        public SketchDataFrameSelectColumnModel clone() {
            SketchDataFrameSelectColumnModel model = new SketchDataFrameSelectColumnModel();
            if (this.data.isDataAvailable()) {
                model.data.setData(this.data.getData().copy());
            }

            if (this.selectedHeaders.isDataAvailable()) {
                this.selectedHeaders.getData().forEach(header -> model.selectedHeaders.getData().add(header));
            }

            return model;
        }
    }
}

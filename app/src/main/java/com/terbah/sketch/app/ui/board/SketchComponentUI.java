package com.terbah.sketch.app.ui.board;

import com.terbah.sketch.api.SketchComponent;
import com.terbah.sketch.app.core.config.SketchComponentConfiguration;
import com.terbah.sketch.app.ui.board.entry.SketchComponentSlot;
import com.terbah.sketch.app.ui.board.entry.SketchComponentSlotType;
import com.terbah.sketch.app.ui.controller.SketchBoardControllerMediator;
import com.terbah.sketch.app.ui.util.icon.IconBuilder;
import com.terbah.sketch.app.ui.util.icon.SketchIcon;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.TextAlignment;

import javafx.util.Duration;
import org.controlsfx.glyphfont.FontAwesome;
import org.controlsfx.glyphfont.Glyph;
import org.controlsfx.glyphfont.GlyphFont;
import org.controlsfx.glyphfont.GlyphFontRegistry;

import java.util.Map;

/**
 * @author Dorian TERBAH
 *
 * Class that represents the ui aspect of a component.
 *
 * @version 1.0
 */
public class SketchComponentUI extends BorderPane {

    /**
     * The preferred height for the ui.
     */
    private static final double SKETCH_COMPONENT_UI_HEIGHT = 90.0;

    /**
     * The preferred width for the ui.
     */
    private static final double SKETCH_COMPONENT_UI_WIDTH = 120.0;

    /**
     * Width of an entry.
     */
    private static final double ENTRY_WIDTH = SKETCH_COMPONENT_UI_WIDTH / 5;

    /**
     * Height of an entry.
     */
    private static final double ENTRY_HEIGHT = SKETCH_COMPONENT_UI_HEIGHT / 4;

    /**
     * Boolean to know if the component is selected.
     */
    private boolean isSelected;


    /**
     * Associated configuration.
     */
    private final SketchComponentConfiguration configuration;

    /**
     * Constructor
     *
     * @param configuration The configuration of the associated component.
     */
    public SketchComponentUI(SketchComponentConfiguration configuration) {
        super();
        this.configuration = configuration;
        this.isSelected = false;
    }

    /**
     * Set up the UI.
     *
     * @param mediator The mediator associated to this ui.
     * @param associatedComponent The associated component.
     */
    public void setup(SketchBoardControllerMediator mediator, SketchComponent<?> associatedComponent) {
        this.setPrefSize(SKETCH_COMPONENT_UI_WIDTH, SKETCH_COMPONENT_UI_HEIGHT);
        // build the description UI. //
        VBox descriptionUI = new VBox(10);
        descriptionUI.setAlignment(Pos.CENTER);
        Label nameLabel = new Label(configuration.getComponentName());
        nameLabel.setWrapText(true);
        nameLabel.setTextAlignment(TextAlignment.CENTER);
        Button playButton = new Button();
        playButton.setGraphic(IconBuilder.builder().getIcon(SketchIcon.PLAY, Color.GREEN));
        descriptionUI.getChildren().addAll(nameLabel, playButton);
        descriptionUI.setBorder(new Border(new BorderStroke(Color.BLACK, Color.BLACK, Color.BLACK, Color.BLACK,
                BorderStrokeStyle.NONE, BorderStrokeStyle.SOLID, BorderStrokeStyle.NONE, BorderStrokeStyle.SOLID,
                CornerRadii.EMPTY, new BorderWidths(2.0), Insets.EMPTY)));

        this.setCenter(descriptionUI);
        descriptionUI.setOnMouseClicked(event -> mediator.selectComponent(this));


        // entry pane //
        Map<String, Class<?>> entries = this.configuration.getEntries();
        double space = SKETCH_COMPONENT_UI_HEIGHT / (entries.size() + 2);
        VBox entryPane = new VBox(space);
        entryPane.setPadding(new Insets(space, 0.0, space, 0.0));

        // create the entries
        int entryCount = 0;
        for (Map.Entry<String, Class<?>> entry : entries.entrySet()) {
            String entryName = entry.getKey();
            Tooltip tooltip = new Tooltip(entryName);
            tooltip.setShowDelay(Duration.millis(500.0));
            SketchComponentSlot entrySlot = new SketchComponentSlot(SketchComponentSlotType.ENTRY, associatedComponent, entryName);

            entrySlot.xProperty().bind(this.layoutXProperty());
            entrySlot.yProperty().bind(this.layoutYProperty().add((space * 1.5) + (entryCount * 2 * space)));

            Tooltip.install(entrySlot, tooltip);
            entrySlot.setOnMouseClicked(event -> mediator.addSelectedSlot(entrySlot));
            entrySlot.setHeight(space);
            entrySlot.setWidth(ENTRY_WIDTH);
            entryPane.getChildren().add(entrySlot);

            entryCount++;
        }

        this.setLeft(entryPane);

        // output pane //
        Class<?> returnType = this.configuration.getReturnType();
        if (returnType != null) {
            VBox outputPane = new VBox();
            outputPane.setAlignment(Pos.CENTER);
            SketchComponentSlot outputSlot = new SketchComponentSlot(SketchComponentSlotType.OUTPUT, associatedComponent);

            outputSlot.xProperty().bind(this.layoutXProperty().add(SKETCH_COMPONENT_UI_WIDTH));
            outputSlot.yProperty().bind(this.layoutYProperty().add(SKETCH_COMPONENT_UI_HEIGHT / 2));

            outputSlot.setOnMouseClicked(event -> mediator.addSelectedSlot(outputSlot));
            outputSlot.setWidth(ENTRY_WIDTH);
            outputSlot.setHeight(ENTRY_HEIGHT);
            outputPane.getChildren().add(outputSlot);
            this.setRight(outputPane);
        }

        this.setStyle("-fx-background-color: yellow;");
    }

    /**
     * Update borders according to the selected property.
     */
    private void updateBorders() {
        Color selectedColor = this.isSelected ? Color.RED : Color.BLACK;
        this.setBorder(new Border(new BorderStroke(selectedColor, selectedColor, selectedColor, selectedColor,
                BorderStrokeStyle.SOLID, BorderStrokeStyle.SOLID, BorderStrokeStyle.SOLID, BorderStrokeStyle.SOLID,
                CornerRadii.EMPTY, new BorderWidths(2.0), Insets.EMPTY)));
    }

    public void setSelected(boolean isSelected) {
        this.isSelected = isSelected;
        this.updateBorders();
    }
}

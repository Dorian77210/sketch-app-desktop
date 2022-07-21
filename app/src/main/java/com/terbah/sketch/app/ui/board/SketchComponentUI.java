package com.terbah.sketch.app.ui.board;

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
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.TextAlignment;

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
    }

    /**
     * Set up the UI.
     *
     * @param mediator The mediator associated to this ui.
     */
    public void setup(SketchBoardControllerMediator mediator) {
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

        // entry pane //
        Map<String, Class<?>> entries = this.configuration.getEntries();
        double space = SKETCH_COMPONENT_UI_HEIGHT / (entries.size() + 2);
        VBox entryPane = new VBox(space);
        entryPane.setPadding(new Insets(space, 0.0, space, 0.0));

        // create the entries
        for (Map.Entry<String, Class<?>> entry : entries.entrySet()) {
            String entryName = entry.getKey();
            Class<?> associatedType = entry.getValue();
            SketchComponentSlot entrySlot = new SketchComponentSlot(SketchComponentSlotType.ENTRY, entryName, associatedType);
            entrySlot.setMinHeight(space);
            entrySlot.setMinWidth(ENTRY_WIDTH);
            entryPane.getChildren().add(entrySlot);
        }

        this.setLeft(entryPane);

        // output pane //
        Class<?> returnType = this.configuration.getReturnType();
        if (returnType != null) {
            VBox outputPane = new VBox();
            outputPane.setAlignment(Pos.CENTER);
            SketchComponentSlot outputSlot = new SketchComponentSlot(SketchComponentSlotType.OUTPUT, returnType);
            outputSlot.setMinWidth(ENTRY_WIDTH);
            outputSlot.setMinHeight(ENTRY_HEIGHT);
            outputPane.getChildren().add(outputSlot);
            this.setRight(outputPane);
        }

        this.setStyle("-fx-background-color: yellow;");
    }
}

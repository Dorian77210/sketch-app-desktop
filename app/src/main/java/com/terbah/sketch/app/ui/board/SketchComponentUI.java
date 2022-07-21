package com.terbah.sketch.app.ui.board;

import com.terbah.sketch.app.core.config.SketchComponentConfiguration;
import com.terbah.sketch.app.ui.controller.SketchBoardControllerMediator;
import com.terbah.sketch.app.ui.util.icon.IconBuilder;
import com.terbah.sketch.app.ui.util.icon.SketchIcon;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;

import org.controlsfx.glyphfont.FontAwesome;
import org.controlsfx.glyphfont.Glyph;
import org.controlsfx.glyphfont.GlyphFont;
import org.controlsfx.glyphfont.GlyphFontRegistry;

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
    static final double SKETCH_COMPONENT_UI_HEIGHT = 90.0;

    /**
     * The preferred width for the ui.
     */
    static final double SKETCH_COMPONENT_UI_WIDTH = 120.0;

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

        // build the description UI.
        VBox descriptionUI = new VBox(10);
        descriptionUI.setAlignment(Pos.CENTER);
        Label nameLabel = new Label(configuration.getComponentName());
        nameLabel.setWrapText(true);
        nameLabel.setTextAlignment(TextAlignment.CENTER);
        Button playButton = new Button();
        playButton.setGraphic(IconBuilder.builder().getIcon(SketchIcon.PLAY, Color.GREEN));
        descriptionUI.getChildren().addAll(nameLabel, playButton);
        this.setCenter(descriptionUI);


        this.setStyle("-fx-background-color: yellow;");
    }
}

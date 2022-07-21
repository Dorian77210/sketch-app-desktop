package com.terbah.sketch.app.ui;

import com.terbah.sketch.app.core.board.SketchBoardManager;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * @author Dorian Terbah
 *
 * @version 1.0
 *
 * Represent tabs manager associated to Sketch.
 */
@Service
public class SketchTabView extends TabPane {

    @Autowired
    private SketchBoardManager boardManager;

    public SketchTabView() {
        super();
    }

    @PostConstruct
    public void init() {
        Tab sketchBoardTab = new Tab("Sketch", this.boardManager.createSketchBoard());
        this.getTabs().add(sketchBoardTab);
    }
}

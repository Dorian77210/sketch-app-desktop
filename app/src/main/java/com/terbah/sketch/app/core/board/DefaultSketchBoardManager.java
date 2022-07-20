package com.terbah.sketch.app.core.board;

import com.terbah.sketch.api.SketchComponent;
import com.terbah.sketch.app.ui.board.SketchBoard;
import org.springframework.stereotype.Service;

/**
 * @author Dorian TERBAH
 *
 * Default implementation of the SketchBoardManager.
 *
 * @version 1.0
 * @see SketchBoardManager
 */
@Service
public class DefaultSketchBoardManager implements SketchBoardManager {

    /**
     * Component class selected in the tree view.
     */
    private Class<? extends SketchComponent<?>> componentClassSelected;

    /**
     * Current sketch board active in the app.
     */
    private SketchBoard currentSketchBoard;

    public DefaultSketchBoardManager() {

    }

    @Override
    public SketchBoard createSketchBoard() {
        SketchBoard sketchBoard = new SketchBoard();
        this.currentSketchBoard = sketchBoard;
        return sketchBoard;
    }

    @Override
    public void setComponentClassSelected(Class<? extends SketchComponent<?>> componentClass) {
        this.componentClassSelected = componentClass;
        System.out.println(componentClass);
    }
}

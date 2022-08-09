package com.terbah.sketch.app.core.board;

import com.terbah.sketch.api.SketchComponent;
import com.terbah.sketch.app.core.workflow.SketchComponentWorkflow;
import com.terbah.sketch.app.core.workflow.SketchComponentWorkflowFactory;
import com.terbah.sketch.app.ui.SketchMainScene;
import com.terbah.sketch.app.ui.board.SketchBoard;
import com.terbah.sketch.app.ui.board.SketchComponentUI;
import com.terbah.sketch.app.ui.controller.SketchBoardControllerMediator;
import com.terbah.sketch.app.ui.controller.SketchKeyboardController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

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
     * Factory for the creation of components.
     */
    @Autowired
    private SketchComponentFactory componentFactory;

    /**
     * Factory used for the creation of workflow.
     */
    @Autowired
    private SketchComponentWorkflowFactory workflowFactory;

    /**
     * UI factory for the creation of component UI.
     */
    @Autowired
    private SketchComponentUIFactory uiFactory;

    /**
     * Scene of the app.
     */
    @Autowired
    private SketchMainScene mainScene;

    /**
     * Component class selected in the tree view.
     */
    private Class<? extends SketchComponent<?>> componentClassSelected;

    /**
     * Map of mediators
     */
    private final Map<SketchBoard, SketchBoardControllerMediator> mediators;

    /**
     * Current sketch board active in the app.
     */
    private SketchBoard currentSketchBoard;

    public DefaultSketchBoardManager() {
        this.mediators = new HashMap<>();
    }

    @PostConstruct
    public void init() {
        this.mainScene.setOnKeyPressed(new SketchKeyboardController(this));
    }

    @Override
    public SketchBoard createSketchBoard() {
        SketchBoard sketchBoard = new SketchBoard();
        SketchComponentWorkflow workflow = this.workflowFactory.createWorkflow();
        this.currentSketchBoard = sketchBoard;
        SketchBoardControllerMediator mediator = new SketchBoardControllerMediator(sketchBoard, workflow, this);
        this.mediators.put(sketchBoard, mediator);
        return sketchBoard;
    }

    @Override
    public void setComponentClassSelected(Class<? extends SketchComponent<?>> componentClass) {
        this.componentClassSelected = componentClass;
    }

    @Override
    public SketchComponent<?> createComponent() {
        SketchComponent<?> component = null;
        if (this.componentClassSelected != null) {
            component = this.componentFactory.createComponent(this.componentClassSelected);
            this.componentClassSelected = null;
        }

        return component;
    }

    @Override
    public SketchComponentUI createUIFor(SketchComponent<?> component) {
        return this.uiFactory.createUIFor(component);
    }

    @Override
    public SketchBoardControllerMediator getCurrentMediator() {
        return this.mediators.get(this.currentSketchBoard);
    }
}

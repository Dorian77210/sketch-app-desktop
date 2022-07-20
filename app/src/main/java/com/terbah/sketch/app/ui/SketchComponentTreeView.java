package com.terbah.sketch.app.ui;

import com.terbah.sketch.api.SketchComponent;
import com.terbah.sketch.app.core.board.SketchBoardManager;
import com.terbah.sketch.app.core.config.SketchComponentConfiguration;
import com.terbah.sketch.app.core.config.SketchComponentConfigurationManager;
import com.terbah.sketch.app.ui.model.SketchComponentTreeModel;
import static com.terbah.sketch.app.ui.model.SketchComponentTreeModel.SketchComponentTreeItemModel;

import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.BorderPane;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.Set;


/**
 * @author Dorian TERBAH
 *
 * This ui component will show a tree of available components
 *
 * @version 1.0
 */
@Service
public class SketchComponentTreeView extends BorderPane {

    private final TreeView<SketchComponentTreeItemModel> treeView;

    /**
     * The tree model associated.
     */
    @Autowired
    private SketchComponentTreeModel treeModel;

    @Autowired
    private SketchComponentConfigurationManager configurationManager;

    @Autowired
    private SketchBoardManager boardManager;

    /**
     * Constant used for the filter method.
     */
    private static final String NO_FILTER = "";

    public SketchComponentTreeView() {
        super(null);
        this.treeView = new TreeView<>();

        this.treeView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null)
            {
                SketchComponentTreeItemModel itemModel = newValue.getValue();
                Class<? extends SketchComponent<?>> componentClass = itemModel.getComponentClass();
                if (componentClass != null)
                {
                    this.boardManager.setComponentClassSelected(componentClass);
                }
            }
        });

        this.setCenter(this.treeView);

        TextField filterField = new TextField();
        filterField.setPromptText("Search for components ...");
        filterField.setOnKeyTyped(event -> {
            this.filterBy(filterField.getText());
        });

        this.setTop(filterField);
    }

    @PostConstruct
    public void init() {
        this.filterBy(NO_FILTER);
    }

    public void filterBy(final String filter)
    {
        // Create the associated model
        for (Map.Entry<Class<? extends SketchComponent<?>>, SketchComponentConfiguration> entry : this.configurationManager.getComponentsClass().entrySet()) {
            SketchComponentConfiguration configuration = entry.getValue();
            String namespace = configuration.getNamespace().toLowerCase();

            if (namespace.contains(filter.toLowerCase()))
            {
                this.treeModel.insertComponent(entry.getKey());
            }
        }

        SketchComponentTreeItemModel root = this.treeModel.getRoot();

        TreeItem<SketchComponentTreeItemModel> rootItem = new TreeItem<>(root);
        rootItem.setExpanded(true);
        this.treeView.setRoot(rootItem);

        for (SketchComponentTreeItemModel itemModel : root.getChildren())
        {
            TreeItem<SketchComponentTreeItemModel> item = this.proceedItemModel(itemModel);
            rootItem.getChildren().add(item);
        }
    }

    private TreeItem<SketchComponentTreeItemModel> proceedItemModel(SketchComponentTreeItemModel itemModel)
    {
        TreeItem<SketchComponentTreeItemModel> treeItem = new TreeItem<>(itemModel);

        Set<SketchComponentTreeItemModel> children = itemModel.getChildren();
        if (!children.isEmpty())
        {
            for (SketchComponentTreeItemModel childrenItemModel : children) {
                TreeItem<SketchComponentTreeItemModel> item = this.proceedItemModel(childrenItemModel);
                treeItem.getChildren().add(item);
            }
        }

        return treeItem;
    }
}

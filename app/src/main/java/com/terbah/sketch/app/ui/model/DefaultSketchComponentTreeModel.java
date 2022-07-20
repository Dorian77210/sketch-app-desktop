package com.terbah.sketch.app.ui.model;


import com.terbah.sketch.api.SketchComponent;
import com.terbah.sketch.app.core.config.SketchComponentConfiguration;
import com.terbah.sketch.app.core.config.SketchComponentConfigurationManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Dorian TERBAH
 *
 * Default implementation of the SketchComponentTreeModel.
 *
 * @version 1.0
 * @see SketchComponentTreeModel
 */
@Service
public class DefaultSketchComponentTreeModel implements SketchComponentTreeModel {

    /**
     * The root node of the tree
     */
    private final SketchComponentTreeItemModel root;

    /**
     * The component configuration manager.
     */
    @Autowired
    private SketchComponentConfigurationManager configurationManager;

    public DefaultSketchComponentTreeModel() {
        this.root = new SketchComponentTreeItemModel("List of components");
    }

    @Override
    public void insertComponent(Class<? extends SketchComponent<?>> componentClass) {
        SketchComponentConfiguration configuration = this.configurationManager.getConfigurationByComponentClass(componentClass);
        String namespace = configuration.getNamespace();
        List<String> namespaceElements = Arrays.stream(namespace.split("/"))
                .map(String::trim)
                .collect(Collectors.toList());
        namespaceElements.add(configuration.getComponentName());

        SketchComponentTreeItemModel currentNode = this.root;
        SketchComponentTreeItemModel foundNode;
        Set<SketchComponentTreeItemModel> currentNodes = currentNode.getChildren();

        for (String namespaceElement : namespaceElements) {
            foundNode = currentNodes.stream()
                    .filter(node -> node.getNamespaceElement().equals(namespaceElement))
                    .reduce((first, second) -> first)
                    .orElse(null);

            if (foundNode == null) {
                currentNode = currentNode.insertChildren(namespaceElement);
            }
            else
            {
                currentNode = foundNode;
            }

            currentNodes = currentNode.getChildren();
        }

        currentNode.setComponentClass(componentClass);
    }

    @Override
    public void clear() {
        this.root.children.clear();
    }

    @Override
    public boolean containsComponent(Class<? extends SketchComponent<?>> componentClass) {
        SketchComponentConfiguration configuration = this.configurationManager.getConfigurationByComponentClass(componentClass);
        String namespace = configuration.getNamespace();
        List<String> namespaceElements = Arrays.stream(namespace.split("/"))
                .map(String::trim)
                .collect(Collectors.toList());
        namespaceElements.add(configuration.getComponentName());

        SketchComponentTreeItemModel currentNode = this.root;
        SketchComponentTreeItemModel foundNode;
        Set<SketchComponentTreeItemModel> currentNodes = currentNode.getChildren();

        boolean found = true;

        for (String namespaceElement : namespaceElements) {
            foundNode = currentNodes.stream()
                    .filter(node -> node.getNamespaceElement().equals(namespaceElement))
                    .reduce((first, second) -> first)
                    .orElse(null);

            if (foundNode == null) {
                found = false;
                break;
            }
            else
            {
                currentNode = foundNode;
                currentNodes = currentNode.getChildren();
            }
        }

        return found;
    }

    /**
     * @author Dorian Terbah
     *
     * @version 1.0
     *
     * Class that represents the node inside the tree of component
     */
    public static class SketchComponentTreeItemModel
    {
        /**
         * The children in the tree of the current component.
         */
        private final Set<SketchComponentTreeItemModel> children;

        /**
         * The current namespace element.
         */
        private final String namespaceElement;

        /**
         * The class associated to the node. This can be null.
         */
        private Class<? extends SketchComponent<?>> componentClass;

        /**
         * Constructor of the class <code>SketchComponentTreeItemModel</code>
         *
         * @param packageElement The package element associated to the node.
         */
        SketchComponentTreeItemModel(String packageElement)
        {
            this(packageElement, null);
        }

        /**
         * Constructor of the class <code>SketchComponentTreeItemModel</code>
         * @param namespaceElement The package element associated to the node.
         * @param componentClass The component class associated to the node.
         */
        SketchComponentTreeItemModel(String namespaceElement, Class<? extends SketchComponent<?>> componentClass)
        {
            this.children = new HashSet<>();
            this.namespaceElement = namespaceElement;
            this.componentClass = componentClass;
        }

        /**
         *
         * @return The children of the current node.
         */
        public Set<SketchComponentTreeItemModel> getChildren()
        {
            return this.children;
        }

        /**
         *
         * @return The component class.
         */
        public Class<? extends SketchComponent<?>> getComponentClass()
        {
            return this.componentClass;
        }

        /**
         *
         * @return The namespace element.
         */
        public String getNamespaceElement()
        {
            return this.namespaceElement;
        }

        /**
         * Add a new children.
         *
         * @param packageElement The package element for the new node.
         */
        public SketchComponentTreeItemModel insertChildren(final String packageElement)
        {
            SketchComponentTreeItemModel itemModel = new SketchComponentTreeItemModel(packageElement);
            this.children.add(itemModel);
            return itemModel;
        }

        /**
         * Set the component class associated to this node.
         * @param componentClass The new component class
         */
        public void setComponentClass(final Class<? extends SketchComponent<?>> componentClass)
        {
            this.componentClass = componentClass;
        }

        @Override
        public String toString()
        {
            return this.namespaceElement;
        }

        @Override
        public boolean equals(Object object)
        {
            if (object == null) return false;
            if (! (object instanceof SketchComponentTreeItemModel)) return false;
            SketchComponentTreeItemModel itemModel = (SketchComponentTreeItemModel) object;
            if (!this.namespaceElement.equals(itemModel.namespaceElement)) return false;
            return this.componentClass == itemModel.componentClass;
        }

        @Override
        public int hashCode()
        {
            return this.namespaceElement.hashCode();
        }
    }
}

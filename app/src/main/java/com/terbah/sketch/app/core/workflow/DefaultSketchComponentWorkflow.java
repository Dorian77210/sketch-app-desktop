package com.terbah.sketch.app.core.workflow;

import com.terbah.sketch.api.SketchComponent;
import com.terbah.sketch.api.exception.SketchComponentExecuteException;
import com.terbah.sketch.app.core.config.SketchComponentConfiguration;
import com.terbah.sketch.app.core.config.SketchComponentConfigurationManager;
import com.terbah.sketch.app.core.injector.SketchDataInjector;
import com.terbah.sketch.app.core.logger.SketchLoggerManager;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

class DefaultSketchComponentWorkflow implements SketchComponentWorkflow {

    /**
     * Edges of the workflow. Each component will be mapped with their
     * entries names and the parent component associated.
     */
    private final Map<SketchComponent<?>, Map<String, SketchComponent<?>>> edges;

    /**
     * Map a component with his children
     */
    private final Map<SketchComponent<?>, Map<String, SketchComponent<?>>> children;

    /**
     * Components that doesn't have any links with other components.
     */
    private final List<SketchComponent<?>> orphanComponents;

    /**
     * Logger used in the workflow.
     */
    private final Logger logger;

    /**
     * Data injector to inject data to the components.
     */
    private final SketchDataInjector dataInjector;

    /**
     * The configuration manager of the application.
     */
    private final SketchComponentConfigurationManager configurationManager;

    public DefaultSketchComponentWorkflow(SketchComponentConfigurationManager configurationManager, SketchDataInjector injector) {
        this.edges = new HashMap<>();
        this.children = new HashMap<>();
        this.logger = SketchLoggerManager.getLogger(this.getClass());

        this.orphanComponents = new LinkedList<>();

        this.configurationManager = configurationManager;
        this.dataInjector = injector;
    }

    @Override
    public void insertComponent(SketchComponent<?> component) {
        this.orphanComponents.add(component);
    }

    /**
     * Build a deque with the parents and the parents of the parents etc of the component
     * @param component The component which one the algo has to find the parents.
     * @return The deque with all the parents
     */
    private Deque<SketchComponent<?>> buildComponentQueue(SketchComponent<?> component) {
        Deque<SketchComponent<?>> componentStack = new LinkedList<>();
        List<SketchComponent<?>> currentComponents = new LinkedList<>();
        currentComponents.add(component);
        componentStack.push(component);

        // push all the parents of the component, then the parents of the parents, etc
        while (!currentComponents.isEmpty()) {
            List<SketchComponent<?>> tmpComponents = new LinkedList<>();

            for (SketchComponent<?> currentComponent : currentComponents) {
                if (this.hasParents(currentComponent)) {
                    List<SketchComponent<?>> parents = new LinkedList<>(this.edges.get(currentComponent).values());
                    for (SketchComponent<?> parent : parents) {
                        componentStack.push(parent);
                        tmpComponents.add(parent);
                    }
                }
            }

            currentComponents.clear();
            currentComponents.addAll(tmpComponents);
        }

        return componentStack;
    }

    @Override
    public boolean execute(SketchComponent<?> component) {
        Map<SketchComponent<?>, Object> data = new HashMap<>();
        Deque<SketchComponent<?>> componentStack = this.buildComponentQueue(component);

        SketchComponent<?> currentComponent = null;
        Object result = null;

        // execute each components
        while (!componentStack.isEmpty()) {
            currentComponent = componentStack.pop();

            if (this.hasParents(currentComponent)) {
                Map<String, SketchComponent<?>> parents = this.edges.get(currentComponent);
                for (Map.Entry<String, SketchComponent<?>> entry : parents.entrySet()) {
                    String entryName = entry.getKey();
                    Object parentResult = data.get(parents.get(entryName));
                    if (!this.dataInjector.injectData(currentComponent, entryName, parentResult)) {
                        // throw error or put it in the console
                        return false;
                    }
                }
            }

            try {
                result = currentComponent.execute();
                this.logger.log(Level.FINE, "Result = {0}", result);
                data.put(currentComponent, result);
            } catch (SketchComponentExecuteException e) {
                this.logger.log(Level.SEVERE, String.format("Error during the execution of the component %s", currentComponent), e);
                return false;
            }
        }

        if (this.children.containsKey(currentComponent)) {
            // inject the result of the source component in his children
            for (var entry : this.children.get(currentComponent).entrySet()) {
                this.dataInjector.injectData(entry.getValue(), entry.getKey(), result);
            }
        }

        return true;
    }

    /**
     * @param component The component to check.
     * @return <code>true</code> if the component has parents, otherwise <code>false</code>.
     */
    private boolean hasParents(SketchComponent<?> component) {
        Map<String, SketchComponent<?>> parents = this.edges.get(component);
        return (parents != null) && (!parents.isEmpty());
    }

    @Override
    public boolean createLinkBetween(SketchComponent<?> parent, SketchComponent<?> child, String entryName) {
        if (!this.isAssociationPossible(parent, child, entryName)
        || this.existsLinkBetween(parent, child, entryName)
        || this.isParent(child, parent)) {
            return false;
        }

        Map<String, SketchComponent<?>> parents;
        Map<String, SketchComponent<?>> children;

        if (!this.edges.containsKey(child)) {
            this.edges.put(child, new HashMap<>());
        }

        parents = this.edges.get(child);
        parents.put(entryName, parent);

        // create the entry in the children map
        if (!this.children.containsKey(parent)) {
            this.children.put(parent, new HashMap<>());
        }

        children = this.children.get(parent);
        children.put(entryName, child);

        return true;
    }

    @Override
    public void deleteComponent(SketchComponent<?> component) {
        this.orphanComponents.remove(component);
        this.edges.remove(component);
        // remove in the children ?
        this.children.remove(component);

        // remove links where the component is a parent

        this.edges.entrySet().forEach(entry -> {
            List<String> entriesToDelete = new LinkedList<>();
            Map<String, SketchComponent<?>> parents = entry.getValue();
            for (var parentEntry : parents.entrySet()) {
                SketchComponent<?> parent = parentEntry.getValue();
                if (parent.equals(component)) {
                    entriesToDelete.add(parentEntry.getKey());
                }
            }

            for (String entryToDelete : entriesToDelete) {
                parents.remove(entryToDelete);
            }
        });
    }

    /**
     * Check if a connection between two component can be made.
     *
     * @param parent The potential parent of the association.
     * @param child The potential child of the association.
     * @param entryName The entry name of the child.
     * @return <code>false</code> if the association can't be made, else <code>false</code>
     */
    private boolean isAssociationPossible(SketchComponent<?> parent, SketchComponent<?> child, String entryName) {
        SketchComponentConfiguration parentConfiguration = this.configurationManager.getConfigurationByComponentClass(parent.getClass());
        SketchComponentConfiguration childConfiguration = this.configurationManager.getConfigurationByComponentClass(child.getClass());

        Class<?> parentReturnType = parentConfiguration.getReturnType();
        Optional<SketchComponentConfiguration.SketchInjectableMethod> methodInjectable = childConfiguration.getMethodInjectable(entryName);

        return methodInjectable.isPresent() && methodInjectable.get().getParamType().isAssignableFrom(parentReturnType);
    }

    @Override
    public boolean existsLinkBetween(SketchComponent<?> parent, SketchComponent<?> child, String entryName) {
        if (!this.edges.containsKey(child)) {
            return false;
        }

        Map<String, SketchComponent<?>> parents = this.edges.get(child);
        SketchComponent<?> potentialParent = parents.get(entryName);
        return parent.equals(potentialParent);
    }

    /**
     * Check if a component is the parent of another
     *
     * @param parent The potential parent.
     * @param child The potential child.
     * @return <code>true</code> if the first component is the parent of the second, else <code>false</code>.
     */
    private boolean isParent(SketchComponent<?> parent, SketchComponent<?> child) {
        Map<String, SketchComponent<?>> links = this.edges.get(child);
        if (links == null) {
            return false;
        }
        List<SketchComponent<?>> components = new LinkedList<>(links.values());
        return components.contains(parent);
    }

    @Override
    public void removeLink(SketchComponent<?> child, String entryName) {
        Map<String, SketchComponent<?>> parents = this.edges.get(child);
        if (parents != null) {
            parents.remove(entryName);
        }
    }

    @Override
    public void clear() {
        this.edges.clear();
        this.orphanComponents.clear();
    }
}

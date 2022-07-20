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
     * Components that doesn't have any links with other components.
     */
    private final List<SketchComponent<?>> orphanComponents;

    /**
     * Logger used in the workflow.
     */
    private Logger logger;

    /**
     * Data injector to inject data to the components.
     */
    private SketchDataInjector dataInjector;

    /**
     * The configuration manager of the application.
     */
    private final SketchComponentConfigurationManager configurationManager;

    public DefaultSketchComponentWorkflow(SketchComponentConfigurationManager configurationManager, SketchDataInjector injector) {
        this.edges = new HashMap<>();
        this.logger = SketchLoggerManager.getLogger(this.getClass());

        this.orphanComponents = new ArrayList<>();

        this.configurationManager = configurationManager;
        this.dataInjector = injector;
    }

    @Override
    public void insertComponent(SketchComponent<?> component) {
        this.orphanComponents.add(component);
    }

    @Override
    public boolean execute(SketchComponent<?> component) {
        Map<SketchComponent<?>, Object> data = new HashMap<>();
        Stack<SketchComponent<?>> componentStack = new Stack<>();
        List<SketchComponent<?>> currentComponents = new ArrayList<>();
        currentComponents.add(component);
        componentStack.push(component);

        // push all the parents of the component, then the parents of the parents, etc
        while (!currentComponents.isEmpty()) {
            List<SketchComponent<?>> tmpComponents = new ArrayList<>();

            for (SketchComponent<?> currentComponent : currentComponents) {
                if (this.hasParents(currentComponent)) {
                    List<SketchComponent<?>> parents = new ArrayList<>(this.edges.get(currentComponent).values());
                    for (SketchComponent<?> parent : parents) {
                        componentStack.push(parent);
                        tmpComponents.add(parent);
                    }
                }
            }

            currentComponents.clear();
            currentComponents.addAll(tmpComponents);
        }

        // execute each components
        while (!componentStack.isEmpty()) {
            SketchComponent<?> currentComponent = componentStack.pop();

            if (this.hasParents(currentComponent)) {
                Map<String, SketchComponent<?>> parents = this.edges.get(currentComponent);
                for (String entryName : parents.keySet()) {
                    Object parentResult = data.get(parents.get(entryName));
                    if (!this.dataInjector.injectData(currentComponent, entryName, parentResult)) {
                        // todo: throw error or put it in the console
                        return false;
                    }
                }
            }

            try {
                Object result = currentComponent.execute();
                this.logger.log(Level.FINE, "Result = " + result);
                data.put(currentComponent, result);
            } catch (SketchComponentExecuteException e) {
                this.logger.log(Level.SEVERE, "Error during the execution of the component " + currentComponent, e);
                return false;
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
        if (!this.edges.containsKey(child)) {
            this.edges.put(child, new HashMap<>());
        }

        parents = this.edges.get(child);
        parents.put(entryName, parent);

        return true;
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
        List<SketchComponent<?>> components = new ArrayList<>(links.values());
        return components.contains(parent);
    }

    @Override
    public void removeLink(SketchComponent<?> child, String entryName) {
        Map<String, SketchComponent<?>> parents = this.edges.get(child);
        if (parents != null) {
            parents.remove(entryName);
        }
    }
}

package org.vaadin.addons.tatu;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.treegrid.TreeGrid;
import com.vaadin.flow.internal.nodefeature.ElementData;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Tree Grid Test")
@Route(value = "tree", layout = MainLayout.class)
public class TreeGridView extends VerticalLayout {

    private List<String> rootItems;

    public TreeGridView() {
        TreeGrid<String> treeGrid = new MyTree<String>();
        treeGrid.setClassName("my-tree-grid");
        rootItems = Arrays.asList("A", "B", "C");
        treeGrid.setItems(rootItems.stream(),
                p -> p.length() > 3 ? Stream.empty()
                        : rootItems.stream().map(p::concat));

        treeGrid.setEnabled(false);
        treeGrid.addHierarchyColumn(x -> x).setClassNameGenerator(
                item -> !treeGrid.isEnabled() ? "disabled" : null);
        treeGrid.expand(Arrays.asList("A"));

        add(treeGrid);

        add(new Button("enabled/disabled", e -> {
            treeGrid.setEnabled(!treeGrid.isEnabled());
        }));
    }

    public class MyTree<T> extends TreeGrid<T> {

        @Override
        public void onEnabledStateChanged(boolean enabled) {
            if (getElement().getNode().hasFeature(ElementData.class)) {
                getElement().setAttribute("disabled", !enabled);
            }

            if (getTreeData() != null) {
                /*
                 * DataCommunicator.reset() will cause collapse - expand cycle
                 * and thus flicker. In case of TreeData we can avoid this.
                 */
                refreshChildItems(getTreeData().getRootItems());
            } else {
                /*
                 * The DataCommunicator needs to be reset so components rendered
                 * inside the cells can be updated to the new enabled state. The
                 * enabled state is passed as a property to the client via
                 * DataGenerators.
                 */
                getDataCommunicator().reset();
            }
        }

        private void refreshChildItems(List<T> rootItems) {
            for (T item : rootItems) {
                getDataProvider().refreshItem(item, false);
                refreshChildItems(getTreeData().getChildren(item));
            }
        }
    }
}

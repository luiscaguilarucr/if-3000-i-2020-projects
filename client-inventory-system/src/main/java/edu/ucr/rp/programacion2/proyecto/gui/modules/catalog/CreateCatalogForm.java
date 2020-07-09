package edu.ucr.rp.programacion2.proyecto.gui.modules.catalog;

import edu.ucr.rp.programacion2.proyecto.domain.Catalog;
import edu.ucr.rp.programacion2.proyecto.domain.Inventory;
import edu.ucr.rp.programacion2.proyecto.gui.manage.ManagePane;
import edu.ucr.rp.programacion2.proyecto.gui.manage.model.PaneViewer;
import edu.ucr.rp.programacion2.proyecto.gui.modules.util.PaneUtil;
import edu.ucr.rp.programacion2.proyecto.logic.*;
import edu.ucr.rp.programacion2.proyecto.util.InventoryConverter;
import edu.ucr.rp.programacion2.proyecto.util.builders.BuilderFX;
import edu.ucr.rp.programacion2.proyecto.util.builders.CatalogBuilder;
import edu.ucr.rp.programacion2.proyecto.util.refresh.RefreshService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;
import java.util.List;

import static edu.ucr.rp.programacion2.proyecto.gui.modules.util.LabelConstants.TITLE_CATALOG_FORM;

/**
 * This class shows the actions to add new catalog.
 *
 * @author Luis Carlos Aguilar Morales | B90514
 * @version 2.0
 */
public class CreateCatalogForm implements PaneViewer {
    private static InventoryService inventoryService;
    private static CatalogService catalogService;
    private static RefreshService refreshService;
    private static final CatalogBuilder catalogBuilder = new CatalogBuilder();
    private static TextField catalogNameTextField;
    private static TextField featureNameTextField;
    private static Label inventoryIndicationLabel;
    private static Label catalogNameLabel;
    private static Label featureNameLabel;
    private static Button addFeatureButton;
    private static Button saveCatalogButton;
    private static Button cancelButton;
    private static ComboBox<Inventory> inventoryComboBox;
    private static ListView<String> featureListView;
    private static ObservableList<Inventory> inventoryObservableList;
    private static ObservableList<String> featureObservableList;
    private static final List<String> schema = new ArrayList<>();
    private static Boolean emptySpace = false;
    private static Boolean wasAdded = false;
    private static GridPane pane;
    private static List<String> featureList = new ArrayList<>();

    /**
     * Return the pane with all the components and styles added.
     *
     * @return {@code GridPane} pane with components.
     */
    public GridPane getCatalogFormPane() {
        pane = PaneUtil.buildPanePlus();
        initializeInventoryService();
        addControls();
        addHandlers();
        setStyle();
        return pane;
    }

    /**
     * This method initializes the inventory service.
     */
    private static void initializeInventoryService() {
        inventoryService = InventorySocketService.getInstance();
        refreshService = RefreshService.getInstance();
    }

    /**
     * This method initializes the catalog service.
     */
    private void updateCatalogService(Inventory inventory) {
        catalogService = CatalogSocketService.getInstance();
        catalogService.setInventory(inventory);
    }

    /**
     * This methods restarts the GridPane to make it reusable and validates whether it is possible to remove a catalog.
     */
    public static void refresh() {
        initializeInventoryService();
        try {
            if (inventoryService.getAll().size() == 0) {
                ManagePane.clearPane();
                PaneUtil.showAlert(Alert.AlertType.INFORMATION, "There are no inventories", "You must add at least one inventory to be able to access this function");
            }
            refreshItems();
        } catch (ServiceException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void refreshItems() throws ServiceException {
        inventoryComboBox.setDisable(false);
        addFeatureButton.setVisible(false);
        catalogNameTextField.setDisable(false);
        featureNameTextField.setDisable(false);
        featureListView.setVisible(false);
        featureObservableList.clear();
        featureList.clear();
        catalogNameTextField.clear();
        featureNameTextField.clear();
        schema.clear();
        /*Inventory inventory = inventoryComboBox.getSelectionModel().getSelectedItem();
        inventoryComboBox.getSelectionModel().clearSelection();
        List<Inventory> list = inventoryService.getAll();
        for (int i = 0; i < inventoryObservableList.size(); i++) {

        }*/
        inventoryObservableList.clear();
        inventoryObservableList.addAll(inventoryService.getAll());
        //inventoryComboBox.getSelectionModel().select(inventory);
        //refreshService.refreshObservableList(inventoryComboBox, inventoryObservableList);
    }

    /**
     * Configure and add the required components in the pane.
     */
    private void addControls() {
        featureObservableList = FXCollections.observableArrayList();
        featureListView = PaneUtil.buildListView(pane, featureObservableList, 4, 1, 1, 5);
        featureListView.setVisible(false);
        BuilderFX.buildLabelTitleNormal(TITLE_CATALOG_FORM, pane, 0, 0);
        inventoryIndicationLabel = PaneUtil.buildLabel(pane, "Chose an inventory", 0, 1);
        buildInventoryComboBox();
        catalogNameLabel = PaneUtil.buildLabel(pane, "Catalog name: ", 0, 2);
        catalogNameTextField = PaneUtil.buildTextInput(pane, 2);
        featureNameLabel = PaneUtil.buildLabel(pane, "Feature:", 0, 3);
        featureNameTextField = PaneUtil.buildTextInput(pane, 3);
        addFeatureButton = PaneUtil.buildButtonImage(new Image("add.png"), pane, 2, 3, false);
        saveCatalogButton = PaneUtil.buildButton("Save catalog", pane, 1, 4, true);
        saveCatalogButton.setVisible(false);
        addFeatureButton.setVisible(false);
        cancelButton = PaneUtil.buildButton("Cancel", pane, 2, 1, false);
    }

    /**
     * Add functionality to buttons or events.
     */
    private void addHandlers() {
        cancelButton.setOnAction(e -> {
            ManagePane.clearPane();
        });

        inventoryComboBox.setOnAction(e -> {
            if (inventoryComboBox.getValue() != null) {
                updateCatalogService(inventoryComboBox.getValue());
            }
        });

        saveCatalogButton.setOnAction(e -> {
            saveCatalogButton.setVisible(false);
            ManagePane.clearPane();
            generateCatalog();
        });

        featureNameTextField.setOnMouseClicked(e -> {
            addFeatureButton.setVisible(true);
        });

        addFeatureButton.setOnAction(e -> {
            saveCatalogButton.setVisible(true);
            inventoryComboBox.setDisable(true);
            catalogNameTextField.setDisable(true);
            addFeature();
        });
    }

    /**
     * This method builds a ComboBox that displays the inventory list.
     */
    private void buildInventoryComboBox() {
        inventoryObservableList = FXCollections.observableArrayList();
        inventoryComboBox = PaneUtil.buildComboBox(pane, inventoryObservableList, 1, 1);
    }

    /**
     * This method allows to add 1 or many characteristics to a given catalog.
     */
    private void addFeature() {
        if (catalogNameTextField.getText().isEmpty()) {
            emptySpace = true;
        }
        if (featureNameTextField.getText().isEmpty()) {
            emptySpace = true;
        }
        if (emptySpace) {
            featureNameTextField.setPromptText("Obligatory field");
            featureNameTextField.setStyle("-fx-background-color: #FDC7C7");
        } else {
            featureObservableList.clear();
            featureListView.setVisible(true);
            featureList.add(featureNameTextField.getText());
            featureObservableList.addAll(featureList);
            schema.add(featureNameTextField.getText());
            featureNameTextField.clear();
            featureNameTextField.setStyle("-fx-background-color: #FFFFFF");
            catalogNameTextField.setDisable(true);
            featureNameTextField.clear();
            saveCatalogButton.setVisible(true);
        }
    }

    /**
     * This method generates a new catalog based on the information entered.
     */
    private void generateCatalog() {
        if (catalogNameTextField.getText().isEmpty()) {
            emptySpace = true;
        }
        if (emptySpace) {
            catalogNameTextField.setPromptText("Obligatory field");
            catalogNameTextField.setStyle("-fx-background-color: #FDC7C7");
        } else if (schema.size() > 0) {
            try {
                featureNameTextField.setPromptText("");
                featureNameTextField.setStyle("-fx-background-color: #FFFFFF");
                updateCatalogService(inventoryComboBox.getValue());
                catalogBuilder.withName(catalogNameTextField.getText());
                catalogBuilder.withSchema(schema);
                Catalog catalog = catalogBuilder.build();
                wasAdded = catalogService.add(catalog);
                if (wasAdded) {
                    wasAdded = false;
                    PaneUtil.showAlert(Alert.AlertType.INFORMATION, "Catalog added", "The catalog was added correctly");
                } else {
                    PaneUtil.showAlert(Alert.AlertType.INFORMATION, "ERROR when adding", "The catalog was not added");
                }
            } catch (ServiceException e) {
                System.out.println(e.getMessage());
            }
        }

    }

    private void setStyle() {
        inventoryComboBox.setConverter(new InventoryConverter());
    }

    @Override
    public GridPane getPane() {
        return getCatalogFormPane();
    }
}

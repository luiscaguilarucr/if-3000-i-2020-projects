package edu.ucr.rp.programacion2.proyecto.gui.javafx.catalog;

import edu.ucr.rp.programacion2.proyecto.domain.Inventory;
import edu.ucr.rp.programacion2.proyecto.gui.javafx.util.PaneUtil;
import edu.ucr.rp.programacion2.proyecto.gui.model.PaneViewer;
import edu.ucr.rp.programacion2.proyecto.logic.CatalogService;
import edu.ucr.rp.programacion2.proyecto.logic.InventoryService;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import org.controlsfx.control.CheckComboBox;

import java.util.ArrayList;

public class DeleteCatalog implements PaneViewer {
    private InventoryService inventoryService;
    private CatalogService catalogService;
    private TextField inventoryNameTextField;
    private Button confirmInventoryButton;
    private Button deleteCatalogButton;
    private Label inventoryIndicationLabel;
    private Label catalogIndicationLabel;
    private ComboBox<String> inventoryComboBox;
    private CheckComboBox checkComboBox;
    private ArrayList<Inventory> inventories;

    public GridPane getDeleteCatalogPane() {
        GridPane pane = PaneUtil.buildPane();
        initializeInventoryService();
        setupInventoryControls(pane);
        addInventoryHandlers(pane);
        return pane;
    }

    private void initializeInventoryService(){
        inventoryService = InventoryService.getInstance();
    }

    private void initializeCatalogService(Inventory inventory){
        catalogService = new CatalogService(inventory);
    }

    private void setupInventoryControls(GridPane pane) {
        if (PaneUtil.setupInventoryControls(inventoryService.getAll())) {
            buildInventoryCheckComboBox(pane);
            confirmInventoryButton = PaneUtil.buildButtonImage(new Image("select.png"), pane, 2, 0);
            setupCatalogControls(pane);
        }
    }

    private void setupCatalogControls(GridPane pane) {
        if (PaneUtil.setupInventoryControls(inventoryService.getAll())) {
            buildInventoryCheckComboBox(pane);
            deleteCatalogButton = PaneUtil.buildButtonImage(new Image("delete.png"), pane, 2, 0);
        }
    }

    private void addInventoryHandlers(GridPane pane) {
        confirmInventoryButton.setOnAction((event -> {
            if(PaneUtil.addInventoryHandlers(inventoryComboBox, inventoryIndicationLabel, confirmInventoryButton)){
                initializeCatalogService(inventoryService.get(inventoryComboBox.getValue()));
                setupCatalogControls(pane);
                addCatalogHandlers(pane);
            }
        }));
    }

    private void addCatalogHandlers(GridPane pane) {
        deleteCatalogButton.setOnAction(actionEvent -> deleteCatalog());
        setupCatalogControls(pane);
    }

    private void buildInventoryCheckComboBox(GridPane pane){
        checkComboBox = PaneUtil.buildCheckComboBox(pane, inventoryService.getNamesList(), 1, 0);
        catalogIndicationLabel = PaneUtil.buildLabel(pane, "Select the catalog you want to remove",0, 0);
    }

    private void deleteCatalog(){
        int i = 0;
        Boolean removed = false;
        ObservableList<String> list = checkComboBox.getCheckModel().getCheckedItems();
        for(String s: list){
            catalogService.remove(catalogService.get(s));
            i++;
        }
        if(i == list.size()){
            removed = true;
        }
        if (removed) {
            PaneUtil.showAlert(Alert.AlertType.INFORMATION, "Catalog removed", "The catalog was removed correctly");
        }else {
            PaneUtil.showAlert(Alert.AlertType.INFORMATION, "ERROR when removing", "The catalog was not removed");
        }
    }

    @Override
    public Pane getPane() {
        return getDeleteCatalogPane();
    }
}

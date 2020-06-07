package edu.ucr.rp.programacion2.proyecto.gui.javafx;

import edu.ucr.rp.programacion2.proyecto.gui.javafx.catalog.CatalogForm;
import edu.ucr.rp.programacion2.proyecto.gui.javafx.catalog.DeleteCatalog;
import edu.ucr.rp.programacion2.proyecto.gui.javafx.inventory.DeleteInventory;
import edu.ucr.rp.programacion2.proyecto.gui.javafx.item.ItemForm;
import edu.ucr.rp.programacion2.proyecto.gui.model.PaneName;
import edu.ucr.rp.programacion2.proyecto.gui.model.PaneViewer;
import edu.ucr.rp.programacion2.proyecto.gui.panes.main.ManagePane;
import edu.ucr.rp.programacion2.proyecto.gui.panes.main.records.InventoryView;
import javafx.application.Platform;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ViewMenuBar implements PaneViewer {
    private Stage stage;
    private DeleteInventory deleteInventory = new DeleteInventory();
    private CatalogForm catalogForm = new CatalogForm();
    private DeleteCatalog deleteCatalog = new DeleteCatalog();
    private ItemForm itemForm = new ItemForm();

    public ViewMenuBar(Stage stage) {
        this.stage = stage;
    }

    public VBox getMenuVBox() {
        VBox vB_MenuBar = new VBox();

        MenuBar mB_View_MenuBar = new MenuBar();

        ////////////////////////////////////////////////////////////////////////// Menu "Inventory"
        Menu m_Inventory = new Menu("Inventory");

        ////////////////////////////////////////// MenuItems for m_Inventory
        //MenuItem mI_SeeInventory
        ImageView iV_mI_SeeInventory = new ImageView(new Image("seeIcon.png"));
        MenuItem mI_SeeInventory = new MenuItem("Search", iV_mI_SeeInventory);

        //MenuItem mI_CreateInventory
        ImageView iV_mI_CreateInventory = new ImageView(new Image("addIcon.png"));
        MenuItem mI_CreateInventory = new MenuItem("Create inventory", iV_mI_CreateInventory);


        //MenuItem mI_DeleteInventory
        ImageView iV_mI_DeleteInventory = new ImageView(new Image("delete.png"));
        MenuItem mI_DeleteInventory = new MenuItem("Delete inventory", iV_mI_DeleteInventory);

        mI_CreateInventory.setOnAction((event) -> {
            ManagePane.setCenterPane(ManagePane.getPanes().get(PaneName.ADD_INVENTORY));
        });

        mI_SeeInventory.setOnAction((event)->{
            ManagePane.setCenterPane(ManagePane.getPanes().get(PaneName.SHOW_INVENTORY));
            InventoryView.refresh();
        });

        mI_DeleteInventory.setOnAction((event) -> {
            ManagePane.setCenterPane(ManagePane.getPanes().get(PaneName.DELETE_INVENTORY));
            deleteInventory.validateShow();
        });

        m_Inventory.getItems().addAll(mI_SeeInventory, mI_CreateInventory, mI_DeleteInventory);


        ////////////////////////////////////////////////////////////////////////// Menu "Catalog"
        Menu m_Catalog = new Menu("Catalog");

        ////////////////////////////////////////// MenuItems for m_Catalog
        //MenuItem iV_mI_AddCatalog
        ImageView iV_mI_AddCatalog = new ImageView(new Image("addIcon.png"));
        MenuItem mI_AddCatalog = new MenuItem("Add catalog", iV_mI_AddCatalog);

        //MenuItem mI_SeeItems
        ImageView iV_mI_ViewAllCatalogs = new ImageView(new Image("seeIcon.png"));
        MenuItem mI_ViewAllCatalogs = new MenuItem("View catalogs", iV_mI_ViewAllCatalogs);

        //MenuItem mI_EditCatalog
        ImageView iV_mI_EditCatalog = new ImageView(new Image("editIcon.png"));
        MenuItem mI_EditCatalog = new MenuItem("Edit catalog", iV_mI_EditCatalog);

        //MenuItem mI_DeleteCatalog
        ImageView iV_mI_DeleteCatalog = new ImageView(new Image("delete.png"));
        MenuItem mI_DeleteCatalog = new MenuItem("Delete catalog", iV_mI_DeleteCatalog);

        // Give action to MenuItems
        mI_AddCatalog.setOnAction((event) -> {
            ManagePane.setCenterPane(ManagePane.getPanes().get(PaneName.ADD_CATALOG));
            catalogForm.validateShow();
        });

        mI_ViewAllCatalogs.setOnAction((event) -> {
        });

        mI_EditCatalog.setOnAction((event) -> {
        });

        mI_DeleteCatalog.setOnAction((event) -> {
            ManagePane.setCenterPane(ManagePane.getPanes().get(PaneName.DELETE_CATALOG));
            deleteCatalog.validateShow();
        });

        m_Catalog.getItems().addAll(mI_AddCatalog, mI_ViewAllCatalogs, mI_EditCatalog, mI_DeleteCatalog);


        ////////////////////////////////////////////////////////////////////////// Menu "Item"
        Menu m_Item = new Menu("Item");

        ////////////////////////////////////////// MenuItems for m_Item
        //MenuItem mI_AddItem
        ImageView iV_mI_AddItem = new ImageView(new Image("addIcon.png"));
        MenuItem mI_AddItem = new MenuItem("Add item", iV_mI_AddItem);

        //MenuItem mI_EditItem
        ImageView iV_mI_EditItem = new ImageView(new Image("editIcon.png"));
        MenuItem mI_EditItem = new MenuItem("Edit item", iV_mI_EditItem);

        //Give action to MenuItems
        mI_AddItem.setOnAction((event) -> {
            ManagePane.setCenterPane(ManagePane.getPanes().get(PaneName.ADD_ITEM));
            itemForm.validateShow();
        });

        mI_EditItem.setOnAction((event) -> {
        });

        m_Item.setOnShowing((event) -> {
        });

        m_Item.getItems().addAll(mI_AddItem, mI_EditItem);


        ////////////////////////////////////////////////////////////////////////// Menu "Configuration"
        Menu m_Configuration = new Menu("File");

        ////////////////////////////////////////// MenuItems for m_Configuration
        //MenuItem iV_mI_About
        ImageView iV_mI_About = new ImageView(new Image("about.png"));
        MenuItem mI_About = new MenuItem("About", iV_mI_About);

        //MenuItem iV_mI_Credits
        ImageView iV_mI_Credits = new ImageView(new Image("createdBy.png"));
        MenuItem mI_Credits = new MenuItem("Credits", iV_mI_Credits);

        /// MenuItem iV_mI_Exit
        ImageView iV_mI_Exit = new ImageView(new Image("exit.png"));
        MenuItem mI_Exit = new MenuItem("Exit", iV_mI_Exit);

        mI_About.setOnAction((event) -> {
            ManagePane.setCenterPane(ManagePane.getPanes().get(PaneName.ABOUT_UI));
        });

        mI_Credits.setOnAction((event) -> {
            ManagePane.setCenterPane(ManagePane.getPanes().get(PaneName.CREDITS_UI));
        });

        mI_Exit.setOnAction((event) -> Platform.exit());
        mI_Exit.setAccelerator(KeyCombination.keyCombination("Ctrl+S"));

        //Get the MenuItems in m_Configuration
        m_Configuration.getItems().addAll(mI_About, mI_Credits, mI_Exit);


        // Get the Menus in MenuBar
        mB_View_MenuBar.getMenus().addAll(m_Configuration, m_Inventory, m_Catalog, m_Item);
        mB_View_MenuBar.setMinWidth(2000);

        /// Get the MenuBar in VBox
        vB_MenuBar.getChildren().addAll(mB_View_MenuBar);

        return vB_MenuBar;
    }//End VBox()

    @Override
    public Pane getPane() {
        return getMenuVBox();
    }
}

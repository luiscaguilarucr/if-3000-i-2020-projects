package edu.ucr.rp.programacion2.proyecto.gui.modules.others;

import edu.ucr.rp.programacion2.proyecto.gui.modules.catalog.CreateCatalogForm;
import edu.ucr.rp.programacion2.proyecto.gui.modules.catalog.DeleteCatalog;
import edu.ucr.rp.programacion2.proyecto.gui.modules.inventory.DeleteInventory;
import edu.ucr.rp.programacion2.proyecto.gui.modules.item.ManageItem;
import edu.ucr.rp.programacion2.proyecto.gui.manage.model.PaneName;
import edu.ucr.rp.programacion2.proyecto.gui.manage.model.PaneViewer;
import edu.ucr.rp.programacion2.proyecto.gui.manage.ManagePane;
import edu.ucr.rp.programacion2.proyecto.gui.modules.catalog.CatalogConfig;
import edu.ucr.rp.programacion2.proyecto.gui.modules.inventory.InventoryView;
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
            ManagePane.setCenterPane(PaneName.ADD_INVENTORY);
        });

        mI_SeeInventory.setOnAction((event)->{
            ManagePane.setCenterPane(PaneName.SHOW_INVENTORY);
            InventoryView.refresh();
        });

        mI_DeleteInventory.setOnAction((event) -> {
            ManagePane.setCenterPane(PaneName.DELETE_INVENTORY);
            DeleteInventory.refresh();
        });

        m_Inventory.getItems().addAll(mI_CreateInventory, mI_SeeInventory, mI_DeleteInventory);


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
            ManagePane.setCenterPane(PaneName.CREATE_CATALOG_FORM);
            CreateCatalogForm.refresh();
        });

        mI_ViewAllCatalogs.setOnAction((event) -> {
            ManagePane.setCenterPane(PaneName.SHOW_INVENTORY);
            InventoryView.refresh();
        });

        mI_EditCatalog.setOnAction((event) -> {
            CatalogConfig.refresh();
            ManagePane.setCenterPane(PaneName.CATALOG_CONFIG);

        });

        mI_DeleteCatalog.setOnAction((event) -> {
            ManagePane.setCenterPane(PaneName.DELETE_CATALOG);
            DeleteCatalog.refresh();
        });

        m_Catalog.getItems().addAll(mI_AddCatalog, mI_ViewAllCatalogs, mI_EditCatalog, mI_DeleteCatalog);


        ////////////////////////////////////////////////////////////////////////// Menu "Item"
        Menu m_Item = new Menu("Item");

        ////////////////////////////////////////// MenuItems for m_Item
        //MenuItem mI_ManageItem
        ImageView iV_mI_ManageItem = new ImageView(new Image("item.png"));
        MenuItem mI_ManageItem = new MenuItem("Manage", iV_mI_ManageItem);

        mI_ManageItem.setOnAction(event -> {
            ManagePane.setCenterPane(PaneName.MANAGE_ITEM);
            ManageItem.refresh();
            ManageItem.setPreviousPane(null);
        });

        m_Item.getItems().addAll(mI_ManageItem);

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
            ManagePane.setCenterPane(PaneName.ABOUT_UI);
        });

        mI_Credits.setOnAction((event) -> {
            ManagePane.setCenterPane(PaneName.CREDITS_UI);
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

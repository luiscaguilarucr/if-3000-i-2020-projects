package edu.ucr.rp.programacion2.proyecto.gui.javafx;

import edu.ucr.rp.programacion2.proyecto.gui.model.PaneName;
import edu.ucr.rp.programacion2.proyecto.gui.model.PaneViewer;
import edu.ucr.rp.programacion2.proyecto.gui.panes.main.ManagePane;
import javafx.application.Platform;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.control.Menu;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCombination;
import javafx.stage.Stage;

public class ViewMenuBar implements PaneViewer {
    private Stage stage;

    public ViewMenuBar(Stage stage){
        this.stage = stage;
    }

    public VBox getMenuVBox() {
        VBox vB_MenuBar = new VBox();

        MenuBar mB_View_MenuBar = new MenuBar();
        
        ////////////////////////////////////////////////////////////////////////// Menu "Inventory"
        Menu m_Inventory = new Menu("Inventory");

        ////////////////////////////////////////// MenuItems for m_Inventory
        //MenuItem mI_CreateInventory
        ImageView iV_mI_CreateInventory = new ImageView(new Image("addIcon.png"));
        MenuItem mI_CreateInventory = new MenuItem("Create inventory", iV_mI_CreateInventory);

        //MenuItem mI_SeeInventory
        ImageView iV_mI_SeeInventory = new ImageView(new Image("seeIcon.png"));
        MenuItem mI_SeeInventory = new MenuItem("List of inventories", iV_mI_SeeInventory);

        //MenuItem mI_ChangeName
        ImageView iV_mI_ChangeName = new ImageView(new Image("editIcon.png"));
        MenuItem mI_ChangeName = new MenuItem("Change name", iV_mI_ChangeName);
        
        //MenuItem mI_DeleteInventory
        ImageView iV_mI_DeleteInventory = new ImageView(new Image("delete.png"));
        MenuItem mI_DeleteInventory = new MenuItem("Delete inventory", iV_mI_DeleteInventory);

        mI_CreateInventory.setOnAction((event)->{
            ManagePane.setCenterPane(ManagePane.getPanes().get(PaneName.ADD_INVENTORY));
        });

        mI_SeeInventory.setOnAction((event)->{
            ManagePane.setCenterPane(ManagePane.getPanes().get(PaneName.SHOW_INVENTORY));
        });

        mI_ChangeName.setOnAction((event) -> {
        });

        mI_DeleteInventory.setOnAction((event) -> {
        });

        //Get the MenuItems in m_Configutarion
        m_Inventory.getItems().addAll(mI_SeeInventory, mI_ChangeName, mI_DeleteInventory);

        
        ////////////////////////////////////////////////////////////////////////// Menu "Catalog"
        Menu m_Catalog = new Menu("Catalog");

        ////////////////////////////////////////// MenuItems for m_Catalog
        //MenuItem iV_mI_AddCatalog
        ImageView iV_mI_AddCatalog = new ImageView(new Image("addIcon.png"));
        MenuItem mI_AddCatalog = new MenuItem("Add catalog", iV_mI_AddCatalog);

        //MenuItem mI_SeeItems
        ImageView iV_mI_ViewAllCatalogs = new ImageView(new Image("seeIcon.png"));
        MenuItem mI_ViewAllCatalogs = new MenuItem("View catalogs", iV_mI_ViewAllCatalogs);
        
        //MenuItem mI_DeleteCatalog
        ImageView iV_mI_DeleteCatalog = new ImageView(new Image("delete.png"));
        MenuItem mI_DeleteCatalog = new MenuItem("Delete catalog", iV_mI_DeleteCatalog);
        
        // Give action to MenuItems
        mI_AddCatalog.setOnAction((event) -> {
            ManagePane.setCenterPane(ManagePane.getPanes().get(PaneName.ADD_CATALOG));
        });
        
        mI_ViewAllCatalogs.setOnAction((event) -> {
        });
        
        mI_DeleteCatalog.setOnAction((event) -> {
        });

        m_Catalog.setOnShowing((event) -> {
        });

        m_Catalog.getItems().addAll(mI_AddCatalog, mI_ViewAllCatalogs, mI_DeleteCatalog);

        
        ////////////////////////////////////////////////////////////////////////// Menu "Item"
        Menu m_Item = new Menu("Item");

        ////////////////////////////////////////// MenuItems for m_Item
        //MenuItem mI_AddItem
        ImageView iV_mI_AddItem = new ImageView(new Image("addIcon.png"));
        MenuItem mI_AddItem = new MenuItem("Add item", iV_mI_AddItem);
        
        //MenuItem mI_EditItem
        ImageView iV_mI_EditItem = new ImageView(new Image("editIcon.png"));
        MenuItem mI_EditItem = new MenuItem("Edit item", iV_mI_EditItem);

        //MenuItem mI_ViewItems
        ImageView iV_mI_ViewItems = new ImageView(new Image("seeIcon.png"));
        MenuItem mI_ViewItems = new MenuItem("View items", iV_mI_ViewItems);
        
        //MenuItem mI_DeleteItem
        ImageView iV_mI_DeleteItem = new ImageView(new Image("delete.png"));
        MenuItem mI_DeleteItem = new MenuItem("Delete item", iV_mI_DeleteItem);
        
        //Give action to MenuItems
        mI_AddItem.setOnAction((event) -> {
            ManagePane.setCenterPane(ManagePane.getPanes().get(PaneName.ADD_ITEM));
        });
        
        mI_ViewItems .setOnAction((event) -> {
        });
        
        mI_EditItem .setOnAction((event) -> {
        });

        mI_DeleteItem .setOnAction((event) -> {
        });
        
        m_Item.setOnShowing((event) -> {
        });

        m_Item.getItems().addAll(mI_AddItem, mI_ViewItems, mI_EditItem, mI_DeleteItem);

       
        
        ////////////////////////////////////////////////////////////////////////// Menu "Search"
        Menu m_Search = new Menu("Search");

        ////////////////////////////////////////// MenuItems for m_Search
        //MenuItem iV_mI_Searcher
        ImageView iV_mI_Searcher = new ImageView(new Image("searchIcon.png"));
        MenuItem mI_Searcher = new MenuItem("Searcher", iV_mI_Searcher);
        
        mI_Searcher.setOnAction((event) -> {
        });

        //Get the MenuItems in m_Search
        m_Search.getItems().addAll(mI_Searcher);
        
        
        
        ////////////////////////////////////////////////////////////////////////// Menu "Configuration"
        Menu m_Configutarion = new Menu("Configuration");

        ////////////////////////////////////////// MenuItems for m_Configutarion
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
        });

        mI_Credits.setOnAction((event) -> {
        });

        mI_Exit.setOnAction((event) -> Platform.exit());
        mI_Exit.setAccelerator(KeyCombination.keyCombination("Ctrl+S"));

        //Get the MenuItems in m_Configutarion
        m_Configutarion.getItems().addAll(mI_About, mI_Credits, mI_Exit);
        
        
        // Get the Menus in MenuBar
        mB_View_MenuBar.getMenus().addAll(m_Configutarion, m_Inventory, m_Catalog, m_Item, m_Search);

        /// Get the MenuBar in VBox
        vB_MenuBar.getChildren().addAll(mB_View_MenuBar);

        return vB_MenuBar;
    }//End VBox()

    @Override
    public Pane getPane() {
        return getMenuVBox();
    }
}

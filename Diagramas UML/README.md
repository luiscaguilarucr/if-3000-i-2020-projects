# UML Class Diagrams

## Server Class Diagram
---
![Server Class Diagram](server/server-class-diagram.jpg)




## Client Class Diagram
---
Las clases **InventorySocketService** y **CatalogSocketService**, se utilizarán en el UI, para realizar las acciones de CRUD. <br><br>
Las clases **InventorySocketPersistance** y **CatalogSocketPersistance**, se conectan con el Servidor y genera peticiones de tipo CatalogRequest o InventoryRequest, según sea la implementación.  

![Client Class Diagram](client/client-class-diagram.jpg)
package edu.ucr.rp.programacion2.proyecto.logic;

import edu.ucr.rp.programacion2.proyecto.domain.Inventory;
import edu.ucr.rp.programacion2.proyecto.util.ThreadPool;
import javafx.collections.ObservableList;

import java.util.List;

public class RefreshService {
    private static RefreshService instance;
    InventorySocketService inventorySocketService = InventorySocketService.getInstance();

    private RefreshService() {}

    public static RefreshService getInstance() {
        if (instance == null) {
            instance = new RefreshService();
        }
        return instance;
    }

    public void refreshObservableList(ObservableList observableList) {
        ThreadPool.getPool().shutdownNow();
        ThreadPool.getPool().execute(() -> {
            while (true) {
                try {
                    observableList.clear();
                    observableList.addAll(inventorySocketService.getAll());
                    ThreadPool.pause();
                } catch (ServiceException e) {
                    e.printStackTrace();
                    ThreadPool.getPool().shutdownNow();
                }
            }
        });
    }

    public void close() {
        ThreadPool.getPool().shutdownNow();
    }
}

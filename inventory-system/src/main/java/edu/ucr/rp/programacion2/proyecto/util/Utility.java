package edu.ucr.rp.programacion2.proyecto.util;

import edu.ucr.rp.programacion2.proyecto.domain.logic.Inventory;

import java.util.ArrayList;

public class Utility {


    public static String instanceOf(Object a, Object b) {
        if (a instanceof Integer && b instanceof Integer) {
            return "integer";
        }
        if (a instanceof String && b instanceof String) {
            return "string";
        }
        if (a instanceof Character && b instanceof Character) {
            return "character";
        }
        if (a instanceof Inventory && b instanceof Inventory) {
            return "inventory";
        }

        return "unknown"; //desconocido
    }

    public static boolean equals(Object a, Object b) {
        switch (instanceOf(a, b)) {
            case "integer":
                return (Integer) a == (Integer) b;
            case "string":
                return ((String)a).compareToIgnoreCase((String) b) == 0;
            case "character":
                return ((Character) a).compareTo((Character) b) == 0;
            case "inventory":
                return ((Inventory)a).getName().equalsIgnoreCase( ((Inventory)b).getName() );

        }
        return false; //en cualquier otro caso
    }
    public static Object searchByName(ArrayList<Object> arrayList, Object object){
        for(Object obj1: arrayList)
            if(equals(obj1, object))
                return obj1;
        return null;
    }

    public static Object indexOf(ArrayList<Object> arrayList, Object object){
        for(Object obj1: arrayList)
            if(equals(obj1, object))
                return obj1;
        return null;
    }
}

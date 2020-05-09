package edu.ucr.rp.programacion2.proyecto.util.files;

import java.util.ArrayList;

public class Utility {

    public static boolean equals(Object obj1, Object obj2){
        return true;//TODO copy from Utility

    }
    public static Integer searchByName(ArrayList<Object> arrayList, Object object){
        for(Object obj1: arrayList)
            if(equals(obj1, object))
                return obj1;
        return null;
    }
}

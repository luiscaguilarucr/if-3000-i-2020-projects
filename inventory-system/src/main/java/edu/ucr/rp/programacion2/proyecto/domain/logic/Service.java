package edu.ucr.rp.programacion2.proyecto.domain.logic;

import java.util.List;

public interface Service<E,K> {
    boolean add(E object);
    boolean remove(E object);
    E get(K name);
    List getAll();
    boolean modify(E object);
}

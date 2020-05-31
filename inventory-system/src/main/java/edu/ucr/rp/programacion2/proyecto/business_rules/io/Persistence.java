package edu.ucr.rp.programacion2.proyecto.business_rules.io;

public interface Persistence<E> {
    boolean save(E e);
    E get();
    boolean delete();
}

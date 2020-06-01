package edu.ucr.rp.programacion2.proyecto.business_rules.io;

/**
 *
 * @param <E> Type of element.
 * @param <T> Type of Collection.
 */
public interface Persistence<E, T> {
    boolean write(E e);
    T read();
    boolean delete(E e);
}

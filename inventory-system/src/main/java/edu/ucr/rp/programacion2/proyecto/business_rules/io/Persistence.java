package edu.ucr.rp.programacion2.proyecto.business_rules.io;

/**
 *
 * @param <E> Type of element.
 * @param <K> key to identified an element
 * @param <T> Type of Collection.
 */
public interface Persistence<E, K, T> {
    boolean save(E e);
    T get();
    boolean delete(E e);
}

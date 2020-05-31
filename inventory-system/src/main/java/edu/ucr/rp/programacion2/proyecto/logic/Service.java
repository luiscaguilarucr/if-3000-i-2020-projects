package edu.ucr.rp.programacion2.proyecto.logic;

/**
 *
 * @param <E> Element
 * @param <K> key to identified an element
 * @param <T> Type of collection.
 */
public interface Service<E, K, T> {
    boolean add(E element);

    boolean edit(E element);

    boolean remove(E element);

    E get(K key);

    T getAll();
}

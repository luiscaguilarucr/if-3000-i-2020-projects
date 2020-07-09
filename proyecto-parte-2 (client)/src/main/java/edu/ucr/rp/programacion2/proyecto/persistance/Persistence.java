package edu.ucr.rp.programacion2.proyecto.persistance;

import java.util.List;

/**
 *
 * @param <E> Type of element.
 * @param <K> Key to identify the element.
 */
public interface Persistence<E, K> {
    boolean insert(E e) throws PersistenceException;
    boolean update(E e) throws PersistenceException;
    E read(K key) throws PersistenceException;
    List<E> readAll() throws PersistenceException;
    boolean delete(E e) throws PersistenceException;
    boolean deleteAll() throws PersistenceException;

}

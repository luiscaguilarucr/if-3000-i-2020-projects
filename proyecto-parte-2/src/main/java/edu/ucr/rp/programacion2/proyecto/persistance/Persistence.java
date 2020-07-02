package edu.ucr.rp.programacion2.proyecto.persistance;

/**
 *
 * @param <E> Type of element.
 * @param <T> Type of Collection.
 */
public interface Persistence<E, T> {
    boolean write(E e) throws PersistenceException;
    T read()  throws PersistenceException;
    boolean delete(E e)  throws PersistenceException;
    boolean deleteAll()  throws PersistenceException;
}

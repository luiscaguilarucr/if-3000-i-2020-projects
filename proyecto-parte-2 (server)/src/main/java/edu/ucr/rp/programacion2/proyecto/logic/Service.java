package edu.ucr.rp.programacion2.proyecto.logic;

import java.util.List;

/**
 *
 * @param <E> Element
 * @param <K> key to identified an element
 */
public interface Service<E, K> {
    boolean add(E element) throws ServiceException;
    boolean edit(E element)  throws ServiceException;
    boolean remove(E element)  throws ServiceException;
    boolean removeAll()  throws ServiceException;
    E get(K key)  throws ServiceException;
    List<E> getAll()  throws ServiceException;
}

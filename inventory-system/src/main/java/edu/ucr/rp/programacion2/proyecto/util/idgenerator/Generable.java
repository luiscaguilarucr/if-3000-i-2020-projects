package edu.ucr.rp.programacion2.proyecto.util.idgenerator;

public interface Generable<E, K> {
    E generate(K key);
    E get(K key);
}

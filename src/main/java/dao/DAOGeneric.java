package dao;

import com.fasterxml.jackson.core.type.TypeReference;

import java.util.List;

/**
 * DAOGeneric интерфейс для обобщённого класса DAOGeneric.
 */
public interface DAOGeneric<T> {

    List<T> getAll(TypeReference<List<T>> type, String fileName);

    List<T> getAll(T type, String fileName);

    void add(T entity, String fileName);
}

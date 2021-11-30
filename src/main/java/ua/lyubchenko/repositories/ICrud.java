package ua.lyubchenko.repositories;

import java.util.List;

public interface ICrud<T> {

    void create(T entity);

    List<T> read();

    void update(T entity);

    void delete(int id);

}

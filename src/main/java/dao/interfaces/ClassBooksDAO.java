package dao.interfaces;

import entities.Classbook;

import java.util.List;

public interface ClassBooksDAO {

    // Метод для получения всех созданных журналов.
    List<Classbook> getAllClassbooks();

    // Метод для добавления нового журнала.
    boolean addNewClassbook(Classbook classbook);
}

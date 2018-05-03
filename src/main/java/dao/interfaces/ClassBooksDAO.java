package dao.interfaces;

import entities.Classbook;

import java.util.List;

public interface ClassBooksDAO {

    // ����� ��� ��������� ���� ��������� ��������.
    List<Classbook> getAllClassbooks();

    // ����� ��� ���������� ������ �������.
    boolean addNewClassbook(Classbook classbook);
}

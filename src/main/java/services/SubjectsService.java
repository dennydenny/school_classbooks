package services;

import dao.SubjectsDAO;
import dao.SubjectsDAOImpl;
import exceptions.IncorrectSubjectException;
import models.Subject;

import java.util.List;

public class SubjectsService {

    private SubjectsDAO subjectsDao;

    // Метод, возвращающий полный список предметов.
    public List<Subject> getSubjects() {
        if (subjectsDao == null) {
            subjectsDao = new SubjectsDAOImpl();
        }
        try {
            return subjectsDao.getAllSubjects();
        }
        catch (IncorrectSubjectException e) {
            //TODO: реализовать обработку исключений.
        }
        return null;
    }

    public void addNewSubject(Subject subject) {

    }
}

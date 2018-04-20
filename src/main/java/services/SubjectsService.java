package services;

import dao.SubjectsDAO;
import dao.SubjectsDAOImpl;
import models.Subject;

import java.util.List;

public class SubjectsService {

    private SubjectsDAO subjects;

    // Метод, возвращающий полный список предметов.
    public List<Subject> getSubjects() {
        if (subjects == null) {
            subjects = new SubjectsDAOImpl();
        }
        return subjects.getAllSubjects();
    }

    public void addNewSubject(Subject subject) {

    }
}

package dao.interfaces;

import entities.Subject;

import java.util.List;

/**
 * Created by daniel.khaliulin on 20.04.2018.
 */
public interface SubjectsDAO {

    List<Subject> getAllSubjects();

    void addNewSubject(Subject subject);
}

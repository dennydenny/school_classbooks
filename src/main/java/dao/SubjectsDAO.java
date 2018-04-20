package dao;

import exceptions.IncorrectSubjectException;
import models.Subject;

import java.util.List;

/**
 * Created by daniel.khaliulin on 20.04.2018.
 */
public interface SubjectsDAO {

    List<Subject> getAllSubjects() throws IncorrectSubjectException;

    List<Subject> addSubject(Subject subject);
}

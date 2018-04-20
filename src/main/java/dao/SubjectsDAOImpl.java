package dao;

import exceptions.IncorrectSubjectException;
import models.Subject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by daniel.khaliulin on 20.04.2018.
 */
public class SubjectsDAOImpl implements SubjectsDAO {

    private List<Subject> subjects;
    private dao.util.FileReader fileReader;

    public SubjectsDAOImpl() {

    }

    @Override
    public List<Subject> getAllSubjects() throws IncorrectSubjectException {
        List<Subject> allSubjects = new ArrayList<>();

        fileReader = new dao.util.FileReader();
        String rawString = fileReader.readFile("subjects.txt");

        if (!rawString.isEmpty()) {

            // ��������� ���� ���� �� ������.
            String[] rawSubjects = rawString.split("\\r?\\n");

            // ��������� ������ ������ � ����������� � ������ Subject.
            for (String rawSubject : rawSubjects) {
                String[] subjectData = rawSubject.split(";");

                if (Integer.parseInt(subjectData[0]) < 0)
                    throw new IncorrectSubjectException("������������� ��������� �������� �� ����� ���� ������ ����.");

                if (subjectData[1].isEmpty())
                    throw new IncorrectSubjectException("����������� �������� �������� ��� �������������� " + subjectData[0]);

                Subject subject = new Subject(Integer.parseInt(subjectData[0]), subjectData[1]);
                allSubjects.add(subject);
            }
            return allSubjects;
        }
        return null;
    }

    @Override
    public List<Subject> addSubject(Subject subject) {
        return null;
    }
}

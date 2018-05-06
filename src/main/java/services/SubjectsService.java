package services;

import dao.interfaces.SubjectsDAO;
import dao.SubjectsDAOImpl;
import entities.Subject;
import org.apache.logging.log4j.LogManager;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class SubjectsService {

    private static final org.apache.logging.log4j.Logger logger = LogManager.getLogger(SubjectsService.class);
    private SubjectsDAO subjectsDao;

    public SubjectsService () {
        subjectsDao = new SubjectsDAOImpl();
    }

    // �����, ������������ ������ ������ ���������.
    public List<Subject> getSubjects() {
        return subjectsDao.getAllSubjects();
    }

    // ����� ���������� ������ ��������. ���������� ������������� ���������� ��������.
    public int addNewSubject(String subjectName) {
        // ���������, ��� ������ �������� ��� �� ����������.
        if (!this.getSubjects().stream().anyMatch(x -> x.getName().equals(subjectName))) {
            // ���� �� ����������, �� ������ �����.
            Subject subject = new Subject();
            subject.setName(subjectName);

            // �������� ������������ ������������� � ��������� ��������� � ���������� �������.
            int subjectId = this.getSubjects().stream().max(Comparator.comparing(y -> y.getId()))
                    .get()
                    .getId() + 1;
            subject.setId(subjectId);

            subjectsDao.addNewSubject(subject);

            return subjectId;
        }
        else {
            // ���� ����������, �� ���������� ������������� ������������� ��������.
            return this.getSubjects().stream()
                    .filter(x -> x.getName().equals(subjectName))
                    .collect(Collectors.toList()).get(0)
                    .getId();
        }
    }

    // ����� ���������� ������� �� ��� ��������������.
    public Subject getSubjectById(int id) {
        List <Subject> matchedSubjects = this.getSubjects().stream().filter(x -> x.getId() == id).collect(Collectors.toList());
        if (matchedSubjects.size() > 1) {
            logger.warn("�� �������������� {} ������� ����� ������ ��������. ��� �������������.", id);
        }
        if (matchedSubjects.size() == 0) {
            logger.warn("�� �������������� {} �� ������� ����� �� ������ ��������.", id);
            return null;
        }

        return matchedSubjects.get(0);
    }

    // ����� ���������� ������� �� ��� �������� ��� Null, ���� ������ �������� ���.
    public Subject getSubjectByName(String subj) {
        List <Subject> matchedSubjects = this.getSubjects().stream().filter(x -> x.getName().equals(subj)).collect(Collectors.toList());
        if (matchedSubjects.size() > 1) {
            logger.warn("�� �������� {} ������� ����� ������ ��������. ��� �������������.", subj);
        }
        if (matchedSubjects.size() == 0) {
            logger.warn("�� �������� {} �� ������� ����� �� ������ ��������.", subj);
            return null;
        }

        return matchedSubjects.get(0);
    }
}

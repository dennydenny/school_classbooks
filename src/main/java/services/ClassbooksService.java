package services;

import dao.interfaces.ClassBooksDAO;
import dao.ClassbooksDAOImpl;
import entities.Classbook;
import entities.Subject;
import org.apache.logging.log4j.LogManager;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * Created by Daniel.Khaliulin on 30.04.2018.
 */
public class ClassbooksService {

    private static final org.apache.logging.log4j.Logger logger = LogManager.getLogger(ClassbooksService.class);
    private ClassBooksDAO classBooksDAO;
    private SubjectsService subjectsService;
    private ClassService classService;

    public ClassbooksService() {
        classBooksDAO = new ClassbooksDAOImpl();
        subjectsService = new SubjectsService();
        classService = new ClassService();
    }

    // �����, ������������ ������ ������ ��������.
    public Map<Classbook, Subject> getClassbooks() {

        try {
            // �������� ������ ��������.
            List<Classbook> classbookList = classBooksDAO.getAllClassbooks();

            // ��������� ���������� ���� ������� � ��������� �� ������ ������ ��������.
            // ̸��� ��������� ��� �������� � UI �������� �������� � ������ ������ �� �� id �� �������.
            Map<Classbook, Subject> mixedList = new HashMap<>();

            for (Classbook classbook : classbookList) {
                mixedList.put(classbook,
                    subjectsService.getSubjectById(classbook.getSubjectId()));
            }
                return mixedList;
            }
        catch (Exception e) {
            logger.error("��� �������� ������ �������� �������� ������.", e);
        }
        return null;
    }

    // ����� ���������� ������ �������.
    public void addNewClassbook(int classId, String subjectName) {
        int subjectId;
        Subject subject;

        // ���������, ���������� �� ����� ������. ���� ��� ����������, �� �� ������ ������.
        if (this.checkIsClassbookExist(classId, subjectName)) {
            logger.info("�������� ������ � �������� {} ��� ������ {} ��� ����������.", subjectName, classId);
            return;
        }

        // ���������, ���������� �� ����� �������. ���� ���, �� ������ �����.
        if (!subjectsService.getSubjects().stream().anyMatch(x -> x.getName().equals(subjectName))) {
            subjectId = subjectsService.addNewSubject(subjectName);
        }
        else {
            // ���� ����������, �� �������� ��� �������������.
            subject = subjectsService.getSubjectByName(subjectName);
            if (subject != null) {
                subjectId = subject.getId();
            }
            else {
                logger.error("�� ������� ����� ������� �� �������� {}.", subjectName);
                return;
            }
        }

        // ���������, ���������� �� ��������� �����. ���� ���, �� ����� ������.
        if (!classService.getAllClasses().stream().anyMatch(y -> y.getClassId() == classId)) {
            logger.error("��������� ������ �� ����������. ������� ������� ������������ �����.");
            return;
        }

        Classbook classbook = new Classbook();
        classbook.setClassId(classId);
        classbook.setSubjectId(subjectId);
        classBooksDAO.addNewClassbook(classbook);
    }

    // ����� ���������� true, ���� ����� ������ ��� ����. False, ���� ���.
    private boolean checkIsClassbookExist(int classId, String subjectName) {
        Map<Classbook, Subject> classbooks = this.getClassbooks().entrySet().stream()
                .filter(x -> x.getKey().getClassId() == classId && x.getValue().getName().equals(subjectName))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        return classbooks.size() >= 1;
    }
}

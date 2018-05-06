package services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.sun.xml.internal.ws.api.ha.StickyFeature;
import dao.ClassesDAOImpl;
import dao.DAOGenericImpl;
import dao.interfaces.ClassesDAO;
import entities.Class;
import entities.Pupil;
import org.apache.logging.log4j.LogManager;

import java.util.List;

/**
 * Created by Daniel.Khaliulin on 30.04.2018.
 */
public class ClassService {
    private static final org.apache.logging.log4j.Logger logger = LogManager.getLogger(ClassService.class);
    private ClassesDAO DAO;
    private String fileName = "classes.txt";

    public ClassService() {
        this.DAO = new ClassesDAOImpl();
    }

    // �����, ������������ ������ ������ �������.
    public List<Class> getAllClasses() {
        return DAO.getAllClasses();
    }

    // �����, ����������� ���������, ���������� �� �����.
    public boolean isClassExist(int classId) {
        return this.getAllClasses().stream()
                .anyMatch(x -> x.getClassId() == classId);
    }

    // �����, ����������� ���������, ���������� �� ������ � ���������� ������.
    public boolean isPupilExistInClass(int classId, String pupilName) {
        return this.getAllClasses().stream()
                .anyMatch(x -> x.getClassId() == classId
                        &&
                        x.getPupils().stream()
                                .anyMatch(y -> y.getName().equals(pupilName)));
    }
}

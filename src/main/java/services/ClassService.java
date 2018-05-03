package services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.sun.xml.internal.ws.api.ha.StickyFeature;
import dao.ClassesDAOImpl;
import dao.DAOGenericImpl;
import dao.interfaces.ClassesDAO;
import entities.Class;
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

    // Метод, возвращающий полный список классов.
    public List<Class> getAllClasses() {
        return DAO.getAllClasses();
    }

    // Метод, позволяющий проверить, существует ли класс.
    public boolean isClassExist(int classId) {
        return this.getAllClasses().stream()
                .anyMatch(x -> x.getClassId() == classId);
    }

    // Метод, позволяющий проверить, существует ли ученик в конкретном классе.
    public boolean isPupilExistInClass(int classId, String pupilName) {
        return this.getAllClasses().stream()
                .anyMatch(x -> x.getClassId() == classId
                        &&
                        x.getPupils().stream()
                                .anyMatch(y -> y.getName().equals(pupilName)));
    }
}

package services;

import dao.interfaces.ClassBooksDAO;
import dao.ClassbooksDAOImpl;
import entities.Classbook;
import entities.Subject;
import org.apache.logging.log4j.LogManager;


import java.util.Comparator;
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
    private List<Classbook> rawClassbooksList;

    public ClassbooksService() {
        classBooksDAO = new ClassbooksDAOImpl();
        subjectsService = new SubjectsService();
        classService = new ClassService();
    }

    // Метод, возвращающий полный список журналов.
    public Map<Classbook, Subject> getClassbooks() {

        try {
            // Получаем список журналов.
            rawClassbooksList = classBooksDAO.getAllClassbooks();

            // Формируем смёрдженный лист классов и предметов на основе списка журналов.
            // Мёрдж необходим для передачи в UI названия предмета и номера класса по их id из журнала.
            Map<Classbook, Subject> mixedList = new HashMap<>();

            for (Classbook classbook : rawClassbooksList) {
                mixedList.put(classbook,
                    subjectsService.getSubjectById(classbook.getSubjectId()));
            }
                return mixedList;
            }
        catch (Exception e) {
            logger.error("При загрузке списка журналов возникла ошибка.", e);
        }
        return null;
    }

    // Метод добавления нового журнала.
    public void addNewClassbook(int classId, String subjectName) {
        int subjectId;
        Subject subject;

        // Проверяем, существует ли такой журнал. Если уже существует, то не делаем ничего.
        if (this.checkIsClassbookExist(classId, subjectName)) {
            logger.info("Заданный журнал с предетом {} для класса {} уже существует.", subjectName, classId);
            return;
        }

        // Проверяем, существует ли такой предмет. Если нет, то создаём новый.
        if (!subjectsService.getSubjects().stream().anyMatch(x -> x.getName().equals(subjectName))) {
            subjectId = subjectsService.addNewSubject(subjectName);
        }
        else {
            // Если существует, то получаем его идентификатор.
            subject = subjectsService.getSubjectByName(subjectName);
            if (subject != null) {
                subjectId = subject.getId();
            }
            else {
                logger.error("Не удалось найти предмет по названию {}.", subjectName);
                return;
            }
        }

        // Проверяем, существует ли указанный класс. Если нет, то пишем ошибку.
        if (!classService.getAllClasses().stream().anyMatch(y -> y.getClassId() == classId)) {
            logger.error("Введённого класса не существует. Просьба указать существующий класс.");
            return;
        }

        Classbook classbook = new Classbook();
        // Идентификатор нового журнала.
        classbook.setId(this.getMaxClassbooksId() + 1);
        // Идентификатор класса.
        classbook.setClassId(classId);
        // Идентификатор предмета.
        classbook.setSubjectId(subjectId);
        classBooksDAO.addNewClassbook(classbook);
    }

    // Метод возвращает true, если такой журнал уже есть. False, если нет.
    private boolean checkIsClassbookExist(int classId, String subjectName) {
        Map<Classbook, Subject> classbooks = this.getClassbooks().entrySet().stream()
                .filter(x -> x.getKey().getClassId() == classId && x.getValue().getName().equals(subjectName))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        return classbooks.size() >= 1;
    }

    // Метод, возвращающий максимальный идентификатор журнала из хранилища.
    private int getMaxClassbooksId() {
        return this.rawClassbooksList.stream()
                .max(Comparator.comparing(y -> y.getId()))
                .get()
                .getId();
    }
}

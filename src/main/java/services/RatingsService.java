package services;

import entities.Class;
import org.apache.logging.log4j.LogManager;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by daniel.khaliulin on 03.05.2018.
 */
public class RatingsService {

    private ClassService classService;
    private static final org.apache.logging.log4j.Logger logger = LogManager.getLogger(RatingsService.class);

    public RatingsService() {
        this.classService = new ClassService();
    }

    // Метод добавления новой оценки.
    public void addNewRate(int classId, String name, String pupilName, int rate, Date date) {
        // Проверяем, что класс существует.
        if (!isClassExist(classId)) {
            logger.warn("Указанного класса не существует.");
            return;
        }

        // Проверяем, что указанный ученик существует.
    }

    // Метод, для проверки существования класса. Возвращает true, если указанный класс существует.
    private boolean isClassExist(int classId) {
        List<Class> classes = classService.getAllClasses().stream()
                .filter(x -> x.getClassId() == classId)
                .collect(Collectors.toList());
        return classes.size() > 0;
    }
}

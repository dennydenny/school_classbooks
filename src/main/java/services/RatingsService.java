package services;

import entities.Class;
import entities.Pupil;
import entities.Rating;
import org.apache.logging.log4j.LogManager;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by daniel.khaliulin on 03.05.2018.
 */
public class RatingsService {

    private ClassService classService;
    private ClassbooksService classbooksService;
    private static final org.apache.logging.log4j.Logger logger = LogManager.getLogger(RatingsService.class);

    public RatingsService() {
        this.classService = new ClassService();
        this.classbooksService = new ClassbooksService();
    }

    // Метод добавления новой оценки.
    public void addNewRate(int classId, String subjectName, String pupilName, int rate, Date date) {
        Rating rating = new Rating();
        rating.setDate(date);
        rating.setMark(rate);

        // Получаем класс по идентификатору и имени ученика.
        Class schoolClass = classService.getAllClasses().stream()
                .filter(x -> x.getClassId() == classId
                        &&
                        x.getPupils().stream()
                                .anyMatch(y -> y.getName().equals(pupilName)))
                .collect(Collectors.toList())
                .get(0);

        // Проставляем идентификатор ученика из списка учеников класса.
        rating.setPupilId(schoolClass.getPupils().stream()
                .filter(x -> x.getName().equals(pupilName))
                .collect(Collectors.toList())
                .get(0)
                .getpupilId());

        //TODO: реализовать получение классбука и понять, нужен ли классбук вообще для оценки. Возможно, стоит вынести отдельный ид для журнала.
        /*rating.setClassbookId(classbooksService.getClassbooks().entrySet().stream()
                .filter(x -> x.getKey().getClassId() == classId
                &&
                            x.getValue().getName().equals(subjectName))
                .collect(Collectors.toList())
                .get(0)
                .getKey().);
                */
    }

    // Метод, для проверки существования класса. Возвращает true, если указанный класс существует.
    private boolean isClassExist(int classId) {
        List<Class> classes = classService.getAllClasses().stream()
                .filter(x -> x.getClassId() == classId)
                .collect(Collectors.toList());
        return classes.size() > 0;
    }
}

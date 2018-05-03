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

    // Метод, возвращающий полный список предметов.
    public List<Subject> getSubjects() {
        return subjectsDao.getAllSubjects();
    }

    // Метод возвращает предмет по его идентификатору.
    public Subject getSubjectById(int id) {
        List <Subject> matchedSubjects = this.getSubjects().stream().filter(x -> x.getId() == id).collect(Collectors.toList());
        if (matchedSubjects.size() > 1) {
            logger.warn("По идентификатору {} найдено более одного предмета. Чет подозрительно.", id);
        }
        if (matchedSubjects.size() == 0) {
            logger.warn("По идентификатору {} не удалось найти ни одного предмета.", id);
            return null;
        }

        return matchedSubjects.get(0);
    }

    // Метод добавления нового предмета. Возвращает идентификатор созданного предмета.
    public int addNewSubject(String subjectName) {
        // Проверяем, что такого предмета ещё не существует.
        if (!this.getSubjects().stream().anyMatch(x -> x.getName().equals(subjectName))) {
            // Если не существует, то создаём новый.
            Subject subject = new Subject();
            subject.setName(subjectName);

            // Получаем максимальный идентификатор в коллекции предметов и прибавляем единицу.
            int subjectId = this.getSubjects().stream().max(Comparator.comparing(y -> y.getId()))
                    .get()
                    .getId() + 1;
            subject.setId(subjectId);

            subjectsDao.addNewSubject(subject);

            return subjectId;
        }
        else {
            // Если существует, то возвращаем идентификатор существующего предмета.
            return this.getSubjects().stream()
                    .filter(x -> x.getName().equals(subjectName))
                    .collect(Collectors.toList()).get(0)
                    .getId();
        }
    }

    // Метод возвращает предмет по его названию или Null, если такого предмета нет.
    public Subject getSubjectByName(String subj) {
        List <Subject> matchedSubjects = this.getSubjects().stream().filter(x -> x.getName().equals(subj)).collect(Collectors.toList());
        if (matchedSubjects.size() > 1) {
            logger.warn("По названию {} найдено более одного предмета. Чет подозрительно.", subj);
        }
        if (matchedSubjects.size() == 0) {
            logger.warn("По названию {} не удалось найти ни одного предмета.", subj);
            return null;
        }

        return matchedSubjects.get(0);
    }
}

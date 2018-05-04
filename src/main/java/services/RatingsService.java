package services;

import dao.RatingsDAOImpl;
import dao.interfaces.RatingsDAO;
import entities.Class;
import entities.Pupil;
import entities.Rating;
import org.apache.logging.log4j.LogManager;

import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by daniel.khaliulin on 03.05.2018.
 */
public class RatingsService {

    private ClassService classService;
    private ClassbooksService classbooksService;
    private RatingsDAO ratingsDAO;
    private static final org.apache.logging.log4j.Logger logger = LogManager.getLogger(RatingsService.class);

    public RatingsService() {
        this.classService = new ClassService();
        this.classbooksService = new ClassbooksService();
        this.ratingsDAO = new RatingsDAOImpl();
    }

    // ����� ���������� ����� ������.
    public void addNewRate(int classId, String subjectName, String pupilName, int rate, Date date) {
        Rating rating = new Rating();
        rating.setDate(date);
        rating.setMark(rate);

        // �������� ������������� �������.
        // �������� ����� �� �������������� � ����� �������.
        Class schoolClass = classService.getAllClasses().stream()
                .filter(x -> x.getClassId() == classId
                        &&
                        x.getPupils().stream()
                                .anyMatch(y -> y.getName().equals(pupilName)))
                .collect(Collectors.toList())
                .get(0);

        // ����������� ������������� ������� �� ������ �������� ������.
        rating.setPupilId(schoolClass.getPupils().stream()
                .filter(x -> x.getName().equals(pupilName))
                .collect(Collectors.toList())
                .get(0)
                .getpupilId());

        // �������� ������������� ������� (��������� �� ����� �������� � �� ������).
        rating.setClassbookId(classbooksService.getClassbooks().entrySet().stream()
                .filter(x -> x.getValue().getName().equals(subjectName) && x.getKey().getClassId() == classId)
                .collect(Collectors.toList())
                .get(0)
                .getKey()
                .getId());

        // ���������������� ������������� ������.
        rating.setId(ratingsDAO.getAllRatings().stream().
                max(Comparator.comparing(y -> y.getId()))
                .get()
                .getId() + 1);
        // ������� � DAO.
        if (ratingsDAO.addNewRate(rating)) {
            logger.info("������� {} ���������� ������ {} �� �������� {}", pupilName, rate, subjectName);
        }
    }

    // ����� ��������� ������ ������.
    public List<Rating> getAllRatings() {
        return ratingsDAO.getAllRatings();
    }

}

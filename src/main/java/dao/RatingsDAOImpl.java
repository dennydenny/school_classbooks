package dao;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import dao.interfaces.RatingsDAO;
import dao.util.FileHelper;
import entities.Rating;
import org.apache.logging.log4j.LogManager;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by daniel.khaliulin on 04.05.2018.
 */
public class RatingsDAOImpl extends AbstractFileWriter implements RatingsDAO {

    private static final org.apache.logging.log4j.Logger logger = LogManager.getLogger(RatingsDAOImpl.class);
    private String fileName = "ratings.txt";
    private List<Rating> allRatings;

    // ����� ��������� ������ ������.
    @Override
    public List<Rating> getAllRatings() {
        // Lite-���, ����� ����������� �� ������ ���� � ��� �� ����.
        if (allRatings != null)
        {
            return allRatings;
        }

        ObjectMapper mapper = new ObjectMapper();
        // ���������� �������, ��� �������� ���� ���� 11.11.11 ����������� � ������ �������� �������� � ���� 11.11.11 04:00.
        mapper.disable(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE);
        mapper.setTimeZone(TimeZone.getTimeZone("GMT+4"));
        try {
            allRatings = new ArrayList<>(Arrays.asList(mapper.readValue(FileHelper.getDataFile(fileName), Rating[].class)));
        } catch (IOException e) {
            logger.fatal("��� �������� ������ ������ �������� ��������� ������.", e);
            return null;
        }
        return allRatings;
    }

    // ����� ���������� ����� ������.
    @Override
    public boolean addNewRate(Rating rating) {
        this.getAllRatings();
        allRatings.add(rating);
        // ������� ����.
        FileHelper.cleanDataFileContent(fileName);
        // ���������� ����������� ������ � ����.
        super.writeListToFile(allRatings, fileName);
        return true;
    }

    // ����� �������� ������.
    public boolean deleteRate(Rating rating) {
        this.getAllRatings();
        // ��������� ���������� ������ �� ������ ������ ������.
        /*allRatings = allRatings.stream()
                .filter(x -> !x.getDate().equals(rating.getDate())
                        && x.getClassbookId() != rating.getClassbookId()
                        && x.getPupilId() != rating.getPupilId())
                .collect(Collectors.toList());
                */
        allRatings = allRatings.stream()
                .filter(x -> x.getId() != rating.getId())
                .collect(Collectors.toList());
        // ������� ����.
        FileHelper.cleanDataFileContent(fileName);
        // ���������� ����������� ������ � ����.
        super.writeListToFile(allRatings, fileName);
        return true;
    }
}

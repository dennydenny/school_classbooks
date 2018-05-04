package dao;

import com.fasterxml.jackson.databind.ObjectMapper;
import dao.interfaces.RatingsDAO;
import dao.util.FileHelper;
import entities.Rating;
import org.apache.logging.log4j.LogManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

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
}

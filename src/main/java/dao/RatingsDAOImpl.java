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

    // Метод получения списка оценок.
    @Override
    public List<Rating> getAllRatings() {
        // Lite-кэш, чтобы многократно не читать один и тот же файл.
        if (allRatings != null)
        {
            return allRatings;
        }

        ObjectMapper mapper = new ObjectMapper();
        try {
            allRatings = new ArrayList<>(Arrays.asList(mapper.readValue(FileHelper.getDataFile(fileName), Rating[].class)));
        } catch (IOException e) {
            logger.fatal("При загрузке списка оценок возникла критичная ошибка.", e);
            return null;
        }
        return allRatings;
    }

    // Метод добавления новой оценки.
    @Override
    public boolean addNewRate(Rating rating) {
        this.getAllRatings();
        allRatings.add(rating);
        // Очищаем файл.
        FileHelper.cleanDataFileContent(fileName);
        // Записываем обновленные данные в файл.
        super.writeListToFile(allRatings, fileName);
        return true;
    }
}

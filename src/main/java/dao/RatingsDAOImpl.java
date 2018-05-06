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

    // Метод получения списка оценок.
    @Override
    public List<Rating> getAllRatings() {
        // Lite-кэш, чтобы многократно не читать один и тот же файл.
        if (allRatings != null)
        {
            return allRatings;
        }

        ObjectMapper mapper = new ObjectMapper();
        // Костыльное решение, без которого даты вида 11.11.11 считывались с учётом местного смещения и были 11.11.11 04:00.
        mapper.disable(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE);
        mapper.setTimeZone(TimeZone.getTimeZone("GMT+4"));
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

    // Метод удаления оценки.
    public boolean deleteRate(Rating rating) {
        this.getAllRatings();
        // Исключаем переданную оценку из общего списка оценок.
        allRatings = allRatings.stream()
                .filter(x -> x.getDate() != rating.getDate()
                        && x.getClassbookId() != rating.getClassbookId()
                        && x.getPupilId() != rating.getPupilId())
                .collect(Collectors.toList());
        // Очищаем файл.
        FileHelper.cleanDataFileContent(fileName);
        // Записываем обновленные данные в файл.
        super.writeListToFile(allRatings, fileName);
        return true;
    }
}

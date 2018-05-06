package dao;

import com.fasterxml.jackson.databind.ObjectMapper;
import dao.util.FileHelper;
import entities.Subject;
import org.apache.logging.log4j.LogManager;

import java.io.IOException;
import java.util.List;

/**
 * Created by daniel.khaliulin on 03.05.2018.
 */
public abstract class AbstractFileWriter<T> {

    private static final org.apache.logging.log4j.Logger logger = LogManager.getLogger(AbstractFileWriter.class);

    // Метод для записи списка объектов в файл.
    public void writeListToFile(List<T> list, String fileName) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.writeValue(FileHelper.getDataFile(fileName), list);
            logger.info("Запись обновленного списка успешно завершена");
        } catch (IOException e) {
            logger.error("При записи обновленного списка возникла ошибка.", e);
        }
    }
}

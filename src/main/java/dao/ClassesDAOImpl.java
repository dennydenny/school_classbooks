package dao;

import com.fasterxml.jackson.databind.ObjectMapper;
import dao.interfaces.ClassesDAO;
import dao.util.FileHelper;
import entities.Class;
import org.apache.logging.log4j.LogManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Daniel.Khaliulin on 30.04.2018.
 */
public class ClassesDAOImpl implements ClassesDAO {

    private static final org.apache.logging.log4j.Logger logger = LogManager.getLogger(ClassesDAOImpl.class);
    private List<Class> allClasses;
    private final String fileName = "classes.txt";

    // Метод, возвращающий все классы.
    @Override
    public List<Class> getAllClasses() {
        // Lite-кэш, чтобы многократно не читать один и тот же файл.
        if (allClasses != null)
        {
            return allClasses;
        }

        ObjectMapper mapper = new ObjectMapper();

        try {
            allClasses = new ArrayList<>(Arrays.asList(
                    mapper.readValue(FileHelper.getDataFile(fileName), Class[].class)));
        } catch (IOException io) {
            logger.fatal("При загрузке списка классов возникла критичная ошибка в работе с файлами.", io);
            return null;
        }
        catch (Exception e) {
            logger.fatal("При загрузке списка классов возникла критичная ошибка.", e);
            return null;
        }
        return allClasses;
    }
}

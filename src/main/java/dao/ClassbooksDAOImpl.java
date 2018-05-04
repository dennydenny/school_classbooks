package dao;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import dao.interfaces.ClassBooksDAO;
import dao.interfaces.ClassesDAO;
import dao.util.FileHelper;
import entities.Classbook;
import org.apache.logging.log4j.LogManager;

import java.io.IOException;
import java.util.List;

/**
 * Created by Daniel.Khaliulin on 30.04.2018.
 */
public class ClassbooksDAOImpl extends AbstractFileWriter implements ClassBooksDAO {

    private static final org.apache.logging.log4j.Logger logger = LogManager.getLogger(ClassbooksDAOImpl.class);
    private String fileName = "classbooks.txt";
    private List<Classbook> allClassbooks;

    @Override
    public List<Classbook> getAllClassbooks() {

        // Lite-кэш, чтобы многократно не читать один и тот же файл.
        if (allClassbooks != null)
        {
            return allClassbooks;
        }

        ObjectMapper mapper = new ObjectMapper();
        try {
            allClassbooks = mapper.readValue(FileHelper.getDataFile(fileName), new TypeReference<List<Classbook>>() {});
        } catch (IOException e) {
            logger.fatal("При загрузке списка журналов возникла критичная ошибка.", e);
            return null;
        }
        return allClassbooks;
    }

    @Override
    public boolean addNewClassbook(Classbook classbook) {
        this.getAllClassbooks();
        allClassbooks.add(classbook);
        // Очищаем файл.
        FileHelper.cleanDataFileContent(fileName);
        // Записываем обновленные данные в файл.
        super.writeListToFile(allClassbooks, fileName);
        return false;
    }
}

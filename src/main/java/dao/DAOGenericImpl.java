package dao;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import dao.util.FileHelper;
import org.apache.logging.log4j.LogManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Обобщённый класс DAOGeneric.
 */
public class DAOGenericImpl<T> implements DAOGeneric<T> {

    private static final org.apache.logging.log4j.Logger logger = LogManager.getLogger(DAOGenericImpl.class);
    private List<T> subjects;
    private List<T> classbooks;
    private List<T> classes;
    T target;

    /**
     * Обобщённый метод для получения данных.
     * Экземпляр списка с указанием типа, данные по которому необходимо получить.
     * Название текстового файла с данными.
     */
    public List<T> getAll(TypeReference<List<T>> type, String fileName) {
        logger.info("Загружается информация из файла " + fileName);
        List<T> list;

        // Lite-кэш, чтобы многократно не читать один и тот же файл.
        List<T> cache = this.getListFromCacheIfExist(type);
        if (cache != null) {
            logger.debug("Данные найдены в кэше.");
            return cache;
        }

        // Данных в кэше нет, начинаем работать с файлом с данными.
        ObjectMapper mapper = new ObjectMapper();
        try {
            list = mapper.readValue(FileHelper.getDataFile(fileName), type);

            // Добавим в кэш.
            if (list.size() > 0) writeToCache(list);
            return list;
        } catch (IOException e) {
            logger.fatal("При загрузке данных возникла критичная ошибка.", e);
            return null;
        }
    }


    // Обобщённый метод для получения данных по любой переданной сущности.
    public List<T> getAll(T type, String fileName) {
        logger.info("Загружается информация из файла " + fileName);
        List<T> list;

        // Lite-кэш, чтобы многократно не читать один и тот же файл.
        //List<T> cache = this.getListFromCacheIfExist(type);
        //if (cache != null) {
        //    logger.debug("Данные найдены в кэше.");
        //    return cache;
        //}

        // Данных в кэше нет, начинаем работать с файлом с данными.
        ObjectMapper mapper = new ObjectMapper();
        this.target = type;
        JavaType types = mapper.getTypeFactory().
                constructCollectionType(
                        ArrayList.class,
                        target.getClass());

        try {
            list = mapper.readValue(FileHelper.getDataFile(fileName), types);

            // Добавим в кэш.
            if (list.size() > 0) writeToCache(list);
            return list;
        } catch (IOException e) {
            logger.fatal("При загрузке данных возникла критичная ошибка.", e);
            return null;
        }
    }

    // Обобщённый метод для добавления новой сущности в хранилище.
    public void add(T entity, String fileName) {
        List<T> list;
        logger.info("Добавление нового объекта в файл " + fileName);

        // Читаем существующие данные.
        try {
            ObjectMapper mapper = new ObjectMapper();
            //list = mapper.readValue(FileHelper.getDataFile(fileName), type);
        }
        catch (Exception e) {

        }
    }

    // Метод возвращает "закэшированный" список объектов относящийся к переданному POJO объекту, если список ранее был создан.
    // Иначе возвращает null.
    private List<T> getListFromCacheIfExist(TypeReference<List<T>> type) {
        String rawName = type.getType().getTypeName();

        // Код плохо пахнет.
        if (rawName.contains("Subject") && subjects != null) {
            return subjects;
        }
        if (rawName.contains("Class") && classes != null) {
            return classes;
        }
        if (rawName.contains("Classbook") && classbooks != null) {
            return classbooks;
        }
        return null;
    }

    // Метод для записи списка в кэш.
    private boolean writeToCache(List<T> list) {
        logger.debug("Кэшируем данные...");

        String rawName = list.get(0).getClass().getCanonicalName();

        // Код плохо пахнет.
        if (rawName.contains("Subject")) {
            subjects = list;
            return true;
        }
        if (rawName.contains("Class")) {
            classes = list;
            return true;
        }
        if (rawName.contains("Classbook")) {
            classbooks = list;
            return true;
        }
        return false;
    }
}

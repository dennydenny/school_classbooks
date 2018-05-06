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
 * ���������� ����� DAOGeneric.
 */
public class DAOGenericImpl<T> implements DAOGeneric<T> {

    private static final org.apache.logging.log4j.Logger logger = LogManager.getLogger(DAOGenericImpl.class);
    private List<T> subjects;
    private List<T> classbooks;
    private List<T> classes;
    T target;

    /**
     * ���������� ����� ��� ��������� ������.
     * ��������� ������ � ��������� ����, ������ �� �������� ���������� ��������.
     * �������� ���������� ����� � �������.
     */
    public List<T> getAll(TypeReference<List<T>> type, String fileName) {
        logger.info("����������� ���������� �� ����� " + fileName);
        List<T> list;

        // Lite-���, ����� ����������� �� ������ ���� � ��� �� ����.
        List<T> cache = this.getListFromCacheIfExist(type);
        if (cache != null) {
            logger.debug("������ ������� � ����.");
            return cache;
        }

        // ������ � ���� ���, �������� �������� � ������ � �������.
        ObjectMapper mapper = new ObjectMapper();
        try {
            list = mapper.readValue(FileHelper.getDataFile(fileName), type);

            // ������� � ���.
            if (list.size() > 0) writeToCache(list);
            return list;
        } catch (IOException e) {
            logger.fatal("��� �������� ������ �������� ��������� ������.", e);
            return null;
        }
    }


    // ���������� ����� ��� ��������� ������ �� ����� ���������� ��������.
    public List<T> getAll(T type, String fileName) {
        logger.info("����������� ���������� �� ����� " + fileName);
        List<T> list;

        // Lite-���, ����� ����������� �� ������ ���� � ��� �� ����.
        //List<T> cache = this.getListFromCacheIfExist(type);
        //if (cache != null) {
        //    logger.debug("������ ������� � ����.");
        //    return cache;
        //}

        // ������ � ���� ���, �������� �������� � ������ � �������.
        ObjectMapper mapper = new ObjectMapper();
        this.target = type;
        JavaType types = mapper.getTypeFactory().
                constructCollectionType(
                        ArrayList.class,
                        target.getClass());

        try {
            list = mapper.readValue(FileHelper.getDataFile(fileName), types);

            // ������� � ���.
            if (list.size() > 0) writeToCache(list);
            return list;
        } catch (IOException e) {
            logger.fatal("��� �������� ������ �������� ��������� ������.", e);
            return null;
        }
    }

    // ���������� ����� ��� ���������� ����� �������� � ���������.
    public void add(T entity, String fileName) {
        List<T> list;
        logger.info("���������� ������ ������� � ���� " + fileName);

        // ������ ������������ ������.
        try {
            ObjectMapper mapper = new ObjectMapper();
            //list = mapper.readValue(FileHelper.getDataFile(fileName), type);
        }
        catch (Exception e) {

        }
    }

    // ����� ���������� "��������������" ������ �������� ����������� � ����������� POJO �������, ���� ������ ����� ��� ������.
    // ����� ���������� null.
    private List<T> getListFromCacheIfExist(TypeReference<List<T>> type) {
        String rawName = type.getType().getTypeName();

        // ��� ����� ������.
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

    // ����� ��� ������ ������ � ���.
    private boolean writeToCache(List<T> list) {
        logger.debug("�������� ������...");

        String rawName = list.get(0).getClass().getCanonicalName();

        // ��� ����� ������.
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

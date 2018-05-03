package dao.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import entities.Subject;
import org.apache.logging.log4j.LogManager;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Scanner;

/**
 * Created by daniel.khaliulin on 20.04.2018.
 */
public class FileHelper {

    private static final org.apache.logging.log4j.Logger logger = LogManager.getLogger(FileHelper.class);

    // Метод, читающий данные из файла.
    public static String readFile(String fileName) {

        StringBuilder result = new StringBuilder("");

        File file = getDataFile(fileName);

        try (Scanner scanner = new Scanner(file)) {

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                // Пропускаем закомментированные и пустые строки.
                if (!line.startsWith("#") && line.length() > 0) {
                    result.append(line).append("\n");
                }
            }

            scanner.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return result.toString();
    }

    // Возвращает экземпляр файла из ресурсов по его имени.
    public static File getDataFile(String fileName) {
        return new File("data/"+fileName);
    }

    // Метод для очистки содержимого файла перед записью обновленных данных.
    public static boolean cleanDataFileContent(String fileName) {

        try {
            PrintWriter writer = new PrintWriter(new File("data/" + fileName));
            writer.print("");
            writer.close();
            return true;
        }
        catch (IOException io) {
            logger.error("При очистке файла {} возника ошибка.", fileName, io);
            return false;
        }
    }

    public static void writeListToFile(List<Object> list, String fileName) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.writeValue(FileHelper.getDataFile(fileName), list);
        } catch (IOException e) {
            logger.error("При записи обновленного списка предметов возникла ошибка.", e);
        }
    }
}

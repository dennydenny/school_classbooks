package dao.util;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

/**
 * Created by daniel.khaliulin on 20.04.2018.
 */
public class FileReader {

    // Метод, читающий данные из файла.
    public String readFile(String fileName) {

        StringBuilder result = new StringBuilder("");

        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource(fileName).getFile());

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
}

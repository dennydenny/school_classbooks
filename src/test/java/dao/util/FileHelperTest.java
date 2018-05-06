package dao.util;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by Daniel.Khaliulin on 30.04.2018.
 */
public class FileHelperTest {

    @BeforeClass
    public static void setUp() throws IOException {

        String fileName = "test.txt";
        File file = new File("data/" + fileName);

        // ѕроверим, что нужный файл существует и если он пуст, то запишем в него что-то.
        if (file.exists() && file.length() == 0) {
            String str = "World";
            BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true));
            writer.append(' ');
            writer.close();
        }
    }

    // ћетод, провер€ющий, что по запросу возвращаетс€ существующий файл.
    @Test
    public void getDataFile_ShouldReturnCorrectFile() throws Exception {

        String resourceFileName = "test.txt";

        File file = FileHelper.getDataFile(resourceFileName);

        Assert.assertTrue(file.exists());
    }

    // ћетод, провер€ющий, что данные из файла действительно удал€ютс€.
    @Test
    public void cleanDataFileContent_ShouldCleanPreparedFile() throws Exception {

        String fileName = "test.txt";
        File file = new File("data/" + fileName);

        boolean result = FileHelper.cleanDataFileContent(fileName);

        Assert.assertTrue(result);
        Assert.assertTrue(file.length() == 0);

    }
}
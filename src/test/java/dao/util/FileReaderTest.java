package dao.util;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by daniel.khaliulin on 20.04.2018.
 */
public class FileReaderTest {

    @Test
    public void testReadFile() throws Exception {

        // arrange
        FileReader fileReader = new FileReader();
        String fileName = "subjects.txt";

        // act
        String result = fileReader.readFile(fileName);

        // assert
        Assert.assertTrue(result.length() > 1);
    }
}
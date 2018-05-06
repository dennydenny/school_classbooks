package dao;

import com.fasterxml.jackson.core.type.TypeReference;
import entities.Class;
import entities.Subject;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Daniel.Khaliulin on 02.05.2018.
 */
public class DAOGenericImplTest {

    @Test
    public void getAll_ShouldReturnOneOrMoreSubjects() throws Exception {
        // Тип для передачи в DAOGeneric.
        /*String fileName = "subjects.txt";
        TypeReference<List<Subject>> type = new TypeReference<List<Subject>>() {};
        DAOGeneric<Subject> dao = new DAOGenericImpl<>();

        Subject[] result = dao.getAll(new Subject[]{}.getClass(),fileName);

        Assert.assertTrue(result.size() >= 1);
        */
    }
}
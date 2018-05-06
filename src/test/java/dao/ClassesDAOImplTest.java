package dao;

import dao.interfaces.ClassesDAO;
import entities.Class;
import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Array;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Daniel.Khaliulin on 02.05.2018.
 */
public class ClassesDAOImplTest {

    @Test
    public void getAllClasses_ShouldReturnMoreThenOneClassFromDAO() throws Exception {

        List<Class> classList;
        ClassesDAO dao = new ClassesDAOImpl();

        classList = dao.getAllClasses();

        Assert.assertTrue(classList.size() >= 1);
    }
}
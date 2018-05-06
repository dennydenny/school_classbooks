package dao;

import dao.interfaces.ClassBooksDAO;
import entities.Classbook;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 * Created by Daniel.Khaliulin on 30.04.2018.
 */
public class ClassbooksDAOImplTest {

    @Test
    public void getAllClassbooks_ShouldReturnOneOrMoreClassbooksFromDAO() throws Exception {

        // arrange
        List<Classbook> classbookList;
        ClassBooksDAO dao = new ClassbooksDAOImpl();

        // act
        classbookList = dao.getAllClassbooks();

        // assert
        // Хотя бы один журнал есть в списке.
        Assert.assertTrue(classbookList.stream().count() >= 1);
    }
}
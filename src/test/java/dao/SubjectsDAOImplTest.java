package dao;

import dao.interfaces.SubjectsDAO;
import entities.Subject;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 * Created by Daniel.Khaliulin on 25.04.2018.
 */
public class SubjectsDAOImplTest {

    // Этот тест проверяет загружаются ли объекты предметов из файла.
    @Test
    public void getAllSubjects_ShouldReturnMoreThenOneSubjectFromDAO() throws Exception {
        // arrange
        List<Subject> subjectsList;
        SubjectsDAO dao = new SubjectsDAOImpl();

        // act
        subjectsList = dao.getAllSubjects();

        // assert
        // Хотя бы один предмет есть в списке.
        Assert.assertTrue(subjectsList.stream().count() >= 1);
    }
}
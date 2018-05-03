package services;

import entities.Subject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Daniel.Khaliulin on 30.04.2018.
 */
public class SubjectsServiceTest {

    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void testGetSubjects() throws Exception {

    }

    @Test
    public void getSubjectById_ShouldReturnSubjectById1() throws Exception {
        int id = 1;
        SubjectsService subjectsService = new SubjectsService();

        Subject result = subjectsService.getSubjectById(id);

        Assert.assertNotNull(result);
    }

    @Test
    public void testAddNewSubject() throws Exception {

    }
}
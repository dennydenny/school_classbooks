package services;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Daniel.Khaliulin on 30.04.2018.
 */
public class ClassServiceTest {

    // ���� ���������, ��� ������ ��������� ���������� ���� ��� ����� �������.
    @Test
    public void getAllClasses_ShouldReturnOneOrMoreClasses() throws Exception {

        ClassService classService = new ClassService();

        Assert.assertTrue(classService.getAllClasses()
                .stream()
                .count() >= 1);
    }
}
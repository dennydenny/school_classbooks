package dao;

import dao.interfaces.RatingsDAO;
import entities.Rating;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class RatingsDAOImplTest {

    @Test
    public void getAllRatings_ShouldReturnMoreThanOneRating() {
        RatingsDAO dao = new RatingsDAOImpl();
        List<Rating> result = dao.getAllRatings();
        Assert.assertTrue(result.size() >= 1);
    }
}
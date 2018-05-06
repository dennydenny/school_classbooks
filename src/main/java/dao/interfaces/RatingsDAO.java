package dao.interfaces;

import entities.Rating;

import java.util.Date;
import java.util.List;

/**
 * Created by daniel.khaliulin on 04.05.2018.
 */
public interface RatingsDAO {

    List<Rating> getAllRatings();

    boolean addNewRate(Rating rating);
    boolean deleteRate(Rating rating);
}

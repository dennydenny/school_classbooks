package dao;

import models.Class;
import models.Pupil;
import models.Rating;

import java.util.List;

public interface SchoolDataReader {

    List<Pupil> getPupilsList();

    List<Class> getClassList();

    List<Rating> getRatings();
}

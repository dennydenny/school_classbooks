package ui;

import com.fasterxml.jackson.databind.ObjectMapper;
import entities.Class;
import entities.Pupil;
import entities.Rating;
import entities.Subject;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Daniel.Khaliulin on 25.04.2018.
 */
public class Serializ {

    public static void main(String[] args) throws IOException {

        Serializ sss = new Serializ();
        sss.run4();
    }

    private File getFile() {
        return new File("data/ratings.txt");
    }

    public void run() throws IOException {
        System.out.println("Hello!");
        ObjectMapper mapper = new ObjectMapper();

        List<Class> classes = new ArrayList<Class>() {};

        for (int i = 1; i <= 4; i++) {
            Class sclass = new Class();

            sclass.setClassId(i);

            List<Pupil> pupils = new ArrayList<Pupil>() {
            };
            pupils.add(new Pupil(5 + i, "Test1"));
            pupils.add(new Pupil(6 + i, "Test2"));
            pupils.add(new Pupil(7 + i, "Test3"));

            sclass.setPupils(pupils);
            classes.add(sclass);
            System.out.println("this is " + i);
        }

        try {
            mapper.writeValue(this.getFile(), classes);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        System.out.println("Filish");
    }
    
    public void run2() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        java.lang.Class<Class[]> ss = Class[].class;
        Class[] classList = mapper.readValue(this.getFile(), Class[].class);
        for (Class cl : classList) {
            System.out.println(cl.getPupils().get(0).getName());
        }
        System.out.println("Finish");
        
    }

    public void run4() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        List<Rating> rate = new ArrayList<>();
        rate.add(new Rating(1,3,5, new Date()));
        rate.add(new Rating(1,6,2, new Date()));

        mapper.writeValue(this.getFile(), rate);
        
        Rating[] ratings = mapper.readValue(this.getFile(), Rating[].class);
        System.out.println(ratings[0].getDate());
    }
}

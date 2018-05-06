package dao;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import dao.interfaces.SubjectsDAO;
import dao.util.FileHelper;
import entities.Subject;
import org.apache.logging.log4j.LogManager;

import java.io.IOException;
import java.util.List;


/**
 * Created by daniel.khaliulin on 20.04.2018.
 */
public class SubjectsDAOImpl extends AbstractFileWriter implements SubjectsDAO {

    private static final org.apache.logging.log4j.Logger logger = LogManager.getLogger(SubjectsDAOImpl.class);
    private final String fileName = "subjects.txt";
    private List<Subject> allSubjects;

    public SubjectsDAOImpl() {

    }

    // �����, ������������ ������ ������ ���������.
    @Override
    public List<Subject> getAllSubjects() {

        // Lite-���, ����� ����������� �� ������ ���� � ��� �� ����.
        if (allSubjects != null)
        {
            return allSubjects;
        }

        ObjectMapper mapper = new ObjectMapper();
        try {
            allSubjects = mapper.readValue(FileHelper.getDataFile(fileName), new TypeReference<List<Subject>>() {});
        } catch (IOException e) {
            logger.fatal("��� �������� ������ ��������� �������� ��������� ������.", e);
            return null;
        }
        return allSubjects;
    }

    @Override
    public void addNewSubject(Subject subject) {
        this.getAllSubjects();
        allSubjects.add(subject);
        // ������� ����.
        FileHelper.cleanDataFileContent(fileName);
        // ���������� ����������� ������ � ����.
        super.writeListToFile(allSubjects, fileName);
    }
}

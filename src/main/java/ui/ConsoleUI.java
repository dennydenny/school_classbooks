package ui;

import entities.Class;
import entities.Classbook;
import entities.Rating;
import exceptions.WrongChooseException;
import entities.Subject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import services.ClassService;
import services.ClassbooksService;
import services.RatingsService;
import services.SubjectsService;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class ConsoleUI {

    private static final Logger logger = LogManager.getLogger(ConsoleUI.class);
    private static SubjectsService subjectsService = new SubjectsService();
    private static ClassbooksService classbooksService = new ClassbooksService();
    private static ClassService classService = new ClassService();
    private static RatingsService ratingsService = new RatingsService();
    private static final Scanner scanner = new Scanner(System.in);
    private static final DateFormat df = new SimpleDateFormat("dd.MM.yyyy");

    // ������ ��������� �������� ����.
    private static String [] actions;
    private static String [] ratingsActions;

    // ����� ����, ��� ������������ ������ �����-�� ���������� ������� ����.
    private static boolean isChooseMaked = false;

    public static void main(String[] args) {
        init();
        // ���������� ������� ����.
        while (!isChooseMaked) {
            try {
                showMainMenu();
            }
            catch (InputMismatchException e) {
                System.out.println("������������ ����! ����������, ����������� ����� ��� ������ ��������.");
            }
            catch (WrongChooseException wrongChoose) {
                System.out.println(wrongChoose.getMessage());
            }
        }
    }

    // �������������� ������ ��������� �������� ����.
    private static void init() {
        // ������� ����.
        actions = new String[2];
        actions[0] = "1) ������� ����� ������";
        actions[1] = "2) �������� c ��������";
        // ������ � ��������.
        ratingsActions = new String[4];
        ratingsActions[0] = "1) ��������� ������";
        ratingsActions[1] = "2) ���������� ������";
        ratingsActions[2] = "3) �������� ������";
        ratingsActions[3] = "4) ������� ������";
    }

    // ����� ����������� �������� ����.
    private static void showMainMenu() throws WrongChooseException {
        logger.debug("���������� ��������");

        System.out.println("\n====================");
        System.out.println("��� ������������ ���������� '�������� ������'");
        System.out.println("����������, �������� ������ ��������:");

        // ���������� � ������� ��������� �������� ����.
        for (String action : actions) {
            System.out.println(action);
        }

        int choose = scanner.nextInt();
        // ������������ ����� ������������.
        handleUserChoose(choose);
        isChooseMaked = false;
    }

    // ����� ��������� ����������������� ������.
    private static void handleUserChoose(int choose) throws WrongChooseException {
        if (choose < 1 || choose > actions.length) {
            logger.info("������������ ������ �������������� �������");
            throw new WrongChooseException("������ �������������� �������. ����������, �������� ����� ���������.");
        }
        // ��������, ��� ������������ ���� ����� ������.
        isChooseMaked = true;
        switch (choose) {
            // �������� �������.
            case 1:
                addNewClassbookOption();
                break;
            // ������ � ��������.
            case 2:
                workWithRatingsOption();
                break;
            default:
                throw new WrongChooseException("������ �������������� �������. ����������, �������� ����� ���������.");
        }
    }

    // �����, ��������� ���������� � �������� ���������� ������ �������.
    private static void addNewClassbookOption() {
        printClassbooksInfo();
        System.out.println("������� �������� �������� (����������� ������ ����� �������): ");
        scanner.nextLine();
        String subjectName = scanner.nextLine();
        System.out.println("������� ����� ������: ");
        int classId = scanner.nextInt();
        scanner.nextLine();
        classbooksService.addNewClassbook(classId, subjectName);
        }

    // ����� ��� ������ � ��������.
    private static void workWithRatingsOption() {
        System.out.println("��� �� ������ �������?");
        Arrays.asList(ratingsActions).stream()
                .forEach(x -> System.out.println(x));
        int option = scanner.nextInt();

        switch (option) {
            // ��������� ������.
            case 1:
                setUpRating();
                break;
            case 2:
                showRatings();
                break;
            case 3:

                break;
            case 4:
                deleteRatings();
                break;
            default:
                break;
        }
    }

    // ����� ��� ����������� ������.
    /* ��� �������� ������ ������ ����� ���� �� ������� � �������, �� ����� ������������
    ����������� �� ������ ��� ������ ������� ������. ���� �������� ������������ �������� ��������
    �� ���� UI.
     */
    private static void setUpRating() {
        // �������� �����.
        int classId = printClassEnterDialog();

        // �������� �������
        String subjectName = printSubjectEnterDialog(classId);

        // �������� ��� �������.
        String pupilName = printPupilNameEnterDialog(classId);

        // �������� ������.
        int rate = printRateEnterDialog();

        // �������� ����
        Date rateDate = printRateDateEnterDialog();

        ratingsService.addNewRate(classId, subjectName, pupilName, rate, rateDate);
    }

    // ������ ��������� ������.
    private static int printClassEnterDialog() {
        boolean classIsCorrect = false;
        int classId = 0;
        // ������� ������ �� ��� ���, ���� ������������ �� ����� ���������� �����.
        while (!classIsCorrect) {
            System.out.println("������� �����:");
            scanner.nextLine();
            classId = scanner.nextInt();

            if (classService.isClassExist(classId)) {
                // ���������, ��� ��� ������ ������ ������ ���� �� ���� ������.
                if (!classbooksService.checkIsClassbookExist(classId, null)) {
                    System.out.println("��� ��������� ������ �� ������� �� ������ ������� :(");
                }
                else {
                    classIsCorrect = true;
                }
            }
            else {
                System.out.println("��������� ������ �� ����������. ����������, ������� ����������.");
            }
        }
        return classId;
    }

    // ������ ��������� ��������.
    private static String printSubjectEnterDialog(int classId) {
        boolean subjectIsCorrect = false;
        String subjectName = new String();

        // ������� ������ �� ��� ���, ���� ������������ �� ����� ���������� �������.
        while (!subjectIsCorrect) {
            System.out.println("������� �������� ��������:");
            scanner.nextLine();
            subjectName = scanner.nextLine();

            // ��������� ������������� ��������.
            if (subjectsService.getSubjectByName(subjectName) == null) {
                System.out.println("��������� �������� �� ����������. ����������, ������� ����������.");
                continue;
            }

            // ��������� ������������� ������� ��� ����� ������ � ��������.
            if (classbooksService.checkIsClassbookExist(classId, subjectName))
            {
                subjectIsCorrect = true;
            }
            else {
                System.out.println("��� ���������� ������ �� ���������� ������� �� ����� ��������. ����������, ������� ������� �� ������������� �������.");
                printClassbooksInfo();
            }
        }
        return subjectName;
    }

    // ������ ��������� ��� �������.
    private static String printPupilNameEnterDialog(int classId) {
        boolean pupiltIsCorrect = false;
        String pupilName = new String();
        // ������� ������ �� ��� ���, ���� ������������ �� ����� ������������ ���.
        while (!pupiltIsCorrect) {
            System.out.println("������� ��� �������:");
            pupilName = scanner.nextLine();

            if (classService.isPupilExistInClass(classId, pupilName)) {
                pupiltIsCorrect = true;
            }
            else {
                System.out.println("��������� ������� �� ���������� ��� ������ �������� �����. ����������, ������� ������ ������.");
            }
        }
        return pupilName;
    }

    // ������ ��������� ������.
    private static int printRateEnterDialog() {
        boolean rateIsCorrect = false;
        int rate = 0;
        // ������� ������ �� ��� ���, ���� ������������ �� ����� ���������� ������.
        while (!rateIsCorrect) {
            System.out.println("������� ������:");
            rate = scanner.nextInt();

            if (rate < 1 || rate > 5) {
                System.out.println("������� ������������ ������. ����������, ������� ������.");
            }
            else {
                rateIsCorrect = true;
            }
        }
        return rate;
    }

    // ������ ��������� ���� ������.
    private static Date printRateDateEnterDialog() {
        boolean rateDateIsCorrect = false;
        Date rateDate = new Date();
        // ������� ������ �� ��� ���, ���� ������������ �� ����� ���������� ���� � ������ �������.
        while (!rateDateIsCorrect) {
            System.out.println("������� ���� ������ (������ dd.mm.yyyy):");
            String rawRateDate = scanner.nextLine();
            scanner.nextLine();

            // �������� ���� � ������� �������.
            try {
                //rateDate = new SimpleDateFormat("dd.MM.yyyy").parse(rawRateDate);
                SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
                //format.setTimeZone(TimeZone.getTimeZone("Europe/Samara"));
                format.setLenient(false);
                rateDate = format.parse(rawRateDate);
                rateDateIsCorrect = true;
            } catch (ParseException e) {
                System.out.println("������� ������������ ���� ��� � �������� �������. ����������, ������� ���������� ���� �������� �������");
            }
        }
        return rateDate;
    }

    // ����� ��� ������ ������������ ��������.
    private static void printClassbooksInfo() {
        System.out.println("������� ������ ��������:");
        for(Map.Entry<Classbook, Subject> entry : classbooksService.getClassbooks().entrySet()) {
            System.out.format(
                    String.format("����� %d, ������� %s.",
                            entry.getKey().getClassId(),
                            entry.getValue().getName()));
            System.out.println();
        }
    }

    // ����� ��� ��������� ������������ ������.
    private static void showRatings() {

        // �������� �����.
        int classId = printClassEnterDialog();

        // �������� �������
        String subjectName = printSubjectEnterDialog(classId);

        // �������� ��� �������.
        String pupilName = printPupilNameEnterDialog(classId);

        // �������� ������������� �������
        int pupilId = classService.getAllClasses().stream()
                // ��������� �� �� ������.
                .filter(x -> x.getClassId() == classId)
                .collect(Collectors.toList())
                .get(0)
                // ��������� ������ �������� ������ � ������� �� �������..
                .getPupils().stream()
                    .filter(p -> p.getName().equals(pupilName))
                    .collect(Collectors.toList())
                    .get(0)
                    .getpupilId();

        // �������� ������������� �������.
        int classbookId = classbooksService.getClassbooks().entrySet().stream()
                .filter(x -> x.getValue().getName().equals(subjectName) && x.getKey().getClassId() == classId)
                .collect(Collectors.toList())
                .get(0)
                .getKey()
                .getId();

        // ������� ������. ��������� ������ ������ �� ������� � �� �������.
        List<Rating> ratings = ratingsService.getAllRatings().stream()
                .filter(x -> x.getClassbookId() == classbookId
                        && x.getPupilId() == pupilId)
                .collect(Collectors.toList());
        if (ratings.size() == 0) {
            System.out.println("� ����� ������� ��� ������ �� ��������.");
        }
        else {
            System.out.println("������ ������:");
            ratings.stream().forEach(r -> System.out.format("\n����: %s ������: %d", df.format(r.getDate()), r.getMark()));
        }
    }

    // ����� ��� �������� ������.
    private static void deleteRatings() {
        // �������� �����.
        int classId = printClassEnterDialog();

        // �������� �������
        String subjectName = printSubjectEnterDialog(classId);

        // �������� ��� �������.
        String pupilName = printPupilNameEnterDialog(classId);

        // �������� ���� ������.
        Date rateDate = printRateDateEnterDialog();

        // ������� � ������.
        ratingsService.deleteRating(classId, subjectName, pupilName, rateDate);
    }
}
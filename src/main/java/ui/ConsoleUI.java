package ui;

import entities.Classbook;
import exceptions.WrongChooseException;
import entities.Subject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import services.ClassbooksService;
import services.SubjectsService;

import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;

public class ConsoleUI {

    private static final Logger logger = LogManager.getLogger(ConsoleUI.class);
    private static SubjectsService subjectsService = new SubjectsService();
    private static ClassbooksService classbooksService = new ClassbooksService();
    private static final Scanner scanner = new Scanner(System.in);

    // ������ ��������� �������� ����.
    private static String [] actions;

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
        actions = new String[2];
        actions[0] = "1) ������� ����� ������";
        actions[1] = "2) �������� c ��������";
    }

    // ����� ����������� �������� ����.
    private static void showMainMenu() throws WrongChooseException {
        logger.debug("���������� ��������");

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
            throw new WrongChooseException("������ �������������� �������. ����������, �������� ����� ���������.12");
        }
        // ��������, ��� ������������ ���� ����� ������.
        isChooseMaked = true;
        switch (choose) {
            case 1:
                AddNewClassbookOption();
                break;
            case 2:
                break;
            default:
                break;
        }
    }

    // �����, ��������� ���������� � �������� ���������� ������ �������.
    private static void AddNewClassbookOption() {
        System.out.println("������� ������ ��������:");

        for(Map.Entry<Classbook, Subject> entry : classbooksService.getClassbooks().entrySet()) {
            System.out.format(
                    String.format("����� %d, ������� %s.",
                            entry.getKey().getClassId(),
                            entry.getValue().getName()));
            System.out.println();
        }

            System.out.println("������� �������� �������� (����������� ������ ����� �������): ");
            scanner.nextLine();
            String subjectName = scanner.nextLine();
            System.out.println("������� ����� ������: ");
            int classId = scanner.nextInt();
            scanner.nextLine();
            classbooksService.addNewClassbook(classId, subjectName);
        }
}
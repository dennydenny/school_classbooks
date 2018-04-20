package ui;

import exceptions.WrongChooseException;
import models.Subject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import services.SubjectsService;

import java.util.InputMismatchException;
import java.util.Scanner;

public class ConsoleUI {

    private static final Logger logger = LogManager.getLogger(ConsoleUI.class);

    // ������ ��������� �������� ����.
    private static String [] actions;

    // ����� ����, ��� ������������ ������ �����-�� ���������� ������� ����.
    private static boolean isChooseMaked = false;

    public static void main(String[] args) {
        init();
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

        Scanner scanner = new Scanner(System.in);
        int choose = scanner.nextInt();
        handleUserChoose(choose);
    }

    // ����� ��������� ����������������� ������.
    private static void handleUserChoose(int choose) throws WrongChooseException {
        if (choose < 1 || choose > actions.length) {
            logger.info("������������ ������ �������������� �������");
            throw new WrongChooseException("������ �������������� �������. ����������, �������� ����� ���������.12");
        }
        isChooseMaked = true;
        switch (choose) {
            case 1:
                break;
            case 2:
                SubjectsService subjectsService = new SubjectsService();
                for (Subject sub : subjectsService.getSubjects()) {
                    System.out.println(sub);
                }
                break;
            default:
                break;
        }
    }
}
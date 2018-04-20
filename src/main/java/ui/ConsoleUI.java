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

    // Массив возможных действий меню.
    private static String [] actions;

    // Метка того, что пользователь выбрал какой-то корректный вариант меню.
    private static boolean isChooseMaked = false;

    public static void main(String[] args) {
        init();
        while (!isChooseMaked) {
            try {
                showMainMenu();
            }
            catch (InputMismatchException e) {
                System.out.println("Некорректный ввод! Пожалуйста, используйте цифры для выбора варианта.");
            }
            catch (WrongChooseException wrongChoose) {
                System.out.println(wrongChoose.getMessage());
            }
        }
    }

    // Инициализируем массив возможных действий меню.
    private static void init() {
        actions = new String[2];
        actions[0] = "1) Создать новый журнал";
        actions[1] = "2) Работать c оценками";
    }

    // Метод отображения главного меню.
    private static void showMainMenu() throws WrongChooseException {
        logger.debug("Приложение запущено");

        System.out.println("Вас приветствует приложение 'Школьный журнал'");
        System.out.println("Пожалуйста, выберите нужное действие:");

        // Перебираем и выводим возможные варианты меню.
        for (String action : actions) {
            System.out.println(action);
        }

        Scanner scanner = new Scanner(System.in);
        int choose = scanner.nextInt();
        handleUserChoose(choose);
    }

    // Метод обработки пользовательского выбора.
    private static void handleUserChoose(int choose) throws WrongChooseException {
        if (choose < 1 || choose > actions.length) {
            logger.info("Пользователь выбрал несуществующий вариант");
            throw new WrongChooseException("Выбран несуществующий вариант. Пожалуйста, выберите среди возможных.12");
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
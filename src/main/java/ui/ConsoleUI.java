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

    // Массив возможных действий меню.
    private static String [] actions;

    // Метка того, что пользователь выбрал какой-то корректный вариант меню.
    private static boolean isChooseMaked = false;

    public static void main(String[] args) {
        init();
        // Показываем главное меню.
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

        int choose = scanner.nextInt();
        // Обрабатываем выбор пользователя.
        handleUserChoose(choose);
        isChooseMaked = false;
    }

    // Метод обработки пользовательского выбора.
    private static void handleUserChoose(int choose) throws WrongChooseException {
        if (choose < 1 || choose > actions.length) {
            logger.info("Пользователь выбрал несуществующий вариант");
            throw new WrongChooseException("Выбран несуществующий вариант. Пожалуйста, выберите среди возможных.12");
        }
        // Отмечаем, что пользователь свой выбор сделал.
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

    // Метол, выводящий информацию о варианте добавления нового журнала.
    private static void AddNewClassbookOption() {
        System.out.println("Текущий список журналов:");

        for(Map.Entry<Classbook, Subject> entry : classbooksService.getClassbooks().entrySet()) {
            System.out.format(
                    String.format("Класс %d, предмет %s.",
                            entry.getKey().getClassId(),
                            entry.getValue().getName()));
            System.out.println();
        }

            System.out.println("Введите название предмета (допускается ввести новый предмет): ");
            scanner.nextLine();
            String subjectName = scanner.nextLine();
            System.out.println("Введите номер класса: ");
            int classId = scanner.nextInt();
            scanner.nextLine();
            classbooksService.addNewClassbook(classId, subjectName);
        }
}